/* Prim's algorithm: lazy implementation
*
* Challenge. Find the min weight edge with exactly one endpoint in T.
* Lazy solution. Maintain a PQ of edges with (at least) one endpoint in T.
*Key = edge; priority = weight of edge.
*Delete-min to determine next edge e = v–w to add to T.
*Disregard if both endpoints v and w are marked (both in T).
*Otherwise, let w be the unmarked vertex (not in T ):
– add to PQ any edge incident to w (assuming other endpoint not in T)
– add e to T and mark w
*
* */

package chap4graphs;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;


public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        mst = new Queue<>();
        marked = new boolean[G.V()];

        visit(G, 0); // assume G is connected
        while (!pq.isEmpty()) {
            Edge e = pq.delMin(); //repeatedly delete the min weight edge e = v–w from PQ
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue; //ignore if both endpoints in T
            mst.enqueue(e); //add edge e to tree
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true; //add v to T
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)]) // for each edge e = v–w, add to PQ if w not already in T
                pq.insert(e);
    }

    public Iterable<Edge> mst() {
        return mst;
    }

}
