public class TodoCommand implements Command {
    private DailyPlanner planner;

    public TodoCommand(DailyPlanner planner) {
        this.planner = planner;
    }

    @Override
    public void execute() {
        planner.addTask(new Task("Finish homework"));
        System.out.println("Next Task: " + planner.getNextTask().getDescription());
    }
}
