import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for CourseRecommendation class
 */
public class CourseRecommendationTest {

    private Course course1, course2, course3;

    @Before
    public void setUp() {
        List<String> emptyPrereqs = new ArrayList<>();
        course1 = new Course("CIS5200", "Machine Learning", emptyPrereqs, 4.0, 4.0, 3.5, 3.5);
        course2 = new Course("CIS5190", "Applied ML", emptyPrereqs, 3.8, 3.9, 3.2, 3.0);
        course3 = new Course("CIS5220", "Deep Learning", emptyPrereqs, 4.2, 4.3, 3.8, 3.7);
    }

    @Test
    public void testCourseRecommendationGetters() {
        List<Course> prereqs = Arrays.asList(course1, course2);
        CourseRecommendation recommendation = new CourseRecommendation(course3, prereqs);

        assertEquals(course3, recommendation.getRecommendedCourse());
        assertEquals(2, recommendation.getPrerequisites().size());
        assertEquals(course1, recommendation.getPrerequisites().get(0));
        assertEquals(course2, recommendation.getPrerequisites().get(1));
    }

    @Test
    public void testCourseRecommendationToString() {
        List<Course> prereqs = Arrays.asList(course1, course2);
        CourseRecommendation recommendation = new CourseRecommendation(course3, prereqs);

        String result = recommendation.toString();

        assertTrue(result.contains("Recommended Course: " + course3));
        assertTrue(result.contains("Prerequisites (in order):"));
        assertTrue(result.contains("1. " + course1));
        assertTrue(result.contains("2. " + course2));
        assertTrue(result.contains("Course Quality: 4.20"));
        assertTrue(result.contains("Instructor Quality: 4.30"));
        assertTrue(result.contains("Difficulty: 3.80"));
        assertTrue(result.contains("Work Required: 3.70"));
    }

    @Test
    public void testCourseRecommendationToStringNoPrerequisites() {
        CourseRecommendation recommendation = new CourseRecommendation(course1, new ArrayList<>());
        String result = recommendation.toString();
        assertTrue(result.contains("No prerequisites required."));
    }

    @Test
    public void testCourseRecommendationToStringNARatings() {
        Course naRatingsCourse = new Course("CIS9999", "Test Course", new ArrayList<>(), -1, -1, -1, -1);
        CourseRecommendation recommendation = new CourseRecommendation(naRatingsCourse, new ArrayList<>());

        String result = recommendation.toString();
        assertTrue(result.contains("Course Quality: N/A"));
        assertTrue(result.contains("Instructor Quality: N/A"));
        assertTrue(result.contains("Difficulty: N/A"));
        assertTrue(result.contains("Work Required: N/A"));
    }
}
