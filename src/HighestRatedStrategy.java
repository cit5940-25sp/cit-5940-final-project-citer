import java.util.*;

public class HighestRatedStrategy implements FoodRecommendationStrategy {
    @Override
    public List<Restaurant> recommend(List<Restaurant> options) {
        options.sort((a, b) -> Double.compare(b.getRating(), a.getRating())); // Sort descending
        return options.subList(0, Math.min(3, options.size()));
    }
}
