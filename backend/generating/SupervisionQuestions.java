package generation;

import java.util.List;

public class SupervisionQuestions {

    private List<Question> questionList;
    private StringBuilder toPrint;

    public SupervisionQuestions(List<Question> questionList) {
        this.questionList = questionList;
        this.toPrint = new StringBuilder();
    }

    public List<Question> getQuestionList(){ return questionList; }

    public void writeGeneralQuestion(Question question){
        toPrint.append(question.getQuestionText() + "\n");
        if (question.isAutomarkable()){
            // formatting automarkable questions as \begin{automarkable}{language}{exampleurl.com}
            //                                      \end{automarkable}
            AutomarkableQuestion automarkableQuestion = (AutomarkableQuestion) question;
            toPrint.append("\\begin{automarkable}{" + automarkableQuestion.getLanguage() + "}{" + automarkableQuestion.getUrl() + "}" + "\n");
            toPrint.append("\\end{automarkable}" + "\n");
        }
        if (question.hasSubQuestion()){
            //exam question with subparts
            toPrint.append("\\begin{enumerate}" + "\n" + "\\item ");
            // recursively call writeQuestions to generate infinite levels of subquestions
            writeQuestions(question.getSubQuestionList());
        }
        toPrint.append("\\end{enumerate}");
    }

    public StringBuilder writeQuestions(List<Question> questionList){
        for (Question question : questionList){
            //formatting exam questions
            if (question.isExamQuestion()) {
                ExamQuestion exam = (ExamQuestion) question;
                if (!question.isSubQuestion()){
                    // will print out in the form of \begin{examquestion}{1900}{1}{1}
                    toPrint.append("\\begin{examquestion}{" + exam.getYear() + "}{" + exam.getPaper() + "}{" + exam.getExamQuestionNumber() + "}" + "\n");
                }
                writeGeneralQuestion(question);
                toPrint.append("\\end{examquestion}");
            }
            //formatting non-exam questions
            else{
                if (!question.isSubQuestion()){
                    // will print out in the form of \section{insert question here}
                    toPrint.append("\\section{" + question.getQuestionText() + "}" + "\n");
                }
                writeGeneralQuestion(question);
            }
        }
        return toPrint;
    }
}