import java.util.*;

/**
 * This is the strategy interface for food recommendation algorithms
 */
public interface FoodRecommendationStrategy {

    /**
     * Each strategy helps set the cuisines in the food graph basis the user's preference
     * @param foodGraph Graph object to retrieve and set the underlying graph structure
     * @param scanner Used to fetch input from the user
     */
    public void setUserCuisines(FoodGraph foodGraph, Scanner scanner);

}

