import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TrieTest {

    private Trie trie;

    @Test
    public void testInsertSingleWord() {
        trie = new Trie();
        trie.insert("hello");

        List<String> results = trie.getWordsWithPrefix("hello");

        assertEquals(1, results.size());
        assertEquals("hello", results.get(0));
    }

    @Test
    public void testInsertMultipleWords() {
        trie = new Trie();
        trie.insert("hello");
        trie.insert("help");
        trie.insert("held");

        List<String> results = trie.getWordsWithPrefix("hel");

        assertEquals(3, results.size());
        assertTrue(results.contains("hello"));
        assertTrue(results.contains("help"));
        assertTrue(results.contains("held"));
    }

    @Test
    public void testPrefixWithNoMatch() {
        trie = new Trie();
        trie.insert("hello");
        trie.insert("help");

        List<String> results = trie.getWordsWithPrefix("world");

        assertEquals(0, results.size());
    }

    @Test
    public void testEmptyPrefix() {
        trie = new Trie();
        trie.insert("hello");
        trie.insert("world");
        trie.insert("hi");

        List<String> results = trie.getWordsWithPrefix("");

        assertEquals(3, results.size());
        assertTrue(results.contains("hello"));
        assertTrue(results.contains("world"));
        assertTrue(results.contains("hi"));
    }

    @Test
    public void testPartialMatch() {
        trie = new Trie();
        trie.insert("apple");
        trie.insert("application");
        trie.insert("append");
        trie.insert("banana");

        List<String> results = trie.getWordsWithPrefix("app");

        assertEquals(3, results.size());
        assertTrue(results.contains("apple"));
        assertTrue(results.contains("application"));
        assertTrue(results.contains("append"));
        assertFalse(results.contains("banana"));
    }

    @Test
    public void testExactPrefixMatch() {
        trie = new Trie();
        trie.insert("car");
        trie.insert("carpet");
        trie.insert("carpool");

        List<String> results = trie.getWordsWithPrefix("car");

        assertEquals(3, results.size());
        assertTrue(results.contains("car"));
        assertTrue(results.contains("carpet"));
        assertTrue(results.contains("carpool"));
    }

    @Test
    public void testCaseSensitivity() {
        trie = new Trie();
        trie.insert("Hello");
        trie.insert("hello");

        List<String> resultsUpper = trie.getWordsWithPrefix("H");
        List<String> resultsLower = trie.getWordsWithPrefix("h");

        assertEquals(1, resultsUpper.size());
        assertEquals("Hello", resultsUpper.get(0));

        assertEquals(1, resultsLower.size());
        assertEquals("hello", resultsLower.get(0));
    }

    @Test
    public void testEmptyTrie() {
        trie = new Trie();
        List<String> results = trie.getWordsWithPrefix("any");

        assertEquals(0, results.size());
    }

    @Test
    public void testLongPrefix() {
        trie = new Trie();
        trie.insert("pneumonoultramicroscopicsilicovolcanoconiosis");

        List<String> results = trie.getWordsWithPrefix("pneumonoultramicroscopic");

        assertEquals(1, results.size());
        assertEquals("pneumonoultramicroscopicsilicovolcanoconiosis", results.get(0));
    }

    @Test
    public void testPrefixLongerThanWord() {
        trie = new Trie();
        trie.insert("short");

        List<String> results = trie.getWordsWithPrefix("shorter");

        assertEquals(0, results.size());
    }

}