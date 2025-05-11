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

        // If we have results from the trie -> use them
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

    public List<Map.Entry<String, String>> getHolidayData() {
        return holidayData;
    }

    public HashMap<String, Set<String>> getPlaceList() {
        return placeList;
    }

    public HashMap<String, DestinationNode> getDestDetails() {
        return destDetails;
    }

}