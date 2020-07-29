package parsing;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorkSender {

    public WorkSender() {

    }

    public void sendWorkToAutomarker(ParseResult result) throws IOException {

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

            JSONObject response = callAutomarker(markerHost, markerPort, metadata, uuid, contents);
            System.out.println(response);

            // TODO: do something with the response.
        }
    }

    private JSONObject callAutomarker(String host, int port, WorkMetadata metadata, String uuid, String automarkableContents) throws IOException {

        String requestId = UUID.randomUUID().toString();

        // Create a JSON object for the JSON-RPC call to the automarker
        JSONObject request = createRequestObject(metadata, uuid, automarkableContents, requestId);

        // Call the automarker
        JSONObject response = doRpc(host, port, request);

        if (!Objects.equals(response.opt("jsonrpc"), "2.0")) {
            throw new RuntimeException(); // TODO
        }

        if (response.has("error")) {
            JSONObject error = response.getJSONObject("error");

            int code = error.getInt("code");
            String message = error.getString("message");

            throw new RuntimeException("JSON-RPC error (code " + code + "): " + message); // TODO
        }

        if (response.has("result")) {
            if (!Objects.equals(response.opt("id"), requestId)) {
                throw new RuntimeException(); // TODO
            }

            return response.getJSONObject("result");
        }

        throw new RuntimeException("Missing result field in response."); // TODO
    }

    private JSONObject createRequestObject(WorkMetadata metadata, String uuid, String automarkableContents, String requestId) {

        JSONObject params = new JSONObject();

        params.put("studentCrsid", metadata.getStudentCrsid());
        params.put("uuid", uuid);
        params.put("automarkableContents", automarkableContents);

        JSONObject request = new JSONObject();

        request.put("jsonrpc", "2.0");
        request.put("method", "automark");
        request.put("params", params);
        request.put("id", requestId);

        return request;
    }

    private JSONObject doRpc(String host, int port, JSONObject request) throws IOException {

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(request.toString());
            String responseString = in.readLine();

            return new JSONObject(responseString);
        }
    }
}
