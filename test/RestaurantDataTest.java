import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.*;

public class RestaurantDataTest {
    private FoodGraph foodGraph;
    private GoodMoodStrategy strategy;
    private NotGoodMoodStrategy notStrategy;
    private RandomStrategy randO;
    private CuisineStrategy cuisineStrategy;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayInputStream testIn;


    @Before
    public void setUp() {
        foodGraph = new FoodGraph();
        strategy = new GoodMoodStrategy();
        cuisineStrategy = new CuisineStrategy();
        notStrategy = new NotGoodMoodStrategy();
        randO = new RandomStrategy();

        //For stream testing
        System.setOut(new PrintStream(outContent));

        // Set up some test cuisines
        Set<String> testCuisines = new HashSet<>();
        testCuisines.add("italian");
        testCuisines.add("chinese");
        testCuisines.add("indian");
        testCuisines.add("mexican");

        foodGraph = new FoodGraph() {

            //Overriding the getCuisines method in foodgraph
            //to return our mock cuisines created above
            @Override
            public Set<String> getCuisines() {
                return testCuisines;
            }
        };
    }


    //Restore streams after each use
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testGoodMoodStrategy() {

        //TESTING THE FACTORY METHOD
        //Simulating the user entering italian and chinese followed by a n on the newline
        // y is for the typo being corrected to italian
        // y is for the strategy asking for additional cuisine
        // mexican is entered for that
        provideInput("italiana chinese\ny\ny\nmexican");

        //Test the cuisine strategy factory and the good mood strategy inside the strategy with that
        cuisineStrategy.setGoodMoodStrategy(foodGraph, new Scanner(System.in));

        // Check that our selection is present in the setting
        assertTrue("Should contain italian", foodGraph.getUserCuisines().contains("italian"));
        //check that chinese is present in the user cuisine preference
        assertTrue("Should contain chinese", foodGraph.getUserCuisines().contains("chinese"));
        assertTrue("Should contain chinese", foodGraph.getUserCuisines().contains("mexican"));

        assertEquals("Should have 3 cuisines", 3, foodGraph.getUserCuisines().size());

        // Check output contains the cuisines
        String output = outContent.toString();
        assertTrue("Should list the cuisines",
                output.contains("italian") && output.contains("chinese") && output.contains("mexican"));

        foodGraph.clearVars();
    }

    @Test
    public void testNotGoodMoodStrategy() {

        cuisineStrategy.setNotGoodM(foodGraph, new Scanner(System.in));

        assertTrue("Should contain coffeshop", foodGraph.getUserCuisines().contains("coffeeshop"));
        assertTrue("Should contain bakery", foodGraph.getUserCuisines().contains("bakery"));
        assertTrue("Should contain ice cream", foodGraph.getUserCuisines().contains("ice cream"));
        assertTrue("Should contain italian", foodGraph.getUserCuisines().contains("italian"));

        assertEquals("Should have 4 cuisines", 4, foodGraph.getUserCuisines().size());
        // Check output contains the cuisines
        String output = outContent.toString();
        assertTrue("Should list the cuisines",
                output.contains("coffeeshop") && output.contains("bakery") &&
                        output.contains("italian") && output.contains("ice cream"));

        foodGraph.clearVars();
    }

    @Test
    public void testRandomStrategy() {
        //Since strategy is random -> we have checked only the size
        cuisineStrategy.randomStrat(foodGraph, new Scanner(System.in));

        assertEquals("Should have 3 cuisines", 3, foodGraph.getUserCuisines().size());

    }


    @Test
    public void testGoodMoodStrategyWithAllInvalidCuisines() {
        // Test what happens when all entered cuisines are invalid
        provideInput("foobar bazqux\nn");

        // Mock findClosestCuisine to return null (no matches found)
        foodGraph = new FoodGraph() {
            @Override
            public Set<String> getCuisines() {
                Set<String> cuisines = new HashSet<>();
                cuisines.add("italian");
                cuisines.add("chinese");
                cuisines.add("indian");
                cuisines.add("mexican");
                return cuisines;
            }

            @Override
            public String findClosestCuisine(String input, Set<String> availableCuisines) {
                return null;
            }
        };

        cuisineStrategy.setGoodMoodStrategy(foodGraph, new Scanner(System.in));

        // Check that popular options were suggested (3 cuisines)
        assertEquals("Should have 3 cuisines from popular options", 3, foodGraph.getUserCuisines().size());

        // Verify output
        String output = outContent.toString();
        assertTrue("Should mention cuisines weren't recognized",
                output.contains("weren't recognized"));
        assertTrue("Should mention suggesting popular options",
                output.contains("Suggesting popular options"));

        foodGraph.clearVars();
    }

    @Test
    public void testRandomStrategyOutputMessages() {
        // Test that random strategy gives proper messages
        cuisineStrategy.randomStrat(foodGraph, new Scanner(System.in));

        String output = outContent.toString();
        assertTrue("Should mention surprise", output.contains("Surprise it is!"));
        assertTrue("Should show selected cuisines",
                output.contains("You've selected these cuisines"));

        foodGraph.clearVars();
    }

    @Test
    public void testNotGoodMoodStrategyOutputMessages() {
        // Test that not good mood strategy gives proper messages
        cuisineStrategy.setNotGoodM(foodGraph, new Scanner(System.in));

        String output = outContent.toString();
        assertTrue("Should express sympathy",
                output.contains("sorry to hear that you dont feel good"));
        assertTrue("Should mention cheering up",
                output.contains("recommend some restaurants to cheer you up"));

        foodGraph.clearVars();
    }
}