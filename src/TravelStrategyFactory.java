import java.util.Scanner;

/**
 * The strategy factory for all travel strategies. This class stores the strategies
 * which can be used in the travel command class
 */
public class TravelStrategyFactory {

    /**
     * Instance of the random travel strategy class
     */
    private RandomTravelStrategy randOm;

    /**
     * Instance of the user travel strategy class
     */
    private UserTravelStrategy userR;

    /**
     * Instance of the travel data class, used for accessing the underlying data structures
     * of the class
     */
    private TravelData td;

    /**
     * Instance of the scanner class used for fetching user inputs
     */
    private Scanner scanner;

    /**
     * Constructor to initialize the strategies
     * @param scanner used to fetch the user input
     * @param td used to access the travel data.
     */
    public TravelStrategyFactory(TravelData td, Scanner scanner) {
        randOm = new RandomTravelStrategy();
        userR = new UserTravelStrategy();
        this.td = td;
        this.scanner = scanner;
    }

    /**
     * Method to execute the random travel strategy
     */
    public void getRandomTravelStrategy() {
        randOm.setTag(td, scanner);
    }

    /**
     * Method to execute the user travel strategy (not random)
     */
    public void getUserTravelStrategy() {
        userR.setTag(td, scanner);
    }

}
