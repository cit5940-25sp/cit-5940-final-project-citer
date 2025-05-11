import java.util.LinkedList;
import java.util.Queue;

public class DailyPlanner {
    private Queue<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task); // Add to the queue
    }

    public Task peekNextTask() {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.peek(); // Look at the front of the queue (oldest task)
    }

    public Task getNextTask() {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.poll(); // Remove and return the front of the queue (oldest task)
    }
}