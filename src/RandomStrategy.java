import java.util.*;

/**
 * The RandomStrategy class implements the FoodRecommendationStrategy interface to provide
 * a food recommendation mechanism where cuisines are selected randomly.
 * This strategy selects up to three random cuisines from the available cuisines
 * in the FoodGraph object and sets them as the user's preferred cuisines. This approach
 * is particularly suited for users looking for a surprise or those who do not have
 * specific preferences.
 */
public class RandomStrategy implements FoodRecommendationStrategy {

    /**
     * Set user cuisines method in the Random strategy. Helps user get random cuisines
     * @param foodGraph Graph object to retrieve and set the underlying graph structure
     * @param scanner Used to fetch input from the user
     */
    @Override
    public void setUserCuisines(FoodGraph foodGraph, Scanner scanner) {
        System.out.println(Colors.CYAN_BRIGHT + "Surprise it is! " + "✨ We'll pick some great options for you." + Colors.RESET);
        // Pick 3 random cuisines
        List<String> shuffledCuisines = new ArrayList<>(foodGraph.getCuisines());
        Collections.shuffle(shuffledCuisines);
        //Select 3 or less than 3 available options
        for (int i = 0; i < Math.min(3, shuffledCuisines.size()); i++) {
            //Add it to user cuisines
            foodGraph.getUserCuisines().add(shuffledCuisines.get(i));
        }

        // Display selected cuisines for confirmation
        System.out.println();
        System.out.println(Colors.CYAN_BRIGHT + "You've selected these cuisines: " + "🎯 " +
                String.join(", ", foodGraph.getUserCuisines()) + Colors.RESET);

    }
}