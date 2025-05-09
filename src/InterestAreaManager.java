// InterestAreaManager.java
import java.util.*;

public class InterestAreaManager {
    // Map to store interest areas and their related courses
    private Map<String, List<String>> interestAreas;

    // Map to store career paths and their recommended interest areas
    private Map<String, List<String>> careerPaths;

    public InterestAreaManager() {
        interestAreas = new HashMap<>();
        careerPaths = new HashMap<>();

        // Initialize interest areas based on the course list
        initializeInterestAreas();

        // Initialize career paths
        initializeCareerPaths();
    }

    private void initializeInterestAreas() {
        // Core areas
        interestAreas.put("Algorithms", new ArrayList<>());
        interestAreas.put("Software Engineering", new ArrayList<>());
        interestAreas.put("Systems", new ArrayList<>());
        interestAreas.put("Theory", new ArrayList<>());

        // AI and Data
        interestAreas.put("Machine Learning", new ArrayList<>());
        interestAreas.put("Artificial Intelligence", new ArrayList<>());
        interestAreas.put("Data Science", new ArrayList<>());
        interestAreas.put("Natural Language Processing", new ArrayList<>());

        // Graphics and Media
        interestAreas.put("Computer Graphics", new ArrayList<>());
        interestAreas.put("Game Development", new ArrayList<>());
        interestAreas.put("Computer Vision", new ArrayList<>());

        // Specialized areas
        interestAreas.put("Security", new ArrayList<>());
        interestAreas.put("Databases", new ArrayList<>());
        interestAreas.put("Networks", new ArrayList<>());
        interestAreas.put("Web Development", new ArrayList<>());
        interestAreas.put("Embedded Systems", new ArrayList<>());
        interestAreas.put("Computational Biology", new ArrayList<>());
        interestAreas.put("HCI", new ArrayList<>());
        interestAreas.put("GPU Computing", new ArrayList<>());

        // Academic tracks
        interestAreas.put("Graduate Research", new ArrayList<>());
        interestAreas.put("PhD Preparation", new ArrayList<>());
    }

    private void initializeCareerPaths() {
        // Data Scientist career path
        List<String> dataScientistInterests = new ArrayList<>(Arrays.asList(
                "Machine Learning", "Data Science", "Algorithms", "Databases", "Natural Language Processing"
        ));
        careerPaths.put("Data Scientist", dataScientistInterests);

        // Software Engineer career path
        List<String> softwareEngineerInterests = new ArrayList<>(Arrays.asList(
                "Software Engineering", "Algorithms", "Systems", "Databases", "Web Development"
        ));
        careerPaths.put("Software Engineer", softwareEngineerInterests);

        // AI/ML Engineer career path
        List<String> mlEngineerInterests = new ArrayList<>(Arrays.asList(
                "Machine Learning", "Artificial Intelligence", "Data Science", "Algorithms", "GPU Computing"
        ));
        careerPaths.put("AI/ML Engineer", mlEngineerInterests);

        // Security Engineer career path
        List<String> securityEngineerInterests = new ArrayList<>(Arrays.asList(
                "Security", "Networks", "Systems", "Theory"
        ));
        careerPaths.put("Security Engineer", securityEngineerInterests);

        // Game Developer career path
        List<String> gameDevInterests = new ArrayList<>(Arrays.asList(
                "Computer Graphics", "Game Development", "Software Engineering", "GPU Computing"
        ));
        careerPaths.put("Game Developer", gameDevInterests);

        // Database Administrator career path
        List<String> dbaInterests = new ArrayList<>(Arrays.asList(
                "Databases", "Systems", "Networks", "Security"
        ));
        careerPaths.put("Database Administrator", dbaInterests);

        // Full-stack Web Developer career path
        List<String> webDevInterests = new ArrayList<>(Arrays.asList(
                "Web Development", "Software Engineering", "Databases", "Networks"
        ));
        careerPaths.put("Full-stack Web Developer", webDevInterests);

        // Embedded Systems Engineer career path
        List<String> embeddedInterests = new ArrayList<>(Arrays.asList(
                "Embedded Systems", "Systems", "Software Engineering"
        ));
        careerPaths.put("Embedded Systems Engineer", embeddedInterests);

        // Computer Vision Engineer career path
        List<String> visionInterests = new ArrayList<>(Arrays.asList(
                "Computer Vision", "Machine Learning", "Computer Graphics", "GPU Computing"
        ));
        careerPaths.put("Computer Vision Engineer", visionInterests);

        // Computational Biologist career path
        List<String> bioInterests = new ArrayList<>(Arrays.asList(
                "Computational Biology", "Data Science", "Machine Learning", "Algorithms"
        ));
        careerPaths.put("Computational Biologist", bioInterests);

        // Research Scientist career path
        List<String> researchInterests = new ArrayList<>(Arrays.asList(
                "Graduate Research", "PhD Preparation", "Machine Learning", "Theory", "Algorithms"
        ));
        careerPaths.put("Research Scientist", researchInterests);

        // UX/UI Engineer career path
        List<String> uxInterests = new ArrayList<>(Arrays.asList(
                "HCI", "Web Development", "Software Engineering"
        ));
        careerPaths.put("UX/UI Engineer", uxInterests);

        // NLP Engineer career path
        List<String> nlpInterests = new ArrayList<>(Arrays.asList(
                "Natural Language Processing", "Machine Learning", "Artificial Intelligence", "Data Science"
        ));
        careerPaths.put("NLP Engineer", nlpInterests);
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
     * Get all career paths
     */
    public Set<String> getAllCareerPaths() {
        return careerPaths.keySet();
    }

    /**
     * Get interest areas for a career path
     */
    public List<String> getInterestsForCareerPath(String careerPath) {
        return careerPaths.getOrDefault(careerPath, new ArrayList<>());
    }

    /**
     * Determine interest areas based on course ID and name
     */
    public List<String> determineInterests(String courseId, String courseName) {
        List<String> interests = new ArrayList<>();
        String courseIdUpper = courseId.toUpperCase();
        String nameLower = courseName.toLowerCase();

        // Algorithms and Theory courses
        if (courseIdUpper.contains("502") || courseIdUpper.equals("CIS5020") ||
                courseIdUpper.equals("CIS5030") || courseIdUpper.equals("CIS6770") ||
                nameLower.contains("algorithm") || nameLower.contains("theory of computation")) {
            interests.add("Algorithms");
        }

        // Software Engineering courses
        if (courseIdUpper.contains("500") || courseIdUpper.contains("505") ||
                courseIdUpper.equals("CIS5050") || courseIdUpper.equals("CIS573") ||
                courseIdUpper.equals("CIS5730") || courseIdUpper.equals("CIS5520") ||
                courseIdUpper.equals("CIS5590") ||
                nameLower.contains("software") || nameLower.contains("programming")) {
            interests.add("Software Engineering");
        }

        // Systems courses
        if (courseIdUpper.contains("501") || courseIdUpper.contains("505") ||
                courseIdUpper.equals("CIS5050") || courseIdUpper.equals("CIS5480") ||
                courseIdUpper.equals("CIS5710") || courseIdUpper.equals("CIS6010") ||
                nameLower.contains("system") || nameLower.contains("architecture") ||
                nameLower.contains("design")) {
            interests.add("Systems");
        }

        // Theory courses
        if (courseIdUpper.equals("CIS5110") || courseIdUpper.equals("CIS511") ||
                courseIdUpper.equals("CIS518") || courseIdUpper.equals("CIS582") ||
                courseIdUpper.equals("CIS6250") || courseIdUpper.equals("CIS571") ||
                nameLower.contains("theory") || nameLower.contains("logic") ||
                nameLower.contains("computation")) {
            interests.add("Theory");
        }

        // Machine Learning courses
        if (courseIdUpper.equals("CIS5190") || courseIdUpper.equals("CIS5200") ||
                courseIdUpper.equals("CIS5220") || courseIdUpper.equals("CIS6200") ||
                courseIdUpper.equals("CIS6250") || courseIdUpper.equals("CIS5690") ||
                nameLower.contains("machine learning") || nameLower.contains("deep learning")) {
            interests.add("Machine Learning");
        }

        // AI courses
        if (courseIdUpper.equals("CIS520") || courseIdUpper.equals("CIS5210") ||
                nameLower.contains("artificial intelligence") || nameLower.contains("ai")) {
            interests.add("Artificial Intelligence");
        }

        // Data Science courses
        if (courseIdUpper.equals("CIS5190") || courseIdUpper.equals("CIS5220") ||
                courseIdUpper.equals("CIS5450") || courseIdUpper.equals("CIS550") ||
                courseIdUpper.equals("CIS5500") ||
                nameLower.contains("data") || nameLower.contains("analytics")) {
            interests.add("Data Science");
        }

        // NLP courses
        if (courseIdUpper.equals("CIS5300") || courseIdUpper.equals("CIS530") ||
                courseIdUpper.equals("CIS526") || courseIdUpper.equals("CIS6300") ||
                nameLower.contains("language") || nameLower.contains("linguistic")) {
            interests.add("Natural Language Processing");
        }

        // Graphics courses
        if (courseIdUpper.equals("CIS560") || courseIdUpper.equals("CIS5600") ||
                courseIdUpper.equals("CIS561") || courseIdUpper.equals("CIS5610") ||
                courseIdUpper.equals("CIS562") || courseIdUpper.equals("CIS5620") ||
                courseIdUpper.equals("CIS563") || courseIdUpper.equals("CIS5660") ||
                courseIdUpper.equals("CIS6600") ||
                nameLower.contains("graphics") || nameLower.contains("animation") ||
                nameLower.contains("rendering")) {
            interests.add("Computer Graphics");
        }

        // Game Development courses
        if (courseIdUpper.equals("CIS564") || courseIdUpper.equals("CIS5640") ||
                courseIdUpper.equals("CIS5680") ||
                nameLower.contains("game")) {
            interests.add("Game Development");
        }

        // Computer Vision courses
        if (courseIdUpper.equals("CIS580") || courseIdUpper.equals("CIS5800") ||
                courseIdUpper.equals("CIS5810") || courseIdUpper.equals("CIS575") ||
                courseIdUpper.equals("CIS6800") ||
                nameLower.contains("vision") || nameLower.contains("perception") ||
                nameLower.contains("image")) {
            interests.add("Computer Vision");
        }

        // Security courses
        if (courseIdUpper.equals("CIS551") || courseIdUpper.equals("CIS5510") ||
                courseIdUpper.equals("CIS5560") ||
                nameLower.contains("security") || nameLower.contains("crypto")) {
            interests.add("Security");
        }

        // Database courses
        if (courseIdUpper.equals("CIS550") || courseIdUpper.equals("CIS5500") ||
                courseIdUpper.equals("CIS6500") ||
                nameLower.contains("database") || nameLower.contains("data management")) {
            interests.add("Databases");
        }

        // Networks courses
        if (courseIdUpper.equals("CIS5530") || courseIdUpper.equals("CIS5490") ||
                courseIdUpper.equals("CIS551") || courseIdUpper.equals("CIS5510") ||
                nameLower.contains("network")) {
            interests.add("Networks");
        }

        // Web Development courses
        if (courseIdUpper.equals("CIS555") || courseIdUpper.equals("CIS5550") ||
                courseIdUpper.equals("CIS5570") ||
                nameLower.contains("web") || nameLower.contains("internet")) {
            interests.add("Web Development");
        }

        // Embedded Systems courses
        if (courseIdUpper.equals("CIS5410") || courseIdUpper.equals("CIS542") ||
                courseIdUpper.equals("CIS540") ||
                nameLower.contains("embedded")) {
            interests.add("Embedded Systems");
        }

        // Computational Biology courses
        if (courseIdUpper.equals("CIS5350") || courseIdUpper.equals("CIS536") ||
                courseIdUpper.equals("CIS5360") || courseIdUpper.equals("CIS635") ||
                nameLower.contains("bio")) {
            interests.add("Computational Biology");
        }

        // HCI courses
        if (courseIdUpper.equals("CIS5120") ||
                nameLower.contains("hci") || nameLower.contains("human computer")) {
            interests.add("HCI");
        }

        // GPU Computing courses
        if (courseIdUpper.equals("CIS5650") || courseIdUpper.equals("CIS5690") ||
                nameLower.contains("gpu")) {
            interests.add("GPU Computing");
        }

        // Graduate Research courses
        if (courseIdUpper.contains("597") || courseIdUpper.contains("599") ||
                courseIdUpper.contains("899") || courseIdUpper.contains("999") ||
                courseIdUpper.startsWith("CIS6") || courseIdUpper.startsWith("CIS7") ||
                courseIdUpper.startsWith("CIS8") || courseIdUpper.startsWith("CIS9") ||
                nameLower.contains("research") || nameLower.contains("thesis") ||
                nameLower.contains("dissertation")) {
            interests.add("Graduate Research");
        }

        // PhD Preparation courses
        if (courseIdUpper.startsWith("CIS6") || courseIdUpper.startsWith("CIS7") ||
                courseIdUpper.startsWith("CIS8") || courseIdUpper.startsWith("CIS9") ||
                courseIdUpper.contains("810") || courseIdUpper.contains("895") ||
                nameLower.contains("advanced topics")) {
            interests.add("PhD Preparation");
        }

        // If no interests were determined, add a default one based on course ID
        if (interests.isEmpty()) {
            if (courseIdUpper.contains("5") || courseIdUpper.contains("6") ||
                    courseIdUpper.contains("7") || courseIdUpper.contains("8") ||
                    courseIdUpper.contains("9")) {
                interests.add("Graduate Studies");
            } else {
                interests.add("Undergraduate Studies");
            }
        }

        return interests;
    }
}



