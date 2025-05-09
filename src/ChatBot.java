import java.util.*;

public class ChatBot {
    // maps command keywords (like "course") to their corresponding Command implementations
    private HashMap<String, Command> ruleMap;

    // maps numeric options to command keywords for numbered selection
    private HashMap<Integer, String> numberToCommand;

    // tracks the next available number for command registration
    private int nextCommandIndex = 1;

    // constructor: initialize data structures
    public ChatBot() {
        ruleMap = new HashMap<>();
        numberToCommand = new HashMap<>();
    }

    // registers a command by keyword and also assigns it a numeric shortcut
    public void registerRule(String input, Command command) {
        ruleMap.put(input, command);
        numberToCommand.put(nextCommandIndex++, input);
    }

    // handles user input: executes the matched command or suggests a correction
    public void handleInput(String input) {
        Command command = ruleMap.get(input);
        if (command != null) {
            // run the associated feature
            command.execute();
        } else {
            String suggestion = suggestCommand(input); // Try to correct a typo
            if (suggestion != null) {
                System.out.println("❓ Did you mean \"" + suggestion + "\"?");
            } else {
                System.out.println("❌ Sorry, I don't understand that.");
            }
        }
    }

    public static void main(String[] args) {
        ChatBot bot = new ChatBot();

        // load datasets from CSV files
        List<Restaurant> restaurants = RestaurantData.loadFromCSV("../data/restaurant_data.csv");
        List<CourseReview> courseReviews = CourseReviewData.loadFromCSV("../data/cis_courses.csv");

        // initialize core data structures
        CoursePlanner coursePlanner = new CoursePlanner();
        DailyPlanner dailyPlanner = new DailyPlanner();

        // register commands and their associated features
        bot.registerRule("course", new AcademicCommand(coursePlanner));
        bot.registerRule("todo", new TodoCommand(dailyPlanner));
        bot.registerRule("food", new FoodCommand(restaurants));
        bot.registerRule("review", new ReviewCommand(courseReviews));

        System.out.println("🤖 Welcome to the Planner Bot!");
        Scanner scanner = new Scanner(System.in);

        // user interaction loop
        while (true) {
            // show available options
            System.out.println("\nWhat would you like help with?");
            for (Map.Entry<Integer, String> entry : bot.numberToCommand.entrySet()) {
                System.out.println("[" + entry.getKey() + "] " + entry.getValue());
            }
            System.out.println("[exit] to quit");
            System.out.print("> ");

            // read user input
            String input = scanner.nextLine().toLowerCase().trim();

            // handle exit case
            if (input.equals("exit")) {
                System.out.println("👋 Goodbye! Have a great day!");
                break;
            }

            // convert numeric input to command string if valid
            if (input.matches("\\d+")) {
                int num = Integer.parseInt(input);
                String mapped = bot.numberToCommand.get(num);
                if (mapped != null) {
                    input = mapped;
                }
            }

            // handle the input using ruleMap
            bot.handleInput(input);
        }
    }

    // suggests the closest command if the user input doesn't match exactly
    private String suggestCommand(String input) {
        int minDistance = Integer.MAX_VALUE;
        String suggestion = null;

        for (String command : ruleMap.keySet()) {
            int dist = editDistance(input, command);
            if (dist < minDistance) {
                minDistance = dist;
                suggestion = command;
            }
        }

        // only suggest if edit distance is small enough (≤2)
        return (minDistance <= 2) ? suggestion : null;
    }

    // computes the edit distance between two strings
    private int editDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    // inserting all characters of b
                    dp[i][j] = j;
                } else if (j == 0) {
                    // deleting all characters of a
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    // characters match
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // minimum of insert, delete, or replace
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
