package generation;

import java.io.IOException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {

        //temporarily set to null until client side stuff comes in
        String studentName = null ;
        String studentEmail = null;
        String svrName = null;
        String svVenue = null;
        String svCourse = null;
        String svNumber = null;
        String svDate = null;
        String svTime = null;

        SupervisionInfo supervisionInfo = new SupervisionInfo(studentName, studentEmail, svrName, svVenue,svCourse, svNumber, svDate, svTime);
        SupervisionQuestions questionList = new SupervisionQuestions(new LinkedList<Question>());
        SupervisionTemplate template = new SupervisionTemplate(supervisionInfo, questionList);

        template.createHeaderAndBodyFiles(); //this will create the separate header and body files
    }

}
