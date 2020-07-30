package parsing;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws URISyntaxException, ParsingException, SubmissionException {
        // Get a path to "example.tex" relative to the current file.
        Path file = Paths.get(Main.class.getResource("example.tex").toURI());

        WorkParser parser = new WorkParser(file);
        ParseResult result = parser.parse();

        WorkSender sender = new WorkSender();
        sender.sendWorkToAutomarker(result);
    }
}
