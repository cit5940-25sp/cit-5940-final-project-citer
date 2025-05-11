import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CoursePlanner class
 */
public class CoursePlannerTest {

    private CoursePlanner planner;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        planner = new CoursePlanner();

        // Capture System.out for testing
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddCourse() {
        List<String> prerequisites = Arrays.asList("CIS5100");
        Course course = new Course("CIS5200", "Machine Learning", prerequisites, 4.0, 4.0, 3.5, 3.5);

        planner.addCourse(course);

        List<Course> allCourses = planner.getAllCourses();
        assertEquals(1, allCourses.size());
        assertEquals(course, allCourses.get(0));
    }

    @Test
    public void testAddCourseToInterest() {
        // Add a course
        List<String> emptyPrereqs = new ArrayList<>();
        Course course = new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5);
        planner.addCourse(course);

        // Add course to interest area
        planner.addCourseToInterest("CIS5200", "Machine Learning");

        // Verify interest areas
        Set<String> interests = planner.getAllInterestAreas();
        assertTrue(interests.contains("Machine Learning"));
    }

    @Test
    public void testAddPrerequisite() {
        // Add courses
        List<String> emptyPrereqs = new ArrayList<>();
        planner.addCourse(new Course("CIS5100", "Foundations", emptyPrereqs, 4.0, 4.0, 3.0, 3.0));
        planner.addCourse(new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5));

        // Add prerequisite relationship
        planner.addPrerequisite("CIS5200", "CIS5100");

        // Check dependent courses
        List<Course> dependents = planner.findDependentCourses("CIS5100");
        assertEquals(1, dependents.size());
        assertEquals("CIS5200", dependents.get(0).getCourseId());
    }

    @Test
    public void testRecommendCoursesWithNoInterestArea() {
        List<CourseRecommendation> recommendations = planner.recommendCourses("Non-existent");

        assertTrue(recommendations.isEmpty());
        assertTrue(outContent.toString().contains("No courses found for interest: Non-existent"));
    }

    @Test
    public void testRecommendCoursesWithNoCoursesInInterest() {
        // Add an empty interest area
        try {
            Field interestManagerField = planner.getClass().getDeclaredField("interestManager");
            interestManagerField.setAccessible(true);
            InterestAreaManager interestManager = (InterestAreaManager) interestManagerField.get(planner);

            // Add a course to interest area but not to planner
            interestManager.addCourseToInterest("CIS5200", "Test Interest");
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }

        List<CourseRecommendation> recommendations = planner.recommendCourses("Test Interest");

        // Should be empty since course doesn't exist in planner
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendCoursesWithValidInterest() {
        // Add a course
        List<String> emptyPrereqs = new ArrayList<>();
        Course course = new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5);
        planner.addCourse(course);

        // Add course to interest area
        planner.addCourseToInterest("CIS5200", "Machine Learning");

        List<CourseRecommendation> recommendations = planner.recommendCourses("Machine Learning");

        assertEquals(1, recommendations.size());
        assertEquals("CIS5200", recommendations.get(0).getRecommendedCourse().getCourseId());
    }

    @Test
    public void testRecommendCoursesWithPrerequisites() {
        // Add courses with prerequisite relationship
        List<String> emptyPrereqs = new ArrayList<>();
        Course prereqCourse = new Course("CIS5100", "Foundations", emptyPrereqs, 3.5, 3.6, 3.0, 3.0);
        Course mainCourse = new Course("CIS5200", "Machine Learning", Arrays.asList("CIS5100"), 4.0, 4.0, 3.5, 3.5);

        planner.addCourse(prereqCourse);
        planner.addCourse(mainCourse);
        planner.addPrerequisite("CIS5200", "CIS5100");

        // Add to interest area
        planner.addCourseToInterest("CIS5200", "Machine Learning");

        List<CourseRecommendation> recommendations = planner.recommendCourses("Machine Learning");

        assertEquals(1, recommendations.size());
        CourseRecommendation recommendation = recommendations.get(0);
        assertEquals("CIS5200", recommendation.getRecommendedCourse().getCourseId());
        assertEquals(1, recommendation.getPrerequisites().size());
        assertEquals("CIS5100", recommendation.getPrerequisites().get(0).getCourseId());
    }

    @Test
    public void testRecommendCoursesForCareerPath() {
        // Add courses
        List<String> emptyPrereqs = new ArrayList<>();
        Course course1 = new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5);
        Course course2 = new Course("CIS5500", "Database Systems", emptyPrereqs, 3.5, 3.6, 3.0, 3.0);

        planner.addCourse(course1);
        planner.addCourse(course2);

        // Add to interest areas
        planner.addCourseToInterest("CIS5200", "Machine Learning");
        planner.addCourseToInterest("CIS5500", "Databases");

        // Test with Data Scientist career path (should include both ML and Databases)
        List<CourseRecommendation> recommendations = planner.recommendCoursesForCareerPath("Data Scientist");

        // Should contain both courses
        assertEquals(2, recommendations.size());
        Set<String> courseIds = new HashSet<>();
        for (CourseRecommendation rec : recommendations) {
            courseIds.add(rec.getRecommendedCourse().getCourseId());
        }
        assertTrue(courseIds.contains("CIS5200"));
        assertTrue(courseIds.contains("CIS5500"));
    }

    @Test
    public void testRecommendCoursesForNonExistentCareerPath() {
        List<CourseRecommendation> recommendations = planner.recommendCoursesForCareerPath("Non-existent");

        assertTrue(recommendations.isEmpty());
        assertTrue(outContent.toString().contains("No interests found for career path: Non-existent"));
    }

    @Test
    public void testDuplicateRemovalInCareerPath() {
        // Add a course
        List<String> emptyPrereqs = new ArrayList<>();
        Course course = new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5);
        planner.addCourse(course);

        // Add to multiple interest areas in the same career path
        planner.addCourseToInterest("CIS5200", "Machine Learning");
        planner.addCourseToInterest("CIS5200", "Data Science");

        // Get recommendations for Data Scientist (includes both ML and Data Science)
        List<CourseRecommendation> recommendations = planner.recommendCoursesForCareerPath("Data Scientist");

        // Count occurrences of CIS5200
        int count = 0;
        for (CourseRecommendation rec : recommendations) {
            if (rec.getRecommendedCourse().getCourseId().equals("CIS5200")) {
                count++;
            }
        }

        // Should appear only once despite being in multiple interest areas
        assertEquals(1, count);
    }

    @Test
    public void testCourseSortingByQuality() {
        // Add courses with different quality ratings
        List<String> emptyPrereqs = new ArrayList<>();
        Course highQualityCourse = new Course("CIS5200", "High Quality", emptyPrereqs, 4.5, 4.0, 3.0, 3.0);
        Course mediumQualityCourse = new Course("CIS5210", "Medium Quality", emptyPrereqs, 3.5, 4.0, 3.0, 3.0);
        Course lowQualityCourse = new Course("CIS5220", "Low Quality", emptyPrereqs, 2.5, 4.0, 3.0, 3.0);
        Course naQualityCourse = new Course("CIS5230", "N/A Quality", emptyPrereqs, -1, 4.0, 3.0, 3.0);

        planner.addCourse(highQualityCourse);
        planner.addCourse(mediumQualityCourse);
        planner.addCourse(lowQualityCourse);
        planner.addCourse(naQualityCourse);

        // Add all to same interest area
        planner.addCourseToInterest("CIS5200", "Test Area");
        planner.addCourseToInterest("CIS5210", "Test Area");
        planner.addCourseToInterest("CIS5220", "Test Area");
        planner.addCourseToInterest("CIS5230", "Test Area");

        // Get recommendations
        List<CourseRecommendation> recommendations = planner.recommendCourses("Test Area");

        // Check sorting order
        assertEquals(4, recommendations.size());
        assertEquals("CIS5200", recommendations.get(0).getRecommendedCourse().getCourseId()); // Highest quality first
        assertEquals("CIS5210", recommendations.get(1).getRecommendedCourse().getCourseId());
        assertEquals("CIS5220", recommendations.get(2).getRecommendedCourse().getCourseId());
        assertEquals("CIS5230", recommendations.get(3).getRecommendedCourse().getCourseId()); // N/A quality last
    }
}

