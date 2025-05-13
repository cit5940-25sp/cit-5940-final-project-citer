
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Tests for the AcademicPlannerUI class
 */
public class AcademicPlannerUITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private MockCoursePlanner mockPlanner;
    private MockAcademicCommand mockCommand;
    private AcademicPlannerUI ui;

    @Before
    public void setUp() {
        // Redirect System.out for testing
        System.setOut(new PrintStream(outContent));

        // Create mock objects
        mockPlanner = new MockCoursePlanner();
        mockCommand = new MockAcademicCommand(mockPlanner);

        // Add test data
        mockPlanner.addInterestArea("Machine Learning");
        mockPlanner.addInterestArea("Databases");
        mockPlanner.addCareerPath("Data Scientist");
        mockPlanner.addCareerPath("Software Engineer");

        // Create UI with mock objects
        ui = new TestableAcademicPlannerUI(mockPlanner, mockCommand);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Test edge cases for interest area recommendations
     */
    @Test
    public void testRecommendByInterestAreaEdgeCases() throws Exception {
        // Test with invalid interest area
        String input1 = "Invalid Interest\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));

        Method method = AcademicPlannerUI.class.getDeclaredMethod("recommendByInterestArea", Scanner.class);
        method.setAccessible(true);
        method.invoke(ui, new Scanner(System.in));

        String output1 = outContent.toString();
        assertTrue("Should show interest area not found message",
                output1.contains("Interest area 'Invalid Interest' not found"));
        assertFalse("Execute method should not be called for invalid interest",
                mockCommand.isExecuted());

        // Reset output and mock
        outContent.reset();
        mockCommand.reset();

        // Test with invalid max recommendations
        String input2 = "Machine Learning\ninvalid\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));

        method.invoke(ui, new Scanner(System.in));

        String output2 = outContent.toString();
        assertTrue("Should show using default recommendations message",
                output2.contains("Using default recommendations"));
        assertTrue("Execute method should be called despite invalid max",
                mockCommand.isExecuted());
    }

    /**
     * Test edge cases for career path recommendations
     */
    @Test
    public void testRecommendByCareerPathEdgeCases() throws Exception {
        // Test with invalid career path
        String input = "Invalid Career\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Method method = AcademicPlannerUI.class.getDeclaredMethod("recommendByCareerPath", Scanner.class);
        method.setAccessible(true);
        method.invoke(ui, new Scanner(System.in));

        String output = outContent.toString();
        assertTrue("Should show career path not found message",
                output.contains("Career path 'Invalid Career' not found"));
        assertFalse("Execute method should not be called for invalid career path",
                mockCommand.isExecuted());
    }

    /**
     * Test with empty data
     */
    @Test
    public void testWithEmptyData() throws Exception {
        // Create new UI with empty planner
        MockCoursePlanner emptyPlanner = new MockCoursePlanner();
        AcademicPlannerUI emptyUI = new TestableAcademicPlannerUI(emptyPlanner, mockCommand);

        // Test listInterestAreas
        Method interestMethod = AcademicPlannerUI.class.getDeclaredMethod("listInterestAreas");
        interestMethod.setAccessible(true);
        interestMethod.invoke(emptyUI);

        // Test listCareerPaths
        Method careerMethod = AcademicPlannerUI.class.getDeclaredMethod("listCareerPaths");
        careerMethod.setAccessible(true);
        careerMethod.invoke(emptyUI);

        // Verify output shows headers but no items
        String output = outContent.toString();
        assertTrue("Should show interest areas header", output.contains("All Interest Areas"));
        assertTrue("Should show career paths header", output.contains("All Career Paths"));

        // Neither should have any items listed
        assertFalse("Should not show any interest areas", output.contains("- "));
    }

    // Helper classes for testing

    /**
     * Mock CoursePlanner for testing
     */
    private static class MockCoursePlanner extends CoursePlanner {
        private Set<String> interestAreas = new HashSet<>();
        private Set<String> careerPaths = new HashSet<>();

        public void addInterestArea(String interest) {
            interestAreas.add(interest);
        }

        public void addCareerPath(String careerPath) {
            careerPaths.add(careerPath);
        }

        @Override
        public Set<String> getAllInterestAreas() {
            return interestAreas;
        }

        @Override
        public Set<String> getAllCareerPaths() {
            return careerPaths;
        }
    }

    /**
     * Mock AcademicCommand for testing
     */
    private static class MockAcademicCommand extends AcademicCommand {
        private String interest = "";
        private String careerPath = "";
        private int maxRecommendations = 4;
        private boolean isCareerPathMode = false;
        private boolean executed = false;

        public MockAcademicCommand(CoursePlanner planner) {
            super(planner);
        }

        @Override
        public void setInterest(String interest) {
            this.interest = interest;
            this.isCareerPathMode = false;
        }

        @Override
        public void setCareerPath(String careerPath) {
            this.careerPath = careerPath;
            this.isCareerPathMode = true;
        }

        @Override
        public void setMaxRecommendations(int max) {
            this.maxRecommendations = max;
        }

        @Override
        public void execute() {
            executed = true;
        }

        public String getInterest() {
            return interest;
        }

        public String getCareerPath() {
            return careerPath;
        }

        public int getMaxRecommendations() {
            return maxRecommendations;
        }

        public boolean isCareerPathMode() {
            return isCareerPathMode;
        }

        public boolean isExecuted() {
            return executed;
        }

        public void reset() {
            interest = "";
            careerPath = "";
            maxRecommendations = 4;
            isCareerPathMode = false;
            executed = false;
        }
    }

    /**
     * Testable version of AcademicPlannerUI that allows injecting mock command
     */
    private static class TestableAcademicPlannerUI extends AcademicPlannerUI {
        private AcademicCommand mockCommand;

        public TestableAcademicPlannerUI(CoursePlanner planner, AcademicCommand mockCommand) {
            super(planner);
            this.mockCommand = mockCommand;

            // Replace the academicCommand field using reflection
            try {
                java.lang.reflect.Field field = AcademicPlannerUI.class.getDeclaredField("academicCommand");
                field.setAccessible(true);
                field.set(this, mockCommand);
            } catch (Exception e) {
                System.err.println("Failed to inject mock command: " + e.getMessage());
            }
        }
    }

    /**
     * Test the menu display
     */
    @Test
    public void testMenuDisplay() {
        // Simulate user selecting exit option
        String input = "5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify menu was displayed correctly
        String output = outContent.toString();
        assertTrue("Menu should be displayed", output.contains("==== Course Recommendation System ===="));
        assertTrue("Menu should show option 1", output.contains("1. Recommend courses by interest area"));
        assertTrue("Menu should show option 2", output.contains("2. Recommend courses by career path"));
        assertTrue("Menu should show option 3", output.contains("3. List all interest areas"));
        assertTrue("Menu should show option 4", output.contains("4. List all career paths"));
        assertTrue("Menu should show option 5", output.contains("5. Return to main menu"));
    }

    /**
     * Test option 1: Recommend by interest area
     */
    @Test
    public void testRecommendByInterestAreaOption() {
        // Simulate user selecting option 1 then exit
        String input = "1\nMachine Learning\n3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify option 1 was processed correctly
        assertTrue("Interest should be set correctly", mockCommand.getInterest().equals("Machine Learning"));
        assertEquals("Max recommendations should be set correctly", 3, mockCommand.getMaxRecommendations());
        assertTrue("Execute method should be called", mockCommand.isExecuted());
        assertFalse("Career path mode should be disabled", mockCommand.isCareerPathMode());
    }

    /**
     * Test option 2: Recommend by career path
     */
    @Test
    public void testRecommendByCareerPathOption() {
        // Simulate user selecting option 2 then exit
        String input = "2\nData Scientist\n4\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify option 2 was processed correctly
        assertTrue("Career path should be set correctly", mockCommand.getCareerPath().equals("Data Scientist"));
        assertEquals("Max recommendations should be set correctly", 4, mockCommand.getMaxRecommendations());
        assertTrue("Execute method should be called", mockCommand.isExecuted());
        assertTrue("Career path mode should be enabled", mockCommand.isCareerPathMode());
    }

    /**
     * Test option 3: List interest areas
     */
    @Test
    public void testListInterestAreasOption() {
        // Simulate user selecting option 3 then exit
        String input = "3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify option 3 was processed correctly
        String output = outContent.toString();
        assertTrue("Should show interest areas header", output.contains("All Interest Areas"));
        assertTrue("Should list Machine Learning", output.contains("- Machine Learning"));
        assertTrue("Should list Databases", output.contains("- Databases"));
    }

    /**
     * Test option 4: List career paths
     */
    @Test
    public void testListCareerPathsOption() {
        // Simulate user selecting option 4 then exit
        String input = "4\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify option 4 was processed correctly
        String output = outContent.toString();
        assertTrue("Should show career paths header", output.contains("All Career Paths"));
        assertTrue("Should list Data Scientist", output.contains("- Data Scientist"));
        assertTrue("Should list Software Engineer", output.contains("- Software Engineer"));
    }

    /**
     * Test handling invalid input
     */
    @Test
    public void testInvalidInput() {
        // Simulate user entering invalid input then exit
        String input = "invalid\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify error message was displayed
        String output = outContent.toString();
        assertTrue("Should show invalid choice message",
                output.contains("Invalid choice. Please enter a number between 1 and 5"));
    }

    /**
     * Test handling out of range option
     */
    @Test
    public void testOutOfRangeOption() {
        // Simulate user selecting out of range option then exit
        String input = "6\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call start method
        ui.start();

        // Verify error message was displayed
        String output = outContent.toString();
        assertTrue("Should show invalid choice message",
                output.contains("Invalid choice. Please enter a number between 1 and 5"));
    }

}
