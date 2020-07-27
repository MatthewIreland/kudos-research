package generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SupervisionTemplate {

    public String fileName;
    public String studentName;
    public String studentEmail;
    public String svVenue;
    public String svCourse;
    public String svNumber;
    public String svDate;
    public String svTime;


    public SupervisionTemplate(String fileName, String studentName, String studentEmail, String svVenue,
                               String svCourse, String svNumber, String svDate, String svTime) {
        //these are the things that the user has to specify when modifying the template
        this.fileName = fileName; //
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.svVenue = svVenue;
        this.svCourse = svCourse;
        this.svNumber = svNumber;
        this.svDate = svDate;
        this.svTime = svTime;
    }

    public void create(){
        HashMap<String, String> studentData = new HashMap<>();
        studentData.put("studentname", studentName);
        studentData.put("studentemail", studentEmail);
        studentData.put("svvenue", svVenue);
        studentData.put("svcourse", svCourse);
        studentData.put("svnumber", svNumber);
        studentData.put("svdate", svDate);
        studentData.put("svtime", svTime);
        try {
            setSupervisionHeader(studentData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSupervisionHeader(HashMap<String, String> studentData) throws IOException {

        try {
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(fileName));
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
            writeToFile.write("\\end{document}");
            writeToFile.close();

        } catch (IOException e){
            System.out.println("Error occurred");
        }
    }
}
