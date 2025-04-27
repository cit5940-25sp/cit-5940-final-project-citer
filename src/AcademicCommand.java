// AcademicCommand.java
public class AcademicCommand implements Command {
    private CoursePlanner planner;

    public AcademicCommand(CoursePlanner planner) {
        this.planner = planner;
    }

    @Override
    public void execute() {
        planner.recommendCourses("Machine Learning");
    }
}