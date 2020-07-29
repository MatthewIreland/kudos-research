package parsing;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkSender {

    public WorkSender() {

    }

    public void sendWorkToAutomarker(ParseResult result) throws IOException {

        WorkMetadata metadata = result.getMetadata();
        List<Automarkable> automarkables = result.getAutomarkableList();

        for (Automarkable automarkable : automarkables) {
            String language = automarkable.getLanguage();
            String markerUrl = automarkable.getMarkerUrl();
            String uuid = automarkable.getUuid();
            String contents = automarkable.getContents();

            // For now, print out the parameters and the contents
            System.out.println("Language: " + language);
            System.out.println("Marker URL: " + markerUrl);
            System.out.println("UUID: " + uuid);
            System.out.println(contents);

            JSONObject response = callAutomarker(metadata, markerUrl, uuid, contents);
            System.out.println(response);

            // TODO: do something with the response.
        }
    }

    private JSONObject callAutomarker(WorkMetadata metadata, String markerUrl, String uuid, String automarkableContents) throws IOException {

        String requestId = UUID.randomUUID().toString();

        // Create a JSON object for the JSON-RPC call to the automarker
        JSONObject request = createRequestObject(metadata, uuid, automarkableContents, requestId);

        // Call the automarker
        JSONObject response = doRpc(markerUrl, request);

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

    private JSONObject doRpc(String markerUrl, JSONObject request) throws IOException {

        URL url = new URL(markerUrl);
        if (url.getProtocol().matches("https?")) {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            try (OutputStream output = connection.getOutputStream()) {
                byte[] body = request.toString().getBytes(StandardCharsets.UTF_8);
                output.write(body);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode < 200 || responseCode >= 400) {
                throw new RuntimeException(); // TODO
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String body = reader.lines().collect(Collectors.joining());

                return new JSONObject(body);
            }
        } else {
            throw new RuntimeException("Invalid protocol (" + url.getProtocol() + "), expected http or https."); // TODO
        }
    }
}
