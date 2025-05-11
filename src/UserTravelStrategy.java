import java.util.*;

/**
 * This is the general strategy that the user will be using to set the categories/ tags
 */
public class UserTravelStrategy implements TravelTagRecommendation {

    /*
     * Maximum edit distance is used when the Tries are not successful in returning suggestions
     */
    private int maxEditDist = 2;
    private TravelFoodUI tfi;


    /**
     * Helps the user set the travel tags
     * @param travelData to access the datasets
     * @param scanner scanner to read in user input
     */
    @Override
    public void setTag(TravelData travelData, Scanner scanner) {

        tfi = new TravelFoodUI(travelData);
        //Get the input from the user
        String tags = scanner.nextLine();
        //Split the input basis whitespace
        String[] tagArray = tags.toLowerCase().split(" ");

        //If the length > 3 ask the user to limit their selection
        if (tagArray.length > 3) {
            System.out.println(Colors.RED_BOLD_BRIGHT + "⚠️ Please enter a maximum of " + 3 + " categories/tags." + Colors.RESET);
            return;
        }

        // Build a trie with all valid categories from travelData
        Trie categoryTrie = new Trie();
        for (String category : travelData.getTags()) {
            categoryTrie.insert(category.toLowerCase());
        }

        // For each user input, find the closest matches
        List<String> selectedCategories = new ArrayList<>();

        for (String userInput : tagArray) {
            if (userInput.trim().isEmpty()) continue;

            String lowercaseInput = userInput.toLowerCase().trim();

            // Check if the exact category exists
            if (travelData.getTags().contains(userInput)) {
                selectedCategories.add(userInput);
                continue;
            }

            // Try to find closest matching categories
            List<String> suggestions = travelData.findSuggestions(categoryTrie, lowercaseInput, travelData.getTags(), maxEditDist);

            if (suggestions.isEmpty()) {
                System.out.println(Colors.YELLOW_BOLD_BRIGHT + "❌ No close matches found for: " + userInput + Colors.RESET);
            } else {
                System.out.println(Colors.CYAN_BOLD_BRIGHT + "🔍 Did you mean \"" + suggestions.getFirst() + "\"? (Y/N) " + Colors.RESET);
                tags = scanner.nextLine();
                if (tags.equalsIgnoreCase("Y")|| tags.equalsIgnoreCase("YES")) {
                    selectedCategories.add(suggestions.getFirst());
                }
            }
        }

        // Process the selected categories
        if (selectedCategories.isEmpty()) {
            System.out.println(Colors.YELLOW_BOLD_BRIGHT + "📝 No valid categories selected." + Colors.RESET);
        } else {
            System.out.println(Colors.GREEN_BOLD_BRIGHT + "\n✅ Selected categories:" + Colors.RESET);
            for (String category : selectedCategories) {
                System.out.println(Colors.PURPLE_BOLD_BRIGHT + "  🏷️ " + category + Colors.RESET);
                // Display tags for the selected category
                travelData.getUserTags().add(category);
            }
        }
        System.out.println();

        tfi.displayLocations();
    }
}
