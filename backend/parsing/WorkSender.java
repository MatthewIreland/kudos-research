package parsing;

import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class WorkSender {

    public WorkSender() {

    }

    public void sendWorkToAutomarker(ParseResult result) {

        WorkMetadata metadata = result.getMetadata();
        List<Automarkable> automarkables = result.getAutomarkableList();

        for (Automarkable automarkable : automarkables) {
            // For now, print out the parameters and the contents
            System.out.println("Question: " + automarkable.getQuestionId());
            System.out.println("Language: " + automarkable.getLanguage());
            System.out.println("Marker: " + automarkable.getMarkerUrl() + ":" + automarkable.getMarkerPort());
            System.out.println(automarkable.getContents());

            // Create a JSON object for the JSON-RPC call to the automarker
            JSONObject request = createRequestObject(automarkable.getQuestionId(), automarkable.getContents());

            // Call the automarker
            JSONObject response = doRpc(request);

            // TODO: do something with the response.
        }
    }

    private JSONObject createRequestObject(String exerciseId, String automarkableContents) {

        JSONObject params = new JSONObject();

        params.put("exerciseId", exerciseId);
        params.put("automarkableContents", automarkableContents);

        JSONObject request = new JSONObject();

        request.put("jsonrpc", "2.0");
        request.put("method", "automark");
        request.put("params", params);
        request.put("id", UUID.randomUUID().toString());

        return request;
    }

    private JSONObject doRpc(JSONObject request) {
        // TODO: get the server and port number, and send the JSON, then return the response.
        return null;
    }
}
