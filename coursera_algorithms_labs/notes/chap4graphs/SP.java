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
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class SP {
    private EdgeWeightedDigraph G;
    private int s;
    private DirectedEdge[] edgeTo;
    private double distTo[];

    public SP(EdgeWeightedDigraph G, int s){
        this.G = G;
        this.s = s;
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


//    public static void main(String[] args) {
//        EdgeWeightedDigraph G;
//        G = new EdgeWeightedDigraph(new In(args[0]));
//        int s = Integer.parseInt(args[1]);
//        SP sp = new SP(G, s);
//        for (int t = 0; t < G.V(); t++) {
//            StdOut.print(s + " to " + t);
//            StdOut.printf(" (%4.2f): ", sp.distTo(t));
//            if (sp.hasPathTo(t))
//                for (DirectedEdge e : sp.pathTo(t))
//                    StdOut.print(e + " ");
//            StdOut.println();
//        }
//    }
}
