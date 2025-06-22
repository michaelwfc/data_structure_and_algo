/*
* Topological order (or topological sorting) is a linear ordering of the vertices in a directed acyclic graph (DAG) such that:
* For every directed edge v → w, vertex v comes before w in the ordering.
* Key Conditions
* Only works for DAGs: A graph must be directed and must not contain cycles.
* If the graph has a cycle, a topological order does not exist.
* */

package chap4graphs;
import edu.princeton.cs.algs4.Topological;
//import edu.princeton.cs.algs4.DirectedEdge;

public class AcyclicSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        Topological topological = new Topological(G); // topological order
        for (int v : topological.order()) {
            for (DirectedEdge e : G.adj(v)) {
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

        }
    }

}
