/*
* Dijkstra's algorithm
- Consider vertices in increasing order of distance from s  (non-tree vertex with the lowest distTo[] value).
- Add vertex to tree and relax all edges pointing from that vertex.
* */
package chap4graphs;

import chap2sorting.IndexMinPQ;

public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;


    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for(int v=0; v<G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq.insert(s,0.0);
        while(!pq.isEmpty()) {
            int v = pq.delMin(); // relax vertices in order of distance from s
            for(DirectedEdge e: G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e)
    {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight())
        {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

            if(pq.contains(w))pq.decreaseKey(w, distTo[w]); // update pq with the new distTo[w]
            else pq.insert(w,distTo[w]);  // insert w with distTo[w] into pq
        }
    }

}
