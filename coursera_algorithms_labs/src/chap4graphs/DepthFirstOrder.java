/*
Topological sort. Redraw DAG so all edges point upwards
*
* Proposition. Reverse DFS postorder of a DAG is a topological order.
* */

package chap4graphs;

import java.util.Iterator;
//import java.util.Stack;
import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Queue;

public class DepthFirstOrder {
    /**
     * - Preorder : Put the vertex on a queue before the recursive calls.
     * - Postorder : Put the vertex on a queue after the recursive calls.
     * - Reverse postorder : Put the vertex on a stack after the recursive calls.
     */
    private boolean[] marked;
    private Queue<Integer> pre; // vertices in preorder
    private Queue<Integer> post; // vertices in postorder
//    private Stack<Integer> reversePost; // vertices in reverse postorder

    public DepthFirstOrder(Digraph G) {
        pre = new Queue<Integer>();
        post = new Queue<Integer>();

        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    // constructor for edge-weighted digraphs
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new Queue<Integer>();
        post = new Queue<Integer>();

        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }


    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        // when we complete the dfs search for vertex v
        // In postorder, a node is added after all of its descendants have been visited.
        //So if you reverse the postorder, you get the reverse postorder — which is exactly the topological order in a DAG.
        post.enqueue(v);
//        reversePost.push(v);
    }


    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post.enqueue(v);
    }


    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    //returns all vertices in “reverse DFS postorder”
    public Iterable<Integer> reversePost() {
//        return this.post;
        Stack<Integer>reverse = new Stack<Integer>();
        for(int v: this.post){
            reverse.push(v);
        }
        return reverse;
    }
}
