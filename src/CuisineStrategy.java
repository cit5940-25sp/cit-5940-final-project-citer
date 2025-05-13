import java.util.*;


/**
 * This is a strategy factory for initializing and storing all the food strategies.
 * The strategies are called using specific getters
 */
public class CuisineStrategy {
    /**
     * Field for Good mood strategy
     */
    private GoodMoodStrategy goodM;
    /**
     * Field for not good mood strategy
     */
    private NotGoodMoodStrategy notGoodM;
    /**
     * Field for Random strategy
     */
    private RandomStrategy random;

    /**
     * Constructor to initialize all 3 types of food strategies
     */
    public CuisineStrategy() {
        goodM = new GoodMoodStrategy();
        notGoodM = new NotGoodMoodStrategy();
        random = new RandomStrategy();
    }

    /**
     * Method to help call the good mood strategy
     * @param fGraph Used for accessing the underlying graph
     * @param scanner Used for capturing user input
     */
    public void setGoodMoodStrategy(FoodGraph fGraph, Scanner scanner) {
        goodM.setUserCuisines(fGraph, scanner);
    }

    /**
     * Method to help call the not good mood strategy
     * @param fGraph Used for accessing the underlying graph
     * @param scanner Used for capturing user input
     */
    public void setNotGoodM(FoodGraph fGraph, Scanner scanner) {
        notGoodM.setUserCuisines(fGraph, scanner);
    }

    /**
     * Method to help call the random strategy
     * @param fGraph Used for accessing the underlying graph
     * @param scanner Used for capturing user input
     */
    public void randomStrat(FoodGraph fGraph, Scanner scanner) {
        random.setUserCuisines(fGraph, scanner);
    }
}