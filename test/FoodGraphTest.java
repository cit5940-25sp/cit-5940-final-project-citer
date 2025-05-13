import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.Set;

import static org.junit.Assert.*;

public class FoodGraphTest {
    private FoodGraph foodGraph;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        foodGraph = new FoodGraph();
        // Redirect System.out to our ByteArrayOutputStream
        System.setOut(new PrintStream(outContent));

        // Create sample data for testing - this might need to be adjusted based on your actual implementation
        foodGraph.buildGraph("data/food_dataset.csv");
    }


    /**
     *
     */
    @Test
    public void testBuildGraphNodes() {

        // Test that we have the correct number of cuisines
        assertEquals("Should have 44 cuisines", 44, foodGraph.getCuisines().size());

        // Test that we have the correct number of restaurants
        assertEquals("Should have 423 restaurants", 423, foodGraph.getNumRestaurants());
    }

    @After
    public void restoreStreams() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void findSuggestedRestaurants() {
        // Set up test data
        foodGraph.getUserCuisines().add("asian");
        foodGraph.getUserCuisines().add("brunch");
        foodGraph.getUserCuisines().add("club");
        //Test with only $$$
        foodGraph.getCost().add("$$$");


        //Calling find suggestions
        foodGraph.findSuggestions();

        // Get the captured output as a string
        String output = outContent.toString();

        // Perform assertions on the output
        assertTrue("Output should contain Buddakan in asian category",
                output.contains("buddakan"));

        assertTrue("Output should contain  Mawn in asian category",
                output.contains("mawn"));

        assertTrue("Output should contain  vesper center city in clubs category",
                output.contains("vesper center city"));

        assertFalse("Output should not contain front street in brunch category as its $$",
                output.contains("front street"));


        // Check for specific cuisines in the output
        assertTrue("Output should contain asian cuisine",
                output.contains("asian"));
        assertTrue("Output should contain brunch cuisine",
                output.contains("brunch"));
        assertTrue("Output should contain club cuisine",
                output.contains("club"));

        // Check for cost level in the output
        assertTrue("Output should contain $$$ cost level",
                output.contains("($$$)"));

        // Verify that user cuisines and cost lists were cleared
        assertTrue("User cuisines should be empty after findSuggestions",
                foodGraph.getUserCuisines().isEmpty());
        assertTrue("Cost list should be empty after findSuggestions",
                foodGraph.getCost().isEmpty());
    }

    @Test
    public void testSetAndGetMood(){
        boolean check = true;
        foodGraph.setMood(check);

        assertTrue("Mood should be true", foodGraph.getMood());
    }


    @Test
    public void testClosestCuisine() {
        Set<String> cuisineSet = foodGraph.getCuisines();
        //Test cuisines to check the auto correct feature
        String inputCuisine1 = "amurican";
        String inputCuisine2 = "chinose";
        String inputCuisine3 = "indaaan";


        //Check if trie/ levenshtein distance suggests the correct word
        String check1 = foodGraph.findClosestCuisine(inputCuisine1, cuisineSet);
        assertEquals("american", check1);

        String check2 = foodGraph.findClosestCuisine(inputCuisine2, cuisineSet);
        assertEquals("chinese", check2);

        String check3 = foodGraph.findClosestCuisine(inputCuisine3, cuisineSet);
        assertEquals("indian", check3);
    }
}