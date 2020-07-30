package generation;

import java.util.List;

public class AutomarkableQuestion extends Question {
    private String language;
    private String url;

    public AutomarkableQuestion(String language, String url, String questionText, boolean isExamQuestion, boolean automarkable, List<Question> subQuestionList, boolean isSubQuestion) {
        super(questionText, isExamQuestion, automarkable, subQuestionList, isSubQuestion);
        this.language = language;
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }
}
