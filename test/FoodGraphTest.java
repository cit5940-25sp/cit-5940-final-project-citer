import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import static org.junit.Assert.*;

public class FoodGraphTest {

    private FoodGraph foodGraph;

    @Before
    public void setUp() {
        foodGraph = new FoodGraph();
    }

    @Test
    public void testBuildGraphNodes() {
        foodGraph.buildGraph("Databases/Philly Food DB V2.csv");

        // Test that we have the correct number of cuisines
        assertEquals("Should have 44 cuisines", 44, foodGraph.getCuisines().size());

        // Test that we have the correct number of restaurants
        assertEquals("Should have 423 restaurants", 423, foodGraph.getNumRestaurants());
    }


//    @Test
//    public void testBuildGraph_AddsCorrectLocations() {
//        foodGraph.buildGraph(testCsvFile.getAbsolutePath());
//
//        Map<String, Map<String, PriorityQueue<Node>>> nodes = foodGraph.getNodes();
//
//        assertTrue("Italian cuisine should have downtown location",
//                nodes.get("italian").containsKey("downtown"));
//        assertTrue("Chinese cuisine should have uptown location",
//                nodes.get("chinese").containsKey("uptown"));
//        assertTrue("Indian cuisine should have midtown location",
//                nodes.get("indian").containsKey("midtown"));
//    }
//
//    @Test
//    public void testBuildGraph_AddsCorrectRestaurantProperties() {
//        foodGraph.buildGraph(testCsvFile.getAbsolutePath());
//
//        Map<String, Map<String, PriorityQueue<Node>>> nodes = foodGraph.getNodes();
//
//        // Check that Italian downtown has 2 restaurants
//        PriorityQueue<Node> italianDowntown = nodes.get("italian").get("downtown");
//        assertEquals("Italian downtown should have 2 restaurants", 2, italianDowntown.size());
//
//        // Extract and check first restaurant (should be lower price first due to PriorityQueue)
//        Node restaurant1 = italianDowntown.poll();
//        assertEquals("test restaurant 1", restaurant1.getName());
//        assertEquals(15.5, restaurant1.getPrice(), 0.001);
//        assertTrue(restaurant1.isVegetarian());
//
//        // Extract and check second restaurant
//        Node restaurant2 = italianDowntown.poll();
//        assertEquals("test restaurant 2", restaurant2.getName());
//        assertEquals(20.0, restaurant2.getPrice(), 0.001);
//        assertFalse(restaurant2.isVegetarian());
//    }
//
//    @Test
//    public void testBuildGraph_HandlesNonExistentFile() {
//        // Test with a file that doesn't exist
//        foodGraph.buildGraph("non_existent_file.csv");
//
//        // Verify that nothing was added and no exceptions were thrown
//        assertEquals(0, foodGraph.getNumRestaurants());
//    }
//
//    @Test
//    public void testBuildGraph_CuisineAddedToTrie() {
//        foodGraph.buildGraph(testCsvFile.getAbsolutePath());
//
//        // Test that cuisines were added to the Trie by using search
//        // Note: This assumes you have a way to access the Trie or search functionality
//        assertTrue("Italian should be found in the cuisine trie",
//                foodGraph.findCuisineSuggestions("ital").contains("italian"));
//        assertTrue("Chinese should be found in the cuisine trie",
//                foodGraph.findCuisineSuggestions("chi").contains("chinese"));
//    }
//
//    @Test
//    public void testBuildGraph_CaseInsensitivity() throws IOException {
//        // Create a test file with mixed case
//        File mixedCaseFile = tempFolder.newFile("mixed_case.csv");
//        FileWriter writer = new FileWriter(mixedCaseFile);
//        writer.write("name,cuisine,location,price,vegetarian\n");
//        writer.write("Test Restaurant,Italian,Downtown,15.5,yes\n");
//        writer.close();
//
//        foodGraph.buildGraph(mixedCaseFile.getAbsolutePath());
//
//        // Test that everything is stored in lowercase
//        assertTrue(foodGraph.getCuisines().contains("italian"));
//        assertFalse(foodGraph.getCuisines().contains("Italian"));
//
//        Map<String, Map<String, PriorityQueue<Node>>> nodes = foodGraph.getNodes();
//        assertTrue(nodes.containsKey("italian"));
//        assertTrue(nodes.get("italian").containsKey("downtown"));
//    }
}
