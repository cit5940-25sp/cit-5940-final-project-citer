import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;


import static org.junit.Assert.*;

public class TodoCommandTest {
    @Test
    public void executeTest() throws Exception {
        DailyPlanner planner = new DailyPlanner();
        String input = "1\ndo grocery\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        TodoCommand todoCommand = new TodoCommand(planner);
        todoCommand.execute();

        Task nextTask = planner.peekNextTask();
        assertEquals("do grocery", nextTask.getDescription());

    }

    @Test
    public void testDefaultTaskOnWednesday() throws Exception {
        if (LocalDate.now().getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            return;
        }
        DailyPlanner planner = new DailyPlanner();
        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        TodoCommand todoCommand = new TodoCommand(planner);
        todoCommand.execute();

        Task nextTask = planner.peekNextTask();
        assertEquals("CIS 594 HW due", nextTask.getDescription());
    }

    @Test
    public void testPeekAndCompleteTask() throws Exception {
        DailyPlanner planner = new DailyPlanner();
        planner.addTask(new Task("do grocery"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        String input = "2\n3\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        TodoCommand todoCommand = new TodoCommand(planner);
        todoCommand.execute();

        String output = outContent.toString();
        assertTrue(output.contains("🔜 Next Task: do grocery"));
        assertTrue(output.contains("☑️ Completed: do grocery"));

        System.setOut(originalOut);
    }

    @Test
    public void testMultipleTaskAddition() throws Exception {
        DailyPlanner planner = new DailyPlanner();
        String input = "1\ndo grocery\n1\nfinish homework\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        TodoCommand todoCommand = new TodoCommand(planner);
        todoCommand.execute();

        Task nextTask = planner.peekNextTask();
        assertEquals("do grocery", nextTask.getDescription());

        planner.getNextTask();
        Task secondTask = planner.peekNextTask();
        assertEquals("finish homework", secondTask.getDescription());
    }

    @Test
    public void testInvalidMenuOption() throws Exception {
        DailyPlanner planner = new DailyPlanner();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        String input = "9\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        TodoCommand todoCommand = new TodoCommand(planner);
        todoCommand.execute();

        String output = outContent.toString();
        assertTrue(output.contains("❌ Invalid option"));

        System.setOut(originalOut);
    }


}
