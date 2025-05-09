// AcademicCommand.java
import java.util.List;

public class AcademicCommand implements Command {
    private CoursePlanner planner;
    private String interest;
    private int maxRecommendations = 5;  // Default to showing top 5 recommendations

    public AcademicCommand(CoursePlanner planner) {
        this.planner = planner;
        this.interest = "Machine Learning";  // Default interest area
    }

    public AcademicCommand(CoursePlanner planner, String interest) {
        this.planner = planner;
        this.interest = interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setMaxRecommendations(int max) {
        this.maxRecommendations = max;
    }

    @Override
    public void execute() {
        // Check if interest is available
        if (!planner.getAllInterestAreas().contains(interest)) {
            System.out.println("Interest area '" + interest + "' not found. Available interest areas:");
            for (String area : planner.getAllInterestAreas()) {
                System.out.println("- " + area);
            }
            return;
        }

        List<CourseRecommendation> recommendations = planner.recommendCourses(interest);

        if (recommendations.isEmpty()) {
            System.out.println("No course recommendations found for " + interest);
            return;
        }

        System.out.println("Top " + Math.min(maxRecommendations, recommendations.size()) +
                " Course Recommendations for " + interest + ":");

        // Show only the top N recommendations
        int count = 0;
        for (CourseRecommendation rec : recommendations) {
            System.out.println(rec);
            count++;
            if (count >= maxRecommendations) break;
        }
    }
}

