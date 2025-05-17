/* https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 *
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int n;
    private final boolean[] grid;
    private int number;
    //    which sites are connected to which other sites. The last of these is exactly what the union–find data structure is designed for.
    private WeightedQuickUnionUF uf;

    private int xyTo1D(int row, int col) {
        // Plan how you're going to map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index.
        return (row - 1) * n + col;
    }

    private void validateIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException("Row or column is illegal: row=" + row + ", col=" + col);
    }

    /*
     * 1. creates n-by-n grid, with all sites initially blocked
     * 2. connects the top row to the virtual top site (index =0)
     * 3. connects the bottom row to the virtual bottom site (index =n*n+1)
     * */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        grid = new boolean[n * n + 2];
        number = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        // initial the top row and the bottom row
        for (int i = 1; i <= n; i++) {
            int top_index = xyTo1D(1, i);
            uf.union(0, top_index);
            int bottom_index = xyTo1D(n, i);
            uf.union(n * n + 1, bottom_index);
        }
    }

    /* opens the site (row, col) if it is not open already
     * The open() method should do three things.
     * 1. it should validate the indices of the site that it receives.
     * 2. it should somehow mark the site as open.
     * 3. it should perform some sequence of WeightedQuickUnionUF operations that links the site in question to its open neighbors.
     */
    public void open(int row, int col) {
        validateIndex(row, col);
        int index = xyTo1D(row, col);
        if (!grid[index]) {
            grid[index] = true;
            number++;
        }

        if ((row-1>0) && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), index);
        }
        if ((row+1<n+1) && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), index);
        }
        if ((col-1>0) && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col - 1), index);
        }
        if ((col+1<n+1) && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), index);
        }
    }


    /* is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        // when the row,col is not validated, return false
        validateIndex(row, col);
        int index = xyTo1D(row, col);
        return grid[index];
    }


    /*  is the site (row, col) full?
     *  A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
     */
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        if (grid[index]) {
            return uf.connected(0, index);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return number;
    }

    /* does the system percolate?
     *  We say the system percolates if there is a full site in the bottom row. In other words,
     *  a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row.
     */
    public boolean percolates() {
        if (number==0){
            return false;
        }
        return uf.connected(0, n * n + 1);
    }

    private int findRoot(int row, int col) {
        int index = xyTo1D(row, col);
        return uf.find(index);
    }

    // test client (optional)
    /* Test the open() method and the Percolation() constructor. These tests should be in main().
     * An example of a simple test is to call open(1, 1) and open(1, 2), and then to ensure that the two corresponding entries
     * are connected (using two calls to find() in WeightedQuickUnionUF)
     */
    public static void main(String[] args) {
        // Test the open() method
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        int root1 = p.findRoot(1, 1);
        int root2 = p.findRoot(2, 1);
        System.out.println("root1=" + root1 + ";root2=" + root2);
    }
}