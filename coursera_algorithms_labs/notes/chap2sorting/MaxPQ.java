/*
* Binary heap: Java implementation
*
* */

package chap2sorting;

import edu.princeton.cs.algs4.StdOut;



public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;

    public MaxPQ(int capacity) {

        pq = (Key[]) new Comparable[capacity + 1]; //fixed capacity   (for simplicity)
    }

    public boolean isEmpty() {
        return N == 0;
    }

    /*
    * Insert. Add node at end, then swim it up.
    * Cost. At most 1 + lg N compares.
    * */
    public void insert(Key x) {
        pq[++N] = x;
        swim(N);
    }

    /*
    * Delete max. Exchange root with node at end, then sink it down.
    * Cost. At most 2 lg N compares.
    * */
    public Key delMax() {
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null; //prevent loitering
        return max;
    }

    /* Promotion in a heap
    * Scenario. Child's key becomes larger key than its parent's key.
    * To eliminate the violation:
    􀉾Exchange key in child with key in parent.
    􀉾Repeat until heap order restored.
    * */
    private void swim(int k) {
        // parent of node at k is at k/2
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /* Demotion in a heap
    * Scenario. Parent's key becomes smaller than one (or both) of its children's.
    * To eliminate the violation:
    􀉾Exchange key in parent with key in larger child.
    􀉾Repeat until heap order restored.
    * */
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            // children of node at k are 2k and 2k+1
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> maxPQ = new MaxPQ<>(10);
        maxPQ.insert(2);
        maxPQ.insert(5);
        maxPQ.insert(1);
        int output = maxPQ.delMax();
        StdOut.println("max: " + output);

    }
}