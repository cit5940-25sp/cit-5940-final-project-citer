import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CoursePlanner {
//    private Map<String, List<String>> adjList;
//
//    public CoursePlanner() {
//        adjList = new HashMap<>();
//    }
//
//    public void addCourse(String course, List<String> prerequisites) {
//        adjList.put(course, prerequisites);
//    }
//
//    public void recommendCourses(String interest) {
//        System.out.println("Recommending courses for: " + interest);
//        // You would implement actual graph logic here
//    }
// Graph representation for prerequisites (course -> list of prereqs)
private Map<String, List<String>> prerequisiteGraph;

    // Reverse graph for finding courses that require this course
    private Map<String, List<String>> dependencyGraph;

    // Map to store course information
    private Map<String, Course> courses;

    // Map to store interest areas and their related courses
    private Map<String, List<String>> interestAreas;

    public CoursePlanner() {
        prerequisiteGraph = new HashMap<>();
        dependencyGraph = new HashMap<>();
        courses = new HashMap<>();
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
     * Add a course to the system
     * @param courseId The course ID (e.g., "CIS 5940")
     * @param courseName The name of the course
     * @param prerequisites List of prerequisite course IDs
     * @param interests List of interest areas this course belongs to
     * @param courseQuality Course quality rating
     * @param instructorQuality Instructor quality rating
     * @param difficulty Course difficulty rating
     * @param workRequired Work required rating
     */
    public void addCourse(String courseId, String courseName, List<String> prerequisites,
                          List<String> interests, double courseQuality, double instructorQuality,
                          double difficulty, double workRequired) {
        // Create and store the course
        Course course = new Course(courseId, courseName, prerequisites, courseQuality,
                instructorQuality, difficulty, workRequired);
        courses.put(courseId, course);

        // Add to prerequisite graph
        prerequisiteGraph.put(courseId, new ArrayList<>(prerequisites));

        // Add to dependency graph (reverse edges)
        for (String preReq : prerequisites) {
            dependencyGraph.putIfAbsent(preReq, new ArrayList<>());
            dependencyGraph.get(preReq).add(courseId);
        }

        // Add to relevant interest areas
        for (String interest : interests) {
            interestAreas.putIfAbsent(interest, new ArrayList<>());
            interestAreas.get(interest).add(courseId);
        }
    }

    /**
     * Get a list of all prerequisites for a course in correct order
     * @param courseId The course to find prerequisites for
     * @return Ordered list of prerequisites
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

    private void findAllPrerequisites(String courseId, Set<String> visited, List<String> result) {
        if (visited.contains(courseId)) {
            return;
        }

        visited.add(courseId);

        // Visit all prerequisites first
        if (prerequisiteGraph.containsKey(courseId)) {
            for (String preReq : prerequisiteGraph.get(courseId)) {
                findAllPrerequisites(preReq, visited, result);
            }
        }

        // Add current course after all prerequisites
        result.add(courseId);
    }

    /**
     * Recommend courses based on an interest area
     * @param interest The interest area to recommend courses for
     * @return List of recommended courses with their prerequisites
     */
    public List<CourseRecommendation> recommendCourses(String interest) {
        List<CourseRecommendation> recommendations = new ArrayList<>();

        if (!interestAreas.containsKey(interest)) {
            System.out.println("No courses found for interest: " + interest);
            return recommendations;
        }

        System.out.println("Recommending courses for interest: " + interest);

        // Get all courses in this interest area
        List<String> coursesInInterest = interestAreas.get(interest);

        for (String courseId : coursesInInterest) {
            Course course = courses.get(courseId);
            if (course == null) continue;

            // Get ordered prerequisites
            List<String> orderedPreReqs = getOrderedPrerequisites(courseId);

            // Create recommendation
            CourseRecommendation recommendation = new CourseRecommendation(
                    course,
                    getCoursesFromIds(orderedPreReqs)
            );

            recommendations.add(recommendation);
        }

        // Sort recommendations by course quality (highest first)
        recommendations.sort((r1, r2) -> {
            double q1 = r1.getRecommendedCourse().getCourseQuality();
            double q2 = r2.getRecommendedCourse().getCourseQuality();

            // Handle N/A ratings (represented as -1)
            if (q1 == -1 && q2 == -1) return 0;
            if (q1 == -1) return 1;
            if (q2 == -1) return -1;

            return Double.compare(q2, q1);
        });

        return recommendations;
    }

    /**
     * Convert a list of course IDs to Course objects
     */
    private List<Course> getCoursesFromIds(List<String> courseIds) {
        List<Course> result = new ArrayList<>();
        for (String id : courseIds) {
            if (courses.containsKey(id)) {
                result.add(courses.get(id));
            }
        }
        return result;
    }

    /**
     * Find all courses that use the specified course as a prerequisite
     * @param courseId The prerequisite course
     * @return List of courses that require this course
     */
    public List<Course> findDependentCourses(String courseId) {
        List<Course> result = new ArrayList<>();

        if (dependencyGraph.containsKey(courseId)) {
            for (String dependent : dependencyGraph.get(courseId)) {
                if (courses.containsKey(dependent)) {
                    result.add(courses.get(dependent));
                }
            }
        }

        return result;
    }

    /**
     * Get all available courses by interest area
     * @return Map of interest areas to their courses
     */
    public Map<String, List<Course>> getAllCoursesByInterest() {
        Map<String, List<Course>> result = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : interestAreas.entrySet()) {
            List<Course> interestCourses = new ArrayList<>();
            for (String courseId : entry.getValue()) {
                if (courses.containsKey(courseId)) {
                    interestCourses.add(courses.get(courseId));
                }
            }
            result.put(entry.getKey(), interestCourses);
        }

        return result;
    }

}
