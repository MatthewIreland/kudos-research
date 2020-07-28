package parsing;

import org.json.JSONObject;

public class WorkMetadata {

    private final String studentCrsid;
    private final String courseName;
    private final int svNumber;

    public WorkMetadata(String studentCrsid, String courseName, int svNumber) {
        this.studentCrsid = studentCrsid;
        this.courseName = courseName;
        this.svNumber = svNumber;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();

        obj.put("studentCrsid", studentCrsid);
        obj.put("courseName", courseName);
        obj.put("svNumber", svNumber);

        return obj;
    }
}
