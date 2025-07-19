package chap4graphs;

public class FlowEdge {
    private final int v; // edge source
    private final int w; // edge target
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;  // from vertex v to w
        this.w = w;
        this.capacity = capacity;
        this.flow=0.0;
    }

    //vertex this edge points from
    public int from() {
        return v;
    }

    //vertex this edge points to
    public int to() {
        return w;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Illegal endpoint");
    }

    /* Residual capacity.
    - Forward edge: residual capacity = ce - fe.
    - Backward edge: residual capacity = fe.
    * Residual networks allow us to use graph search to find an augmenting path, since any path from source to sink
    in the residual network corresponds directly to an augmenting path in the original network.
    * */
    public double residualCapacityTo(int vertex) {
        if (vertex == v) return flow;
        else if (vertex == w) return capacity- flow;
        else throw new RuntimeException("Inconsistent edge");

    }

    //add delta flow toward v
    public void addResidualFlowTo(int vertex, double delta) {
        if(vertex==v)flow -= delta;
        else if(vertex==w) flow+=delta;
        else throw new RuntimeException("Inconsistent edge");
    }

}
