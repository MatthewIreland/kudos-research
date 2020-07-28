package generation;

public class SupervisionInfo {
    private String fileName;
    private String studentName;
    private String studentEmail;
    private String svVenue;
    private String svCourse;
    private String svNumber;
    private String svDate;
    private String svTime;

    public SupervisionInfo(String fileName, String studentName, String studentEmail, String svVenue, String svCourse, String svNumber, String svDate, String svTime) {
        ///this will set the file name to crsid_course_number_svNumber
        this.fileName = studentEmail.split("\\@")[0].concat("_").concat(svCourse.replaceAll("\\s", "")).concat(svNumber).concat(".tex");
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.svVenue = svVenue;
        this.svCourse = svCourse;
        this.svNumber = svNumber;
        this.svDate = svDate;
        this.svTime = svTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getSvVenue() {
        return svVenue;
    }

    public String getSvCourse() {
        return svCourse;
    }

    public String getSvNumber() {
        return svNumber;
    }

    public String getSvDate() {
        return svDate;
    }

    public String getSvTime() {
        return svTime;
    }
}
