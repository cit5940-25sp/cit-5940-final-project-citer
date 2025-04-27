import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
    public static void main(String[] args) {
        ChatBot bot = new ChatBot();

        // Load datasets
        List<Restaurant> restaurants = RestaurantData.loadFromCSV("../data/restaurant_data.csv");
        List<CourseReview> courseReviews = CourseReviewData.loadFromCSV("../data/cis_courses.csv");

        // Set up Data Structures
        CoursePlanner coursePlanner = new CoursePlanner();
        DailyPlanner dailyPlanner = new DailyPlanner();

        // Register Commands
        bot.registerRule("course", new AcademicCommand(coursePlanner));
        bot.registerRule("todo", new TodoCommand(dailyPlanner));
        bot.registerRule("food", new FoodCommand(restaurants));
        bot.registerRule("review", new ReviewCommand(courseReviews));

        System.out.println("🤖 Welcome to the Planner Bot!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhat would you like help with?");
            System.out.println("Options: [course] [food] [todo] [review] [exit]");
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("exit")) {
                System.out.println("👋 Goodbye! Have a great day!");
                break;
            }
            bot.handleInput(input);
        }
    }

}
