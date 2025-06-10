package chap4graphs;

import java.util.Stack;

/*
* Depth-first search. Put unvisited vertices on a stack.
* Breadth-first search. Put unvisited vertices on a queue.
*
*
* To visit a vertex v :
􀉾Mark vertex v as visited.
􀉾Recursively visit all unmarked vertices adjacent to v.
*
* Proposition. DFS marks all vertices connected to s in time proportional to the sum of their degrees.
*
* */
public class DepthFirstSearch {
    private boolean[] marked;  // marked[v]=true if v connected to s
    private int[] edgeTo; // edgeTo = previous vertex on path from s to v
    private int s;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s); // find vertices connected to s
    }

    // recursive DFS
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<Integer> path =  new Stack<Integer>();
        for(int x= v; x!=s; x= edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;

    }
}
