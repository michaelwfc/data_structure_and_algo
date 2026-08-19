
/*
* Weighted quick-union.
􀉾Modify quick-union to avoid tall trees.
􀉾Keep track of size of each tree (number of objects).
􀉾Balance by linking root of smaller tree to root of larger tree.
*
*
* Data structure. Same as quick-union, but maintain extra array sz[i]
to count number of objects in the tree rooted at i.
*
*
Find. Identical to quick-union.
*
*
* Union. Modify quick-union to:
􀉾Link root of smaller tree to root of larger tree.
􀉾Update the sz[] array.
* */

package chap1;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF {
}