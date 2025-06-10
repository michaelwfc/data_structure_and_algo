/*
* Topological sort
DAG. Directed acyclic graph.
Topological sort. Redraw DAG so all edges point upwards
*
*
* Proposition. Reverse DFS postorder of a DAG is a topological order.
* */

package chap4graphs;

import java.util.Iterator;
import java.util.Stack;

public class DepthFirstOrder {
    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G) {
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) dfs(G, v);
        }
    }
    private void dfs(Digraph G, int v){
        marked[v] = true;
        for(int w:G.adj(v)){
            if(!marked[w]){
                dfs(G,w);
            }
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost(){
        return reversePost;
    }
}
