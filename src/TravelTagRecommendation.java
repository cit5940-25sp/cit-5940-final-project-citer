import java.util.Scanner;

/**
 * This interface is a contract that helps set the desired tags
 */

public interface TravelTagRecommendation {

    /**
     * Each strategy helps set the cuisines in the food graph basis the user's preference
     *
     * @param travelData to access the datasets
     * @param scanner scanner to read in user input
     */
    public void setTag(TravelData travelData, Scanner scanner);

}
