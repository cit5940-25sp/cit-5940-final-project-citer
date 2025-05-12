import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The FoodGraph class implements the methods to create, store and retrieve
 * the contents of the graph.
 */
public class FoodGraph {

    /**
     * The field for storing the graph.It is implemented as a HashMap with
     * String for storing the cuisine, the values are a HashMap which has
     * Cost as its key ($) and value as a priority queue used for Nodes.
     * The ordering is maintained by the user ratings from Yelp/Google reviews
     * Higher rated restaurants are displayed first.
     */
    private HashMap<String, HashMap<String, PriorityQueue<Node>>> nodes;

    /**
     * Field to keep track of the number of edges
     */
    private int numEdges;

    /**
     * Field to keep track of the number of restaurants
     */
    private int numRestaurants;

    /**
     * Field to keep track of the number of cuisines
     */
    private Set<String> cuisines;

    /**
     * Field to keep track of the words that hint towards a bad mood
     */
    private ArrayList<String> moodWords;


    /**
     * Field to keep track of the mood of the user
     */
    private boolean mood;

    /**
     * Field to keep track of the cost preferences of the user
     */
    private ArrayList<String> cost;

    /**
     * Field to keep track if the user is in a hurry
     */
    private boolean time;

    /**
     * Field to keep track of the cuisine preferences of the user
     */
    private Set<String> userCuisines;

    /**
     * Field to implement the auto correct feature using Tries.
     */
    private Trie cuisineTrie;



    /**
     * No argument constructor for initializing the graph to a null state.
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
                "guilty", "ashamed", "embarrassed", "regretful", "heartbroken", "rejected", "desperate","awful","horrid","depressed","",
                "envious", "paranoid", "moody", "restless", "unsettled", "cynical", "apathetic", "irritable",
                "pessimistic", "panicked", "distraught", "devastated", "tearful", "tense", "stressed", "jittery",
                "uneasy", "worthless", "numb", "overwhelmed", "fearful", "withdrawn", "furious", "raging", "horrible",
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
     * @param filePath the location of the dataset
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
                boolean flag = parts[4].trim().equalsIgnoreCase("yes");

                //Put if Absent to populate the node
                nodes.putIfAbsent(parts[1].trim().toLowerCase(), new HashMap<>());

                // Add cuisine to the set and the Trie
                if (cuisines.add(parts[1].trim().toLowerCase())) {
                    // Add to Trie when new cuisine is discovered
                    cuisineTrie.insert(parts[1].trim().toLowerCase());
                }


                nodes.get(parts[1].trim().toLowerCase()).putIfAbsent(parts[2], new PriorityQueue<>());


                //Add the element to the hashmap

                nodes.get(parts[1].trim().toLowerCase()).get(parts[2].trim().toLowerCase()).
                        add(new Node(parts[0].trim().toLowerCase(),
                        Double.parseDouble(parts[3]),flag));
                numRestaurants++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }


    /**
     * Method to traverse the graph and find suggestions based on user cuisine
     * preferences
     */
    public void findSuggestions() {
        HashMap<String, HashSet<String>> suggestions = new HashMap<>();


        for (String cuisine : userCuisines) {
            HashSet<String> restSet = new HashSet<>();
            suggestions.put(cuisine, restSet);


            for (String costLevel : cost) {
                PriorityQueue<Node> queue = nodes.get(cuisine).get(costLevel);

                if (queue == null) continue;

                PriorityQueue<Node> queue2 = new PriorityQueue<>(queue);

                while (!queue2.isEmpty() && restSet.size() < 3) {
                    Node resty = queue2.poll();
                    restSet.add((resty.getName() + " (" + costLevel + ")"));
                }



                if (restSet.size() >= 3) break; // Stop if we've found 3
            }
        }
        System.out.println();
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
     * Calculates how different two strings are (edit distance)
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

        return null;
    }



    /**
     * Getter for the cost selected by the user.
     * @return cost.
     */
    public ArrayList<String> getCost() {
        return cost;
    }

    /**
     * Method to clear the user cuisines and cost
     */
    public void clearVars() {
        userCuisines.clear();
        cost.clear();
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

    /**
     * Getter for the mood words array
     * @return moodWords The field that stores the bad mood words
     */
    public ArrayList<String> getMoodWords() {
        return moodWords;
    }

    /**
     * Setter for the boolean mood variable
     */
    public void setMood(boolean other) {
        mood = other;
    }

    /**
     * getter for the boolean mood variable
     * @return mood the private field for user mood
     */
    public boolean getMood() {
        return mood;
    }

    /**
     * Getter for the number of restaurants
     * @return numRestaurants The number of restaurants in the graph.
     */
    public int getNumRestaurants() {
        return numRestaurants;
    }
}