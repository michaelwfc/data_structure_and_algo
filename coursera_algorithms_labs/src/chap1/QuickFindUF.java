/*
* Quick-find [eager approach]
*
* Data structure.
􀉾Integer array id[] of length N.
􀉾Interpretation: p and q are connected iff they have the same id.
*
* Find. Check if p and q have the same id.
* Union. To merge components containing p and q, change all entries whose id equals id[p] to id[q].
*
* */
package chap1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.QuickFindUF;

public class QuickFindUF {
    private int[] id;

    //set id of each object to itself
    //(N array accesses)
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    //    check whether p and q are in the same component
    //(2 array accesses)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    //    change all entries with id[p] to id[q]
    // (at most 2N + 2 array accesses)
    // Union is too expensive. It takes N 2 array accesses to process a sequence of N union commands on N objects.
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }
}