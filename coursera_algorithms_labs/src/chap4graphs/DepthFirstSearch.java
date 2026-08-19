package chap4graphs;

public class DepthFirstSearch {
    private boolean[] marked; //true if path from s

    //constructor marks vertices reachable from s
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    //recursive DFS does the work
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    // client can ask whether any vertex is connected to s
    public boolean visited(int v) {
        return marked[v];
    }
}