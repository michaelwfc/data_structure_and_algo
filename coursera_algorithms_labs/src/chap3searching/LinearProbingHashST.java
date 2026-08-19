/*
* Linear probing hash table
* Hash. Map key to integer i between 0 and M-1.
* Insert. Put at table index i if free; if not try i+1, i+2, etc.
* Search. Search table index i; if occupied but no match, try i+1, i+2, etc.
* */
package chap3searching;

public class LinearProbingHashST<Key, Value> {
    private int M = 30001;
    private Value[] vals = (Value[]) new Object[M];
    private Key[] keys = (Key[]) new Object[M];

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                break;
        keys[i] = key;
        vals[i] = val;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }
}