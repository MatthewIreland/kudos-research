package generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SupervisionTemplate{

    private SupervisionInfo supervisionInfo;
    private SupervisionAgenda tasks;

    public SupervisionTemplate(SupervisionInfo supervisionInfo, SupervisionAgenda tasks) {
        //these are the things that the user has to specify when modifying the template
        this.supervisionInfo = supervisionInfo;
        this.tasks = tasks;
    }

    private LinkedHashMap<String, String> getStudentHeaderInfo(){
        LinkedHashMap<String, String> studentData = new LinkedHashMap<>();
        studentData.put("studentname", supervisionInfo.getStudentName());
        studentData.put("studentemail", supervisionInfo.getStudentEmail());
        studentData.put("svrname", supervisionInfo.getSvrName());
        studentData.put("svvenue", supervisionInfo.getSvVenue());
        studentData.put("svcourse", supervisionInfo.getSvCourse());
        studentData.put("svnumber", supervisionInfo.getSvNumber());
        studentData.put("svdate", supervisionInfo.getSvDate());
        studentData.put("svtime", supervisionInfo.getSvTime());
        return studentData;
    }

    public void setSupervisionHeader(){
        LinkedHashMap<String, String> studentData = getStudentHeaderInfo();
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

    public void setSupervisionQuestions() throws IOException {
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter("supervision_questions.tex"))){
            // wrapping the questions in an enumerate environment
            StringBuilder toPrint = new StringBuilder("\\begin{enumerate}" + "\n" + "\n");
            toPrint.append(tasks.writeTasks(tasks.getTaskList()));
            toPrint.append("\n" + "\\end{questions}" + "\n" + "\\");
            toPrint.append("\\end{enumerate}");
            writeToFile.write(toPrint.toString());

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error occurred");
        }
    }

    public void createHeaderAndBodyFiles() throws IOException {
        setSupervisionHeader(); //separate per_supervision_headers.tex
        setSupervisionQuestions(); //separate supervision_questions.tex
    }
}