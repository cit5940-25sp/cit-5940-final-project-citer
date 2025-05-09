import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author VarunS.
 *
 */
public class FoodGraph {

    //The graph is being implemented as an adjacency list.
    // It is devised as -> Cuisine will be the key factor -> Which will have Nodes(Restaurants)
    // The nodes are then categorized in a hashmap basis the cost ($).
    // Each restaurant has a boolean flag to signify if the restaurant
    // is near the campus (In case that matters to the user)
    // $ $$ $$$ $$$$
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
    private String mood;
    private ArrayList<String> cost;
    private boolean time;
    private Set<String> userCuisines;

    //For auto correct !!!
    private Trie cuisineTrie;



    /**
     * Constructor for initializing the graph to a null state.
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
                "sad", "angry", "anxious", "frustrated", "annoyed", "upset", "depressed", "lonely", "scared", "nervous", "insecure", "jealous", "bitter", "resentful", "hopeless", "helpless",
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
                if (cuisines.add(parts[1])) {
                    // Add to Trie when a new cuisine is discovered
                    cuisineTrie.insert(parts[1]);
                }

                nodes.get(parts[1]).putIfAbsent(parts[2], new PriorityQueue<>());

                //Add the element to the hashmap

                nodes.get(parts[1]).get(parts[2]).add(new Node(parts[0], Double.parseDouble(parts[3]),flag));
                numRestaurants++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }


    /**
     * Method to find the suggestions for the user based on the criteria they mention to the bot
     *
     */
    public void findSuggestions() {
        HashMap<String, HashSet<String>> suggestions = new HashMap<>();

        setCriteria();

        for (String cuisine : userCuisines) {
            HashSet<String> restSet = new HashSet<>();
            suggestions.put(cuisine, restSet);

            for (String costLevel : cost) {
                PriorityQueue<Node> queue = nodes.get(cuisine).get(costLevel);
                if (queue == null) continue;

                // Use an iterator so we don't modify the queue destructively
                Iterator<Node> iter = queue.iterator();
                while (iter.hasNext() && restSet.size() < 3) {
                    restSet.add(iter.next().getName());
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
    }

    /**
     * Method which is run inside the findsSuggestions to help set criteria for the search.
     */
    public void setCriteria() {
        Scanner scanner = new Scanner(System.in);

        //Check mood
        System.out.println(Colors.CYAN_BOLD + "In a single word -> describe how you feel " + "😊" + Colors.RESET);

        String feel = scanner.nextLine().toLowerCase();

        if (moodWords.contains(feel)) {
            mood = "bad";
        } else {
            mood = "good";
        }

        //If mood is bad then have a set of cuisines -> handled


        if (mood.equals("good")) {
            System.out.println(Colors.CYAN_BOLD + "What would you like to eat today? " + "🍽️" + Colors.RESET);

            // Displaying the cuisines available in the file
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



            // surprise me option
            System.out.println(Colors.GREEN_BRIGHT + "Please select up to 3 cuisines (separated by spaces) " + "🌮 🍕 🍜" + Colors.RESET);
            System.out.println(Colors.GREEN_BRIGHT + "Or type 'surprise' if you want us to choose for you! " + "🎲" + Colors.RESET);
            String response = scanner.nextLine().toLowerCase();

            // Handle "surprise me" option
            if (response.contains("surprise")) {
                System.out.println(Colors.CYAN_BRIGHT + "Surprise it is! " + "✨ We'll pick some great options for you." + Colors.RESET);
                // Pick 3 random cuisines
                List<String> shuffledCuisines = new ArrayList<>(cuisines);
                Collections.shuffle(shuffledCuisines);
                //Select 3 or less than 3 available options
                for (int i = 0; i < Math.min(3, shuffledCuisines.size()); i++) {
                    //Add it to user cuisines
                    userCuisines.add(shuffledCuisines.get(i));
                }
            } else {
                // Validate user input
                String[] responseParts = response.split(" ");
                Set<String> validCuisines = new HashSet<>();
                List<String> invalidCuisines = new ArrayList<>();

                for (String selectedCuisine : responseParts) {
                    selectedCuisine = selectedCuisine.trim();
                    if (selectedCuisine.isEmpty()) continue;

                    // Check if exactly matches any cuisine
                    boolean found = false;
                    for (String cuisine : cuisines) {
                        if (cuisine.equalsIgnoreCase(selectedCuisine)) {
                            validCuisines.add(cuisine);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // Try to find close matches -> uses the levenshtein distance
                        String closestMatch = findClosestCuisine(selectedCuisine, cuisines);
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
                    List<String> popularCuisines = new ArrayList<>(cuisines);
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
                            for (String cuisine : cuisines) {
                                if (cuisine.equalsIgnoreCase(additionalCuisine)) {
                                    validCuisines.add(cuisine);
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                String closestMatch = findClosestCuisine(additionalCuisine, cuisines);
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

                userCuisines.addAll(validCuisines);
            }

            // Display selected cuisines for confirmation
            System.out.println(Colors.CYAN_BRIGHT + "You've selected these cuisines: " + "🎯 " +
                    String.join(", ", userCuisines) + Colors.RESET);

            // Ask about special occasion
            System.out.println(Colors.YELLOW_BRIGHT + "Do you have a special occasion to celebrate? (Y/N) " + "🎉" + Colors.RESET);
            response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes") || response.equals("ye")) {
                userCuisines.add("bars & breweries");
                userCuisines.add("club");
                System.out.println(Colors.CYAN_BRIGHT + "Awesome! Added some celebration spots to your list." + " 🥂" + Colors.RESET);
            }
        } else {
            //If mood is bad
            cuisines.add("coffeeshop");
            cuisines.add("bakery");
            cuisines.add("ice cream");
            cuisines.add("italian");

        }


        //Now ask the user how much are willing to spend on a scale of $ to $$$$
        System.out.println(Colors.CYAN_BRIGHT + "On a scale of $ to $$$$ how much are you willing to spend " + "💰" + Colors.RESET);
        System.out.println(Colors.CYAN + "Restaurants will be recommended around your choice" + Colors.RESET);



        feel = scanner.nextLine();
        if (feel.length() == 1) {
            cost.add(feel);
            cost.add("$$");
        } else if (feel.length() == 4) {
            cost.add(feel);
            cost.add("$$$");
        } else {
            cost.add(feel);
            //Add one level up
            cost.add(feel + "$");
            //Add one level down
            cost.add(feel.substring(0, feel.length() - 1));
        }


        //Final checks on the time commitments
        System.out.println(Colors.YELLOW_BRIGHT + "Are you in a hurry? (Y/N) " + "⏱️" + Colors.RESET);

        feel = scanner.nextLine().toLowerCase();

        if (feel.equals("y") || feel.equals("yes") || feel.equals("ye")) {
            time = true;
            cuisines.add("fast food");
        }

        //NOTE: SCANNER not closed because it gets closed in the main chat-bot class.
    }

    /**
     * Method to return the number of edges in the graph
     * @return Number of edges
     */
    public int getNumEdges() {
        numEdges = nodes.size();
        return numEdges;
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
    private String findClosestCuisine(String input, Set<String> availableCuisines) {
        // First check for exact match or substring match
        for (String cuisine : availableCuisines) {
            if (cuisine.toLowerCase().contains(input.toLowerCase()) ||
                    input.toLowerCase().contains(cuisine.toLowerCase())) {
                return cuisine; // Direct partial match
            }
        }

        // Start with a prefix search if the input is at least 1 character long
        if (input.length() > 0) {
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
}