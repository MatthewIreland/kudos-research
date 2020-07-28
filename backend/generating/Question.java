package generation;

public class Question{
    private String questionText;
    private int questionID;
    private boolean isExamQuestion;
    private boolean automarkable;
    private boolean isSubQuestion;

    public Question(String questionText, int questionID, boolean isExamQuestion, boolean automarkable, boolean isSubQuestion){
        this.questionText = questionText;
        this.questionID = questionID;
        this.isExamQuestion = isExamQuestion;
        this.automarkable = automarkable;
        this.isSubQuestion = isSubQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean getAutomarkable(){
        return automarkable;
    }

    public boolean getExamQuestion() {
        return isExamQuestion;
    }

    public int getQuestionNumber() {
        return questionID;
    }

    public boolean isSubQuestion() {
        return isSubQuestion;
    }
}
