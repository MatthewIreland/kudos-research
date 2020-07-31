package generation;

import java.util.List;

public class SupervisionAgenda {

    private List<Task> taskList;
    private StringBuilder toPrint = new StringBuilder();

    public SupervisionAgenda(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList(){ return taskList; }

    public StringBuilder writeTasks(List<Task> taskList){
        for (Task task : taskList){
            //formatting non-exam questions as a default; exam questions need to be formatted in the UI
            toPrint.append("\\question{}" + "\n");
            toPrint.append("\\item " + "\\textbf{" + task.getTaskText() + "}" + "\n");
            if (task.isAutomarkable()){
                // formatting automarkable tasks as \begin{automarkable}{language}{exampleurl.com}
                //                                      \end{automarkable}
                AutomarkableTask automarkableTask = (AutomarkableTask) task;
                toPrint.append("\\begin{automarkable}{" + automarkableTask.getLanguage() + "}{" + automarkableTask.getUrl() + "}" + "\n");
                toPrint.append("\\end{automarkable}" + "\n");
            }
            else{
                toPrint.append("% enter text here" + "\n");
            }
            if (task.hasSubTasks()){
                //exam question with subparts
                toPrint.append("\\begin{enumerate}" + "\n");
                // recursively call writeTasks to generate infinite levels of subtasks
                writeTasks(task.getSubTaskList());
                toPrint.append("\\end{enumerate}" + "\n");
            }

        }
        return toPrint;
    }
}