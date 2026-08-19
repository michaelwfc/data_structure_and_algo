/*
* Tries. [from retrieval, but pronounced "try"]
􀉾Store characters in nodes (not keys).
􀉾Each node has R children, one for each possible character. (for now, we do not draw null links)
*
* Search in a trie
* Follow links corresponding to each character in the key.
􀉾Search hit: node where search ends has a non-null value.
􀉾Search miss: reach null link or node where search ends has null value.
*
* Insertion into a trie
* Follow links corresponding to each character in the key.
􀉾Encounter a null link: create new node.
􀉾Encounter the last character of the key: set value in that node.
*
*
* To delete a key-value pair:
􀉾Find the node corresponding to key and set value to null.
􀉾If node has null value and all null links, remove that node (and recur).
* */

package chap5string;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TrieST<Value> {
    private static final int R = 256;
    private Node root = new Node();

    private static class Node {
        private Object value; //use Object instead of Value since  no generic array creation in Java
        // characters are implicitly defined by link index
        // neither keys nor characters are explicitly stored
        private Node[] next = new Node[R];
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.value = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public boolean contains(String key) {
        Value val = get(key);
        return val != null;

    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length())
            x.value = null;
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        //after the recursive calls for a node x, we return null if the client value
        //and all of the links in a node are null; otherwise we return x.
        if (x.value != null) return x;
        for (char c = 0; c < R; c++)
            if (x.next[c] != null) return x;
        return null;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    //keys having s as a prefix
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> q = new Queue<>();
        collect(get(root, prefix, 0), prefix, q);
        return q;
    }

    private void collect(Node x, String prefix, Queue<String> q) {
        if (x == null) return;
        if (x.value != null) q.enqueue(prefix);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, q);
        }
    }

    //keys that match s (where . is a wildcard)
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> q = new Queue<>();
        collect(root, "", pattern, q);
        return q;
    }

    /* similar to keysWithPrefix()
    * add an argument specifying the pattern to collect() and add a test to make a recursive call
    for all links when the pattern character is a wildcard or only for the link corresponding
    to the pattern character otherwise
    * */
    public void collect(Node x, String prefix, String pattern, Queue<String> q) {
        int d = prefix.length();
        if (x == null) return;
        if (x.value != null && d == pattern.length()) q.enqueue(prefix);
        if (d == pattern.length()) return;

        char next = pattern.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c) {
                collect(x.next[c], prefix + c, pattern, q);
            }
        }
    }

    //longest key that is a prefix of s
    String longestPrefixOf(String query) {
        int length = search(root, query, 0, 0);
        return query.substring(0, length);

    }

    private int search(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.value != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return search(x.next[c], query, d + 1, length);
    }


    public static void main(String[] args) {
        String cwd = System.getProperty("user.dir");
        System.out.println("Current working directory: " + cwd);

        String dictionaryFile = "dictionary-yawl.txt"; //../../Project9_Boggle
        In in = new In(dictionaryFile);
        String[] dictionary = in.readAllStrings();
        TrieST trie = new TrieST();
        int word_index = 1;
        for (String word : dictionary) {
            trie.put(word, word_index);
            word_index++;
        }
        String word = "APPLE";
        int value = (int) trie.get(word);
        StdOut.println("word:" + word + " has value: " + value);

        String prefix = "AAT";
        boolean hasPrefix = trie.contains(prefix);
        StdOut.println("prefix:" + prefix + " is in dictionary: " + hasPrefix);

        Iterable<String> keys = trie.keysWithPrefix(prefix);
        for (String key : keys) {
            StdOut.println("prefix with:" + prefix + " has key: " + key);
        }

    }
    }

