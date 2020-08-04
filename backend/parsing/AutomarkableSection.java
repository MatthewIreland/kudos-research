package parsing;

public class AutomarkableSection {

    private final String language;
    private final String markerHost;
    private final int markerPort;
    private final String uuid;
    private final String contents;

    public AutomarkableSection(String language, String markerHost, int markerPort, String uuid, String contents) {
        this.language = language;
        this.markerHost = markerHost;
        this.markerPort = markerPort;
        this.uuid = uuid;
        this.contents = contents;
    }

    public String getLanguage() {
        return language;
    }

    public String getMarkerHost() {
        return markerHost;
    }

    public int getMarkerPort() {
        return markerPort;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContents() {
        return contents;
    }
}
