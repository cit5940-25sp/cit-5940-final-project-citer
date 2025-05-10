import java.util.List;

public interface FoodRecommendationStrategy {
    List<Restaurant> recommend(List<Restaurant> options);
}

