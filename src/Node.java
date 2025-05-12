/**
 * The internal class holds the data for the restaurants
 * This data is the restaurant name and the Rating that we wish to store
 * But will be stored in a priority queue
 */
public class Node implements Comparable<Node> {


    /**
     * Field for restaurant name
     */
    private String restaurantName;

    /**
     * Field for restaurant rating
     */
    private double rating;

    /**
     * Field for checking if the restaurant is near Penn
     */
    private boolean nearCollFlag;


    /**
     * constructor for intializing the private fields of the class
     * @param restaurantName The name of the restaurant
     * @param rating The google/ yelp review and rating for the restaurant
     * @param nearCollFlag To check if the restaurant is near Penn
     */
    public Node(String restaurantName, double rating, boolean nearCollFlag) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.nearCollFlag = nearCollFlag;
    }


    /**
     *The compare to method to help order the restaurants basis their Yelp/ Google ratings
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
     * Method to get the near to College Flag!
     * @return Boolean flag
     */
    public boolean getNearCollFlag() {
        return nearCollFlag;
    }

}