package generation;

import java.util.List;

public class SupervisionQuestions{

    private List<Question> questionList;

    public SupervisionQuestions(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String writeQuestions(){
        String toPrint = new String();
        for(Question question: questionList){
            //TODO deal with subquestion stuff
            if(question.getExamQuestion()){
                ExamQuestion exam = (ExamQuestion) question;
                toPrint += "\\begin{examquestion}{" + exam.getYear() + "}{" + exam.getPaper() + "}{" + exam.getQuestionNumber() + "}";
                toPrint += "\n";
                if(exam.getAutomarkable()){
                    //prints out \begin{automarkable}{ocaml}{https://automarker.example.com}{1234}
                    Question question1 = (Question) exam;
                    AutomarkableQuestion automarkableQuestion = (AutomarkableQuestion) question1;
                    toPrint += "\\begin{automarkable}{" + automarkableQuestion.getLanguage() + "}{" + automarkableQuestion.getUrl() + "}{" + automarkableQuestion.getPort() + "}" + "\n";
                    toPrint += automarkableQuestion.getQuestionText();
                    toPrint += "\\end{automarkable}";
                }
                else{

                    toPrint += "\n";
                    //subquestion stuff
                    toPrint += "\\end{examquestion}";
                }
            }
            else{
                //TODO: format in sections
                toPrint += "\\section{" + question.getQuestionText() + "}";
                toPrint += "\n";
                if(question.getAutomarkable()){
                    AutomarkableQuestion automarkableQuestion = (AutomarkableQuestion) question;
                    toPrint += "\\begin{automarkable}{" + automarkableQuestion.getLanguage() + "}{" + automarkableQuestion.getUrl() + "}{" + automarkableQuestion.getPort() + "}" + "\n";
                    toPrint += automarkableQuestion.getQuestionText();
                    toPrint += "\\end{automarkable}";
                }
            }
        }
        return toPrint;
    }
}
