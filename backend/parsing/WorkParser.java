package parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static parsing.WorkParserConstants.*;

public class WorkParser {

    private final Path workFile;

    public WorkParser(Path workFile) {
        this.workFile = workFile;
    }

    public ParseResult parse() throws ParsingException {

        String contents;

        try {
            // Read the work file and join the lines into a single string.
            contents = Files.lines(workFile).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new ParsingException("IO Exception encountered while parsing", e);
        }

        WorkMetadata metadata = parseMetadata(contents);

        List<AutomarkableSection> automarkables = parseAutomarkable(contents);

        return new ParseResult(metadata, automarkables);
    }

    private WorkMetadata parseMetadata(String input) throws ParsingException {

        Matcher crsidMatcher = crsidRegex.matcher(input);

        if (crsidMatcher.find()) {
            String crsid = crsidMatcher.group(CRSID_GROUP);

            WorkMetadata metadata = new WorkMetadata(crsid);

            if (crsidMatcher.find()) {
                throw new ParsingException("Found more than one CRSID");
            }

            return metadata;
        } else {
            throw new ParsingException("Couldn't find student CRSID");
        }
    }

    private List<AutomarkableSection> parseAutomarkable(String input) throws ParsingException {
        List<AutomarkableSection> output = new LinkedList<>();

        // Use the regex to find all instances of `automarkable` environments.
        Matcher automarkableMatcher = automarkableRegex.matcher(input);

        while (automarkableMatcher.find()) {
            String language = automarkableMatcher.group(LANGUAGE_GROUP);
            String host = automarkableMatcher.group(MARKER_HOST_GROUP);
            int port;
            try {
                port = Integer.parseInt(automarkableMatcher.group(MARKER_PORT_GROUP));
            } catch (NumberFormatException e) {
                throw new ParsingException("Port value isn't a valid integer", e);
            }
            String uuid = automarkableMatcher.group(UUID_GROUP);
            String contents = automarkableMatcher.group(CONTENTS_GROUP);

            AutomarkableSection automarkable = new AutomarkableSection(language, host, port, uuid, contents);
            output.add(automarkable);
        }

        return output;
    }
}
