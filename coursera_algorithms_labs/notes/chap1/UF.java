/*
* We assume "is connected to" is an equivalence relation:
􀉾Reflexive: p is connected to p.
􀉾Symmetric: if p is connected to q, then q is connected to p.
􀉾Transitive: if p is connected to q and q is connected to r,
then p is connected to r.
*
* Connected components. Maximal set of objects that are mutually connected.
7
* */

package chap1;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.UF;


public class UF {
    UF(int N) //    initialize union-find data structure with  N objects (0 to N – 1)

    void union(int p, int q)  //add connection between p and q

    boolean connected(int p, int q) // are p and q in the same component?

    int find(int p) // component identifier for p (0 to N – 1)

    int count() // number of components
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        UF uf = new UF(N);
        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }

}

