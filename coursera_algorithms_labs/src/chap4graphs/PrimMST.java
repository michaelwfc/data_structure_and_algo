/*
* Prim's algorithm: eager implementation
*
* Challenge. Find min weight edge with exactly one endpoint in T.
Eager solution. Maintain a PQ of vertices connected by an edge to T,
where priority of vertex v = weight of shortest edge connecting v to T.
􀉾Delete min vertex v and add its associated edge e = v–w to T.
􀉾Update PQ by considering all edges e = v–x incident to v
– ignore if x is already in T
– add x to PQ if not already on it
– decrease priority of x if v–x becomes shortest edge connecting x to T
*
* */
package chap4graphs;

import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public PrimMST(EdgeWeightedGraph G){
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for(int v=0;v< G.V();v++)
            distTo[v] = Double.POSITIVE_INFINITY;


    }
}
