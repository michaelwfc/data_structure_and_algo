/*
* Data structure.
􀉾Integer array id[] of length N.
􀉾Interpretation: id[i] is parent of i.
􀉾Root of i is id[id[id[...id[i]...]]].
*
*
* Find. Check if p and q have the same root.
Union. To merge components containing p and q, set the id of p's root to the id of q's root.
*
*
* Quick-find defect.
􀉾Union too expensive (N array accesses).
􀉾Trees are flat, but too expensive to keep them flat.

* Quick-union defect.
􀉾Trees can get tall.
􀉾Find too expensive (could be N array accesses).
*
* */

package chap1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.QuickUnionUF

public class QuickUnionUF
{
    private int[] id;
    public QuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
    }
    private int root(int i)
    {
        while (i != id[i]) i = id[i];
        return i;
    }
    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }
    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}