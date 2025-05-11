import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests for InterestAreaManager class
 */
public class InterestAreaManagerTest {

    private InterestAreaManager manager;

    @Before
    public void setUp() {
        manager = new InterestAreaManager();
    }

    @Test
    public void testInitializedInterestAreas() {
        Set<String> areas = manager.getAllInterestAreas();
        assertNotNull(areas);
        assertFalse(areas.isEmpty());
        assertTrue(areas.contains("Machine Learning"));
        assertTrue(areas.contains("Data Science"));
        assertTrue(areas.contains("Algorithms"));
    }

    @Test
    public void testInitializedCareerPaths() {
        Set<String> paths = manager.getAllCareerPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
        assertTrue(paths.contains("Data Scientist"));
        assertTrue(paths.contains("Software Engineer"));
    }

    @Test
    public void testAddAndGetCoursesInInterest() {
        manager.addCourseToInterest("CIS5200", "Machine Learning");
        manager.addCourseToInterest("CIS5190", "Machine Learning");

        List<String> courses = manager.getCoursesInInterest("Machine Learning");
        assertEquals(2, courses.size());
        assertTrue(courses.contains("CIS5200"));
        assertTrue(courses.contains("CIS5190"));
    }

    @Test
    public void testAddDuplicateCourseToInterest() {
        manager.addCourseToInterest("CIS5200", "Machine Learning");
        manager.addCourseToInterest("CIS5200", "Machine Learning"); // Add duplicate

        List<String> courses = manager.getCoursesInInterest("Machine Learning");
        assertEquals(1, courses.size()); // Should only contain one instance
    }

    @Test
    public void testGetCoursesForNonExistentInterest() {
        List<String> courses = manager.getCoursesInInterest("Non-existent Interest");
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }

    @Test
    public void testGetInterestsForCareerPath() {
        List<String> interests = manager.getInterestsForCareerPath("Data Scientist");
        assertNotNull(interests);
        assertFalse(interests.isEmpty());
        assertTrue(interests.contains("Machine Learning"));
        assertTrue(interests.contains("Data Science"));
    }

    @Test
    public void testGetInterestsForNonExistentCareerPath() {
        List<String> interests = manager.getInterestsForCareerPath("Non-existent Career");
        assertNotNull(interests);
        assertTrue(interests.isEmpty());
    }

    @Test
    public void testDetermineInterestsForMachineLearningCourse() {
        List<String> interests = manager.determineInterests("CIS5200", "Machine Learning");
        assertTrue(interests.contains("Machine Learning"));
    }

    @Test
    public void testDetermineInterestsForDatabaseCourse() {
        List<String> interests = manager.determineInterests("CIS550", "Database & Info Systems");
        assertTrue(interests.contains("Databases"));
        assertTrue(interests.contains("Data Science"));
    }

    @Test
    public void testDetermineInterestsForGenericCourse() {
        List<String> interests = manager.determineInterests("CIS9999", "Generic Course");
        assertFalse(interests.isEmpty()); // Should assign some default interest
    }
}