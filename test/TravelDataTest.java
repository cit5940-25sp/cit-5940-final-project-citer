import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;

import static org.junit.Assert.*;

public class TravelDataTest {

    private TravelData travelData;

    @Before
    public void setUp() {
        travelData = new TravelData();
        travelData.addDataFromDataFile(
                "data/Holidays_calendar.csv",
                "data/place_and_category.csv",
                "data/destination_details.csv"
        );
    }

    @Test
    public void testHolidayDataIsLoaded() {
        List<Map.Entry<String, String>> holidays = travelData.getHolidayData();
        assertNotNull("Holiday data should not be null", holidays);
        assertEquals(12, holidays.size());

    }

    @Test
    public void testPlaceListIsLoaded() {
        Map<String, Set<String>> placeList = travelData.getPlaceList();
        assertNotNull("Place list should not be null", placeList);
        //Should be 100 unique categories mapped to places as in the databse
        assertEquals(100, placeList.size());

    }

    @Test
    public void testDestinationDetailsAreLoaded() {
        Map<String, DestinationNode> details = travelData.getDestDetails();
        assertNotNull("Destination details should not be null", details);

        //There should be 87 unique locations in the dataset
        assertEquals(87, details.size());

        //checking if all nodes initialised well
        for (Map.Entry<String, DestinationNode> entry : details.entrySet()) {
            DestinationNode node = entry.getValue();
                assertNotNull(node.getSee());
            assertNotNull(node.getDoStuff());
            assertNotNull(node.getFood());
            break;
        }
    }

    @Test
    public void testClearUserTags() {
        ArrayList<String> tags = travelData.getUserTags();
        tags.add("beach");
        tags.add("hiking");

        //check if adding tags -> lets the user tags getting updated
        assertEquals(2, travelData.getUserTags().size());

        travelData.clearUserTags();
        //User tags should be empty after clearing
        assertTrue( travelData.getUserTags().isEmpty());
    }

    @Test
    public void testFindSuggestions() {
        // Build mock trie and category list
        Trie trie = new Trie();
        trie.insert("hiking");
        trie.insert("skiing");

        ArrayList<String> allTags = new ArrayList<>(Arrays.asList("hiking", "camping", "biking", "climbing"));

        List<String> result = travelData.findSuggestions(trie, "hik", allTags, 2);
        //returns the auto corrected version of hiking
        assertTrue( result.contains("hiking"));

        //returns the auto corrected version of camping
        List<String> result2 = travelData.findSuggestions(trie, "campng", allTags, 2);
        assertTrue(result2.contains("camping"));
    }

}
