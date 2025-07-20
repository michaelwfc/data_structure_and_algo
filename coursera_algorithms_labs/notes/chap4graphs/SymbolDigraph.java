package chap4graphs;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;


public class SymbolDigraph {
    private ST<String, Integer> st = new ST();
    private String[] keys;
    private Digraph graph; // use the customized Digraph class

    public SymbolDigraph(String filename, String delimiter) {
        In in = new In(filename);

        while(in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);

            for(int i = 0; i < a.length; ++i) {
                if (!this.st.contains(a[i])) {
                    this.st.put(a[i], this.st.size());
                }
            }
        }

        this.keys = new String[this.st.size()];

        for(String name : this.st.keys()) {
            this.keys[(Integer)this.st.get(name)] = name;
        }

        this.graph = new Digraph(this.st.size());
        in = new In(filename);

        while(in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = (Integer)this.st.get(a[0]);

            for(int i = 1; i < a.length; ++i) {
                int w = (Integer)this.st.get(a[i]);
                this.graph.addEdge(v, w);
            }
        }

    }

    public boolean contains(String s) {
        return this.st.contains(s);
    }

    /** @deprecated */
    @Deprecated
    public int index(String s) {
        return (Integer)this.st.get(s);
    }

    public int indexOf(String s) {
        return (Integer)this.st.get(s);
    }

    /** @deprecated */
    @Deprecated
    public String name(int v) {
        this.validateVertex(v);
        return this.keys[v];
    }

    public String nameOf(int v) {
        this.validateVertex(v);
        return this.keys[v];
    }

    /** @deprecated */
    @Deprecated
    public Digraph G() {
        return this.graph;
    }

    public Digraph digraph() {
        return this.graph;
    }

    private void validateVertex(int v) {
        int V = this.graph.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Digraph graph = sg.digraph();

        while(!StdIn.isEmpty()) {
            String t = StdIn.readLine();

            for(int v : graph.adj(sg.index(t))) {
                String var10000 = sg.nameOf(v);
                StdOut.println("   " + var10000);
            }
        }

    }
}
