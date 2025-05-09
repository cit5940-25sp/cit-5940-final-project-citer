import java.util.Stack;

public class DailyPlanner {
    private Stack<Task> taskStack;

    public DailyPlanner() {
        taskStack = new Stack<>();
    }

    public void addTask(Task task) {
        taskStack.push(task);
    }

    public Task getNextTask() {
        return taskStack.isEmpty() ? new Task("No tasks!") : taskStack.pop();
    }
    public Task peekNextTask() {
        return taskStack.isEmpty() ? new Task("No tasks!") : taskStack.peek();
    }

}