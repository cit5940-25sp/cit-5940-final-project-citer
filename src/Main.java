// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create course planner
        CoursePlanner planner = new CoursePlanner();

        // Load course data from CSV
        planner.loadCoursesFromCSV("cis_courses.csv");

        // Load prerequisite data from CSV
        planner.loadPrerequisitesFromCSV("prereq.csv");

        // Create the academic command
        AcademicCommand academicCommand = new AcademicCommand(planner);

        // Create scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Interactive loop
        while (true) {
            System.out.println("\n==== Course Recommendation System ====");
            System.out.println("1. Recommend courses by interest area");
            System.out.println("2. Recommend courses by career path");
            System.out.println("3. List all interest areas");
            System.out.println("4. List all career paths");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Recommend by interest area
                    System.out.println("\nAvailable interest areas:");
                    for (String area : planner.getAllInterestAreas()) {
                        System.out.println("- " + area);
                    }
                    System.out.print("\nEnter interest area: ");
                    String interest = scanner.nextLine();

                    academicCommand.setInterest(interest);
                    System.out.print("Enter max number of recommendations: ");
                    try {
                        int max = Integer.parseInt(scanner.nextLine());
                        academicCommand.setMaxRecommendations(max);
                    } catch (NumberFormatException e) {
                        System.out.println("Using default (5) recommendations.");
                    }

                    academicCommand.execute();
                    break;

                case 2:
                    // Recommend by career path
                    System.out.println("\nAvailable career paths:");
                    for (String path : planner.getAllCareerPaths()) {
                        System.out.println("- " + path);
                    }
                    System.out.print("\nEnter career path: ");
                    String careerPath = scanner.nextLine();

                    academicCommand.setCareerPath(careerPath);
                    System.out.print("Enter max number of recommendations: ");
                    try {
                        int max = Integer.parseInt(scanner.nextLine());
                        academicCommand.setMaxRecommendations(max);
                    } catch (NumberFormatException e) {
                        System.out.println("Using default (5) recommendations.");
                    }

                    academicCommand.execute();
                    break;

                case 3:
                    // List all interest areas
                    System.out.println("\nAll Interest Areas:");
                    for (String area : planner.getAllInterestAreas()) {
                        System.out.println("- " + area);
                    }
                    break;

                case 4:
                    // List all career paths
                    System.out.println("\nAll Career Paths:");
                    for (String path : planner.getAllCareerPaths()) {
                        System.out.println("- " + path);
                    }
                    break;

                case 5:
                    // Exit
                    System.out.println("Thank you for using the Course Recommendation System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}

