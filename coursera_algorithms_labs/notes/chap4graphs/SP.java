/*
* Single-source shortest paths API
* Goal. Find the shortest path from s to every other vertex.
*
* The single-source single-sink problem is a special case. If you reverse the digraph, you can solve the single-sink version of the problem (by interchanging the roles of
sss and tt). The all-pairs problem is a generalization where you solve the single-source version of the problem for every vertex.
*
* */
package chap4graphs;

import edu.princeton.cs.algs4.StdOut;


public class SP {

    public SP(EdgeWeightedDigraph G, int s)

    double distTo[];
    Iterable<DirectedEdge> pathTo[];

    boolean hasPathTo(int v);

    public void main() {
        SP sp = new SP(G, s);
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v))
                StdOut.print(e + " ");
            StdOut.println();
        }
    }
}
