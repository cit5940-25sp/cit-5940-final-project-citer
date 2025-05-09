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
    private boolean time = false;
    private Set<String> userCuisines;



    /**
     * Constructor for initializing the graph to a null state.
     */
    public FoodGraph() {
        nodes = new HashMap<>();
        numEdges = 0;
        numRestaurants = 0;
        cuisines = new HashSet<>();

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
                "fatigued", "unhappy", "spacing", "fuming", "unwell", "disrupted"
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
                String[] parts = line.split(",");

                // Create a boolean flag and set it to value from the file.
                boolean flag = parts[4].equals("yes");

                //Put if Absent to populate the node
                nodes.putIfAbsent(parts[1], new HashMap<>());
                cuisines.add(parts[1]);
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

        for (Map.Entry<String, HashSet<String>> entry : suggestions.entrySet()) {
            //Print the cuisine as blue underlined first
            System.out.println(Colors.BLUE_UNDERLINED + entry.getKey() + Colors.RESET);

            //Then print the restaurants in the entry
            for (String rest : entry.getValue()) {
                System.out.println(Colors.RED + rest + Colors.RESET);
            }
        }
    }

    /**
     * Method which is run inside the findsSuggestions to help set criteria for the search.
     */
    public void setCriteria() {
        Scanner scanner = new Scanner(System.in);

        //Check mood
        System.out.println(Colors.PURPLE_BOLD + "In a single word -> describe how you feel :)" + Colors.RESET);

        String feel = scanner.nextLine().toLowerCase();

        if (moodWords.contains(feel)) {
            mood = "bad";
        } else {
            mood = "good";
        }

        //If mood is bad then have a set of cuisines
        if (mood.equals("bad")) {
            userCuisines.add("coffee/donuts");
            userCuisines.add("pizza");
            userCuisines.add("mexican");
            userCuisines.add("asian");
        }

        //Setters for cuisines if the mood is good!
        if (mood.equals("good")) {
            System.out.println(Colors.BLUE_BOLD + "What would you like to eat :)" + Colors.RESET);

            StringBuilder cusiine = new StringBuilder();
            for (String str: cuisines) {
                cusiine.append(str.toLowerCase());
            }
            System.out.println(Colors.RED + cusiine + Colors.RESET);
            System.out.println(Colors.GREEN + "You can select any 3 cuisines, please enter your response" + Colors.RESET);
            System.out.println(Colors.GREEN + "you can enter your responses with a whitespace in between" + Colors.RESET);
            String response = scanner.nextLine().toLowerCase();

            String[] responseParts = response.split(" ");

            if (responseParts.length > 3) {
                System.out.println(Colors.RED + "Please enter only 3 cuisines of your choice" + Colors.RESET);
                responseParts = scanner.nextLine().toLowerCase().split(" ");
            }

            System.out.println(Colors.PURPLE_BRIGHT + "Do you have a special occasion to celebrate ? (Y/N)" + Colors.RESET);
            response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes") || response.equals("ye")) {
                userCuisines.add("bars & breweries");
                userCuisines.add("club");
            }

            //Add the user cuisines to the cuisines parameter.
            userCuisines.addAll(Arrays.asList(responseParts));
        }

        //Now ask the user how much are willing to spend on a scale of $ to $$$$
        System.out.println(Colors.BLUE_BOLD_BRIGHT + "On a scale of $ to $$$$ how much are you willing to spend" + Colors.RESET);
        System.out.println(Colors.BLUE_BOLD_BRIGHT + "Restaurants will be recommended a level above and lower to your choice" + Colors.RESET);


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
        System.out.println(Colors.RED + "Are you in a hurry ? (Y/N)" + Colors.RESET);

        feel = scanner.nextLine().toLowerCase();

        if (feel.equals("y") || feel.equals("yes") || feel.equals("ye")) {
            time = true;
            cuisines.add("fast food");
        }
    }

    /**
     * Method to return the number of edges in the graph
     * @return Number of edges
     */
    public int getNumEdges() {
        numEdges = nodes.size();
        return numEdges;
    }
}