import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements the algorithm for getting a random travel strategy for tags
 */

public class RandomTravelStrategy implements TravelTagRecommendation {

    /**
     * This method will decide the tags at random
     * @param travelData to access the datasets
     * @param scanner scanner to read in user input
     */

    @Override
    public void setTag(TravelData travelData, Scanner scanner){
        ArrayList<String> tags = travelData.getTags();

        //Shuffle the tags
        Collections.shuffle(tags);

        //Get any three tags from the collection

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
        travelData.getUserTags().add(tags.get(rand.nextInt(tags.size())));
        }

        System.out.println(Colors.CYAN_BRIGHT + "We've selected these tags for you: " + "🎯 " +
                String.join(", ", travelData.getUserTags()) + Colors.RESET);

    }
}
