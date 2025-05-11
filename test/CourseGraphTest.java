import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CourseGraph class
 */
public class CourseGraphTest {

    private CourseGraph graph;

    @Before
    public void setUp() {
        graph = new CourseGraph();
    }

    @Test
    public void testAddAndGetPrerequisites() {
        graph.addPrerequisite("CIS5220", "CIS5200");
        graph.addPrerequisite("CIS5220", "CIS5190");

        List<String> prereqs = graph.getPrerequisites("CIS5220");
        assertEquals(2, prereqs.size());
        assertTrue(prereqs.contains("CIS5200"));
        assertTrue(prereqs.contains("CIS5190"));
    }

    @Test
    public void testGetEmptyPrerequisites() {
        List<String> emptyPrereqs = graph.getPrerequisites("CIS5000");
        assertNotNull(emptyPrereqs);
        assertTrue(emptyPrereqs.isEmpty());
    }

    @Test
    public void testGetDependentCourses() {
        graph.addPrerequisite("CIS5220", "CIS5200");
        graph.addPrerequisite("CIS5300", "CIS5200");

        List<String> dependents = graph.getDependentCourses("CIS5200");
        assertEquals(2, dependents.size());
        assertTrue(dependents.contains("CIS5220"));
        assertTrue(dependents.contains("CIS5300"));
    }

    @Test
    public void testGetEmptyDependentCourses() {
        List<String> emptyDeps = graph.getDependentCourses("CIS5999");
        assertNotNull(emptyDeps);
        assertTrue(emptyDeps.isEmpty());
    }

    @Test
    public void testOrderedPrerequisites() {
        // Create a chain: CIS5100 <- CIS5200 <- CIS5220
        graph.addPrerequisite("CIS5200", "CIS5100");
        graph.addPrerequisite("CIS5220", "CIS5200");

        List<String> orderedPrereqs = graph.getOrderedPrerequisites("CIS5220");
        assertEquals(2, orderedPrereqs.size());
        assertEquals("CIS5100", orderedPrereqs.get(0)); // First prerequisite
        assertEquals("CIS5200", orderedPrereqs.get(1)); // Direct prerequisite
    }

    @Test
    public void testOrderedPrerequisitesWithNoCourses() {
        List<String> noPrereqs = graph.getOrderedPrerequisites("CIS5100");
        assertNotNull(noPrereqs);
        assertTrue(noPrereqs.isEmpty());
    }

    @Test
    public void testCyclicPrerequisites() {
        // Create a cycle: A -> B -> C -> A
        graph.addPrerequisite("B", "A");
        graph.addPrerequisite("C", "B");
        graph.addPrerequisite("A", "C");

        // Should not cause infinite recursion
        List<String> prereqs = graph.getOrderedPrerequisites("A");
        assertNotNull(prereqs);

        // Each course should appear at most once
        Set<String> uniquePrereqs = new HashSet<>(prereqs);
        assertEquals(prereqs.size(), uniquePrereqs.size());
    }
}
