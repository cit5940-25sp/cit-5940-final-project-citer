import java.util.*;

public class FoodCommand implements Command {
    private FoodGraph foodGraph;
    private Scanner scanner;
    private CuisineStrategy cuisineStrategy;
    private TravelFoodUI travelFoodUI;

    public FoodCommand() {
        foodGraph = new FoodGraph();
        foodGraph.buildGraph("/Users/varunsingh/Desktop/Course notes/CIT 5940/Projects/CIT 594 Final/Databases/Philly Food DB V2.csv");
        scanner = new Scanner(System.in);
        cuisineStrategy = new CuisineStrategy();
        travelFoodUI = new TravelFoodUI(foodGraph);
    }

    @Override
    public void execute() {
        //Welcome message and the user mood checks
        if (travelFoodUI.moodFind(scanner)) {
            cuisineStrategy.setNotGoodM(foodGraph, scanner);

            System.out.println(Colors.YELLOW_BRIGHT + "Do you wish to change the selection ? (Y/N) " + Colors.RESET);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) {
                //Clear the previously selected strategy
                foodGraph.clearVars();
                //Display the cuisines
                travelFoodUI.displayCuisines();
                //Ask the user if they want us to choose for them
                System.out.println();
                System.out.println(Colors.GREEN_BRIGHT + "Would you want us to choose for you ? (Y/N) " + "🎲" + Colors.RESET);
                input = scanner.nextLine();

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
}
