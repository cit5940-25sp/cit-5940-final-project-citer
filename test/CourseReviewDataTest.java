import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for CourseReviewData class
 */
public class CourseReviewDataTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testLoadFromCSV() throws Exception {
        // Create a test CSV file
        File testFile = tempFolder.newFile("test_reviews.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Code,Course,Course Quality,Instructor Quality,Difficulty,Work Required\n");
            writer.write("CIS5200,Machine Learning,4.0,4.2,3.5,10.0\n");
            writer.write("CIS5190,Applied ML,3.8,3.9,3.2,8.5\n");
            writer.write("CIS5220,Deep Learning,N/A,4.3,3.8,9.0\n"); // N/A should be skipped
            writer.write("CIS5500,Database Systems,3.5,3.6,3.0,7.5\n");
        }

        List<CourseReview> reviews = CourseReviewData.loadFromCSV(testFile.getAbsolutePath());

        // N/A row should be skipped
        assertEquals(3, reviews.size());

        // Check first review
        CourseReview firstReview = reviews.get(0);
        assertEquals("CIS5200", firstReview.getCode());
        assertEquals("Machine Learning", firstReview.getTitle());
        assertEquals(4.0, firstReview.getCourseQuality(), 0.001);
    }

    @Test
    public void testLoadFromCSVWithInvalidFile() {
        // Test with non-existent file
        List<CourseReview> reviews = CourseReviewData.loadFromCSV("non_existent_file.csv");

        // Should return an empty list, not throw exception
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    public void testLoadFromCSVWithInvalidFormat() throws Exception {
        // Create a test CSV file with invalid format
        File testFile = tempFolder.newFile("invalid_format.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Code,Course\n"); // Missing columns
            writer.write("CIS5200,Machine Learning\n"); // Missing values
        }

        List<CourseReview> reviews = CourseReviewData.loadFromCSV(testFile.getAbsolutePath());

        // Should return an empty list since all rows are invalid
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }
}