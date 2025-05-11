import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CourseLoader class
 */
public class CourseLoaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private CourseLoader loader;

    @Before
    public void setUp() {
        loader = new CourseLoader();
    }

    @Test
    public void testLoadCoursesFromCSV() throws Exception {
        // Create a test CSV file
        File testFile = tempFolder.newFile("test_courses.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Code,Course,Course Quality,Instructor Quality,Difficulty,Work Required\n");
            writer.write("CIS5200,Machine Learning,4.0,4.2,3.5,3.5\n");
            writer.write("CIS5190,Applied ML,3.8,3.9,3.2,3.0\n");
        }

        Map<String, Course> courses = loader.loadCoursesFromCSV(testFile.getAbsolutePath());

        assertEquals(2, courses.size());
        assertTrue(courses.containsKey("CIS5200"));
        assertTrue(courses.containsKey("CIS5190"));

        Course mlCourse = courses.get("CIS5200");
        assertEquals("Machine Learning", mlCourse.getCourseName());
        assertEquals(4.0, mlCourse.getCourseQuality(), 0.001);
    }

    @Test
    public void testLoadCoursesFromCSVWithInvalidFile() {
        // Test with non-existent file
        Map<String, Course> courses = loader.loadCoursesFromCSV("non_existent_file.csv");

        // Should return an empty map, not throw exception
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }

    @Test
    public void testLoadCoursesFromCSVWithInvalidFormat() throws Exception {
        // Create a test CSV file with invalid format
        File testFile = tempFolder.newFile("invalid_format.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Code,Course\n"); // Missing columns
            writer.write("CIS5200,Machine Learning\n"); // Too few columns
        }

        Map<String, Course> courses = loader.loadCoursesFromCSV(testFile.getAbsolutePath());

        // Should return an empty map since all rows are invalid
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }

    @Test
    public void testLoadPrerequisitesFromCSV() throws Exception {
        // Create a test CSV file
        File testFile = tempFolder.newFile("test_prereqs.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("course,prerequisite\n");
            writer.write("CIS5220,CIS5200\n");
            writer.write("CIS5220,CIS5190\n");
        }

        CourseGraph graph = new CourseGraph();
        loader.loadPrerequisitesFromCSV(testFile.getAbsolutePath(), graph);

        List<String> prereqs = graph.getPrerequisites("CIS5220");
        assertEquals(2, prereqs.size());
        assertTrue(prereqs.contains("CIS5200"));
        assertTrue(prereqs.contains("CIS5190"));
    }

    @Test
    public void testLoadPrerequisitesFromCSVWithInvalidFile() {
        CourseGraph graph = new CourseGraph();

        // Test with non-existent file
        try {
            loader.loadPrerequisitesFromCSV("non_existent_file.csv", graph);
            // If we reach here, no exception was thrown, which is good
            assertTrue(true);
        } catch (Exception e) {
            fail("Should not throw exception for missing prerequisite file");
        }
    }

}
