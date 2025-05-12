import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class TestNodeFood {
    private Node root;

    String restaurantName;
    double rating;
    boolean nearCollFlag;

    @Before
    public void setUp() {
        root = new Node("RandomRestro", 5.80, false);
    }


    @Test
    public void testGettersAndSetters() {
        assertEquals("RandomRestro", root.getName());
        assertEquals(5.80, root.getRating(), 0);
        assertEquals(root.getNearCollFlag(), false);
    }
}
