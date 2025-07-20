/*
* In practice. Use adjacency-lists representation.
􀉾Algorithms based on iterating over vertices pointing from v.
􀉾Real-world digraphs tend to be sparse.
*
* */

package chap4graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Digraph {
    private final int V;
    // Adjacency-lists digraph representation: Maintain vertex-indexed array of lists.
    private final Bag<Integer>[] adj;
    private int E;

    //create an empty digraph with V vertices
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    // create a digraph from input stream
    Digraph(In in) {
        V = in.readInt();
        E = in.readInt();
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    // add edge v–w
    public void addEdge(int v, int w) {
        adj[v].add(w); //add edge v→w
        E++;
    }

    // iterator for vertices pointing from v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    //number of edges
    public int E() {
        return E;
    }

    //reverse of this digraph
    Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                R.addEdge(w, v);
        return R;
    }

    // string representation
//    String toString()

    public static void main(String[] args) {
        String filename = "algs4-data/tinyDG.txt";//args[0];
        In in = new In(filename);
        Digraph G = new Digraph(in);
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println(v + "->" + w);

    }
}