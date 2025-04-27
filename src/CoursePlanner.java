import java.util.*;

public class CoursePlanner {
    private Map<String, List<String>> adjList;

    public CoursePlanner() {
        adjList = new HashMap<>();
    }

    public void addCourse(String course, List<String> prerequisites) {
        adjList.put(course, prerequisites);
    }

    public void recommendCourses(String interest) {
        System.out.println("Recommending courses for: " + interest);
        // You would implement actual graph logic here
    }
}
