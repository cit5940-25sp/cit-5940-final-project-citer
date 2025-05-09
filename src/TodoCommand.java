import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class TodoCommand implements Command {
    private DailyPlanner planner;
    private Scanner scanner = new Scanner(System.in);

    public TodoCommand(DailyPlanner planner) {
        this.planner = planner;
    }

    @Override
    public void execute() {
        // Load default task based on the day
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        switch (today) {
            case WEDNESDAY:
                planner.addTask(new Task("CIS 594 HW due"));
                break;
            case THURSDAY:
                planner.addTask(new Task("CIS 596 HW due"));
                break;
            case FRIDAY:
                planner.addTask(new Task("CIS 595 HW due"));
                break;
            default:
                // No default task
                break;
        }

        // Interactive menu
        while (true) {
            System.out.println("\n📋 TODO Menu:");
            System.out.println("[1] Add a custom task");
            System.out.println("[2] View next task (peek)");
            System.out.println("[3] Complete next task (pop)");
            System.out.println("[4] Exit TODO menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine().trim();
                    planner.addTask(new Task(desc));
                    System.out.println("✅ Task added.");
                    break;
                case "2":
                    Task peek = planner.peekNextTask();
                    System.out.println("🔜 Next Task: " + peek.getDescription());
                    break;
                case "3":
                    Task popped = planner.getNextTask();
                    System.out.println("☑️ Completed: " + popped.getDescription());
                    break;
                case "4":
                    return;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
