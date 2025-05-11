import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * The travel data class stores the data from 3 datasets
 * The Penn Holiday Calendar, The Tag-based destination and detailed-dataset
 * This is then used by the getters/setters of the strategies to be integrated in the chatbot
 */
public class TravelData {
    //Fields in the graph

    //Private field for the holiday data set
    // A map entry used to preserve the order of the holidays.
    private List<Map.Entry<String, String>> holidayData;

    //Private field for the list of places
    private HashMap<String, Set<String>> placeList;
    private ArrayList<String> tags;

    //Private field for the destination details
    private HashMap<String, DestinationNode> destDetails;

    //User tags preference: Used by the strategies
    private ArrayList<String> userTags;


    /**
     * Default constructor that initializes the data structures
     *
     */
    public TravelData() {
        holidayData = new ArrayList<>();
        placeList = new HashMap<>();
        destDetails = new HashMap<>();
        tags = new ArrayList<>();
        userTags = new ArrayList<>();
    }


    /**
     * Method that populates the data structures from the data sets
     * Uses the reference path of the datasets -> Holiday calendar, The places and destination files
     * @param holidayFile
     * @param placeFile
     * @param destFile
     */
    public void addDataFromDataFile(String holidayFile, String placeFile, String destFile) {
        //Populate all the data structures
        makeHoliday(holidayFile);
        makeDetails(destFile);
        makePlaceTags(placeFile);
    }


    /**
     * Method to make the holiday data structure
     */
    public void makeHoliday(String holiFile) {
        try(BufferedReader bread = new BufferedReader(new FileReader(holiFile))) {
            //Read the first line as its just headers
            bread.readLine();

            String line;
            while((line = bread.readLine()) != null) {
                String[] lineParts = line.trim().split(",");
                if (lineParts.length >= 2) {
                    // Create a SimpleEntry to store key-value pairs and maintain order
                    holidayData.add(new SimpleEntry<>(lineParts[0].trim(), lineParts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Cant open file " + holiFile);
        }
    }


    /**
     * Method to make the place tags data structure
     */
    public void makePlaceTags(String placeFile) {
        try(BufferedReader bread = new BufferedReader(new FileReader(placeFile))) {
            //Read the first line as its just headers
            bread.readLine();

            String line;
            while((line = bread.readLine()) != null) {
                String[] lineParts = line.trim().toLowerCase().split(",");
                //Put the key if absent
                if (!placeList.containsKey(lineParts[0].trim())) {
                    tags.add(lineParts[0].trim());
                }
                placeList.putIfAbsent(lineParts[0].trim(), new HashSet<>());
                //Add the item if key is present
                placeList.get(lineParts[0]).add(lineParts[1].replaceAll("\"",""));
            }
        } catch (IOException e) {
            System.err.println("Cant open file " + placeFile);
        }
    }

    /**
     * Method to make the destination details data structure
     */
    public void makeDetails(String destFile) {
        try(BufferedReader bread = new BufferedReader(new FileReader(destFile))) {
            //Read the first line as its just headers
            bread.readLine();

            String line;
            while((line = bread.readLine()) != null) {
                String[] lineParts = line.trim().toLowerCase().split(",");

                DestinationNode dest = new DestinationNode();
                String[] thingsToSee = lineParts[1].trim().split(";");
                String[] thingsToDo = lineParts[2].trim().split(";");
                String[] thingsToEat = lineParts[3].trim().split(";");
                int cost = Integer.parseInt(lineParts[4].trim());
                String distance = lineParts[5].trim();

                for (String thing : thingsToSee) {
                    dest.getSee().add(thing);
                }

                for (String thing : thingsToDo) {
                    dest.getDoStuff().add(thing);
                }

                for (String thing : thingsToEat) {
                    dest.getFood().add(thing);
                }

                dest.setCost(cost);
                dest.setDistance(distance);

                destDetails.put(lineParts[0].trim(), dest);
            }
        } catch (IOException e) {
            System.err.println("Cant open file " + destFile);
        }
    }

    /**
     * Displays the Upenn's Calendar's data
     */
    public void displayHolidayData() {
        System.out.println(Colors.PURPLE_BRIGHT + "Before we begin here are the Holiday's from the Penn Academic Calendar \uD83C\uDF08" + Colors.RESET);

        // Define column headers
        String holidayHeader = "Holiday";
        String dateHeader = "Date";

        // Find the maximum length of holiday names for alignment
        int maxHolidayLength = holidayHeader.length();
        for (Map.Entry<String, String> entry : holidayData) {
            maxHolidayLength = Math.max(maxHolidayLength, entry.getKey().length());
        }

        // Print the headers with proper formatting
        String headerFormat = "%-" + (maxHolidayLength + 2) + "s | %s%n";
        System.out.printf(headerFormat, holidayHeader, dateHeader);

        // Print a separator line
        System.out.print("-".repeat(maxHolidayLength + 2) + "-+-" + "-".repeat(15) + "\n");

        // Print each holiday and its date in the order they were added
        for (Map.Entry<String, String> entry : holidayData) {
            System.out.printf(headerFormat, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Displays the place categories and their associated tags
     */
    public void displayPlaceList() {
        System.out.println(Colors.CYAN_BRIGHT + "Available Categories \uD83D\uDCC1" + Colors.RESET);

        // Get all categories
        List<String> categories = new ArrayList<>(placeList.keySet());

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
     * Method to return the tags for the given file
     * @return
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * Method to return the userTags for the given file
     * @return
     */
    public ArrayList<String> getUserTags() {
        return userTags;
    }

    /**
     * Method to clear the user tags
     */
    public void clearUserTags() {
        userTags.clear();
    }

    /**
     * Method to display the locations based on the user tags
     */
    public void displayLocations() {
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "These are the locations based on your choice of categories" + " \uD83C\uDF40" + Colors.RESET);

        for (String tag : userTags) {
            System.out.println(Colors.PURPLE_UNDERLINED + " \uD83C\uDF43" + tag + Colors.RESET);

            Set<String> tagSet = placeList.get(tag);

            for (String s : tagSet) {
                System.out.println(Colors.CYAN_BRIGHT + s + Colors.RESET);
            }
            System.out.println();
        }
    }

    /**
     * Displays the location data with enhanced colors and emojis
     */
    public void displayLocationData(Scanner scanner) {
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "✈️ Enter the name of the location you wish to visit 🗺️ or type 'exit' to return ❌" + Colors.RESET);
        String line = scanner.nextLine();
        ArrayList<String> locations = new ArrayList<>(destDetails.keySet());

        while (!line.equalsIgnoreCase("exit")) {
            System.out.println();

            //Implementing the auto-correct feature.
            if (!destDetails.containsKey(line)) {
                Trie categoryTrie = new Trie();
                for (String category : destDetails.keySet()) {
                    categoryTrie.insert(category.toLowerCase());
                }

                List<String> suggest = findSuggestions(categoryTrie, line, locations, 2);

                if (!suggest.isEmpty()) {
                    System.out.println(Colors.YELLOW_BOLD_BRIGHT + "🤔 Did you mean \"" + suggest.get(0) + "\"? (Yes/No) " + Colors.RESET);

                    String chh = scanner.nextLine();

                    if (chh.equalsIgnoreCase("yes") || chh.equalsIgnoreCase("y")) {
                        line = suggest.get(0);
                    } else {
                        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "🔍 Please enter the name again of the location you wish to visit 🌴" + Colors.RESET);
                        line = scanner.nextLine();
                        continue;
                    }
                } else {
                    System.out.println(Colors.RED_BRIGHT + "❌ Location not found! Please try again with a different name." + Colors.RESET);
                    System.out.println(Colors.PURPLE_BOLD_BRIGHT + "🔍 Enter location name: " + Colors.RESET);
                    line = scanner.nextLine();
                    continue;
                }
            }

            // Location header with emojis
            System.out.println("\n" + Colors.GREEN_BOLD_BRIGHT + "🌟 DESTINATION: " + line.toUpperCase() + " 🌟" + Colors.RESET);
            System.out.println(Colors.YELLOW_BRIGHT + "💰 Average cost per day: $" + destDetails.get(line).getCost() + Colors.RESET);

            // Separator line
            System.out.println(Colors.CYAN_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + Colors.RESET);

            // Must Visit section
            System.out.println(Colors.CYAN_BRIGHT + "🏛️ MUST VISIT ATTRACTIONS:" + Colors.RESET);
            ArrayList<String> tags = destDetails.get(line).getSee();
            for (String tag : tags) {
                System.out.println(Colors.CYAN_BRIGHT + "  • " + tag + Colors.RESET);
            }
            System.out.println();

            // Activities section
            System.out.println(Colors.BLUE_BOLD_BRIGHT + "🏄 ACTIVITIES & EXPERIENCES:" + Colors.RESET);
            ArrayList<String> acts = destDetails.get(line).getDoStuff();
            for (String tag : acts) {
                System.out.println(Colors.BLUE_BRIGHT + "  • " + tag + Colors.RESET);
            }
            System.out.println();

            // Food section
            System.out.println(Colors.RED_BOLD_BRIGHT + "🍽️ LOCAL CUISINE & DINING:" + Colors.RESET);
            ArrayList<String> food = destDetails.get(line).getFood();
            for (String tag : food) {
                System.out.println(Colors.RED_BRIGHT + "  • " + tag + Colors.RESET);
            }

            // Separator line
            System.out.println(Colors.CYAN_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + Colors.RESET);

            System.out.println(Colors.GREEN_BRIGHT + "✨ Where to next? Enter another destination or type 'exit' to return ✈️" + Colors.RESET);
            line = scanner.nextLine();
        }
    }


    /**
     * Finds suggestions based on Trie prefix search and Levenshtein distance
     * @param trie The trie containing all valid words
     * @param input The user input to find suggestions for
     * @param allCategories Set of all valid categories
     * @param maxDistance Maximum edit distance to consider
     * @return List of suggestions ordered by relevance
     */
    public List<String> findSuggestions(Trie trie, String input, ArrayList<String> allCategories, int maxDistance) {
        // First try to find prefix matches from the trie
        List<String> prefixMatches = trie.getWordsWithPrefix(input);

        // If we have prefix matches, prioritize them
        if (!prefixMatches.isEmpty()) {
            return prefixMatches;
        }

        // Otherwise, use Levenshtein distance to find close matches
        Map<String, Integer> distanceMap = new HashMap<>();

        for (String category : allCategories) {
            int distance = levenshteinDistance(input, category.toLowerCase());
            if (distance <= maxDistance) {
                distanceMap.put(category, distance);
            }
        }

        // Sort by edit distance (closest first)
        List<String> result = new ArrayList<>(distanceMap.keySet());
        result.sort(Comparator.comparingInt(distanceMap::get));

        return result;
    }

    /**
     * Calculates the Levenshtein distance between two strings
     * @param a First string
     * @param b Second string
     * @return The edit distance between the strings
     */
    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }

}