/*Edge-weighted digraph: adjacency-lists implementation in Java
 * Same as EdgeWeightedGraph except replace Graph with Digraph.
 * */

package chap4graphs;

import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedDigraph {
    private final int V;
    private final Bag<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    // add edge e = v→w to only v's adjacency list
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);

    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    int V() {
        return V;
    }
}
