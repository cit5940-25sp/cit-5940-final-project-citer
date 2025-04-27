import java.util.*;

public class FoodCommand implements Command {
    private Map<String, List<Restaurant>> cuisineMap;

    public FoodCommand(List<Restaurant> dataset) {
        cuisineMap = new HashMap<>();
        for (Restaurant r : dataset) {
            cuisineMap.computeIfAbsent(r.getCuisine(), k -> new ArrayList<>()).add(r);
        }
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        // Ask which strategy
        System.out.print("Would you like [highest rated] or [random] recommendation? ");
        String strategyChoice = scanner.nextLine().trim().toLowerCase();

        FoodRecommendationStrategy strategy;
        if (strategyChoice.contains("random")) {
            strategy = new RandomStrategy();
        } else {
            strategy = new HighestRatedStrategy(); // default if unsure
        }

        // Ask cuisine
        System.out.print("What type of cuisine are you in the mood for? ");
        String choice = scanner.nextLine().trim();

        List<Restaurant> results = cuisineMap.getOrDefault(choice, new ArrayList<>());
        if (results.isEmpty()) {
            System.out.println("❌ Sorry, no recommendations available for that cuisine.");
        } else {
            System.out.println("🍴 Here are your recommendations:");
            for (Restaurant r : strategy.recommend(results)) {
                System.out.println("- " + r.getName() + " (Rating: " + r.getRating() + ")");
            }
        }
    }
}
