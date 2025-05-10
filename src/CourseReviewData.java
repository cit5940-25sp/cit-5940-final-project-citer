import java.io.*;
import java.util.*;

public class CourseReviewData {
    public static List<CourseReview> loadFromCSV(String filePath) {
        List<CourseReview> reviews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header
                }
                String[] parts = line.split(",", -1); // Allow missing values

                if (parts.length < 6) continue;

                String code = parts[0].trim();
                String title = parts[1].trim();
                String courseQualityStr = parts[2].trim();
                String instructorQualityStr = parts[3].trim();
                String difficultyStr = parts[4].trim();
                String workloadStr = parts[5].trim();

                try {
                    if (courseQualityStr.equalsIgnoreCase("N/A")) continue;
                    if (instructorQualityStr.equalsIgnoreCase("N/A")) continue;
                    if (difficultyStr.equalsIgnoreCase("N/A")) continue;
                    if (workloadStr.equalsIgnoreCase("N/A")) continue;

                    double courseQuality = Double.parseDouble(courseQualityStr);
                    double instructorQuality = Double.parseDouble(instructorQualityStr);
                    double difficulty = Double.parseDouble(difficultyStr);
                    double workload = Double.parseDouble(workloadStr);

                    CourseReview review = new CourseReview(code, title, courseQuality, instructorQuality, difficulty, workload);
                    reviews.add(review);
                } catch (Exception e) {
                    System.out.println("Skipping row due to bad format: " + Arrays.toString(parts));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return reviews;
    }
}
