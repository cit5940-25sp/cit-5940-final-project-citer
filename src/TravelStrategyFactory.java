import java.util.Scanner;

/**
 * The strategy factory for all travel strategies
 */
public class TravelStrategyFactory {
    //Strategy variables defined for the strategies
    private RandomTravelStrategy randOm;
    private UserTravelStrategy userR;
    private TravelData td;
    private Scanner scanner;

    /**
     * Constructor to initialise the strategies
     */
    public TravelStrategyFactory(TravelData td, Scanner scanner) {
        randOm = new RandomTravelStrategy();
        userR = new UserTravelStrategy();
        this.td = td;
        this.scanner = scanner;
    }

    /**
     * The getter to fetch the random travel strategy
     */
    public void getRandomTravelStrategy() {
        randOm.setTag(td, scanner);
    }

    /**
     * The getter to fetch the user travel strategy
     */
    public void getUserTravelStrategy() {
        userR.setTag(td, scanner);
    }

}
