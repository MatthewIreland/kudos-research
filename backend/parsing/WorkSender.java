package parsing;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

            // Create a JSON object for the JSON-RPC call to the automarker
            JSONObject request = createRequestObject(metadata, uuid, contents);

            // Call the automarker
            JSONObject response = doRpc(markerUrl, request);
            System.out.println(response);

            // TODO: do something with the response.
        }
    }

    private JSONObject createRequestObject(WorkMetadata metadata, String uuid, String automarkableContents) {

        JSONObject params = new JSONObject();

        params.put("metadata", metadata.toJson());
        params.put("uuid", uuid);
        params.put("automarkableContents", automarkableContents);

        JSONObject request = new JSONObject();

        request.put("jsonrpc", "2.0");
        request.put("method", "automark");
        request.put("params", params);
        request.put("id", UUID.randomUUID().toString());

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
