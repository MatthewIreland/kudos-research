package generation;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {
        //temporarily set to null until client side stuff comes in
        String fileName = null;
        String studentName = null;
        String studentEmail = null;
        String svVenue = null;
        String svCourse = null;
        String svNumber = null;
        String svDate = null;
        String svTime = null;

        SupervisionInfo supervisionInfo = new SupervisionInfo(fileName, studentName, studentEmail, svVenue,svCourse, svNumber, svDate, svTime);
        SupervisionQuestions questionList = new SupervisionQuestions(new LinkedList<Question>());
        SupervisionTemplate template = new SupervisionTemplate(supervisionInfo, questionList);

        template.create();
    }

}
