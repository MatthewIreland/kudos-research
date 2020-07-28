package parsing;

public class WorkMetadata {

    private final String studentCrsid;
    private final String courseName;
    private final int svNumber;

    public WorkMetadata(String studentCrsid, String courseName, int svNumber) {
        this.studentCrsid = studentCrsid;
        this.courseName = courseName;
        this.svNumber = svNumber;
    }
}
