package generation;

import java.util.List;

public class ExamQuestion extends Question{

    int year;
    int paper;
    int examQuestionNumber;

    public ExamQuestion(String questionText, boolean isExamQuestion, boolean automarkable, List<Question> subQuestionList, boolean isSubQuestion, int year, int paper, int examQuestionNumber) {
        super(questionText, isExamQuestion, automarkable, subQuestionList, isSubQuestion);
        this.year = year;
        this.paper = paper;
        this.examQuestionNumber = examQuestionNumber;
    }

    public int getYear() {
        return year;
    }

    public int getPaper(){
        return paper;
    }

    public int getExamQuestionNumber() {
        return examQuestionNumber;
    }
}
