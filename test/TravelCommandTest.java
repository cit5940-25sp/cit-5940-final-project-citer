import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TravelCommandTest {
    private TravelCommand travelCommand;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * Test TravelData initialization
     */
    @Test
    public void testTravelDataInitialization() {
        // Use a minimal test just to verify data is loaded
        provideInput("exit\n");
        travelCommand = new TravelCommand();

        //Constructor initialises the travel data
        assertNotNull(travelCommand.travelData);
        //variable not null
        assertFalse("TravelData should have loaded tags",
                travelCommand.travelData.getTags().isEmpty());
        //Holiday data loaded
        assertFalse("Holiday data should be loaded",
                travelCommand.travelData.getHolidayData().isEmpty());
    }

    /**
     * Test the initial display of holiday data
     */
    @Test
    public void testHolidayDisplay() {
        // Simple test for initial output
        provideInput("exit\n");
        travelCommand = new TravelCommand();


        travelCommand.travelFoodUI.displayHolidayData();

        String output = outContent.toString();
        //shows penn holidats
        assertTrue(output.contains("Holiday") || output.contains("holiday"));
    }

    /**
     * Test displaying the place list
     */
    @Test
    public void testPlaceListDisplay() {
        // Simple test for place list display
        provideInput("exit\n");
        travelCommand = new TravelCommand();

        // Call UI method directly
        travelCommand.travelFoodUI.displayPlaceList();

        String output = outContent.toString();
        assertTrue("Should display categories/tags",
                !output.isEmpty() && output.length() > 50);
    }

    /**
     * Test the random travel strategy
     */
    @Test
    public void testRandomTravelStrategy() {
        // Test random strategy in isolation
        provideInput("exit\n");
        travelCommand = new TravelCommand();

        // Call strategy method directly
        travelCommand.travelFactory.getRandomTravelStrategy();

        // Verify results
        ArrayList<String> selectedTags = travelCommand.travelData.getUserTags();
        assertEquals( 3, selectedTags.size());
    }


}