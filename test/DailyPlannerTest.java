import org.junit.Test;
import static org.junit.Assert.*;


public class DailyPlannerTest {
    @Test
    public void dailyPlannerTest() {
    }

    @Test
    public void addTaskTest() {
    }

    @Test
    public void getNextTaskTest() {
    }

    @Test
    public void peekNextTaskTask() {
    }

    private DailyPlanner planner;

    public void setUp() {
        planner = new DailyPlanner();
    }

    @Test
    public void testAddAndPeekTask() {
        Task task = new Task("Read paper");
        planner.addTask(task);
        assertEquals("Read paper", planner.peekNextTask().getDescription());
    }

    @Test
    public void testGetNextTask() {
        planner.addTask(new Task("Submit report"));
        Task next = planner.getNextTask();
        assertEquals("Submit report", next.getDescription());
    }

    @Test
    public void testEmptyPeekAndPop() {
        assertEquals("No tasks!", planner.peekNextTask().getDescription());
        assertEquals("No tasks!", planner.getNextTask().getDescription());
    }

}

