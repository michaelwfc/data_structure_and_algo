/*
* Dijkstra's algorithm
- Consider vertices in increasing order of distance from s  (non-tree vertex with the lowest distTo[] value).
- Add vertex to tree and relax all edges pointing from that vertex.
*
*
* Both algorithms build a rooted tree by adding an edge to a growing tree:
* Prim’s adds next the non-tree vertex that is closest to the tree;
* Dijkstra’s adds next the non-tree vertex that is closest to the source.
*
*
* */
package chap4graphs;

import chap2sorting.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;


    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin(); // relax vertices in order of distance from s( shortest distance to s)
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    // This implementation of Dijkstra’s algorithm grows the SPT by adding an edge at a time, always
    // choosing the edge from a tree vertex to a non-tree vertex whose destination w is closest to s.
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]); // if pq contains w vertex, update pq with the new distTo[w] as key
            else pq.insert(w, distTo[w]);  // insert w with distTo[w] into pq
        }
    }

    // distance from s to v, ∞ if no path
    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

}
