package chap4graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class FlowNetwork {
    private final int V;
    private Bag<FlowEdge>[] adj;

    //create an empty flow network with V vertices
    public FlowNetwork(int V) {
        this.V = V;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<FlowEdge>();
    }

    // construct flow network input stream
    public FlowNetwork(In in, int v) {

    }



    int V(){return this.V;}

    //number of edges
    int E(){
        int E = 0;
        for (int v = 0; v < V; v++)
            E += adj[v].size();
        return E/2;
    }

    void addEdge(FlowEdge e){
        int v =  e.from();
        int w =e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    Iterable<FlowEdge> adj(int v){
        return adj[v];
    }

    //all edges in this flow network
//    Iterable<FlowEdge> edges(){
//
//    }

//    String toString()

}
