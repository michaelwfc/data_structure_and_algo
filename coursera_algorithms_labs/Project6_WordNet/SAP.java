

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.Stack;


public class SAP {
    private Digraph G;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("G is null");
        this.G = new Digraph(G);
    }

    private void validateVertex(int v) {
        int V = this.G.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        } else {
            int vertexCount = 0;

            for(Integer v : vertices) {
                ++vertexCount;
                if (v == null) {
                    throw new IllegalArgumentException("vertex is null");
                }

                this.validateVertex(v);
            }

            if (vertexCount == 0) {
                throw new IllegalArgumentException("zero vertices");
            }
        }
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths dibfsSource = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths dibfsDestine = new BreadthFirstDirectedPaths(G, w);
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (dibfsSource.hasPathTo(i) && dibfsDestine.hasPathTo(i)) {
                int currentLength = dibfsSource.distTo(i) + dibfsDestine.distTo(i);
                if (currentLength < minLength) {
                    minLength = currentLength;
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths dibfsSource = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths dibfsDestine = new BreadthFirstDirectedPaths(G, w);
        int ancestor = -1;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (dibfsSource.hasPathTo(i) && dibfsDestine.hasPathTo(i)) {
                int currentLength = dibfsSource.distTo(i) + dibfsDestine.distTo(i);
                if (currentLength < minLength) {
                    ancestor = i;
                    minLength= currentLength;
                }
            }
        }
        return ancestor;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        validateVertices(vs);
        validateVertices(ws);
        BreadthFirstDirectedPaths dibfsSource = new BreadthFirstDirectedPaths(G, vs);
        BreadthFirstDirectedPaths dibfsDestine = new BreadthFirstDirectedPaths(G, ws);
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (dibfsSource.hasPathTo(i) && dibfsDestine.hasPathTo(i)) {
                int currentLength = dibfsSource.distTo(i) + dibfsDestine.distTo(i);
                if (currentLength < minLength) {
                    minLength = currentLength;
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;


    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws){
        validateVertices(vs);
        validateVertices(ws);
        BreadthFirstDirectedPaths dibfsSource = new BreadthFirstDirectedPaths(G, vs);
        BreadthFirstDirectedPaths dibfsDestine = new BreadthFirstDirectedPaths(G, ws);
        int ancestor = -1;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (dibfsSource.hasPathTo(i) && dibfsDestine.hasPathTo(i)) {
                int currentLength = dibfsSource.distTo(i) + dibfsDestine.distTo(i);
                if (currentLength < minLength) {
                    ancestor = i;
                    minLength= currentLength;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
