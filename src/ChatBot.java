import java.util.*;

public class ChatBot {
    // maps command keywords to their corresponding Command implementations
    private HashMap<String, Command> ruleMap;
    // tie for auto correction
    private Trie commandTrie = new Trie();
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
    public void featureChosen(String input, Command command) {
        ruleMap.put(input, command);
        numberToCommand.put(nextCommandIndex++, input);
        commandTrie.insert(input); // insert into the trie
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

        // Create and initialize the CoursePlanner
        CoursePlanner coursePlanner = new CoursePlanner();
        try {
            coursePlanner.loadCoursesFromCSV("cis_courses.csv");
            coursePlanner.loadPrerequisitesFromCSV("prereq.csv");
        } catch (Exception e) {
            System.out.println("Warning: Error loading course data: " + e.getMessage());
        }

        // Create the AcademicPlannerUI
        AcademicPlannerUI academicPlannerUI = new AcademicPlannerUI(coursePlanner);

        // load datasets from CSV files
        List<Restaurant> restaurants = RestaurantData.loadFromCSV("../data/restaurant_data.csv");
        List<CourseReview> courseReviews = CourseReviewData.loadFromCSV("cis_courses.csv");

        // initialize core data structures
        DailyPlanner dailyPlanner = new DailyPlanner();

        // Create a custom AcademicCommand that will launch the course recommendation system
        Command academicCommand = new Command() {
            @Override
            public void execute() {
                // This launches the course recommendation system
                academicPlannerUI.start();
            }
        };


        // register commands and their associated features
        bot.featureChosen("course", new AcademicCommand(coursePlanner));
        bot.featureChosen("todo", new TodoCommand(dailyPlanner));
        bot.featureChosen("food", new FoodCommand(restaurants));
        bot.featureChosen("review", new ReviewCommand(courseReviews));

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
        List<String> candidates = commandTrie.getWordsWithPrefix(input.substring(0, 1));

        // fallback if none found
        if (candidates.isEmpty()) {
            for (String key : ruleMap.keySet()) {
                candidates.add(key);
            }
        }

        String best = null;
        int minDist = Integer.MAX_VALUE;
        for (String cmd : candidates) {
            int dist = editDistance(input, cmd);
            if (dist < minDist) {
                minDist = dist;
                best = cmd;
            }
        }

        return minDist <= 2 ? best : null;
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

//    private static void launchCourseRecommendationSystem(CoursePlanner planner) {
//        // Create the academic command
//        AcademicCommand academicCommand = new AcademicCommand(planner);
//
//        // Create scanner for user input
//        Scanner scanner = new Scanner(System.in);
//
//        // Interactive loop
//        while (true) {
//            System.out.println("\n==== Course Recommendation System ====");
//            System.out.println("1. Recommend courses by interest area");
//            System.out.println("2. Recommend courses by career path");
//            System.out.println("3. List all interest areas");
//            System.out.println("4. List all career paths");
//            System.out.println("5. Return to main menu");
//            System.out.print("Enter your choice (1-5): ");
//
//            int choice;
//            try {
//                choice = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
//                continue;
//            }
//
//            switch (choice) {
//                case 1:
//                    // Recommend by interest area
//                    System.out.println("\nAvailable interest areas:");
//                    for (String area : planner.getAllInterestAreas()) {
//                        System.out.println("- " + area);
//                    }
//                    System.out.print("\nEnter interest area: ");
//                    String interest = scanner.nextLine();
//
//                    academicCommand.setInterest(interest);
//                    System.out.print("Enter max number of recommendations: ");
//                    try {
//                        int max = Integer.parseInt(scanner.nextLine());
//                        academicCommand.setMaxRecommendations(max);
//                    } catch (NumberFormatException e) {
//                        System.out.println("Using default recommendations.");
//                    }
//
//                    academicCommand.execute();
//                    break;
//
//                case 2:
//                    // Recommend by career path
//                    System.out.println("\nAvailable career paths:");
//                    for (String path : planner.getAllCareerPaths()) {
//                        System.out.println("- " + path);
//                    }
//                    System.out.print("\nEnter career path: ");
//                    String careerPath = scanner.nextLine();
//
//                    academicCommand.setCareerPath(careerPath);
//                    System.out.print("Enter max number of recommendations: ");
//                    try {
//                        int max = Integer.parseInt(scanner.nextLine());
//                        academicCommand.setMaxRecommendations(max);
//                    } catch (NumberFormatException e) {
//                        System.out.println("Using default recommendations.");
//                    }
//
//                    academicCommand.execute();
//                    break;
//
//                case 3:
//                    // List all interest areas
//                    System.out.println("\nAll Interest Areas:");
//                    for (String area : planner.getAllInterestAreas()) {
//                        System.out.println("- " + area);
//                    }
//                    break;
//
//                case 4:
//                    // List all career paths
//                    System.out.println("\nAll Career Paths:");
//                    for (String path : planner.getAllCareerPaths()) {
//                        System.out.println("- " + path);
//                    }
//                    break;
//
//                case 5:
//                    // Return to main menu
//                    return;
//
//                default:
//                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
//            }
//        }
//    }
}