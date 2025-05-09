import org.junit.Test;
import static org.junit.Assert.*;


public class TaskTest {

    @Test
    public void taskTest() {
    }
    @Test
    public void getDescriptionTest() {
        Task task = new Task("Test the bot");
        assertEquals("Test the bot", task.getDescription());
    }
}
