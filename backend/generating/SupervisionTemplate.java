package generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SupervisionTemplate{

    private SupervisionInfo supervisionInfo;
    private SupervisionQuestions questions;


    public SupervisionTemplate(SupervisionInfo supervisionInfo){
        //these are the things that the user has to specify when modifying the template
        this.supervisionInfo = supervisionInfo;
    }

    public SupervisionTemplate(SupervisionInfo supervisionInfo, SupervisionQuestions questions) {
        //these are the things that the user has to specify when modifying the template
        this.supervisionInfo = supervisionInfo;
        this.questions = questions;
    }

    public void create(){
        HashMap<String, String> studentData = new HashMap<>();
        studentData.put("studentname", supervisionInfo.getStudentName());
        studentData.put("studentemail", supervisionInfo.getStudentEmail());
        studentData.put("svvenue", supervisionInfo.getSvVenue());
        studentData.put("svcourse", supervisionInfo.getSvCourse());
        studentData.put("svnumber", supervisionInfo.getSvNumber());
        studentData.put("svdate", supervisionInfo.getSvDate());
        studentData.put("svtime", supervisionInfo.getSvTime());
        try {
            setSupervisionHeader(studentData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSupervisionHeader(HashMap<String, String> studentData) throws IOException {

        try {
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(supervisionInfo.getFileName()));
            Iterator dataItem = studentData.entrySet().iterator();

            // Constant that stores the newcommand name just for ease
            String newCommand = "\\newcommand";

            // This could be made more general with removal of backslashes etc.
            writeToFile.write("\\documentclass[10pt,twoside,a4paper]{article}");
            while (dataItem.hasNext()){
                Map.Entry pair = (Map.Entry)dataItem.next();
                writeToFile.write(newCommand.concat("{\\".concat(pair.getKey().toString())).concat("}{").concat(pair.getValue().toString()).concat("}"));
                writeToFile.newLine();
            }
            writeToFile.write("\\begin{document}");
            writeToFile.newLine();
            questions.writeQuestions();
            writeToFile.write("\\end{document}");
            writeToFile.close();

        } catch (IOException e){
            System.out.println("Error occurred");
        }
    }
}
