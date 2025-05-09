// InterestAreaManager.java
import java.util.*;

public class InterestAreaManager {
    // Map to store interest areas and their related courses
    private Map<String, List<String>> interestAreas;

    public InterestAreaManager() {
        interestAreas = new HashMap<>();

        // Initialize some common interest areas
        interestAreas.put("Machine Learning", new ArrayList<>());
        interestAreas.put("Software Engineering", new ArrayList<>());
        interestAreas.put("Data Science", new ArrayList<>());
        interestAreas.put("Algorithms", new ArrayList<>());
        interestAreas.put("Systems", new ArrayList<>());
        interestAreas.put("Theory", new ArrayList<>());
    }

    /**
     * Add a course to an interest area
     */
    public void addCourseToInterest(String courseId, String interest) {
        interestAreas.putIfAbsent(interest, new ArrayList<>());
        if (!interestAreas.get(interest).contains(courseId)) {
            interestAreas.get(interest).add(courseId);
        }
    }

    /**
     * Get all courses in an interest area
     */
    public List<String> getCoursesInInterest(String interest) {
        return interestAreas.getOrDefault(interest, new ArrayList<>());
    }

    /**
     * Get all interest areas
     */
    public Set<String> getAllInterestAreas() {
        return interestAreas.keySet();
    }

    /**
     * Determine interest areas based on course name
     * This is a helper method to automatically categorize courses
     */
    public List<String> determineInterests(String courseId, String courseName) {
        List<String> interests = new ArrayList<>();

        // Example logic based on course name keywords
        String nameLower = courseName.toLowerCase();

        if (nameLower.contains("algorithm")) {
            interests.add("Algorithms");
        }

        if (nameLower.contains("data") || nameLower.contains("database")) {
            interests.add("Data Science");
        }

        if (nameLower.contains("machine") || nameLower.contains("learning") ||
                nameLower.contains("ai") || nameLower.contains("artificial intelligence")) {
            interests.add("Machine Learning");
        }

        if (nameLower.contains("software") || nameLower.contains("programming") ||
                nameLower.contains("development")) {
            interests.add("Software Engineering");
        }

        if (nameLower.contains("system") || nameLower.contains("architecture") ||
                nameLower.contains("network")) {
            interests.add("Systems");
        }

        if (nameLower.contains("theory") || nameLower.contains("formal") ||
                nameLower.contains("logic")) {
            interests.add("Theory");
        }

        // If no interests were determined, add a default one based on course ID
        if (interests.isEmpty()) {
            if (courseId.contains("5")) {
                interests.add("Graduate Studies");
            } else {
                interests.add("Undergraduate Studies");
            }
        }

        return interests;
    }
}

