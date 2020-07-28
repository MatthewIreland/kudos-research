package parsing;

import java.util.List;

public class ParseResult {

    private final WorkMetadata metadata;
    private final List<Automarkable> automarkableList;

    public ParseResult(WorkMetadata metadata, List<Automarkable> automarkableList) {
        this.metadata = metadata;
        this.automarkableList = automarkableList;
    }

    public WorkMetadata getMetadata() {
        return metadata;
    }

    public List<Automarkable> getAutomarkableList() {
        return automarkableList;
    }
}
