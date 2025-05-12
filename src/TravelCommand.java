import java.util.Scanner;

/**
 * The travel command executes the travel strategies.
 * It is used to implement the Command interface
 */
public class TravelCommand implements Command {

    /**
     * Represents an instance of {@link TravelData} used for storing and managing travel-related datasets.
     * The purpose is to integrate data such as holidays, destinations, and tags
     * into the functionalities provided by the chatbot strategies.
     */
    TravelData travelData;

    /**
     * An instance of the scanner, used for fetching user input
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * An instance of the travel factory class which is used for getting the various travel strategies.
     */
    TravelStrategyFactory travelFactory;

    /**
     * The travel food UI handles all UI-related tasks
     */
    TravelFoodUI travelFoodUI;

    /**
     * Zero argument constructor for the class, initializes all field objects and variables
     * and also loads the datasets into their respective data structures !
     */
    public TravelCommand() {
        travelData = new TravelData();
        //Load the data from the datasets
        travelData.addDataFromDataFile("Databases/UPenn_Holiday_Almanac_2025_2026.csv", "Databases/Updated_Verified_Costs_Research.csv", "Databases/Detailed dataset.csv");
        travelFactory = new TravelStrategyFactory(travelData, scanner);
        travelFoodUI = new TravelFoodUI(travelData);
    }


    /**
     * The execute command helps implement the command and strategy design pattern.
     * It switches context on runtime based on user inputs.
     */
    @Override
    public void execute() {
        //To implement.
        travelFoodUI.displayHolidayData();
        System.out.println();
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "✨ What do you want to do? ✨" + Colors.RESET);
        System.out.println(Colors.YELLOW_BRIGHT + "1. 🔍 Search for a place manually" + Colors.RESET);
        System.out.println(Colors.YELLOW_BRIGHT + "2. 🏷️  Get places by categories" + Colors.RESET);
        String chh = scanner.nextLine();

        //Error checking for the options
        while (!chh.equals("1") && !chh.equals("2")) {
            System.out.println(Colors.PURPLE_BOLD_BRIGHT + "Please enter either 1 or 2" + Colors.RESET);
            chh = scanner.nextLine();
        }

        if (chh.equals("1")) {
            travelFoodUI.displayLocationData(scanner);
            return;
        } else {
            System.out.println(Colors.PURPLE_BOLD_BRIGHT + "🎲 Would you like us to surprise you with some awesome places? (Y/N) 🎲" + Colors.RESET);
            String check = scanner.nextLine();
            if (check.equalsIgnoreCase("Y") || check.equalsIgnoreCase("Yes")) {
                travelFactory.getRandomTravelStrategy();

                System.out.println(Colors.PURPLE_BOLD_BRIGHT + "👍 Would you like to go ahead with these categories? (Y/N) 👎" + Colors.RESET);
                check = scanner.nextLine();
                if (check.equalsIgnoreCase("N") || check.equalsIgnoreCase("No")) {
                    travelData.clearUserTags();
                } else {
                    travelFoodUI.displayLocations();
                    travelFoodUI.displayLocationData(scanner);
                    travelData.clearUserTags();
                    return;
                }
            }

            System.out.println(Colors.CYAN_BOLD_BRIGHT + "🌟 Ready? What do you feel like doing? 🔥" + Colors.RESET);
            travelFoodUI.displayPlaceList();
            System.out.println(Colors.CYAN_BRIGHT + "🌈 Enter any 3 categories of your choice followed by a whitespace 🏄‍♂️ 🚴‍♂️ 🧗‍♀️" + Colors.RESET);
            travelFactory.getUserTravelStrategy();
            travelFoodUI.displayLocationData(scanner);
            travelData.clearUserTags();
        }
    }
}