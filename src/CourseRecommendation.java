import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Recommendation class to represent a course recommendation with prerequisites
 */
public class CourseRecommendation {
    private Course recommendedCourse;
    private List<Course> prerequisites;

    public CourseRecommendation(Course recommendedCourse, List<Course> prerequisites) {
        this.recommendedCourse = recommendedCourse;
        this.prerequisites = prerequisites;
    }

    public Course getRecommendedCourse() {
        return recommendedCourse;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recommended Course: ").append(recommendedCourse).append("\n");

        if (!prerequisites.isEmpty()) {
            sb.append("Prerequisites (in order):\n");
            for (int i = 0; i < prerequisites.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(prerequisites.get(i)).append("\n");
            }
        } else {
            sb.append("No prerequisites required.\n");
        }

        // Add rating information
        sb.append("Course Details:\n");
        if (recommendedCourse.getCourseQuality() != -1) {
            sb.append("  Course Quality: ").append(String.format("%.2f", recommendedCourse.getCourseQuality())).append("\n");
        } else {
            sb.append("  Course Quality: N/A\n");
        }

        if (recommendedCourse.getInstructorQuality() != -1) {
            sb.append("  Instructor Quality: ").append(String.format("%.2f", recommendedCourse.getInstructorQuality())).append("\n");
        } else {
            sb.append("  Instructor Quality: N/A\n");
        }

        if (recommendedCourse.getDifficulty() != -1) {
            sb.append("  Difficulty: ").append(String.format("%.2f", recommendedCourse.getDifficulty())).append("\n");
        } else {
            sb.append("  Difficulty: N/A\n");
        }

        if (recommendedCourse.getWorkRequired() != -1) {
            sb.append("  Work Required: ").append(String.format("%.2f", recommendedCourse.getWorkRequired())).append("\n");
        } else {
            sb.append("  Work Required: N/A\n");
        }

        return sb.toString();
    }
}

