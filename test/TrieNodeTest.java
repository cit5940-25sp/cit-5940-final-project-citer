import org.junit.Test;
import static org.junit.Assert.*;

public class TrieNodeTest {

    @Test
    public void trieNodeTest() {
        TrieNode tn = new TrieNode();
        assertFalse(tn.isEndOfWord);
        assertNull(tn.word);
    }

}