
/**
 * Load courses from CSV file
 * @param filename The path to the CSV file
 */

// CourseGraph.java
import java.util.*;

public class CourseGraph {
    // Graph representation for prerequisites (course -> list of preReqs)
    private Map<String, List<String>> prerequisiteGraph;

    // Reverse graph for finding courses that require this course
    private Map<String, List<String>> dependencyGraph;

    public CourseGraph() {
        prerequisiteGraph = new HashMap<>();
        dependencyGraph = new HashMap<>();
    }

    /**
     * Add prerequisite relationship between courses
     */
    public void addPrerequisite(String course, String prerequisite) {
        prerequisiteGraph.putIfAbsent(course, new ArrayList<>());
        prerequisiteGraph.get(course).add(prerequisite);

        dependencyGraph.putIfAbsent(prerequisite, new ArrayList<>());
        dependencyGraph.get(prerequisite).add(course);

    }

    /**
     * Get prerequisites for a course
     */
    public List<String> getPrerequisites(String courseId) {
        return prerequisiteGraph.getOrDefault(courseId, new ArrayList<>());
    }

    /**
     * Get courses that depend on this course
     */
    public List<String> getDependentCourses(String courseId) {
        return dependencyGraph.getOrDefault(courseId, new ArrayList<>());
    }

    /**
     * Get a list of all prerequisites for a course in correct order
     * (topological sort)
     */
    public List<String> getOrderedPrerequisites(String courseId) {
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();

        // Use DFS to get all prerequisites
        findAllPrerequisites(courseId, visited, result);

        // Remove the course itself from the result
        if (!result.isEmpty() && result.get(result.size() - 1).equals(courseId)) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    /**
     Running DFS on Prerequisite courses
    */
    private void findAllPrerequisites(String courseId, Set<String> visited, List<String> result) {
        if (visited.contains(courseId)) {
            return;
        }

        visited.add(courseId);

        // Visit all prerequisites first
        if (prerequisiteGraph.containsKey(courseId)) {
            for (String prereq : prerequisiteGraph.get(courseId)) {
                findAllPrerequisites(prereq, visited, result);
            }
        }

        // Add current course after all prerequisites
        result.add(courseId);
    }
}

