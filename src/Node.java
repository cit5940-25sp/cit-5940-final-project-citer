/**
 * @author VarunS.
 *
 */
public class Node implements Comparable<Node> {
    //The internal class holds the data for the restaurants
    //This data is the restaurant name and the Rating that we wish to store
    //But will be stored in a priority queue
    String restaurantName;
    double rating;
    boolean nearCollFlag;


    /**
     * Default constructor for the Internal Data class
     * @param restaurantName, rating, boolean flags
     */
    public Node(String restaurantName, double rating, boolean nearCollFlag) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.nearCollFlag = nearCollFlag;
    }


    /**
     *
     * @param o the object to be compared.
     * @return Descending order based on rating.
     */
    @Override
    public int compareTo(Node o) {
        //Method returns the ratings in descending order.
        return Double.compare(o.rating, this.rating);
    }

    /**
     * Method to get the name of the restaurant.
     * @return Restaurant Name
     */
    public String getName() {
        return restaurantName;
    }

    /**
     * Method to get the rating of the restaurant.
     * @return Restaurant Rating
     */
    public double getRating() {
        return rating;
    }


    /**
     * Method to get the near to College Flag !
     * @return Boolean flag
     */
    public boolean getNearCollFlag() {
        return nearCollFlag;
    }

}