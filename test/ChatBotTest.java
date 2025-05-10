import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class ChatBotTest {

    @Test
    public void testRuleMapUpdated() throws Exception {
        ChatBot chatBot = new ChatBot();
        chatBot.featureChosen("course", null);

        HashMap<String, Command> ruleMap = getRuleMap(chatBot);
        assertTrue(ruleMap.containsKey("course"));
    }

    @Test
    public void testNumberAssignment() throws Exception {
        ChatBot chatBot = new ChatBot();
        chatBot.featureChosen("course", null);
        chatBot.featureChosen("todo", null);

        HashMap<Integer, String> numberMap = getNumberToCommand(chatBot);
        assertEquals("course", numberMap.get(1));
        assertEquals("todo", numberMap.get(2));
    }

    // Helper to access private ruleMap
    @SuppressWarnings("unchecked")
    private HashMap<String, Command> getRuleMap(ChatBot bot) throws Exception {
        java.lang.reflect.Field field = ChatBot.class.getDeclaredField("ruleMap");
        field.setAccessible(true);
        return (HashMap<String, Command>) field.get(bot);
    }

    // Helper to access private numberToCommand
    @SuppressWarnings("unchecked")
    private HashMap<Integer, String> getNumberToCommand(ChatBot bot) throws Exception {
        java.lang.reflect.Field field = ChatBot.class.getDeclaredField("numberToCommand");
        field.setAccessible(true);
        return (HashMap<Integer, String>) field.get(bot);
    }

    @Test
    public void testHandleInputInvalidCommand() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.handleInput("zzzzzz");

            String output = outContent.toString();
            boolean hasErrorMessage = output.contains("Sorry") || output.contains("don't understand");
            assertTrue(output, hasErrorMessage);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testCommandSuggestionSmallTypo() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("command", null);
            chatBot.handleInput("commnad");

            String output = outContent.toString();
            boolean hasSuggestion = output.contains("mean") && output.contains("command");
            assertTrue(output, hasSuggestion);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testCommandSuggestionNoMatch() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("command", null);
            chatBot.handleInput("zzzzzzz");

            String output = outContent.toString();
            assertFalse(output, output.contains("Did you mean"));
            assertTrue(output.contains("Sorry") || output.contains("don't understand"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testMultipleCommandsRegistration() throws Exception {
        ChatBot chatBot = new ChatBot();

        chatBot.featureChosen("first", null);
        chatBot.featureChosen("second", null);

        HashMap<String, Command> ruleMap = getRuleMap(chatBot);
        assertTrue(ruleMap.containsKey("first"));
        assertTrue(ruleMap.containsKey("second"));

        HashMap<Integer, String> numberMap = getNumberToCommand(chatBot);
        assertEquals("first", numberMap.get(1));
        assertEquals("second", numberMap.get(2));
    }

    @Test
    public void testEditDistanceAlgorithm() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("weather", null);
            chatBot.handleInput("weathor");

            String output = outContent.toString();
            assertTrue(output, output.contains("weather"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testSingleCharacterTypo() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("coffee", null);
            chatBot.handleInput("coffie");
            String output = outContent.toString();
            assertTrue(output, output.contains("coffee"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testRealisticTypo() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("schedule", null);
            chatBot.handleInput("scedule");
            String output = outContent.toString();
            assertTrue(output, output.contains("schedule"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testTrieWithCommonPrefix() {
        ChatBot chatBot = new ChatBot();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            chatBot.featureChosen("help", null);
            chatBot.featureChosen("hello", null);
            chatBot.handleInput("helo");
            String output = outContent.toString();
            boolean suggestsEither = output.contains("help") || output.contains("hello");
            assertTrue(output, suggestsEither);
        } finally {
            System.setOut(originalOut);
        }
    }
}