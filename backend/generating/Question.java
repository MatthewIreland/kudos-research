package generation;

import java.util.List;

public class Question{
    private String questionText;
    private int questionID;
    private boolean isExamQuestion;
    private boolean automarkable;
    private List<Question> subQuestionList;
    private boolean hasSubQuestion;
    private boolean isSubQuestion;

    public Question(String questionText, int questionID, boolean isExamQuestion, boolean automarkable, List<Question> subQuestionList, boolean isSubQuestion){
        this.questionText = questionText;
        this.questionID = questionID;
        this.isExamQuestion = isExamQuestion;
        this.automarkable = automarkable;
        this.subQuestionList = subQuestionList;
        this.isSubQuestion = isSubQuestion;

        if (subQuestionList.size() > 1){
            this.hasSubQuestion=true;
        }
        else {
            this.hasSubQuestion=false;
        }
    }

    public String getQuestionText() { return questionText; }

    public boolean isAutomarkable(){ return automarkable; }

    public boolean isExamQuestion() { return isExamQuestion; }

    public int getQuestionNumber() { return questionID; }

    public boolean hasSubQuestion() { return hasSubQuestion; }

    public List<Question> getSubQuestionList() { return subQuestionList; }

    public boolean isSubQuestion() { return isSubQuestion; }

}
