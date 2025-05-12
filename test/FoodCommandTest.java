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
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    //Initialize the set up with food command and change the output to print stream
    @Before
    public void setUp() {
        foodCommand = new FoodCommand();
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

//    @Test
//    public void testBasicFoodCommandExecution() {
//        // Simulate a basic happy path: Good mood, select cuisine, choose cost level, no special occasion
//        provideInput("good\nno\nasian\nn\nn\n$$$\n");
//
//        foodCommand.execute();
//
//        String output = outContent.toString();
//
//        // Verify the flow of the command execution
//
//        assertTrue("Ask for mood", output.contains("describe how you feel"));
//        //first input is good
//        assertTrue("display cuisines", output.contains("Available cuisines"));
//        //second input is no -> to the do you want us to choose
//        assertTrue("Ask about choosing for user", output.contains("choose for you"));
//        // third input has asian
//        assertTrue("user selection", output.contains("You've selected"));
//        //next is saying no to add more
//        assertTrue("special occasion", output.contains("special occasion"));
//        //next output is no for special occasuin
//        assertTrue("price range", output.contains("how much are you willing to spend"));
//        // next output is $$$ as cost
//        assertTrue("recommendations", output.contains("Here are your recommendations"));
//    }
}