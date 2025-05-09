import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CoursePlanner {
    private Map<String, Course> courses;
    private CourseGraph courseGraph;
    private InterestAreaManager interestManager;
    private CourseLoader courseLoader;

    public CoursePlanner() {
        courses = new HashMap<>();
        courseGraph = new CourseGraph();
        interestManager = new InterestAreaManager();
        courseLoader = new CourseLoader();
    }

    /**
     * Add a course to the system
     */
    public void addCourse(Course course) {
        courses.put(course.getCourseId(), course);

        // Add prerequisites to graph
        for (String preReq : course.getPrerequisites()) {
            courseGraph.addPrerequisite(course.getCourseId(), preReq);
        }
    }

    /**
     * Add a course to an interest area
     */
    public void addCourseToInterest(String courseId, String interest) {
        interestManager.addCourseToInterest(courseId, interest);
    }

    /**
     * Add prerequisite relationship
     */
    public void addPrerequisite(String course, String prerequisite) {
        courseGraph.addPrerequisite(course, prerequisite);
    }

    /**
     * Load courses from CSV
     */
    public void loadCoursesFromCSV(String filename) {
        // Load courses
        Map<String, Course> loadedCourses = courseLoader.loadCoursesFromCSV(filename);

        // Add courses to our system
        for (Course course : loadedCourses.values()) {
            courses.put(course.getCourseId(), course);

            // Automatically determine interests based on course name
            List<String> interests = interestManager.determineInterests(
                    course.getCourseId(), course.getCourseName());

            // Add course to each interest area
            for (String interest : interests) {
                addCourseToInterest(course.getCourseId(), interest);
            }
        }

        // For demonstration, add some prerequisite relationships
        // In a real system, this would come from another data source
        inferPrerequisites();
    }

    /**
     * Infer prerequisites based on course IDs
     * This is just a simple heuristic for demonstration
     */
    private void inferPrerequisites() {
        // Example: higher-numbered courses might require lower-numbered ones in the same series
        List<String> allCourseIds = new ArrayList<>(courses.keySet());
        Collections.sort(allCourseIds);

        for (int i = 0; i < allCourseIds.size(); i++) {
            String courseId = allCourseIds.get(i);

            // Look for potential prerequisites (courses with similar ID but lower number)
            for (int j = 0; j < i; j++) {
                String potentialPrereq = allCourseIds.get(j);

                // If course IDs have the same prefix but different numbers
                if (courseId.substring(0, 3).equals(potentialPrereq.substring(0, 3))) {
                    int courseNum, prereqNum;
                    try {
                        courseNum = Integer.parseInt(courseId.substring(3));
                        prereqNum = Integer.parseInt(potentialPrereq.substring(3));

                        // If the course number is directly after the prerequisite
                        if (courseNum == prereqNum + 1 || courseNum == prereqNum + 10) {
                            courseGraph.addPrerequisite(courseId, potentialPrereq);
                        }
                    } catch (NumberFormatException e) {
                        // Skip if we can't parse the numbers
                    }
                }
            }
        }
    }

    /**
     * Recommend courses based on an interest area
     */
    public List<CourseRecommendation> recommendCourses(String interest) {
        List<CourseRecommendation> recommendations = new ArrayList<>();

        if (!interestManager.getAllInterestAreas().contains(interest)) {
            System.out.println("No courses found for interest: " + interest);
            return recommendations;
        }

        System.out.println("Recommending courses for interest: " + interest);

        // Get all courses in this interest area
        List<String> coursesInInterest = interestManager.getCoursesInInterest(interest);

        for (String courseId : coursesInInterest) {
            Course course = courses.get(courseId);
            if (course == null) continue;

            // Get ordered prerequisites
            List<String> orderedPrereqs = courseGraph.getOrderedPrerequisites(courseId);
            List<Course> prereqCourses = new ArrayList<>();

            // Convert prerequisite IDs to Course objects
            for (String prereqId : orderedPrereqs) {
                Course prereq = courses.get(prereqId);
                if (prereq != null) {
                    prereqCourses.add(prereq);
                }
            }

            // Create recommendation
            CourseRecommendation recommendation = new CourseRecommendation(course, prereqCourses);
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
     * Get all courses in the system
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    /**
     * Get all interest areas
     */
    public Set<String> getAllInterestAreas() {
        return interestManager.getAllInterestAreas();
    }

    /**
     * Find courses that depend on the specified course
     */
    public List<Course> findDependentCourses(String courseId) {
        List<Course> result = new ArrayList<>();
        List<String> dependents = courseGraph.getDependentCourses(courseId);

        for (String dependent : dependents) {
            Course course = courses.get(dependent);
            if (course != null) {
                result.add(course);
            }
        }

        return result;
    }

    public Set<String> getAllCareerPaths() {
        return interestManager.getAllCareerPaths();
    }

    /**
     * Recommend courses based on a career path
     */
    public List<CourseRecommendation> recommendCoursesForCareerPath(String careerPath) {
        List<CourseRecommendation> allRecommendations = new ArrayList<>();

        // Get all interest areas relevant to this career path
        List<String> relevantInterests = interestManager.getInterestsForCareerPath(careerPath);

        if (relevantInterests.isEmpty()) {
            System.out.println("No interests found for career path: " + careerPath);
            return allRecommendations;
        }

        System.out.println("Recommending courses for career path: " + careerPath);
        System.out.println("Relevant interest areas: " + String.join(", ", relevantInterests));

        // Collect recommendations from all relevant interest areas
        for (String interest : relevantInterests) {
            List<CourseRecommendation> interestRecommendations = recommendCourses(interest);
            allRecommendations.addAll(interestRecommendations);
        }

        // Remove duplicates (same course might be recommended in multiple interest areas)
        List<CourseRecommendation> uniqueRecommendations = new ArrayList<>();
        Set<String> addedCourseIds = new HashSet<>();

        for (CourseRecommendation rec : allRecommendations) {
            String courseId = rec.getRecommendedCourse().getCourseId();
            if (!addedCourseIds.contains(courseId)) {
                uniqueRecommendations.add(rec);
                addedCourseIds.add(courseId);
            }
        }

        // Sort by course quality
        uniqueRecommendations.sort((r1, r2) -> {
            double q1 = r1.getRecommendedCourse().getCourseQuality();
            double q2 = r2.getRecommendedCourse().getCourseQuality();

            // Handle N/A ratings (represented as -1)
            if (q1 == -1 && q2 == -1) return 0;
            if (q1 == -1) return 1;
            if (q2 == -1) return -1;

            return Double.compare(q2, q1);
        });

        return uniqueRecommendations;
    }

    public void loadPrerequisitesFromCSV(String filename) {
        courseLoader.loadPrerequisitesFromCSV(filename, courseGraph);
    }

}
