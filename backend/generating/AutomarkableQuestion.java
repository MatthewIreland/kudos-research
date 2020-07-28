package generation;

public class AutomarkableQuestion extends Question {
    private String language;
    private String url;
    private int port;

    public AutomarkableQuestion(String language, String url, int port, String questionText, int questionNumber, boolean isExamQuestion, boolean automarkable, boolean isSubQuestion) {
        super(questionText, questionNumber, isExamQuestion, automarkable, isSubQuestion);
        this.language = language;
        this.url = url;
        this.port = port;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    //\begin{automarkable}{ocaml}{https://automarker.example.com}{1234}
}
