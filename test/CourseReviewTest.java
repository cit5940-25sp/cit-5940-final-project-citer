import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for CourseReview class
 */
public class CourseReviewTest {

    @Test
    public void testCourseReviewCreation() {
        CourseReview review = new CourseReview("CIS5200", "Machine Learning", 4.0, 4.2, 3.5, 10.0);

        assertEquals("CIS5200", review.getCode());
        assertEquals("Machine Learning", review.getTitle());
        assertEquals(4.0, review.getCourseQuality(), 0.001);
        assertEquals(4.2, review.getInstructorQuality(), 0.001);
        assertEquals(3.5, review.getDifficulty(), 0.001);
        assertEquals(10.0, review.getWorkload(), 0.001);
    }

    @Test
    public void testCourseReviewToString() {
        CourseReview review = new CourseReview("CIS5200", "Machine Learning", 4.0, 4.2, 3.5, 10.0);
        String result = review.toString();

        assertTrue(result.contains("CIS5200: Machine Learning"));
        assertTrue(result.contains("Course Quality: 4.0"));
        assertTrue(result.contains("Instructor Quality: 4.2"));
        assertTrue(result.contains("Difficulty: 3.5"));
        assertTrue(result.contains("Workload: 10.0 hrs/week"));
    }
}
