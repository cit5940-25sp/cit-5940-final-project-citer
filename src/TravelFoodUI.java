import java.util.*;

/**
 * Provides a level of abstraction for the UI display fpr the restaurant and travel commands
 */
public class TravelFoodUI {

    /**
     * The instance of the travel data structure used to access the underlying datasets
     */
    private TravelData travelData;

    /**
     * The instance of the food graph used to access the underlying datasets
     */
    private FoodGraph foodGraph;

    /**
     * Constructor for intialising the traveldata, and used in the travel command
     * @param travelData structure
     */
    public TravelFoodUI(TravelData travelData) {
        this.travelData = travelData;
    }

    /**
     * Constructor for intialising the foodgraph, and used in the food command
     * @param foodGraph structure
     */
    public TravelFoodUI(FoodGraph foodGraph) {
        this.foodGraph = foodGraph;
    }


    /**
     * If the user wants to celebrate, then set cuisines for (Bars, Clubs, Late night)
     * @param scanner for fetching user input
     */
    public void displayCelebration(Scanner scanner) {
        System.out.println();
        System.out.println(Colors.YELLOW_BRIGHT + "Do you have a special occasion to celebrate? (Y/N) " + "🎉" + Colors.RESET);
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("y") || response.equals("yes") || response.equals("ye")) {
            foodGraph.getUserCuisines().add("bars & breweries");
            foodGraph.getUserCuisines().add("club");
            foodGraph.getUserCuisines().add("gastropub");
            foodGraph.getUserCuisines().add("dive bar");

            System.out.println(Colors.CYAN_BRIGHT + "Awesome! Added some celebration spots to your list." + " 🥂" + Colors.RESET);
        }
    }

    /**
     * Method used to display the available cuisine to the terminal
     */
    public void displayCuisines() {
        System.out.println(Colors.CYAN + "Available cuisines: " + "👨‍🍳" + Colors.RESET);
        // First, ensure we have a truly unique list
        Set<String> uniqueCuisines = new HashSet<>(foodGraph.getCuisines());
        List<String> sortedCuisines = new ArrayList<>(uniqueCuisines);
        Collections.sort(sortedCuisines);

        // Display in columns
        int columns = 3;
        int itemsPerColumn = (int) Math.ceil(sortedCuisines.size() / (double)columns);

        for (int i = 0; i < itemsPerColumn; i++) {
            StringBuilder lineBuilder = new StringBuilder("  ");

            for (int j = 0; j < columns; j++) {
                int index = i + (j * itemsPerColumn);
                if (index < sortedCuisines.size()) {
                    lineBuilder.append(String.format("%-20s", sortedCuisines.get(index)));
                }
            }
            System.out.println(Colors.WHITE_BRIGHT + lineBuilder.toString() + Colors.RESET);
        }
    }


    /**
     * This method will help set the cost parameters to be used for finding the restaurants.
     * @param scanner for fetching in the cost parameters of the user
     */
    public void displayCost(Scanner scanner) {
        System.out.println();
        System.out.println(Colors.CYAN_BRIGHT + "On a scale of $ to $$$$ how much are you willing to spend " + "💰" + Colors.RESET);
        System.out.println(Colors.CYAN + "Restaurants will be recommended around your choice" + Colors.RESET);

        String feel = scanner.nextLine();
        if (feel.isEmpty() || !feel.matches("\\$+")) {
            // Default if empty or invalid input
            foodGraph.getCost().add("$");
            foodGraph.getCost().add("$$");
        } else {
            //First add the cost entered by the user
            foodGraph.getCost().add(feel);


            if (feel.length() > 1) {
                // Add one level down if not already at minimum
                foodGraph.getCost().add(feel.substring(0, feel.length() - 1));
            }
        }
    }


    /**
     * Returns true if the mood is not good for the user!
     * @param scanner for fetching the user input when they enter how they feel
     * @return a boolean value, true if the mood is bad
     */
    public boolean moodFind(Scanner scanner) {
        System.out.println(Colors.CYAN_BOLD + "In a single word -> describe how you feel " + "😊" + Colors.RESET);
        String feel = scanner.nextLine().toLowerCase();

        boolean found =  (foodGraph.getMoodWords().contains(feel));

        foodGraph.setMood(found);

        return found;
    }

    //All methods below are related to the travel data for the chatbot


    /**
     * Displays the Penn's Calendar's data
     */
    public void displayHolidayData() {
        System.out.println(Colors.PURPLE_BRIGHT + "Before we begin here are the Holiday's from the Penn Academic Calendar \uD83C\uDF08" + Colors.RESET);

        // Define column headers
        String holidayHeader = "Holiday";
        String dateHeader = "Date";

        // Find the maximum length of holiday names for alignment
        int maxHolidayLength = holidayHeader.length();
        for (Map.Entry<String, String> entry : travelData.getHolidayData()) {
            maxHolidayLength = Math.max(maxHolidayLength, entry.getKey().length());
        }

        // Print the headers with proper formatting
        String headerFormat = "%-" + (maxHolidayLength + 2) + "s | %s%n";
        System.out.printf(headerFormat, holidayHeader, dateHeader);

        // Print a separator line
        System.out.print("-".repeat(maxHolidayLength + 2) + "-+-" + "-".repeat(15) + "\n");

        // Print each holiday and its date in the order they were added
        for (Map.Entry<String, String> entry : travelData.getHolidayData()) {
            System.out.printf(headerFormat, entry.getKey(), entry.getValue());
        }
    }


    /**
     * Displays the place categories and their associated tags
     */
    public void displayPlaceList() {
        System.out.println(Colors.CYAN_BRIGHT + "Available Categories \uD83D\uDCC1" + Colors.RESET);

        // Get all categories
        List<String> categories = new ArrayList<>(travelData.getPlaceList().keySet());

        // Sort categories alphabetically
        Collections.sort(categories);

        // Determine how many columns to use based on the length of the longest category
        int maxCategoryLength = 0;
        for (String category : categories) {
            maxCategoryLength = Math.max(maxCategoryLength, category.length());
        }

        int columnWidth = maxCategoryLength + 4; // Add some padding
        int numColumns = 5;

        // Print separator line
        System.out.println("--------------------------------------------");

        // Print categories in columns
        String columnFormat = "%-" + columnWidth + "s";
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf(columnFormat, categories.get(i));

            // Start a new line after printing the last column or at the end of the list
            if ((i + 1) % numColumns == 0 || i == categories.size() - 1) {
                System.out.println();
            }
        }

        System.out.println("--------------------------------------------");
    }


    /**
     * Method to display the locations based on the user tags
     */
    public void displayLocations() {
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "These are the locations based on your choice of categories" + " \uD83C\uDF40" + Colors.RESET);

        for (String tag : travelData.getUserTags()) {
            System.out.println(Colors.PURPLE_UNDERLINED + " \uD83C\uDF43" + tag + Colors.RESET);

            Set<String> tagSet = travelData.getPlaceList().get(tag);

            for (String s : tagSet) {
                System.out.println(Colors.CYAN_BRIGHT + s + Colors.RESET);
            }
            System.out.println();
        }
    }



    /**
     * This method displays the location data with the cost per day, the things to see and do
     * and the cuisine to eat.
     */
    public void displayLocationData(Scanner scanner) {
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "✈️ Enter the name of the location you wish to visit 🗺️ or type 'exit' to return ❌" + Colors.RESET);
        String line = scanner.nextLine().toLowerCase();
        ArrayList<String> locations = new ArrayList<>(travelData.getDestDetails().keySet());

        while (!line.equalsIgnoreCase("exit")) {
            System.out.println();

            //Implementing the auto-correct feature.
            if (!travelData.getDestDetails().containsKey(line)) {
                Trie categoryTrie = new Trie();
                for (String category : travelData.getDestDetails().keySet()) {
                    categoryTrie.insert(category.toLowerCase());
                }

                List<String> suggest = travelData.findSuggestions(categoryTrie, line, locations, 2);

                if (!suggest.isEmpty()) {
                    System.out.println(Colors.YELLOW_BOLD_BRIGHT + "🤔 Did you mean \"" + suggest.get(0) + "\"? (Yes/No) " + Colors.RESET);

                    String chh = scanner.nextLine().toLowerCase();

                    if (chh.equalsIgnoreCase("yes") || chh.equalsIgnoreCase("y")) {
                        line = suggest.get(0);
                    } else {
                        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "🔍 Please enter the name again of the location you wish to visit 🌴" + Colors.RESET);
                        line = scanner.nextLine().toLowerCase();
                        continue;
                    }
                } else {
                    System.out.println(Colors.RED_BRIGHT + "❌ Location not found! Please try again with a different name." + Colors.RESET);
                    System.out.println(Colors.PURPLE_BOLD_BRIGHT + "🔍 Enter location name: " + Colors.RESET);
                    line = scanner.nextLine().toLowerCase();
                    continue;
                }
            }

            // Location header with emojis
            System.out.println("\n" + Colors.GREEN_BOLD_BRIGHT + "🌟 DESTINATION: " + line.toUpperCase() + " 🌟" + Colors.RESET);
            System.out.println(Colors.YELLOW_BRIGHT + "💰 Average cost per day: $" + travelData.getDestDetails().get(line).getCost() + Colors.RESET);

            // Separator line
            System.out.println(Colors.CYAN_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + Colors.RESET);

            // Must Visit section
            System.out.println(Colors.CYAN_BRIGHT + "🏛️ MUST VISIT ATTRACTIONS:" + Colors.RESET);
            ArrayList<String> tags = travelData.getDestDetails().get(line).getSee();
            for (String tag : tags) {
                System.out.println(Colors.CYAN_BRIGHT + "  • " + tag + Colors.RESET);
            }
            System.out.println();

            // Activities section
            System.out.println(Colors.BLUE_BOLD_BRIGHT + "🏄 ACTIVITIES & EXPERIENCES:" + Colors.RESET);
            ArrayList<String> acts = travelData.getDestDetails().get(line).getDoStuff();
            for (String tag : acts) {
                System.out.println(Colors.BLUE_BRIGHT + "  • " + tag + Colors.RESET);
            }
            System.out.println();

            // Food section
            System.out.println(Colors.RED_BOLD_BRIGHT + "🍽️ LOCAL CUISINE & DINING:" + Colors.RESET);
            ArrayList<String> food = travelData.getDestDetails().get(line).getFood();
            for (String tag : food) {
                System.out.println(Colors.RED_BRIGHT + "  • " + tag + Colors.RESET);
            }
            System.out.println();

            System.out.println(Colors.BLUE_BOLD_BRIGHT + "Click here to find out more about this place" + Colors.RESET);
            String url = ("https://www.google.com/search?q="+line.split(" ")[0]+"%20"+ line.split(" ")[1]);
            System.out.println(Colors.BLUE_BOLD_BRIGHT + url + Colors.RESET);

            // Separator line
            System.out.println(Colors.CYAN_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + Colors.RESET);

            System.out.println(Colors.GREEN_BRIGHT + "✨ Where to next? Enter another destination or type 'exit' to return ✈️" + Colors.RESET);
            line = scanner.nextLine().toLowerCase();
        }
    }
}
