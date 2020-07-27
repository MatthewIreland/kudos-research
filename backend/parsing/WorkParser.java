package parsing;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WorkParser {

    // Magic constants identifying the different groups in the regular expression.
    private static final String LANGUAGE_GROUP = "language", EXERCISE_ID_GROUP = "exerciseId", CONTENTS_GROUP = "contents";

    // A regular expression to find each `automarkable` environment and extract its arguments and contents.
    private static final Pattern automarkableRegex;

    static {
        // All of that really could be one line but I found it easier to read like this.
        String languageArg = "\\{(?<" + LANGUAGE_GROUP + ">.*?)\\}";
        String exerciseArg = "\\{(?<" + EXERCISE_ID_GROUP + ">.*?)\\}";

        String pattern =
                "\\\\begin\\{automarkable\\}" + languageArg + exerciseArg
                        + "(?<" + CONTENTS_GROUP + ">.*?)" +
                "\\\\end\\{automarkable\\}";

        automarkableRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    private final Path workFile;

    public WorkParser(Path workFile) {
        this.workFile = workFile;
    }

    public void parseAutomarkable() throws IOException {

        // Read the work file and join the lines into a single string.
        String contents = Files.lines(workFile).collect(Collectors.joining("\n"));

        // Use the regex to find all instances of `automarkable` environments
        Matcher matcher = automarkableRegex.matcher(contents);
        while (matcher.find()) {
            // For now, print out the parameters and the contents
            System.out.println("Language: " + matcher.group(LANGUAGE_GROUP));
            System.out.println("Exercise ID: " + matcher.group(EXERCISE_ID_GROUP));
            System.out.println(matcher.group(CONTENTS_GROUP));

            // Create a JSON object for the JSON-RPC call to the automarker
            JSONObject request = createRequestObject(matcher.group(EXERCISE_ID_GROUP), matcher.group(CONTENTS_GROUP));

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
