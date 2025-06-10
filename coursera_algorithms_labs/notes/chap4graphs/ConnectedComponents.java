/*
* Goal. Partition vertices into connected components
*
* Initialize all vertices v as unmarked.
For each unmarked vertex v, run DFS to identify all
vertices discovered as part of the same component.
* */

package chap4graphs;


public class ConnectedComponents {
    private boolean[] marked;
    private int[] id;  //id[v] = id of component containing v
    private int count;  //number of components

    public ConnectedComponents(Graph G) {
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v); // run DFS from one vertex in  each component
                count++;
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count; // all vertices discovered in same call of dfs have same id
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }

    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }
}
