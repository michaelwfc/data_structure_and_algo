package chap4graphs;

import edu.princeton.cs.algs4.Bag;


/*
 * Adjacency-list graph representation: Java implementation
 * */
public class Graph {
    private final int V;
    private Bag<Integer>[] adj; // adjacency lists ( using Bag data type )

    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V]; // create empty graph  with V vertices
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    // add edge v-w
    // (parallel edges and self-loops allowed)
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    //  iterator for vertices adjacent to v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}