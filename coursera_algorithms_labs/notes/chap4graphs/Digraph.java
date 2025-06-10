/*
* In practice. Use adjacency-lists representation.
􀉾Algorithms based on iterating over vertices pointing from v.
􀉾Real-world digraphs tend to be sparse.
*
* */

package chap4graphs;

import edu.princeton.cs.algs4.Bag;


public class Digraph {
    private final int V;
    private final Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w); //add edge v→w
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}