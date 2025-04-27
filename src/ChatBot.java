import java.util.HashMap;

public class ChatBot {
    private HashMap<String, Command> ruleMap;

    public ChatBot() {
        ruleMap = new HashMap<>();
    }

    public void registerRule(String input, Command command) {
        ruleMap.put(input, command);
    }

    public void handleInput(String input) {
        Command command = ruleMap.get(input);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Sorry, I don't understand that.");
        }
    }
}
