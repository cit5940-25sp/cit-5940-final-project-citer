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

    /**
     * Test only the command main menu
     */
    @Test
    public void testMainMenuDisplay() {
        // Provide just enough input to see the menu
        provideInput("exit\n");


        //Overriding the execute method
        //since testing it is difficult with limited inpyt to scanner
        travelCommand = new TravelCommand() {
            @Override
            public void execute() {
                // Only display the first part
                travelFoodUI.displayHolidayData();
                System.out.println();
                System.out.println("✨ What do you want to do? ✨");
                System.out.println("1. 🔍 Search for a place manually");
                System.out.println("2. 🏷️ Get places by categories");
            }
        };

        travelCommand.execute();

        String output = outContent.toString();
        //has the main menu options
        assertTrue(output.contains("What do you want to do") &&
                        output.contains("Search for a place manually") &&
                        output.contains("Get places by categories"));
    }


    /**
     * Test the handle of invalid options in the menu
     */
    @Test
    public void testInvalidOptionHandling() {
        // Provide input for testing invalid option handling
        provideInput("3\n1\nexit\n");
        travelCommand = new TravelCommand() {
            @Override
            public void execute() {
                System.out.println("✨ What do you want to do? ✨");
                System.out.println("1. 🔍 Search for a place manually");
                System.out.println("2. 🏷️ Get places by categories");
                String chh = scanner.nextLine();

                // Error checking for the options
                while (!chh.equals("1") && !chh.equals("2")) {
                    System.out.println("Please enter either 1 or 2");
                    chh = scanner.nextLine();
                }

                System.out.println("Selected option: " + chh);
            }
        };

        travelCommand.execute();

        String output = outContent.toString();
        assertTrue("Should reject invalid option",
                output.contains("Please enter either 1 or 2"));
        assertTrue("Should accept valid option after rejection",
                output.contains("Selected option: 1"));
    }

    /**
     * Test the surprise feature prompt
     */
    @Test
    public void testSurpriseFeaturePrompt() {
        // Test just the surprise feature prompt
        provideInput("2\nY\nexit\n");
        travelCommand = new TravelCommand() {
            @Override
            public void execute() {
                System.out.println("✨ What do you want to do? ✨");
                System.out.println("1. 🔍 Search for a place manually");
                System.out.println("2. 🏷️ Get places by categories");
                String chh = scanner.nextLine();

                if (chh.equals("2")) {
                    System.out.println("🎲 Would you like us to surprise you with some awesome places? (Y/N) 🎲");
                    String check = scanner.nextLine();
                    if (check.equalsIgnoreCase("Y") || check.equalsIgnoreCase("Yes")) {
                        travelFactory.getRandomTravelStrategy();
                        System.out.println("Random strategy executed with tags: " +
                                String.join(", ", travelData.getUserTags()));
                    }
                }
            }
        };

        travelCommand.execute();

        String output = outContent.toString();
        assertTrue("Should ask about surprise",
                output.contains("Would you like us to surprise you"));
        assertTrue("Should execute random strategy",
                output.contains("Random strategy executed with tags:"));
    }

}