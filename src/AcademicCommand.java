// Update AcademicCommand.java to handle career paths
import java.util.List;

/**
 * AcademicCommand.java
 *
 * This class implements the Command interface for academic planning functionality.
 * It provides course recommendations based on either interest areas or career paths.
 */

public class AcademicCommand implements Command {
    /** Course planner that provides recommendation functionality */
    private CoursePlanner planner;

    /** Selected interest area for recommendations */
    private String interest;

    /** Selected career path for recommendationsh */
    private String careerPath;

    /** Maximum number of recommendations to display */
    private int maxRecommendations = 4;  // Default to showing top 4 recommendations

    /** Flag to determine recommendation mode (interest vs career path) */
    private boolean isCareerPathMode = false;

    /**
     * Default constructor with Machine Learning as the default interest area
     *
     * @param planner The course planner to use for recommendations
     */
    public AcademicCommand(CoursePlanner planner) {
        this.planner = planner;
        this.interest = "Machine Learning";  // Default interest area
    }

    /**
     * Constructor with a specific interest area
     *
     * @param planner The course planner to use for recommendations
     * @param interest The interest area to recommend courses for
     */
    public AcademicCommand(CoursePlanner planner, String interest) {
        this.planner = planner;
        this.interest = interest;
        this.isCareerPathMode = false;
    }

    /**
     * Constructor that can handle either interest area or career path
     *
     * @param planner The course planner to use for recommendations
     * @param path The interest area or career path string
     * @param isCareerPath Flag to determine if path is a career path (true) or interest area (false)
     */
    public AcademicCommand(CoursePlanner planner, String path, boolean isCareerPath) {
        this.planner = planner;
        if (isCareerPath) {
            this.careerPath = path;
            this.isCareerPathMode = true;
        } else {
            this.interest = path;
            this.isCareerPathMode = false;
        }
    }

    /**
     * Sets the interest area for recommendations and switches to interest mode
     *
     * @param interest The interest area to recommend courses for
     */
    public void setInterest(String interest) {
        this.interest = interest;
        this.isCareerPathMode = false;
    }

    /**
     * Sets the career path for recommendations and switches to career path mode
     *
     * @param careerPath The career path to recommend courses for
     */
    public void setCareerPath(String careerPath) {
        this.careerPath = careerPath;
        this.isCareerPathMode = true;
    }

    /**
     * Sets the maximum number of recommendations to display
     *
     * @param max The maximum number of recommendations
     */
    public void setMaxRecommendations(int max) {
        this.maxRecommendations = max;
    }

    /**
     * Executes the command to provide course recommendations
     * Delegates to either interest-based or career path-based recommendations
     * based on the current mode
     */
    @Override
    public void execute() {
        if (isCareerPathMode) {
            recommendForCareerPath();
        } else {
            recommendForInterest();
        }
    }

    /**
     * Provides course recommendations based on the selected interest area
     * Displays up to maxRecommendations courses sorted by quality
     */
    private void recommendForInterest() {
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

    /**
     * Provides course recommendations based on the selected career path
     * A career path maps to multiple interest areas to provide a comprehensive
     * set of courses relevant to that career
     * Displays up to maxRecommendations courses sorted by quality
     */
    private void recommendForCareerPath() {
        // Check if career path is available
        if (!planner.getAllCareerPaths().contains(careerPath)) {
            System.out.println("Career path '" + careerPath + "' not found. Available career paths:");
            for (String path : planner.getAllCareerPaths()) {
                System.out.println("- " + path);
            }
            return;
        }

        List<CourseRecommendation> recommendations = planner.recommendCoursesForCareerPath(careerPath);

        if (recommendations.isEmpty()) {
            System.out.println("No course recommendations found for career path: " + careerPath);
            return;
        }

        System.out.println("Top " + Math.min(maxRecommendations, recommendations.size()) +
                " Course Recommendations for " + careerPath + " career path:");

        // Show only the top N recommendations
        int count = 0;
        for (CourseRecommendation rec : recommendations) {
            System.out.println(rec);
            count++;
            if (count >= maxRecommendations) break;
        }
    }
}

