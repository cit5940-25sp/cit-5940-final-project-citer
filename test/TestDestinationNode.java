import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDestinationNode {

    private DestinationNode node;

    @Before
    public void setUp() {
        node = new DestinationNode();
    }

    @Test
    public void testVariableInitialisedandEmpty1() {
        assertNotNull(node.getSee());
        assertTrue(node.getSee().isEmpty());
    }

    @Test
    public void testVariableInitialisedandEmpty2() {
        assertNotNull(node.getDoStuff());
        assertTrue(node.getDoStuff().isEmpty());
    }

    @Test
    public void testVariableInitialisedandEmpty3() {
        assertNotNull(node.getFood());
        assertTrue(node.getFood().isEmpty());
    }

    @Test
    public void testVariableInitialisedandEmpty4() {
        assertEquals(0, node.getCost());
    }

    @Test
    public void testSetAndGetCost() {
        node.setCost(150);
        assertEquals(150, node.getCost());
    }

    @Test
    public void testVariableInitialisedandEmpty5() {
        assertEquals("", node.getDistance());
    }

    @Test
    public void testAddItemsToLists() {
        node.getSee().add("Statue of Liberty");
        node.getDoStuff().add("Hiking");
        node.getFood().add("Cheesesteak");

        assertEquals(1, node.getSee().size());
        assertEquals("Statue of Liberty", node.getSee().get(0));

        assertEquals(1, node.getDoStuff().size());
        assertEquals("Hiking", node.getDoStuff().get(0));

        assertEquals(1, node.getFood().size());
        assertEquals("Cheesesteak", node.getFood().get(0));
    }
}
