package parsing;

public class Automarkable {

    private final String questionId;
    private final String language;
    private final String markerUrl;
    private final int markerPort;
    private final String contents;

    public Automarkable(String questionId, String language, String markerUrl, int markerPort, String contents) {
        this.questionId = questionId;
        this.language = language;
        this.markerUrl = markerUrl;
        this.markerPort = markerPort;
        this.contents = contents;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getLanguage() {
        return language;
    }

    public String getMarkerUrl() {
        return markerUrl;
    }

    public int getMarkerPort() {
        return markerPort;
    }

    public String getContents() {
        return contents;
    }
}
