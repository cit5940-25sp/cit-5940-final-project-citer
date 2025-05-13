import java.util.*;

/**
 * The GoodMoodStrategy class is a concrete implementation of the
 * FoodRecommendationStrategy interface. It handles cuisine selection
 * logic when the user's mood is good and they wish to select their
 * preferred cuisines manually.
 */
public class GoodMoodStrategy implements FoodRecommendationStrategy {


    /**
     * Sets the user cuisine variable if the user mood is good.
     * @param foodGraph Graph object for accessing the underlying graph structure
     * @param scanner for fetching user input
     */

    @Override
    public void setUserCuisines(FoodGraph foodGraph, Scanner scanner) {
        //This strategy is accessed only when the user's mood is not bad and they haveset
        // not requested us to select cuisines for them.
        String response = scanner.nextLine().toLowerCase();

        String[] responseParts = response.split(" ");
        Set<String> validCuisines = new HashSet<>();
        List<String> invalidCuisines = new ArrayList<>();

        for (String selectedCuisine : responseParts) {
            selectedCuisine = selectedCuisine.trim();
            if (selectedCuisine.isEmpty())
                continue;

            // Check if exactly matches any cuisine
            boolean found = false;
            for (String cuisine : foodGraph.getCuisines()) {
                if (cuisine.equalsIgnoreCase(selectedCuisine)) {
                    validCuisines.add(cuisine);
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Try to find close matches -> uses the levenshtein distance
                String closestMatch = foodGraph.findClosestCuisine(selectedCuisine, foodGraph.getCuisines());
                if (closestMatch != null) {
                    System.out.println(Colors.YELLOW + "Did you mean '" + closestMatch + "' instead of '" +
                            selectedCuisine + "'? (y/n) " + "🤔" + Colors.RESET);
                    String confirm = scanner.nextLine().toLowerCase();
                    if (confirm.startsWith("y")) {
                        validCuisines.add(closestMatch);
                        System.out.println(Colors.GREEN_BRIGHT + "Great! Added " + closestMatch + " to your selections." + " ✅" + Colors.RESET);
                    } else {
                        invalidCuisines.add(selectedCuisine);
                    }
                } else {
                    invalidCuisines.add(selectedCuisine);
                }
            }

            // Limit to 3 cuisines
            if (validCuisines.size() >= 3) break;
        }

        // Handle case where user entered invalid cuisines
        if (!invalidCuisines.isEmpty()) {
            System.out.println(Colors.YELLOW + "These cuisines weren't recognized: " + "❓ " +
                    String.join(", ", invalidCuisines) + Colors.RESET);
        }

        // Handle case where user didn't select any valid cuisines
        if (validCuisines.isEmpty()) {
            System.out.println(Colors.YELLOW + "No valid cuisines selected. Suggesting popular options... " + "💡" + Colors.RESET);
            // Add 2-3 popular cuisines from available ones
            List<String> popularCuisines = new ArrayList<>(foodGraph.getCuisines());
            Collections.shuffle(popularCuisines);
            for (int i = 0; i < Math.min(3, popularCuisines.size()); i++) {
                validCuisines.add(popularCuisines.get(i));
            }
        }

        // If user selects less than 3 cuisines, ask if they want to add more
        if (validCuisines.size() < 3) {
            System.out.println(Colors.CYAN_BRIGHT +
                    "You've selected " + validCuisines.size() + " cuisine(s). Would you like to add more? (y/n) " + "➕" +
                    Colors.RESET);
            String addMore = scanner.nextLine().toLowerCase();

            if (addMore.startsWith("y") && validCuisines.size() < 3) {
                System.out.println(Colors.GREEN_BRIGHT + "Please select " + (3 - validCuisines.size()) +
                        " more cuisine(s): " + "👇" + Colors.RESET);
                response = scanner.nextLine().toLowerCase();

                // Process additional selections
                String[] additionalParts = response.split(" ");
                for (String additionalCuisine : additionalParts) {
                    additionalCuisine = additionalCuisine.trim();
                    if (additionalCuisine.isEmpty()) continue;

                    // Similar validation as before
                    boolean found = false;
                    for (String cuisine : foodGraph.getCuisines()) {
                        if (cuisine.equalsIgnoreCase(additionalCuisine)) {
                            validCuisines.add(cuisine);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        String closestMatch = foodGraph.findClosestCuisine(additionalCuisine, foodGraph.getCuisines());
                        if (closestMatch != null && !validCuisines.contains(closestMatch)) {
                            System.out.println(Colors.YELLOW + "Did you mean '" + closestMatch + "'? (y/n) " + "🤔" + Colors.RESET);
                            String confirm = scanner.nextLine().toLowerCase();
                            if (confirm.startsWith("y")) {
                                validCuisines.add(closestMatch);
                                System.out.println(Colors.GREEN_BRIGHT + "Added " + closestMatch + " to your selections." + " ✅" + Colors.RESET);
                            }
                        }
                    }
                    if (validCuisines.size() >= 3) break;
                }
            }
        }

        foodGraph.getUserCuisines().addAll(validCuisines);


        // Display selected cuisines for confirmation
        System.out.println();
        System.out.println(Colors.CYAN_BRIGHT + "You've selected these cuisines: " + "🎯 " +
                String.join(", ", foodGraph.getUserCuisines()) + Colors.RESET);
    }

}
