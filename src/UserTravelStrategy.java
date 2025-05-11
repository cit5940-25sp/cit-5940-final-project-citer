import java.util.*;

/**
 * This is the general strategy that the user will be using to set the categories/ tags
 */
public class UserTravelStrategy implements TravelTagRecommendation {

    private int MAX_TAGS = 3;
    private int MAX_SUGGESTIONS = 5;
    private static final int MAX_EDIT_DISTANCE = 2;

    /**
     * Helps the user set the travel tags
     * @param travelData to access the datasets
     * @param scanner scanner to read in user input
     */
    @Override
    public void setTag(TravelData travelData, Scanner scanner) {
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "Enter categories or tags (separated by spaces)" + Colors.RESET);

        //Get the input from the user
        String tags = scanner.nextLine();
        //Split the input basis whitespace
        String[] tagArray = tags.toLowerCase().split(" ");

        //If the length > 3 ask the user to limit their selection
        if (tagArray.length > MAX_TAGS) {
            System.out.println( Colors.PURPLE_BOLD_BRIGHT + "Please enter a maximum of " + MAX_TAGS + " categories/tags." + Colors.RESET);
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
            List<String> suggestions = travelData.findSuggestions(categoryTrie, lowercaseInput, travelData.getTags(), MAX_EDIT_DISTANCE);

            if (suggestions.isEmpty()) {
                System.out.println("No close matches found for: " + userInput);
            } else {
                System.out.println("Did you mean ❓" + suggestions.getFirst());
                tags = scanner.nextLine();
                if (tags.equalsIgnoreCase("Y")|| tags.equalsIgnoreCase("YES")) {
                    selectedCategories.add(suggestions.getFirst());
                }
            }
        }

        // Process the selected categories
        if (selectedCategories.isEmpty()) {
            System.out.println("No valid categories selected.");
        } else {
            System.out.println("\nSelected categories:");
            for (String category : selectedCategories) {
                System.out.println(Colors.PURPLE_BOLD_BRIGHT + "- " + category + "🔆" + Colors.RESET);
                // Display tags for the selected category
                travelData.getUserTags().add(category);
                }
            }
        System.out.println();

        travelData.displayLocations();
        }
}