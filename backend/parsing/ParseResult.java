package parsing;

import java.util.List;

public class ParseResult {

    private final WorkMetadata metadata;
    private final List<AutomarkableSection> automarkableList;

    public ParseResult(WorkMetadata metadata, List<AutomarkableSection> automarkableList) {
        this.metadata = metadata;
        this.automarkableList = automarkableList;
    }

    public WorkMetadata getMetadata() {
        return metadata;
    }

    public List<AutomarkableSection> getAutomarkableList() {
        return automarkableList;
    }
}
