package parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WorkParser {

    // Magic constants identifying the different groups in the regular expression.
    private static final String PREFIX_GROUP = "prefix", QUESTION_ID_GROUP = "questionId", CONTENTS_GROUP = "contents",
            LANGUAGE_GROUP = "language", MARKER_URL_GROUP = "url", MARKER_PORT_GROUP = "port";

    // A regular expression to find `question`, `subquestion`, etc. environments, and extract args and contents.
    private static final Pattern questionRegex;

    static {
        // All of that really could be one line but I found it easier to read like this.
        String questionIdArg = "\\{(?<" + QUESTION_ID_GROUP + ">.*?)\\}";

        String pattern =
                "\\\\begin\\{(?<" + PREFIX_GROUP + ">(?:sub)*)question\\}" + questionIdArg
                        + "(?<" + CONTENTS_GROUP + ">.*?)" +
                "\\\\end\\{\\k<" + PREFIX_GROUP + ">question\\}";

        questionRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    // A regular expression to find `automarkable` environments, and extract args and contents.
    private static final Pattern automarkableRegex;

    static {
        String languageArg = "\\{(?<" + LANGUAGE_GROUP + ">.*?)\\}";
        String urlArg = "\\{(?<" + MARKER_URL_GROUP + ">.*?)\\}";
        String portArg = "\\{(?<" + MARKER_PORT_GROUP + ">.*?)\\}";

        String pattern =
                "\\\\begin\\{automarkable\\}" + languageArg + urlArg + portArg
                        + "(?<" + CONTENTS_GROUP + ">.*?)" +
                "\\\\end\\{automarkable\\}";

        automarkableRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    private final Path workFile;

    public WorkParser(Path workFile) {
        this.workFile = workFile;
    }

    public ParseResult parseAutomarkable() throws IOException {

        // Read the work file and join the lines into a single string.
        String contents = Files.lines(workFile).collect(Collectors.joining("\n"));

        WorkMetadata metadata = parseMetadata(contents);

        List<Automarkable> automarkables = new LinkedList<>();

        // Go through the question hierarchy and parse automarkable environments.
        parseQuestions(contents, "", automarkables);

        return new ParseResult(metadata, automarkables);
    }

    private WorkMetadata parseMetadata(String input) {
        // TODO
        return new WorkMetadata("spqr1", "FoCS", 1);
    }

    private void parseQuestions(String input, String currentQuestionId, List<Automarkable> output) {

        // Check for (sub)*questions in the input.
        boolean hasQuestions = false;

        Matcher questionMatcher = questionRegex.matcher(input);
        while (questionMatcher.find()) {
            hasQuestions = true;

            String questionId = questionMatcher.group(QUESTION_ID_GROUP);
            String questionContents = questionMatcher.group(CONTENTS_GROUP);

            // Generates question ids like 1, 2.a, 3.b.i, etc.
            String nextQuestionId = currentQuestionId.isEmpty() ? questionId : currentQuestionId + "." + questionId;

            // Recursively parse sub-questions.
            parseQuestions(questionContents, nextQuestionId, output);
        }

        // If there are no questions in the input, look for an automarkable environment.
        if (!hasQuestions) {
            Matcher automarkableMatcher = automarkableRegex.matcher(input);

            if (automarkableMatcher.find()) {

                String language = automarkableMatcher.group(LANGUAGE_GROUP);
                String url = automarkableMatcher.group(MARKER_URL_GROUP);
                int port = Integer.parseInt(automarkableMatcher.group(MARKER_PORT_GROUP));
                String contents = automarkableMatcher.group(CONTENTS_GROUP);

                Automarkable automarkable = new Automarkable(currentQuestionId, language, url, port, contents);

                output.add(automarkable);
            }

            if (automarkableMatcher.find()) {
                System.err.println("Warning: found more than one automarkable in question " + currentQuestionId);
                // TODO: should this be an error?
            }
        }
    }
}
