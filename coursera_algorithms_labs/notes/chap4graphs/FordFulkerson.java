
/**
* This implementation of the Ford-Fulkerson algorithm finds the shortest augmenting path in the
residual network, finds the bottneck capacity in that path, and augments the flow along that path,
continuing until no path from source to sink exists.
* *
* */
package chap4graphs;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;


public class FordFulkerson {
    private boolean[] marked;   // true if s->v path in residual network
    private FlowEdge[] edgeTo;  // last edge on s->v path
    private double value;       // value of flow

    /**
     * Constructor for the FordFulkerson class.
     * @param G the flow network
     * @param s the source vertex
     * @param t the sink vertex
     */
    public FordFulkerson(FlowNetwork G, int s, int t) {
        // While there exists an augmenting path, use it.
        while (hasAugmentingPath(G, s, t)) {
            // Compute bottleneck capacity.
            double bottle = Double.POSITIVE_INFINITY;
            // Traverse the augmenting path from v back to s to find the minimum residual capacity
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

            // Augment flow.
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }


    /**
     * Finding an augmenting path in the residual network via breadth-first search.
     * @param G the flow network
     * @param s the source vertex
     * @param t the sink vertex
     * @return true if an augmenting path from source to sink exists, otherwise false
     */
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];
        Queue<Integer> q = new Queue<>();

        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                // If there is a residual capacity from v to w, and w is not marked, so that it is augmenting path
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    public double value() {
        return value;
    }

    //is v reachable from s in residual network?
    public boolean inCut(int v) {
        return marked[v];
    }

//    public static void main(String[] args) {
//        FlowNetwork G = new FlowNetwork(new In(args[0]));
//        int s = 0, t = G.V() - 1;
//        FordFulkerson maxflow = new FordFulkerson(G, s, t);
//        StdOut.println("Max flow from " + s + " to " + t);
//        for (int v = 0; v < G.V(); v++)
//            for (FlowEdge e : G.adj(v))
//                if ((v == e.from()) && e.flow() > 0)
//                    StdOut.println(" " + e);
//        StdOut.println("Max flow value = " + maxflow.value());
//    }

}
