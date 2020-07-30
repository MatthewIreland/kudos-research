package generation;

import java.util.List;

public class Task {
    private String taskText;
    private boolean isAutomarkable;
    private List<Task> subTaskList;
    private boolean hasSubTasks;
    private boolean isSubTask;

    public Task(String taskText, boolean isAutomarkable, List<Task> subTaskList, boolean isSubTask){
        this.taskText = taskText;
        this.isAutomarkable = isAutomarkable;
        this.subTaskList = subTaskList;
        this.isSubTask = isSubTask;

        if (subTaskList.size() > 1){
            this.hasSubTasks =true;
        }
        else {
            this.hasSubTasks =false;
        }
    }

    public String getTaskText() { return taskText; }

    public boolean isAutomarkable(){ return isAutomarkable; }

    public boolean hasSubTasks() { return hasSubTasks; }

    public List<Task> getSubTaskList() { return subTaskList; }

    public boolean isSubTask() { return isSubTask; }

}
