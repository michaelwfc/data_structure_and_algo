/* Shortest paths in edge-weighted DAGs
* Topological order (or topological sorting) is a linear ordering of the vertices in a directed acyclic graph (DAG) such that:
* For every directed edge v → w, vertex v comes before w in the ordering.
* Key Conditions
* Only works for DAGs: A graph must be directed and must not contain cycles.
* If the graph has a cycle, a topological order does not exist.
* */

package chap4graphs;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class AcyclicSP extends SP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        // 在Java中，任何class的构造方法，第一行语句必须是调用父类的构造方法。
        // 如果没有明确地调用父类的构造方法，编译器会帮我们自动加一句super();
        // 如果父类没有默认的构造方法，子类就必须显式调用super()并给出参数以便让编译器定位到父类的一个合适的构造方法

        super(G, s);
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // topological order
        Topological topological = new Topological(G);
        // iterate over vertices in topological order
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

//    public double distTo(int v) // standard client query methods
//    public boolean hasPathTo(int v) // for SPT implementatations
//    public Iterable<Edge> pathTo(int v) // (See page 649.)

}
