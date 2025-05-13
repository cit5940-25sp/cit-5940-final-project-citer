import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

//This class will test the travel strategies
public class TravelStrategyTest {
    private TravelData travelData;
    private TravelStrategyFactory strategyFactory;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private InputStream originalIn;

    @Before
    public void setUp() {
        travelData = new TravelData();

        travelData.addDataFromDataFile("data/Holidays_calendar.csv", "data/place_and_category.csv", "data/destination_details.csv");



        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(outContent));
    }

    //method to restore the streamss
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }


    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void testRandomTravelStrategySelectsThreeTags() {
        Scanner scanner = new Scanner(System.in);
        RandomTravelStrategy rand = new RandomTravelStrategy();
        rand.setTag(travelData, scanner);

        //Should select 3 random tags
        assertEquals( 3, travelData.getUserTags().size());

        String output = outContent.toString();
    }

    @Test
    public void testUserTravelStrategyWithValidTags() {
        provideInput("historic foodie urban\n");

        UserTravelStrategy strategy = new UserTravelStrategy();
        strategy.setTag(travelData, new Scanner(System.in));

        ArrayList<String> selected = travelData.getUserTags();

        assertTrue("Should contain 'historic'", selected.contains("historic"));
        assertTrue("Should contain 'foodie'", selected.contains("foodie"));
        assertTrue("Should contain 'urban'", selected.contains("urban"));
        assertEquals("Should have 3 selected tags", 3, selected.size());
    }

    @Test
    public void testUserTravelStrategyWithTypos() {
        provideInput("histoiric foddie\nY\nY\n");

        UserTravelStrategy strategy = new UserTravelStrategy();
        strategy.setTag(travelData, new Scanner(System.in));

        ArrayList<String> selected = travelData.getUserTags();

        assertTrue(selected.contains("historic"));
        assertTrue(selected.contains("foodie"));
        assertEquals(2, selected.size());

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean"));
    }

    @Test
    public void testUserTravelStrategyWithTooManyTags() {
        provideInput("historic politics urban bridges\n");

        UserTravelStrategy strategy = new UserTravelStrategy();
        strategy.setTag(travelData, new Scanner(System.in));

        //Too many inputs -> leads to empty result
        assertTrue( travelData.getUserTags().isEmpty());

        String output = outContent.toString();
        assertTrue(output.contains("Please enter a maximum of 3 categories"));
    }

    @Test
    public void testTravelStrategyFactoryUsage() {
        provideInput("museums hiking\nY\nN\n");

        strategyFactory = new TravelStrategyFactory(travelData, new Scanner(System.in));

        strategyFactory.getUserTravelStrategy();

        assertEquals( 2, travelData.getUserTags().size());

        travelData.clearUserTags();
        strategyFactory.getRandomTravelStrategy();
        assertEquals("Should store 3 random tags", 3, travelData.getUserTags().size());
    }
}
