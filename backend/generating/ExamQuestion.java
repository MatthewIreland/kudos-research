package generation;

import java.util.List;

public class ExamQuestion extends Question{

    int year;
    int paper;
    int questionNumber;

    public ExamQuestion(String questionText, int questionID, boolean isExamQuestion, boolean automarkable, List<Question> subQuestionList, boolean isSubQuestion, int year, int paper, int questionNumber) {
        super(questionText, questionID, isExamQuestion, automarkable, subQuestionList, isSubQuestion);
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
