import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the ReviewCommand class
 */
public class ReviewCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private List<CourseReview> testReviews;

    @Before
    public void setUp() {
        // Redirect System.out for testing
        System.setOut(new PrintStream(outContent));

        // Create test data
        testReviews = new ArrayList<>();
        testReviews.add(new CourseReview("CIS5200", "Machine Learning", 4.0, 4.2, 3.5, 10.0));
        testReviews.add(new CourseReview("CIS5190", "Applied ML", 3.8, 3.9, 3.2, 8.5));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Test that ReviewCommand finds an existing course
     */
    @Test
    public void testFindsExistingCourse() {
        // Create command
        ReviewCommand command = new ReviewCommand(testReviews);

        // Simulate user input
        String input = "CIS5200\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute command
        command.execute();

        // Check output
        String output = outContent.toString();
        assertTrue("Should find the course review", output.contains("Review for CIS5200"));
        assertTrue("Should display course name", output.contains("Machine Learning"));
        assertTrue("Should display course quality", output.contains("Course Quality: 4.0"));
        assertTrue("Should display instructor quality", output.contains("Instructor Quality: 4.2"));
    }

    /**
     * Test that ReviewCommand handles non-existent courses
     */
    @Test
    public void testHandlesNonExistentCourse() {
        // Create command
        ReviewCommand command = new ReviewCommand(testReviews);

        // Simulate user input for non-existent course
        String input = "CIS9999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute command
        command.execute();

        // Check output
        String output = outContent.toString();
        assertTrue("Should show course not found message",
                output.contains("Course not found"));
    }

    /**
     * Test that ReviewCommand handles empty review list
     */
    @Test
    public void testHandlesEmptyReviews() {
        // Create command with empty list
        ReviewCommand command = new ReviewCommand(new ArrayList<>());

        // Simulate user input
        String input = "CIS5200\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute command
        command.execute();

        // Check output
        String output = outContent.toString();
        assertTrue("Should show course not found message",
                output.contains("Course not found"));
    }

    /**
     * Test that ReviewCommand is case-insensitive
     */
    @Test
    public void testCaseInsensitivity() {
        // Create command
        ReviewCommand command = new ReviewCommand(testReviews);

        // Simulate user input with lowercase
        String input = "cis5200\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute command
        command.execute();

        // Check output
        String output = outContent.toString();
        assertTrue("Should find the course despite case difference",
                output.contains("Review for CIS5200"));
    }

    /**
     * Test that ReviewCommand trims whitespace from input
     */
    @Test
    public void testTrimsWhitespace() {
        // Create command
        ReviewCommand command = new ReviewCommand(testReviews);

        // Simulate user input with whitespace
        String input = "  CIS5200  \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute command
        command.execute();

        // Check output
        String output = outContent.toString();
        assertTrue("Should find the course after trimming whitespace",
                output.contains("Review for CIS5200"));
    }

    /**
     * Test that ReviewCommand handles null reviews list
     */
    @Test
    public void testHandlesNullReviews() {
        try {
            // Create command with null list
            ReviewCommand command = new ReviewCommand(null);

            // Simulate user input
            String input = "CIS5200\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Execute command (should throw NullPointerException)
            command.execute();

            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected behavior
            assertTrue(true);
        }
    }

    /**
     * Test that ReviewCommand can find different courses
     */
    @Test
    public void testFindsMultipleCourses() {
        // Create command
        ReviewCommand command = new ReviewCommand(testReviews);

        // Test first course
        String input1 = "CIS5200\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        command.execute();
        String output1 = outContent.toString();
        assertTrue("Should find first course", output1.contains("Machine Learning"));

        // Reset output
        outContent.reset();

        // Test second course
        String input2 = "CIS5190\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        command.execute();
        String output2 = outContent.toString();
        assertTrue("Should find second course", output2.contains("Applied ML"));
    }
}
