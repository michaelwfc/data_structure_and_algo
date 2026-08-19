/** the longest-paths in edge-weighted DAGs
 * Formulate as a shortest paths problem in edge-weighted DAGs.
 * - Negate all weights.
 * - Find shortest paths.
 * - Negate weights in result.
 * */

package chap4graphs;

//import edu.princeton.cs.algs4.DirectedEdge;
//import edu.princeton.cs.algs4.EdgeWeightedDigraph;
//import edu.princeton.cs.algs4.Topological;


public class AcyclicLP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;


    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        // 在Java中，任何class的构造方法，第一行语句必须是调用父类的构造方法。
        // 如果没有明确地调用父类的构造方法，编译器会帮我们自动加一句super();
        // 如果父类没有默认的构造方法，子类就必须显式调用super()并给出参数以便让编译器定位到父类的一个合适的构造方法
//        super(G, s);
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.NEGATIVE_INFINITY; // use NEGATIVE_INFINITY instead of Double.POSITIVE_INFINITY

        distTo[s] = 0.0;

        // topological order
        Topological topological = new Topological(G);
        if(!topological.hasOrder()){
            throw new IllegalArgumentException("Digraph is not a DAG");
        }
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
        // in original relax, we want to find the shortest path to w: distTo[w] >  distTo[v] + e.weight()
        // now we want to find the maximum path to w: distTo[w] > distTo[v] + e.weight()
        if (distTo[w] < distTo[v] + e.weight())
        {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

        }
    }

    // distance from s to v, ∞ if no path
    public double distTo(int v) {
        return distTo[v];
    }
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

//    public Iterable<Edge> pathTo(int v) // (See page 649.)

}
