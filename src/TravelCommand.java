import java.util.Scanner;

/**
 * The travel command executes the travel strategies.
 */
public class TravelCommand implements Command {
    TravelData travelData;
    Scanner scanner = new Scanner(System.in);
    TravelStrategyFactory travelFactory;
    TravelFoodUI travelFoodUI;

    public TravelCommand() {
        travelData = new TravelData();
        //Load the data from the datasets
        travelData.addDataFromDataFile("Databases/UPenn_Holiday_Almanac_2025_2026.csv", "Databases/Updated_Verified_Costs_Research.csv", "Databases/Detailed dataset.csv");
        travelFactory = new TravelStrategyFactory(travelData, scanner);
        travelFoodUI = new TravelFoodUI(travelData);
    }



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