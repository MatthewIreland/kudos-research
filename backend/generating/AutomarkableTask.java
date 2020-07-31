package generation;

import java.util.List;

public class AutomarkableTask extends Task {
    private String language;
    private String url;

    public AutomarkableTask(String taskText, List<Task> subTaskList, String language, String url) {
        super(taskText, true, subTaskList);
        this.language = language;
        this.url = url;
    }

    public AutomarkableTask(String taskText, String language, String url){
        super(taskText, true);
        this.language = language;
        this.url = url;
    }

    @Override
    public boolean isAutomarkable() { return true; }

    public String getLanguage() { return language; }

    public String getUrl() { return url; }
}
