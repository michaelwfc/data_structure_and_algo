/* Index priority queue
In many applications, it makes sense to allow clients to refer
to items that are already on the priority queue. One easy way to do so is to associate
a unique integer index with each item.

* Associate an index between 0 and N - 1 with each key in a priority queue.
* - Client can insert and delete-the-minimum.
* - Client can change the key by specifying the index.
* */
package chap2sorting;

public class IndexMinPQ<Key extends Comparable<Key>> {


    // binary heap using 1-based indexing, Change  pq[] to hold indices,
    // 1-> root, 2-> left child, 3-> right child// number of elements on PQ
    // pg[1] to get the index for the minimum item on binary heap
    private int[] pq;

    // inverse: qp[pq[i]] = pq[qp[i]] = i,qp[i] gives the position of i in pq[] (the index j such that pq[j] is i)
    // j =  qp[i],
    private int[] qp;

    // items with priorities, an array keys[] to hold the key values
    // keys[index] return key
    private Key[] keys;
    private int N;  //

    //create indexed priority queue with indices 0, 1, …, N-1
    public IndexMinPQ(int N) {
        keys = (Key[]) new Comparable[N + 1];
        qp = new int[N + 1];
        pq = new int[N + 1];
        for (int i = 0; i <= N; i++)
            qp[i] = -1; // Use the convention that qp[i] = -1 if i is not on the queue
    }

    //insert item ; associate it with k
    public void insert(int k, Key key) {
        N++;
        pq[N] = k;  // insert N position on binary heap,which has index k
        qp[k] = N;  // associate index k with position N
        keys[k] = key; // insert k position of the key
        swim(N);

    }

    //change the item associated with k to item
    public void change(int k, Key key) {
        keys[k] = key;
        // swim from k to top
        swim(qp[k]);
        sink(qp[k]);
    }

    //is k associated with some item?
    public boolean contains(int k) {

        return qp[k] != -1;
    }

    public void delete(int k){
        if (!contains(k))
            return;
//        exch(k, N--);
        int index = qp[k];
        exch(index, N--);
        swim(index);
        sink( index);
        keys[pq[N+1]]= null;
        qp[pq[N+1]] = -1;
    }

    public Key min() {
        //return the smallest item by 1st index on binary heap
        // 1:  the root on binary heap(Min heap) is the smallest item
        // pq[1]: get the index of keys
        return keys[pq[1]];
    }

    public int MinIndex() {
        return pq[1];
    }

    public int delMin() {
        int indexOfMin = pq[1];  // get the index of the minimum
        exch(1, N--); // exchange the minimum with the last element in the heap
        sink(1);
        keys[pq[N + 1]] = null;
        qp[pq[N + 1]] = -1;
        return indexOfMin;
    }

    /*
    *  Lower the priority (i.e. assign a smaller key) to the item at index i, and re-adjust the heap accordingly.
    * It's often needed when you discover a better path, lower cost, or higher priority during an algorithm.
    *  For example: In Dijkstra’s algorithm, when a shorter path to a vertex is found, you use decreaseKey(vertexIndex, newDistance) to update the priority queue.
    * */
    public void decreaseKey(int k, Key key) {
        if(!contains(k)) throw new IllegalArgumentException("index is not in the priority queue");
        if(keys[k].compareTo(key) <=0) throw new IllegalArgumentException("New key is greater than current key");

        keys[k] = key;
        swim(qp[k]);

    }


    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /* Promotion in a heap
    * Scenario. Child's key becomes smaller key than its parent's key.
    * To eliminate the violation:
    􀉾Exchange key in child with key in parent.
    􀉾Repeat until heap order restored.
    * */

    private void swim(int k) {
        // parent of node at k is at k/2
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /* Demotion in a heap
    * Scenario. Parent's key becomes larger than one (or both) of its children's.
    * To eliminate the violation:
    􀉾Exchange key in parent with key in larger child.
    􀉾Repeat until heap order restored.
    * */
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            // children of node at k are 2k and 2k+1, to select the smallest child
            if (j < N && less(j + 1, j)) j++;
            if (less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        //   return pq[i].compareTo(pq[j]) < 0;
        Key keyI = keys[pq[i]]; // position i-> index-> key
        Key keyJ = keys[pq[j]];
        return keyI.compareTo(keyJ) < 0;

    }

    private void exch(int i, int j) {
//        Key t = pq[i];
        int t = pq[i];  //get the index of the key
        pq[i] = pq[j];
        pq[j] = t;
    }

    public void main(String[] args) {

    }

}
