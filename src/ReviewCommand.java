// ReviewCommand.java
import java.util.*;

public class ReviewCommand implements Command {
    private List<CourseReview> reviews;

    public ReviewCommand(List<CourseReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course code (e.g., CIS1200 or CIS5000): ");
        String input = scanner.nextLine().trim().toUpperCase();

        for (CourseReview review : reviews) {
            if (review.getCode().equalsIgnoreCase(input)) {
                System.out.println("🎯 Review for " + input + ":");
                System.out.println(review);
                return;
            }
        }
        System.out.println("Course not found. Please try again.");
    }
}
