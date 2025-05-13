import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;


import static org.junit.Assert.*;

public class FoodCommandTest {
    private FoodCommand foodCommand;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;
    private String input = "";

    //Initialize the set up with food command and change the output to print stream
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    //reset the system out and after the test cases
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    //Input to be kept as the dummy string
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testBasicFoodCommandExecution() {
        // Simulate a basic happy path: Good mood, select cuisine, choose cost level, no special occasion
        provideInput("good\nno\nasian\nn\nn\n$$$\n");
        //Initialize the food command!
        foodCommand = new FoodCommand();
        foodCommand.execute();
        String output = outContent.toString();

        // Verify the flow of the command execution

        assertTrue("Ask for mood", output.contains("describe how you feel"));
        //first input is good
        assertTrue("display cuisines", output.contains("Available cuisines"));
        //second input is no -> to the do you want us to choose
        assertTrue("Ask about choosing for user", output.contains("choose for you"));
        // third input has asian
        assertTrue("user selection", output.contains("You've selected"));
        //next is saying no to add more
        assertTrue("special occasion", output.contains("special occasion"));
        //next output is no for special occasuin
        assertTrue("price range", output.contains("how much are you willing to spend"));
        // next output is $$$ as cost
        assertTrue("recommendations", output.contains("Here are your recommendations"));
    }

    @Test
    public void testBadMoodWithoutChangingSelection() {
        //Sad no no
        provideInput("sad\nn\nn\n$$\n");

        foodCommand = new FoodCommand();
        foodCommand.execute();

        String output = outContent.toString();
        //sad
        assertTrue(output.contains("describe how you feel"));
        //no
        assertTrue(output.contains("Do you wish to change the selection"));
        //no
        assertTrue(output.contains("special occasion")); // celebration prompt
        // $$
        assertTrue(output.contains("how much are you willing to spend"));
        //ok
        assertTrue(output.contains("Here are your recommendations"));
    }


    @Test
    public void testBadMoodChangeAndRandomCuisine() {
        provideInput("sad\ny\ny\nn\n$$\n");

        foodCommand = new FoodCommand();
        foodCommand.execute();

        String output = outContent.toString();

        assertTrue(output.contains("Do you wish to change the selection"));
        assertTrue(output.contains("Would you want us to choose for you"));
        assertTrue(output.contains("Here are your recommendations"));
    }

    @Test
    public void testGoodMoodRandomCuisine() {
        provideInput("good\ny\nn\n$$\n");

        foodCommand = new FoodCommand();
        foodCommand.execute();

        String output = outContent.toString();

        assertTrue(output.contains("Available cuisines"));
        assertTrue(output.contains("Would you want us to choose for you"));
        assertTrue(output.contains("Here are your recommendations"));
    }

    @Test
    public void testGoodMoodManualCuisineSelection() {
        provideInput("good\nn\nindian italian chinese\nn\n$$\n");

        foodCommand = new FoodCommand();
        foodCommand.execute();

        String output = outContent.toString();

        assertTrue(output.contains("Please select up to 3 cuisines"));
        assertTrue(output.contains("You've selected"));
        assertTrue(output.contains("Here are your recommendations"));
    }

    @Test
    public void testEarlyExit() {
        provideInput("good\nExit\n");

        foodCommand = new FoodCommand();
        foodCommand.execute();

        String output = outContent.toString();

        assertTrue(output.contains("Available cuisines"));
        assertFalse(output.contains("Here are your recommendations"));
    }
}