package parsing;

public class Automarkable {

    private final String language;
    private final String markerUrl;
    private final String uuid;
    private final String contents;

    public Automarkable(String language, String markerUrl, String uuid, String contents) {
        this.language = language;
        this.markerUrl = markerUrl;
        this.uuid = uuid;
        this.contents = contents;
    }

    public String getLanguage() {
        return language;
    }

    public String getMarkerUrl() {
        return markerUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContents() {
        return contents;
    }
}
