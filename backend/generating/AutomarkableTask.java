package generation;

import java.util.List;

public class AutomarkableTask extends Task {
    private String language;
    private String url;

    public AutomarkableTask(String language, String url, String taskText, boolean isAutomarkable, List<Task> subTaskList, boolean isSubTask) {
        super(taskText, isAutomarkable, subTaskList, isSubTask);
        this.language = language;
        this.url = url;
    }

    public String getLanguage() { return language; }

    public String getUrl() { return url; }
}
