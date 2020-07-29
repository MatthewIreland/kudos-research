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
    private static final String LANGUAGE_GROUP = "language", MARKER_URL_GROUP = "url", UUID_GROUP = "uuid",CONTENTS_GROUP = "contents";

    // A regular expression to find `automarkable` environments, and extract args and contents.
    private static final Pattern automarkableRegex;

    static {
        String languageArg = "\\{(?<" + LANGUAGE_GROUP + ">.*?)\\}";
        String urlArg = "\\{(?<" + MARKER_URL_GROUP + ">.*?)\\}";
        String uuidArg = "\\{(?<" + UUID_GROUP + ">.*?)\\}";

        String pattern =
                "\\\\begin\\{automarkable\\}" + languageArg + urlArg + uuidArg
                        + "(?<" + CONTENTS_GROUP + ">.*?)" +
                "\\\\end\\{automarkable\\}";

        automarkableRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    private static final String CRSID_GROUP = "crsid";

    private static final Pattern crsidRegex;

    static {
        String pattern = "\\\\newcommand\\{\\\\studentemail\\}\\{(?<" + CRSID_GROUP + ">\\w+?)@cam\\.ac\\.uk\\}";

        crsidRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    private final Path workFile;

    public WorkParser(Path workFile) {
        this.workFile = workFile;
    }

    public ParseResult parse() throws IOException {

        // Read the work file and join the lines into a single string.
        String contents = Files.lines(workFile).collect(Collectors.joining("\n"));

        WorkMetadata metadata = parseMetadata(contents);

        List<Automarkable> automarkables = parseAutomarkable(contents);

        return new ParseResult(metadata, automarkables);
    }

    private WorkMetadata parseMetadata(String input) {

        Matcher crsidMatcher = crsidRegex.matcher(input);

        if (crsidMatcher.find()) {
            String crsid = crsidMatcher.group(CRSID_GROUP);

            WorkMetadata metadata = new WorkMetadata(crsid);

            if (crsidMatcher.find()) {
                throw new RuntimeException("Found more than one CRSID"); // TODO
            }

            return metadata;
        } else {
            throw new RuntimeException("Couldn't find student CRSID"); // TODO
        }
    }

    private List<Automarkable> parseAutomarkable(String input) {
        List<Automarkable> output = new LinkedList<>();

        // Use the regex to find all instances of `automarkable` environments.
        Matcher automarkableMatcher = automarkableRegex.matcher(input);

        while (automarkableMatcher.find()) {
            String language = automarkableMatcher.group(LANGUAGE_GROUP);
            String url = automarkableMatcher.group(MARKER_URL_GROUP);
            String uuid = automarkableMatcher.group(UUID_GROUP);
            String contents = automarkableMatcher.group(CONTENTS_GROUP);

            Automarkable automarkable = new Automarkable(language, url, uuid, contents);
            output.add(automarkable);
        }

        return output;
    }
}
