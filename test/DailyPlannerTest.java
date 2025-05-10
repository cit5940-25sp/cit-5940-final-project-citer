import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class DailyPlannerTest {
    private DailyPlanner planner;

    @Test
    public void testAddTask() {
        planner = new DailyPlanner();
        Task task = new Task("Complete project");
        planner.addTask(task);

        Task nextTask = planner.peekNextTask();
        assertNotNull(nextTask);
        assertEquals("Complete project", nextTask.getDescription());
    }

    @Test
    public void testPeekNextTask() {
        planner = new DailyPlanner();
        Task task = new Task("Study for exam");
        planner.addTask(task);

        Task peekedTask = planner.peekNextTask();

        assertNotNull(peekedTask);
        assertEquals("Study for exam", peekedTask.getDescription());

        Task peekedAgain = planner.peekNextTask();
        assertNotNull(peekedAgain);

        String firstDesc = peekedTask.getDescription();
        String secondDesc = peekedAgain.getDescription();
        assertEquals(firstDesc, secondDesc);

        assertSame(peekedTask, peekedAgain);
    }

    @Test
    public void testGetNextTask() {
        planner = new DailyPlanner();
        Task task = new Task("Call client");
        planner.addTask(task);

        Task removedTask = planner.getNextTask();

        assertNotNull(removedTask);
        assertEquals("Call client", removedTask.getDescription());

        Task nextTask = planner.peekNextTask();
        assertNull(nextTask);
    }

    @Test
    public void testEmptyPlanner() {
        planner = new DailyPlanner();
        Task peekedTask = planner.peekNextTask();

        assertNull(peekedTask);

        Task removedTask = planner.getNextTask();

        assertNull(removedTask);
    }

    @Test
    public void testMultipleTasks() {
        planner = new DailyPlanner();
        Task task1 = new Task("Morning meeting");
        Task task2 = new Task("Lunch with team");
        Task task3 = new Task("Evening review");

        planner.addTask(task1);
        planner.addTask(task2);
        planner.addTask(task3);

        Task firstTask = planner.getNextTask();
        Task secondTask = planner.getNextTask();
        Task thirdTask = planner.getNextTask();

        assertNotNull(firstTask);
        assertNotNull(secondTask);
        assertNotNull(thirdTask);
        assertEquals("Morning meeting", firstTask.getDescription());
        assertEquals("Lunch with team", secondTask.getDescription());
        assertEquals("Evening review", thirdTask.getDescription());

        assertNull(planner.peekNextTask());
    }

    @Test
    public void testPeekAfterGet() {
        planner = new DailyPlanner();
        Task task1 = new Task("First task");
        Task task2 = new Task("Second task");

        planner.addTask(task1);
        planner.addTask(task2);

        planner.getNextTask();

        Task peekedTask = planner.peekNextTask();

        assertNotNull(peekedTask);
        assertEquals("Second task", peekedTask.getDescription());
    }

    @Test
    public void testAddAfterGet() {
        planner = new DailyPlanner();
        Task task1 = new Task("Original task");
        planner.addTask(task1);
        planner.getNextTask();

        Task task2 = new Task("New task");
        planner.addTask(task2);

        Task nextTask = planner.peekNextTask();
        assertNotNull(nextTask);
        assertEquals("New task", nextTask.getDescription());
    }

    @Test
    public void testQueueBehavior() {
        planner = new DailyPlanner();
        Task task1 = new Task("First");
        Task task2 = new Task("Second");
        Task task3 = new Task("Third");

        planner.addTask(task1);
        planner.addTask(task2);

        Task removed = planner.getNextTask();
        assertNotNull(removed);
        assertEquals("First", removed.getDescription());

        planner.addTask(task3);

        Task next = planner.peekNextTask();
        assertNotNull(next);
        assertEquals("Second", next.getDescription());

        removed = planner.getNextTask();
        assertNotNull(removed);
        assertEquals("Second", removed.getDescription());

        next = planner.peekNextTask();
        assertNotNull(next);
        assertEquals("Third", next.getDescription());
    }
}