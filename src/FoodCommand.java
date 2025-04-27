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
        System.out.print("What type of cuisine are you in the mood for? ");
        String choice = scanner.nextLine();

        List<Restaurant> results = cuisineMap.getOrDefault(choice, new ArrayList<>());
        if (results.isEmpty()) {
            System.out.println("Sorry, no recommendations available for that cuisine.");
        } else {
            System.out.println("Here are some " + choice + " food suggestions:");
            for (Restaurant r : results) {
                System.out.println("- " + r.getName() + " (Rating: " + r.getRating() + ")");
            }
        }
    }
}