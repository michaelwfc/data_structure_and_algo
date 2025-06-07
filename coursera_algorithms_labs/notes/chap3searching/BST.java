package chap3searching;

/*
 * Definition. A BST is a binary tree in symmetric order.
 * Symmetric order. Each node has a key, and every node’s key is:
 * Larger than all keys in its left subtree.
 * Smaller than all keys in its right subtree.
 *
 * Java definition. A BST is a reference to a root Node
 *
 * Tree shape
 * Remark. Tree shape depends on order of insertion.
 * */


//import java.util.Queue;

import edu.princeton.cs.algs4.Queue;


public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    /*
     * A Node is comprised of four fields:
     * A Key and a Value.
     * A reference to the left(smaller keys) and right(larger keys) subtree.
     *
     * Key and Value are generic types; Key is Comparable
     * */
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count; // number of node in subtree

        public Node(Key key, Value val, int count) {
            this.key = key;
            this.val = val;
            this.count = count;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    // Put. Associate value with key
    // Cost. Number of compares is equal to 1 + depth of node.
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    //concise, but tricky,
    //recursive code;
    //read carefully!
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        // ?
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    //Floor. Largest key ≤ a given key.
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;

    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    // Rank. How many keys < k ?
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);

    }


    public Iterable<Key> iterator() {
        Queue<Key> q = new Queue<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);

    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*
     * The main defect of Hibbard deletion is that it unbalances the tree, leading to  square root of, n, end square root height.
     * If instead of replacing the node to delete with its successor, you flip a coin and replace it with either its successor or predecessor,
     * then, in practice, the height becomes logarithmic (but nobody has been able to prove this fact mathematically).
     *
     * * */
    public void delete(Key key) {
        root = delete(root, key);

    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            // ???
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }


}