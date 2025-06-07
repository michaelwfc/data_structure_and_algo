package chap3searching;

private static final boolean RED = true;
private static final boolean BLACK = false;

/**
 * We think of the links  as being of two different types:
 * red links, which bind together two 2-nodes to represent 3-nodes,
 * black links, which bind together the 2-3 tree.
 *
 * An equivalent definition. Another way to proceed is to define red-black BSTs as BSTs
 * having red and black links and satisfying the following three restrictions:
 * ■ Red links lean left.
 * ■ No node has two red links connected to it.
 * ■ The tree has perfect black balance : every path from the root to a null link has the  same number of black links.
 * There is a 1-1 correspondence between red-black BSTs defined in this way and 2-3 trees.
 */
/

public class RBBST<Key extends Comparable<Key>, Value> extends BST {
    /*
     * Each node is pointed to by precisely one link (from its parent) ⇒ can encode color of links in nodes.
     *
     * */
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        boolean color; // color of parent link

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    /*
     * Left rotation. Orient a (temporarily) right-leaning red link to lean left
     * Invariants. Maintains symmetric order and perfect black balance.
     * */
    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /*
     * Right rotation. Orient a left-leaning red link to (temporarily) lean right.
     */
    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

//    @Override
//    public void put(Key key, Value val) {
//        root = put(root, key, val);
//    }


    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        if (isRed(h.right) && (!isRed(h.left))) h = rotateLeft(h);
        if (isRed(h.left) && (isRed(h.left.left))) h = rotateRight(h);
        if (isRed(h.left) && (isRed(h.right))) flipColors(h);
        return h;
    }
}
