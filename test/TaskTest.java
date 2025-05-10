import org.junit.Test;
import static org.junit.Assert.*;


public class TaskTest {

    @Test
    public void getDescriptionTest() {
        Task task = new Task("Test the bot");
        assertEquals("Test the bot", task.getDescription());
    }

    @Test
    public void getDescriptionTest2() {
        Task task = new Task("");
        assertEquals("", task.getDescription());
    }
}
