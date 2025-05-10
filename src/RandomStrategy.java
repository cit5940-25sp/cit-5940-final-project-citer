import java.util.*;

public class RandomStrategy implements FoodRecommendationStrategy {


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
        System.out.println(Colors.CYAN_BRIGHT + "You've selected these cuisines: " + "🎯 " +
                String.join(", ", foodGraph.getUserCuisines()) + Colors.RESET);

    }
}