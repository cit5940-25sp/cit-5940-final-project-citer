import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author VarunS.
 *
 */
public class FoodGraph {

    private HashMap<String, HashMap<String, PriorityQueue<Node>>> nodes;

    //To keep track of the number of edges:
    private int numEdges;

    //Number of restaurants in the data set
    private int numRestaurants;

    //Number of cuisines in the data set
    private Set<String> cuisines;

    //An arrayList to populate bad mood words for the user
    private ArrayList<String> moodWords;


    //These fields help determine the criteria for the user.
    private boolean mood;
    private ArrayList<String> cost;
    private boolean time;
    private Set<String> userCuisines;

    //For auto correct
    private Trie cuisineTrie;



    /**
     * Constructor for initializing the graph to a null state.
     * and define the other fields in the graph.
     */
    public FoodGraph() {
        nodes = new HashMap<>();
        numEdges = 0;
        numRestaurants = 0;
        cuisines = new HashSet<>();
        cost = new ArrayList<>();
        userCuisines = new HashSet<>();
        time = false;

        cuisineTrie = new Trie();


        //Set of bad mood words
        moodWords = new ArrayList<>(List.of(
                "terrible","sad", "angry", "anxious", "frustrated", "annoyed", "upset", "depressed", "lonely", "scared", "nervous", "insecure", "jealous", "bitter", "resentful", "hopeless", "helpless",
                "guilty", "ashamed", "embarrassed", "regretful", "heartbroken", "rejected", "desperate",
                "envious", "paranoid", "moody", "restless", "unsettled", "cynical", "apathetic", "irritable",
                "pessimistic", "panicked", "distraught", "devastated", "tearful", "tense", "stressed", "jittery",
                "uneasy", "worthless", "numb", "overwhelmed", "fearful", "withdrawn", "furious", "raging",
                "seething", "hostile", "contemptuous", "belligerent", "grumpy", "snappy", "cold", "dismissive",
                "condescending", "aggressive", "passive-aggressive", "spiteful", "vindictive", "sarcastic",
                "defensive", "abrasive", "combative", "antagonistic", "critical", "judgemental", "condemning",
                "distracted", "disoriented", "confused", "foggy", "forgetful", "overthinking", "unmotivated",
                "indecisive", "doubtful", "disconnected", "unfocused", "suspicious", "mistrustful", "wary",
                "obsessive", "ruminating", "burned out", "exhausted", "drained", "weary", "sluggish", "sleepy",
                "burnt out", "fatigued", "dull", "blank", "heavy", "powerless", "slow", "lethargic", "groggy",
                "unrefreshed", "alienated", "misunderstood", "ignored", "left out", "neglected", "unloved",
                "unappreciated", "inadequate", "inferior", "insignificant", "useless", "flawed", "broken",
                "abandoned", "defeated", "shamed", "melancholy", "hopelessness", "misery", "anguish", "wretched",
                "sorrowful", "downcast", "despair", "dejected", "mournful", "bleak", "forlorn", "dismal", "disheartened", "anguished",
                "brokenhearted", "gloomy", "morose", "tragic", "somber", "low", "blue", "crestfallen",
                "disillusioned", "crushed", "shattered", "tormented", "grief-stricken", "lonesome", "abandoned",
                "disgusted", "revolted", "nauseated", "disturbed", "grossed out", "appalled", "horrified",
                "terrified", "petrified", "dreadful", "panicky", "alarmed", "terrorized", "shaky", "skittish",
                "frantic", "hysterical", "tortured", "exasperated", "provoked", "infuriated", "irate", "cross",
                "incensed", "offended", "outraged", "upset", "aggravated", "antsy", "edgy", "jaded", "fed up",
                "displeased", "troubled", "distraught", "uneasy", "skeptical", "doubtful", "hesitant", "torn",
                "uncertain", "conflicted", "disrespected", "overloaded", "burdened", "defeated", "unwanted",
                "disregarded", "overlooked", "excluded", "misjudged", "unheard", "small", "crummy", "lousy",
                "inferior", "diminished", "insulted", "abused", "violated", "neglected", "bullied", "oppressed",
                "cheated", "manipulated", "exploited", "used", "sabotaged", "betrayed", "let down", "victimized",
                "shunned", "belittled", "mocked", "ridiculed", "disgraced", "punished", "condemned", "scorned",
                "buried", "doomed", "forgotten", "hollow", "unstable", "in pain", "mentally drained", "tired", "stressed", "overwhelmed", "burned out", "exhausted", "sleepy", "unmotivated",
                "frustrated", "irritated", "bored", "sad", "lonely", "worried", "anxious", "confused",
                "behind", "lost", "procrastinating", "pressured", "drained", "cramming", "nervous",
                "insecure", "underprepared", "restless", "distracted", "sluggish", "fed up", "done",
                "unfocused", "mentally tired", "falling apart", "sick", "blank", "falling behind",
                "low energy", "spacing out", "can't think", "too much to do", "can't focus", "rushed",
                "checked out", "mentally foggy", "over it", "dead inside", "empty", "zoned out", "dizzy",
                "overthinking", "annoyed", "grumpy", "hungry", "sleep-deprived", "out of it", "ugh", "stuck",
                "tired", "stressed", "overwhelmed", "exhausted", "unmotivated", "sleepy",
                "frustrated", "irritated", "bored", "sad", "lonely", "worried", "anxious",
                "confused", "behind", "lost", "pressured", "drained", "nervous", "insecure",
                "restless", "distracted", "sluggish", "hopeless", "defeated", "blank",
                "disengaged", "withdrawn", "cynical", "low", "moody", "panicked",
                "empty", "hungry", "cranky", "grumpy", "disappointed", "disheartened",
                "shaky", "snappy", "gloomy", "groggy", "tense", "burned", "listless",
                "foggy", "overthinking", "melancholy", "downcast", "jaded", "fogged",
                "unfocused", "slow", "angsty", "fed", "numb", "lethargic", "lonesome",
                "crushed", "dizzy", "overworked", "unrested", "crammed", "unready",
                "fatigued", "unhappy", "spacing", "fuming", "unwell", "disrupted","bad"
        ));
    }

    /**
     * Method to build the graph with the list of restaurants.
     * The file has to be a neatly formatted csv file.
     * @param filePath
     */

    public void buildGraph(String filePath) {
        try (BufferedReader bread = new BufferedReader(new FileReader(filePath))) {
            //This is for skipping the headers
            String line  = bread.readLine();

            while ((line = bread.readLine()) != null) {
                //Split the line based on commas
                line = line.toLowerCase();
                String[] parts = line.trim().split(",");

                // Create a boolean flag and set it to value from the file.
                boolean flag = parts[4].equals("yes");

                //Put if Absent to populate the node
                nodes.putIfAbsent(parts[1], new HashMap<>());

                // Add cuisine to the set and the Trie
                if (cuisines.add(parts[1].trim())) {
                    // Add to Trie when a new cuisine is discovered
                    cuisineTrie.insert(parts[1]);
                }

                nodes.get(parts[1]).putIfAbsent(parts[2], new PriorityQueue<>());

                //Add the element to the hashmap

                nodes.get(parts[1]).get(parts[2]).add(new Node(parts[0],
                        Double.parseDouble(parts[3]),flag));
                numRestaurants++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }


    /**
     * Method to traverse the graph and find
     *
     */
    public void findSuggestions() {
        HashMap<String, HashSet<String>> suggestions = new HashMap<>();


        for (String cuisine : userCuisines) {
            HashSet<String> restSet = new HashSet<>();
            suggestions.put(cuisine, restSet);

            for (String costLevel : cost) {
                PriorityQueue<Node> queue = nodes.get(cuisine).get(costLevel);
                if (queue == null) continue;

                // Use an iterator so we don't modify the queue destructively
                Iterator<Node> iter = queue.iterator();
                while (iter.hasNext() && restSet.size() < 3) {
                    restSet.add((iter.next().getName() + " (" + costLevel + ")"));
                }

                if (restSet.size() >= 3) break; // Stop if we've found 3
            }
        }

        System.out.println(Colors.CYAN_BOLD + "🍴 Here are your recommendations:" + Colors.RESET);

        for (Map.Entry<String, HashSet<String>> entry : suggestions.entrySet()) {
            //Print the cuisine as blue underlined first
            System.out.println(Colors.CYAN_UNDERLINED + "📋 " + entry.getKey() + Colors.RESET);

            //Then print the restaurants in the entry
            for (String rest : entry.getValue()) {
                System.out.println(Colors.YELLOW_BRIGHT + "  ✨ " + rest + Colors.RESET);
            }
        }
        clearVars();
    }

    /**
     * Calculates how different two strings are (edit distance) (Borrowed from CIT 5960)
     */
    private int editDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    /**
     * Finds the closest matching cuisine from the available options using Trie
     * @param input User input to match
     * @param availableCuisines The available set of cuisines
     * @return The closest match, or null if no good match found
     */
    public String findClosestCuisine(String input, Set<String> availableCuisines) {
        // First check for exact match or substring match
        for (String cuisine : availableCuisines) {
            if (cuisine.toLowerCase().contains(input.toLowerCase()) ||
                    input.toLowerCase().contains(cuisine.toLowerCase())) {
                return cuisine; // Direct partial match
            }
        }

        // Start with a prefix search if the input is at least 1 character long
        if (!input.isEmpty()) {
            // Get words with same starting character(s)
            List<String> candidates = cuisineTrie.getWordsWithPrefix(input.substring(0, 1));

            if (!candidates.isEmpty()) {
                // Find the candidate with lowest edit distance
                String bestMatch = null;
                int bestDistance = Integer.MAX_VALUE;

                for (String candidate : candidates) {
                    int distance = editDistance(input.toLowerCase(), candidate.toLowerCase());
                    int threshold = Math.max(2, input.length() / 3);

                    if (distance < bestDistance && distance <= threshold) {
                        bestDistance = distance;
                        bestMatch = candidate;
                    }
                }
                return bestMatch;
            }
        }

        return null; // No good match found
    }


    /**
     * Returns true if the mood is not good for the user!
     * @param scanner
     * @return
     */
    public boolean moodFind(Scanner scanner) {
        System.out.println(Colors.CYAN_BOLD + "In a single word -> describe how you feel " + "😊" + Colors.RESET);
        String feel = scanner.nextLine().toLowerCase();
        mood =  (moodWords.contains(feel));

        return mood;
    }

    /**
     * This method will help set the cost parameters to be used for finding the restaurants.
     * @param scanner
     */
    public void checkCost(Scanner scanner) {
        System.out.println(Colors.CYAN_BRIGHT + "On a scale of $ to $$$$ how much are you willing to spend " + "💰" + Colors.RESET);
        System.out.println(Colors.CYAN + "Restaurants will be recommended around your choice" + Colors.RESET);

        String feel = scanner.nextLine();
        if (feel.isEmpty() || !feel.matches("\\$+")) {
            // Default if empty or invalid input
            cost.add("$");
            cost.add("$$");
        } else {
            //First add the cost entered by the user
            cost.add(feel);

            // Add adjacent cost levels for more options
            if (feel.length() < 4) {
                // Add one level up if not already at maximum
                cost.add(feel + "$");
            }

            if (feel.length() > 1) {
                // Add one level down if not already at minimum
                cost.add(feel.substring(0, feel.length() - 1));
            }
        }
    }

    /**
     * Getter for the cost selected by the user.
     * @return cost.
     */
    public ArrayList<String> getCost() {
        return cost;
    }

    /**
     * If the user wants to celebrate, then set cuisines for P(Bars, Clubs, Late night)
     * @param scanner
     */
    public void celebration(Scanner scanner) {
        System.out.println(Colors.YELLOW_BRIGHT + "Do you have a special occasion to celebrate? (Y/N) " + "🎉" + Colors.RESET);
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("y") || response.equals("yes") || response.equals("ye")) {
            userCuisines.add("bars & breweries");
            userCuisines.add("club");
            userCuisines.add("bodega/late night");
            userCuisines.add("dive bar");
            System.out.println(Colors.CYAN_BRIGHT + "Awesome! Added some celebration spots to your list." + " 🥂" + Colors.RESET);
        }
    }

    /**
     * Method to clear the user cuisines and cost
     */
    public void clearVars() {
        userCuisines.clear();
        cost.clear();
    }

    public void displayCuisines() {
        System.out.println(Colors.CYAN + "Available cuisines: " + "👨‍🍳" + Colors.RESET);
        // First, ensure we have a truly unique list
        Set<String> uniqueCuisines = new HashSet<>(cuisines);
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
     * Getter for the total cuisines
     * @return cuisines
     */
    public Set<String> getCuisines() {
        return cuisines;
    }

    /**
     * Getter for user cuisines
     * @return user cuisines
     */

    public Set<String> getUserCuisines() {
        return userCuisines;
    }
}