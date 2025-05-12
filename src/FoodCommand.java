import java.util.*;

/**
 * This is the food command class which is executed by the chatbot
 * It implements the Command interface
 */
public class FoodCommand implements Command {
    /**
     * Private field for the food graph
     */
    private FoodGraph foodGraph;
    /**
     * Private field for scanner
     */
    private Scanner scanner;
    /**
     * Private field for the cuisine strategy factory
     */
    private CuisineStrategy cuisineStrategy;
    /**
     * Private field for the food UI
     */
    private TravelFoodUI travelFoodUI;

    /**
     * No argument constructor used to initialize all fields in the underlying class
     */
    public FoodCommand() {
        foodGraph = new FoodGraph();
        foodGraph.buildGraph("Databases/Philly Food DB V2.csv");
        scanner = new Scanner(System.in);
        cuisineStrategy = new CuisineStrategy();
        travelFoodUI = new TravelFoodUI(foodGraph);
    }

    /**
     * The execute command from the interface is implemented here
     */
    @Override
    public void execute() {
        //Welcome message and the user mood checks
        if (travelFoodUI.moodFind(scanner)) {
            cuisineStrategy.setNotGoodM(foodGraph, scanner);

            System.out.println(Colors.YELLOW_BRIGHT + "Do you wish to change the selection ? (Y/N) " + Colors.RESET);
            String input = scanner.nextLine();
            checkResponse(input, scanner);
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) {
                //Clear the previously selected strategy
                foodGraph.clearVars();
                //Display the cuisines
                travelFoodUI.displayCuisines();
                //Ask the user if they want us to choose for them
                System.out.println();
                System.out.println(Colors.GREEN_BRIGHT + "Would you want us to choose for you ? (Y/N) " + "🎲" + Colors.RESET);
                input = scanner.nextLine();

                checkResponse(input, scanner);

                if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) {
                    cuisineStrategy.randomStrat(foodGraph, scanner);
                } else {
                    System.out.println(Colors.GREEN_BRIGHT + "Please select up to 3 cuisines (separated by spaces) " + "🌮 🍕 🍜" + Colors.RESET);
                    cuisineStrategy.setGoodMoodStrategy(foodGraph, scanner);
                }
            }
        } else {
            //Run the good mood strategy!
            travelFoodUI.displayCuisines();
            System.out.println(Colors.GREEN_BRIGHT + "Would you want us to choose for you ? (Y/N) " + "🎲" + Colors.RESET);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Exit")) {
                return;
            }

            checkResponse(input, scanner);

            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) {
                cuisineStrategy.randomStrat(foodGraph, scanner);
            } else {
                System.out.println(Colors.GREEN_BRIGHT + "Please select up to 3 cuisines (separated by spaces) " + "🌮 🍕 🍜" + Colors.RESET);
                cuisineStrategy.setGoodMoodStrategy(foodGraph, scanner);
            }
        }
        travelFoodUI.displayCelebration(scanner);
        travelFoodUI.displayCost(scanner);
        foodGraph.findSuggestions();
    }

    private void checkResponse(String input, Scanner scanner) {
        while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("Yes") &&
                !input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("No")) {
            System.out.println("Please enter a valid option");
            input = scanner.nextLine();
        }
    }
}
