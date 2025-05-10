import java.util.*;

class Trie {
    private TrieNode root;

    // constructor initializes the root of the Trie
    public Trie() {
        root = new TrieNode();
    }

    // inserts a word into the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            // for each character, move to the corresponding child node, creating it if it doesn't exist
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        // mark the last node as the end of a word
        node.isEndOfWord = true;
        node.word = word; // store the complete word at the end node for retrieval
    }

    // returns a list of all words in the Trie that start with the given prefix
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;

        // traverse the Trie to find the node that matches the end of the prefix
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                // if at any point the prefix character is missing, return empty list
                return results;
            }
            node = node.children.get(c);
        }

        // recursively collect all complete words starting from this node
        collect(node, results);
        return results;
    }

    // helper method to collect all words under a given node
    private void collect(TrieNode node, List<String> results) {
        if (node == null) return;

        // if this node represents a complete word, add it to the result list
        if (node.isEndOfWord) {
            results.add(node.word);
        }

        // recursively visit all child nodes
        for (TrieNode child : node.children.values()) {
            collect(child, results);
        }
    }
}