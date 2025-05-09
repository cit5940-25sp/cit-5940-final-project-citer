// CourseLoader.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CourseLoader {
    /**
     * Load courses from CSV file
     * @param filename The path to the CSV file
     * @return Map of course IDs to Course objects
     */
    public Map<String, Course> loadCoursesFromCSV(String filename) {
        Map<String, Course> courses = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header line
                }

                String[] values = line.split(",");
                if (values.length < 6) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String courseId = values[0];
                String courseName = values[1];

                // Parse ratings, handling N/A values
                double courseQuality = parseRating(values[2]);
                double instructorQuality = parseRating(values[3]);
                double difficulty = parseRating(values[4]);
                double workRequired = parseRating(values[5]);

                // For now, we'll use empty prerequisites list (to be filled later)
                List<String> prerequisites = new ArrayList<>();

                Course course = new Course(courseId, courseName, prerequisites,
                        courseQuality, instructorQuality,
                        difficulty, workRequired);
                courses.put(courseId, course);
            }

            System.out.println("Successfully loaded " + courses.size() + " courses from " + filename);
        } catch (IOException e) {
            System.err.println("Error loading courses from CSV: " + e.getMessage());
        }

        return courses;
    }

    /**
     * Parse rating value, handling N/A
     */
    private double parseRating(String value) {
        if (value == null || value.trim().equalsIgnoreCase("N/A")) {
            return -1; // Use -1 to represent N/A
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void loadPrerequisitesFromCSV(String filename, CourseGraph courseGraph) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header line
                }

                String[] values = line.split(",");
                if (values.length < 2) {
                    System.out.println("Skipping invalid prerequisite line: " + line);
                    continue;
                }

                String courseId = values[0].trim();
                String prerequisiteId = values[1].trim();

                courseGraph.addPrerequisite(courseId, prerequisiteId);
            }

            System.out.println("Successfully loaded prerequisites from " + filename);
        } catch (IOException e) {
            System.err.println("Error loading prerequisites from CSV: " + e.getMessage());
        }
    }
}

