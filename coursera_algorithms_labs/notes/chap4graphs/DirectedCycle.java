
/**
 *  This class adds to our standard recursive dfs() a boolean array onStack[]
 *  to keep track of the vertices for which the recursive call has not completed.
 *  When it finds an edge v->w to a vertex w that is on  the stack, it has discovered a directed cycle,
 *  which it can recover by following edgeTo[] links.
 * */
package chap4graphs;
//import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Stack;

public class DirectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // vertices on a cycle (if one exists)
    // vertices on recursive call stack
    //Keeps track of which vertices are currently on the recursion call stack (i.e., the path being explored).
    private boolean[] onStack;

    //cycle-finding constructor
    DirectedCycle(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    DirectedCycle(EdgeWeightedDigraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    /**
     * When executing dfs(G, v), we have followed a directed path from the source to v.
     * To keep track of this path, DirectedCycle maintains a vertex-indexed array onStack[]
     * that marks the vertices on the recursive call stack (by setting onStack[v] to true
     * on entry to dfs(G, v) and to false on exit).
     */
    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (this.hasCycle()) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                // If w is on the current stack, then you've found a back edge, and therefore a cycle.
                // which it can recover by following edgeTo[] links.
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        // You are using onStack[] to detect back edges, which are edges that point to a vertex already on the current recursion stack —
        // and these represent cycles in directed graphs.
        // After all neighbors are processed, mark v as off the recursion stack
        onStack[v] = false;
    }

    // dfs for EdgeWeightedDigraph
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (this.hasCycle()) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                // If w is on the current stack, then you've found a back edge, and therefore a cycle.
                // which it can recover by following edgeTo[] links.
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        // You are using onStack[] to detect back edges, which are edges that point to a vertex already on the current recursion stack —
        // and these represent cycles in directed graphs.
        // After all neighbors are processed, mark v as off the recursion stack
        onStack[v] = false;
    }

    //does G have a directed cycle?
    boolean hasCycle() {
        return cycle != null;
    }

    // vertices on a cycle (if one exists)ctor
    Iterable<Integer> cycle() {
        return cycle;
    }

}
