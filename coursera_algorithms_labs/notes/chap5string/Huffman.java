/*
 * Count frequency freq[i] for each char i in input.
 * Start with one node corresponding to each char i (with weight freq[i]).
 * Repeat until single trie formed:
    – select two tries with min weight freq[i] and freq[j]
    – merge into single trie with weight freq[i] + freq[j]
 *
 * */
package chap5string;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
    private static int R = 256;

    private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }

    }

    /*
    * Expanding a bitstream that was encoded with a prefix free code.
    * Read the trie (encoded at the beginning of the bitstream)
    * Read the count of characters to be decoded
    * Use the trie to decode the bitstream
    *   - Start at root.
        - proceed down the trie as directed by the bitstream : read in input bit,Go left if bit is 0; go right if 1
        - If leaf node, print char and return to root.
    * */
    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt(); //read in number of chars
        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) // expand codeword for ith char
            {
                if (BinaryStdIn.readBoolean())
                    x = x.right;
                else x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    /*
     * Write preorder traversal of trie; mark leaf and internal nodes with a bit.
     * */
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }


    //Using preorder traversal to encode a trie as a bitstream
    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            char c = BinaryStdIn.readChar(8);
            return new Node(c, 0, null, null);
        }
        Node x = readTrie();
        Node y = readTrie();
        return new Node('\0', 0, x, y); //used only for leaf nodes
    }

    /*
     * we use a character-indexed array st[] instead of a general symbol table for efficiency,
     * */
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }


    /*Building an encoding table from a (prefix-free) code trie
     * For any trie, it produces a table giving the bitstring associated with each character in the trie
     * (represented as a String of 0s and 1s).
     * */
    private static void buildCode(String[] st, Node x, String s) {
        // make a lookup table from trie
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    /*
    * Huffman encoding is a two-pass algorithm because we will need to read the input stream a second time to compress it.)
    - Count frequency freq[i] for each char i in input.
    - Start with one node corresponding to each char i (with weight freq[i]).
    - Repeat until single trie formed:
        – select two tries with min weight freq[i] and freq[j]
        – merge into single trie with weight freq[i] + freq[j]
    * */
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char i = 0; i < R; i++) {
            if (freq[i] > 0) {
                pq.insert(new Node(i, freq[i], null, null));
            }
        }
        while (pq.size() > 1) {
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    /*
     * Read the input.
     * Tabulate the frequency of occurrence of each char value in the input.
     * Build the Huffman encoding trie corresponding to those frequencies.
     * Build the corresponding codeword table, to associate a bitstring with each char value in the input.
     * Write the trie, encoded as a bitstring.
     * Write the count of characters in the input, encoded as a bitstring.
     * Use the codeword table to write the codeword for each input character.
     *
     * */
    private static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        // Build Huffman code trie.
        Node root = buildTrie(freq);

        // Build code table (recursive).
        String[] st = new String[R];
        buildCode(st, root, "");
        // Print trie for decoder (recursive).
        writeTrie(root);
        BinaryStdOut.write(input.length);
        // Use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '1')
                    BinaryStdOut.write(true);
                else BinaryStdOut.write(false);
            }
        }
        BinaryStdOut.close();

    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            compress();
        } else {
            if (!args[0].equals("+")) {
                throw new IllegalArgumentException("Illegal command line argument");
            }

            expand();
        }

    }

}
