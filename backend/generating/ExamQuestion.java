package generation;

public class ExamQuestion extends Question{

    int year;
    int paper;
    int questionNumber;

    public ExamQuestion(String questionText, int questionID, boolean isExamQuestion, boolean automarkable, boolean isSubQuestion, int year, int paper, int questionNumber) {
        super(questionText, questionID, isExamQuestion, automarkable, isSubQuestion);
        this.year = year;
        this.paper = paper;
        this.questionNumber = questionNumber;
    }

    public int getYear() {
        return year;
    }

    public int getPaper(){
        return paper;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }
}
