import java.util.ArrayList;

/**
 * Destination node stores the details that the user can do,
 * see and eat at the specified destination
 */
public class DestinationNode {

    /**
     * Things to see at the destination
     */
    private ArrayList<String> see;
    /**
     * Things to do at the destination
     */
    private ArrayList<String> doStuff;
    /**
     * Things to eat at the destination
     */
    private ArrayList<String> food;
    /**
     * Estimated cost per day
     */
    private int cost;
    /**
     * Distance from philly whether near or far
     */
    private String distance;

    /**
     * No argument constructor used for initializing the class fields.
     */
    DestinationNode() {
        see = new ArrayList<>();
        doStuff = new ArrayList<>();
        food = new ArrayList<>();
        cost = 0;
        distance = "";
    }

    /**
     * Getter for the see variable
     * @return see
     */
    public ArrayList<String> getSee() {
        return see;
    }

    /**
     * Getter for the doStuff variable
     * @return doStuff
     */
    public ArrayList<String> getDoStuff() {
        return doStuff;
    }

    /**
     * Getter for the food variable
     * @return food
     */
    public ArrayList<String> getFood() {
        return food;
    }

    /**
     * Getter for the cost variable
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Setter for the cost variable
     *
     */
    public void setCost(int cost) {
        this.cost = cost;
    }


    /**
     * Getter for the distance variable
     * @return distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Setter for the distance variable
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

}