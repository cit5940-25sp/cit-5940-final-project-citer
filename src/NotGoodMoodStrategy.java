import java.util.Scanner;

/**
 * This is a strategy for setting the user preference in case the mood is not good
 */
public class NotGoodMoodStrategy implements FoodRecommendationStrategy{

    /**
     * Sets the user cuisines preferences based on the strategy for when the user
     * is not feeling good. Adds predefined cuisine options to the user's preferences
     * and displays a message to cheer them up.
     *
     * @param foodGraph the data structure holding information about the user's food preferences
     * @param scanner the scanner object to interact with user input
     */
    @Override
    public void setUserCuisines(FoodGraph foodGraph, Scanner scanner) {
        System.out.println(Colors.CYAN_BRIGHT + "So sorry to hear that you dont feel good \uD83D\uDE1E");
        System.out.println(Colors.CYAN_BRIGHT + "We will recommend some restaurants to cheer you up !");
        foodGraph.getUserCuisines().add("coffeeshop");
        foodGraph.getUserCuisines().add("bakery");
        foodGraph.getUserCuisines().add("ice cream");
        foodGraph.getUserCuisines().add("italian");

        System.out.println(Colors.CYAN_BRIGHT + "We've selected these cuisines: " + "🎯 " +
                String.join(", ", foodGraph.getUserCuisines()) + Colors.RESET);

        //Check with the user if they would like something else but do not have celebration
        //for the bad mood
    }
}
