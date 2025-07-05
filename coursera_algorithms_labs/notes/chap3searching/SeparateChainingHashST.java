/*
* Hashing: basic plan
Save items in a key-indexed table (index is a function of the key).
Hash function. Method for computing array index from key.
*
* After describing ways to compute hash functions, we shall consider two different approaches to collision resolution:
* separate chaining and linear probing
*
* Use an array of M < N linked lists. [H. P. Luhn, IBM 1953]
􀉾Hash: map key to integer i between 0 and M - 1.
􀉾Insert: put at front of ith chain (if not already there).
􀉾Search: need to search only ith chain.
*
* */
package chap3searching;

public class SeparateChainingHashST<Key, Value> {
    private int M = 97; // // hash table size, number of chains
    private Node[] st = new Node[M]; // array of chains

    private static class Node {
        private Object key;  // no generic array creation
        private Object val;  // (declare key and value of type Object)
        private Node next;

        public Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;

        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key)) return (Value) x.val;
        }
        return null;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next)
            // update the value at i with key
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        // insert a new node with key value at i
        st[i] = new Node(key, val, st[i]);
    }
}