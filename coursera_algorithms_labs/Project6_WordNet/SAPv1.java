/*
 * SAP: Shortest ancestral path.
 * An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x.
 * A shortest ancestral path is an ancestral path of minimum total length. We refer to the common ancestor in a shortest ancestral path as a shortest common ancestor.
 * Note also that an ancestral path is a path, but not a directed path.
 * * */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.Stack;

public class SAPv1 {
    private Digraph G;
    private BreadthFirstDirectedPaths dibfs;
    private ReverseBFS reverseBFS;

    // constructor takes a digraph (not necessarily a DAG)
    public SAPv1(Digraph G) {
        if (G == null) throw new IllegalArgumentException("G is null");
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        dibfs = new BreadthFirstDirectedPaths(this.G, v);
        int bfsDistance =-1;
        if(dibfs.hasPathTo(w))bfsDistance = dibfs.distTo(w);

        reverseBFS = new ReverseBFS(this.G, v);
        int reverseDistance = reverseBFS.distTo(w);

        if (bfsDistance == -1) return reverseDistance;
        else if (reverseDistance == -1) return bfsDistance;
        else if (bfsDistance < reverseDistance) return bfsDistance;
        else return reverseDistance;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws)
    {
        dibfs = new BreadthFirstDirectedPaths(G,vs);
        int minBfsDistance = Integer.MAX_VALUE;
        for(int w:ws)
        {
            int bfsDistance =  dibfs.distTo(w);
            if (dibfs.hasPathTo(w) && bfsDistance< minBfsDistance)minBfsDistance= bfsDistance;
        }

        reverseBFS = new ReverseBFS(this.G, vs);
        for(int w:ws){
            int s= reverseBFS.distTo(w);
            if(reverseBFS.hasPathTo(w)&& s< minBfsDistance)minBfsDistance =s;
            }

        return minBfsDistance== Integer.MAX_VALUE?-1:minBfsDistance;
        }


    private void validateVertex(int v) {
        int V = this.G.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    //     a common ancestor of v and w that partic in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int length = length(v, w);
        if (length == -1) return -1;
        int bfsDistance = dibfs.distTo(w);
        int reverseDistance = reverseBFS.distTo(w);
        if (bfsDistance < reverseDistance) {
            return v;
        } else {
            Stack<Integer> pathStack = (Stack<Integer>) reverseBFS.pathTo(w);
            int ancestor = pathStack.pop();
            return ancestor;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
//    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws){
//
//    }


    private static class ReverseBFS {

        private boolean[] marked;
        private int[] edgeTo;
        private int[] distTo;

        private boolean[] reverseMarked;
        private int[] reversEdgeTo;
        private int[] reverseDistTo;

        // Idea: use BFS to search from, in each step forward, then search path with reverse G to do BFS
        // if get the path, then this path is shortest
        // if not, no path
        public ReverseBFS(Digraph G, int v) {
            this.marked = new boolean[G.V()];
            this.edgeTo = new int[G.V()];
            this.distTo = new int[G.V()];

            this.reverseMarked = new boolean[G.V()]; // the marked array start from the ancestor
            this.reversEdgeTo = new int[G.V()];
            this.reverseDistTo = new int[G.V()]; // the distance start from the ancestor

            for (int s = 0; s < G.V(); ++s) {
                this.distTo[s] = Integer.MAX_VALUE;
                this.reverseDistTo[s] = Integer.MAX_VALUE;
            }

            this.reverseBfs(G, v);
        }


        private void reverseBfs(Digraph G, int v) {
            Queue<Integer> q = new Queue<>();
            marked = new boolean[G.V()];
            Digraph reverseG = G.reverse();
            Queue<Integer> reverseQ = new Queue<>();
            marked[v] = true;
            distTo[v] = 0;
            q.enqueue(v);

            while (!q.isEmpty()) {
                // from this candidate to BFS search path with reverse G for w
                int candidate = q.dequeue();
                reverseMarked[candidate] = true;
                reverseDistTo[candidate] = 0;
                reverseQ.enqueue(candidate);
                while (!reverseQ.isEmpty()) {
                    int s = reverseQ.dequeue();
                    for (int t : reverseG.adj(s)) {
                        if (!reverseMarked[t]) {
                            reverseMarked[t] = true;
                            reversEdgeTo[t] = s;
                            reverseDistTo[t] = reverseDistTo[s] + 1;
                            reverseQ.enqueue(t);
                        }
                    }
                }

                // continue the bfs for G
                for (int t : G.adj(candidate)) {
                    if (!marked[t]) {
                        marked[t] = true;
                        edgeTo[t] = candidate;
                        distTo[t] = distTo[candidate] + 1;
                        q.enqueue(t);
                    }
                }
            }
        }

        public ReverseBFS(Digraph G, Iterable<Integer> sources) {
            this.marked = new boolean[G.V()];
            this.edgeTo = new int[G.V()];
            this.distTo = new int[G.V()];

            this.reverseMarked = new boolean[G.V()]; // the marked array start from the ancestor
            this.reversEdgeTo = new int[G.V()];
            this.reverseDistTo = new int[G.V()]; // the distance start from the ancestor

            for (int s = 0; s < G.V(); ++s) {
                this.distTo[s] = Integer.MAX_VALUE;
                this.reverseDistTo[s] = Integer.MAX_VALUE;
            }

            this.reverseBfs(G, sources);
        }


        private void reverseBfs(Digraph G, Iterable<Integer> sources){
            Queue<Integer> q = new Queue<>();
            marked = new boolean[G.V()];
            Digraph reverseG = G.reverse();
            Queue<Integer> reverseQ = new Queue<>();
            for(int v: sources) {
                marked[v] = true;
                distTo[v] = 0;
                q.enqueue(v);
            }

            while (!q.isEmpty()) {
                // from this candidate to BFS search path with reverse G for w
                int candidate = q.dequeue();
                reverseMarked[candidate] = true;
                reverseDistTo[candidate] = 0;
                reverseQ.enqueue(candidate);
                while (!reverseQ.isEmpty()) {
                    int s = reverseQ.dequeue();
                    for (int t : reverseG.adj(s)) {
                        if (!reverseMarked[t]) {
                            reverseMarked[t] = true;
                            reversEdgeTo[t] = s;
                            reverseDistTo[t] = reverseDistTo[s] + 1;
                            reverseQ.enqueue(t);
                        }
                    }
                }

                // continue the bfs for G
                for (int t : G.adj(candidate)) {
                    if (!marked[t]) {
                        marked[t] = true;
                        edgeTo[t] = candidate;
                        distTo[t] = distTo[candidate] + 1;
                        q.enqueue(t);
                    }
                }
            }
        }

        public boolean hasPathTo(int v) {
            return this.reverseMarked[v];
        }

        public int distTo(int w) {
            if (hasPathTo(w)) {
                // if w is marked ,we know we found a path
                int reverseDistance = reverseDistTo[w];
                Stack<Integer> pathStack = (Stack<Integer>) pathTo(w);
                int ancestor = pathStack.pop();
                int distance = distTo[ancestor];
                return distance + reverseDistance;
            } else return -1;
        }

        public Iterable<Integer> pathTo(int w) {
            if (!hasPathTo(w)) return null;
            else {
                Stack<Integer> pathStack = new Stack<>();
                int x;
                for (x = w; this.reverseDistTo[x] != 0; x = this.reversEdgeTo[x]) {
                    pathStack.push(x);
                }
                pathStack.push(x);
                return pathStack;
            }
        }
    }




    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAPv1 sap = new SAPv1(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}