package generation;

import java.util.List;

public class Task {
    private String taskText;
    private boolean isAutomarkable;
    private List<Task> subTaskList;
    private boolean hasSubTasks = false;

    public Task(String taskText, boolean isAutomarkable, List<Task> subTaskList){
        this.taskText = taskText;
        this.isAutomarkable = isAutomarkable;
        this.subTaskList = subTaskList;
        this.hasSubTasks = true;
    }

    public Task(String taskText, boolean isAutomarkable){
        this.taskText = taskText;
        this.isAutomarkable = isAutomarkable;
        this.subTaskList = null;
    }

    public Task(List<Task> subTaskList){
        this.taskText = "";
        this.isAutomarkable = false;
        this.subTaskList = subTaskList;
        this.hasSubTasks = true;
    }

    public String getTaskText() { return taskText; }

    public boolean isAutomarkable(){ return isAutomarkable; }

    public boolean hasSubTasks() { return hasSubTasks; }

    public List<Task> getSubTaskList() { return subTaskList; }

}
