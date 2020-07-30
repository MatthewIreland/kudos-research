package parsing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WorkSender {

    private static final String SUBMIT_METHOD = "submit_task_for_automarking", RETRIEVE_METHOD = "retrieve_automarking_results";

    public WorkSender() {

    }

    public void sendWorkToAutomarker(ParseResult result) throws SubmissionException {

        WorkMetadata metadata = result.getMetadata();
        List<Automarkable> automarkables = result.getAutomarkableList();

        for (Automarkable automarkable : automarkables) {
            String language = automarkable.getLanguage();
            String markerHost = automarkable.getMarkerHost();
            int markerPort = automarkable.getMarkerPort();
            String uuid = automarkable.getUuid();
            String contents = automarkable.getContents();

            // For now, print out the parameters and the contents
            System.out.println("Language: " + language);
            System.out.println("Marker host and port: " + markerHost + ":" + markerPort);
            System.out.println("UUID: " + uuid);
            System.out.println(contents);

            callAutomarker(markerHost, markerPort, metadata, uuid, contents);
        }
    }

    private void callAutomarker(String host, int port, WorkMetadata metadata, String uuid, String automarkableContents) throws SubmissionException {

        long requestId = 1; // TODO increment id when appropriate

        // Create a JSON object for the JSON-RPC call to the automarker
        JSONObject request = createRequestObject(metadata, uuid, automarkableContents, requestId);

        // Call the automarker
        JSONObject response = doRpc(host, port, request);

        // Validate the response
        validateResponse(response, requestId);
    }

    private void validateResponse(JSONObject response, long requestId) throws SubmissionException {

        // Check JSON-RPC version
        if (!Objects.equals(response.opt("jsonrpc"), "2.0")) {
            throw new SubmissionException("Expected 'jsonrpc' field with value '2.0', got '" + response.opt("jsonrpc") + "'.");
        }

        // Check for JSON-RPC errors
        if (response.has("error")) {
            int code;
            String message;

            try {
                JSONObject error = response.getJSONObject("error");

                code = error.getInt("code");
                message = error.getString("message");
            } catch (JSONException e) {
                throw new SubmissionException("Couldn't parse 'error' field", e);
            }

            throw new SubmissionException("JSON-RPC error (code " + code + "): " + message);
        }

        // Check the result field exists (since there's no error at this point)
        if (!response.has("result")) {
            throw new SubmissionException("Missing 'result' field in response.");
        }

        // Check the id field exists
        if (!response.has("id")) {
            throw new SubmissionException("Missing 'id' field in response.");
        }

        // And has the correct value
        long responseId;
        try {
            responseId = response.getLong("id");
        } catch (JSONException e) {
            throw new SubmissionException("Couldn't parse 'id' field", e);
        }

        if (responseId != requestId) {
            throw new SubmissionException("Mismatched 'id' in response: expected '" + requestId + "', got '" + responseId + "'.");
        }
    }

    private JSONObject createRequestObject(WorkMetadata metadata, String uuid, String automarkableContents, long requestId) {

        // TODO passphrase and salt should be marker-specific and stored in a database
        String token = computeToken("kudos", "salt", requestId, System.currentTimeMillis());

        JSONObject params = new JSONObject();

        params.put("token", token);
        params.put("studentCrsid", metadata.getStudentCrsid());
        params.put("uuid", uuid);
        params.put("automarkableContents", automarkableContents);

        JSONObject request = new JSONObject();

        request.put("jsonrpc", "2.0");
        request.put("method", SUBMIT_METHOD);
        request.put("params", params);
        request.put("id", requestId);

        return request;
    }

    private JSONObject doRpc(String host, int port, JSONObject request) throws SubmissionException {

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(request.toString());
            String responseString = in.readLine();

            return new JSONObject(responseString);
        } catch (IOException e) {
            throw new SubmissionException("IO Exception encountered during RPC.", e);
        } catch (JSONException e) {
            throw new SubmissionException("JSON Exception encountered while parsing RPC response.", e);
        }
    }

    private String computeToken(String passphrase, String salt, long requestId, long timestamp) {

        String str = passphrase + salt + requestId + timestamp;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

            return new String(Base64.getEncoder().encode(hash), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            // This should almost certainly not be caught; it means the SHA-256 algorithm is missing.
            throw new RuntimeException(e);
        }
    }
}
