import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests for Course class
 */
public class CourseTest {

    private Course course;

    @Before
    public void setUp() {
        List<String> prerequisites = new ArrayList<>();
        course = new Course("CIS5200", "Machine Learning", prerequisites, 4.0, 4.2, 3.5, 3.8);
    }

    @Test
    public void testCourseGetters() {
        assertEquals("CIS5200", course.getCourseId());
        assertEquals("Machine Learning", course.getCourseName());
        assertEquals(4.0, course.getCourseQuality(), 0.001);
        assertEquals(4.2, course.getInstructorQuality(), 0.001);
        assertEquals(3.5, course.getDifficulty(), 0.001);
        assertEquals(3.8, course.getWorkRequired(), 0.001);
        assertTrue(course.getPrerequisites().isEmpty());
    }

    @Test
    public void testCourseToString() {
        String expected = "CIS5200: Machine Learning (Quality: 4.00)";
        assertEquals(expected, course.toString());
    }

    @Test
    public void testCourseToStringWithNAQuality() {
        Course naQualityCourse = new Course("CIS9999", "Test Course", new ArrayList<>(), -1, 3.0, 3.0, 3.0);
        String result = naQualityCourse.toString();
        assertFalse(result.contains("Quality"));
    }
}






