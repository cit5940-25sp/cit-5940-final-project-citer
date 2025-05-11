import java.util.List;

/**
 * Course class to store course information
 * - Constructor class//
 */
// Course.java
public class Course {
    private String courseId;
    private String courseName;
    private List<String> prerequisites;
    private double courseQuality;
    private double instructorQuality;
    private double difficulty;
    private double workRequired;

    public Course(String courseId, String courseName, List<String> prerequisites,
                  double courseQuality, double instructorQuality,
                  double difficulty, double workRequired) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.prerequisites = prerequisites;
        this.courseQuality = courseQuality;
        this.instructorQuality = instructorQuality;
        this.difficulty = difficulty;
        this.workRequired = workRequired;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public double getCourseQuality() {
        return courseQuality;
    }

    public double getInstructorQuality() {
        return instructorQuality;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public double getWorkRequired() {
        return workRequired;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(courseId).append(": ").append(courseName);

        if (courseQuality != -1) {
            sb.append(" (Quality: ").append(String.format("%.2f", courseQuality)).append(")");
        }

        return sb.toString();
    }
}
