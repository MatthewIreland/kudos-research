package generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SupervisionTemplate{

    private SupervisionInfo supervisionInfo;
    private SupervisionQuestions questions;


    public SupervisionTemplate(SupervisionInfo supervisionInfo, SupervisionQuestions questions) {
        //these are the things that the user has to specify when modifying the template
        this.supervisionInfo = supervisionInfo;
        this.questions = questions;
    }

    public void setSupervisionHeader(){
        LinkedHashMap<String, String> studentData = new LinkedHashMap<>();
        studentData.put("studentname", supervisionInfo.getStudentName());
        studentData.put("studentemail", supervisionInfo.getStudentEmail());
        studentData.put("svrname", supervisionInfo.getSvrName());
        studentData.put("svvenue", supervisionInfo.getSvVenue());
        studentData.put("svcourse", supervisionInfo.getSvCourse());
        studentData.put("svnumber", supervisionInfo.getSvNumber());
        studentData.put("svdate", supervisionInfo.getSvDate());
        studentData.put("svtime", supervisionInfo.getSvTime());
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter("per_supervision_headers.tex"))){
            Iterator dataItem = studentData.entrySet().iterator();

            // Constant that stores the newcommand name just for ease
            String newCommand = "\\newcommand";

            // This loop will correctly populate the header with details on the student name, course etc.
            while (dataItem.hasNext()){
                Map.Entry pair = (Map.Entry)dataItem.next();
                String toPrint = newCommand + "{\\" + pair.getKey().toString() + "}{" + pair.getValue().toString() + "}";
                writeToFile.write(toPrint);
                // the above line will, for example, print out \newcommand{\studentname}{Harry Potter}
                writeToFile.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred");
        }
    }

    public void setSupervisionBody() throws IOException {
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(supervisionInfo.getFileName()))){
            writeToFile.write("\\input{per_supervision_headers.tex}");
            StringBuilder toPrint = questions.writeQuestions(questions.getQuestionList());
            writeToFile.write(toPrint.toString());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error occurred");
        }
    }

    public void createHeaderAndBodyFiles() throws IOException {
        setSupervisionHeader();
        setSupervisionBody();
    }
}
