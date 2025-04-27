public class CourseReview {
    private String code;
    private String title;
    private double courseQuality;
    private double instructorQuality;
    private double difficulty;
    private double workload;

    public CourseReview(String code, String title, double courseQuality, double instructorQuality, double difficulty, double workload) {
        this.code = code;
        this.title = title;
        this.courseQuality = courseQuality;
        this.instructorQuality = instructorQuality;
        this.difficulty = difficulty;
        this.workload = workload;
    }

    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public double getCourseQuality() { return courseQuality; }
    public double getInstructorQuality() { return instructorQuality; }
    public double getDifficulty() { return difficulty; }
    public double getWorkload() { return workload; }

    @Override
    public String toString() {
        return code + ": " + title +
                " | Course Quality: " + courseQuality +
                ", Instructor Quality: " + instructorQuality +
                ", Difficulty: " + difficulty +
                ", Workload: " + workload + " hrs/week";
    }
}
