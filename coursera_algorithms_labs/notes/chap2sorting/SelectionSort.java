/* ALGORITHM 2.1 SelectionSort sort

Algorithm. ↑ scans from left to right.
Invariants.
-   Entries the left of ↑ (including ↑) fixed and in ascending order.
-   No entry to right of ↑ is smaller than any entry to the left of ↑.

To maintain algorithm invariants:
- Move the pointer to the right.
- Identify index of minimum entry on right
- Exchange into position.
*/
package chap2sorting;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class SelectionSort {
    public static void sort(Comparable[] a) { // Sort a[] into increasing order.
        int N = a.length; // array length

        for (int i = 0; i < N; i++) { // Exchange a[i] with smallest entry in a[i+1...N).
            int min = i; // index of minimal entry.
//
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
        }
    }

    protected  static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    protected  static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    public static boolean isSorted(Comparable[] a,int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    protected  static void show(Comparable[] a) { // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }


    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String filename = "./algs4-data/tiny.txt";
        String[] a = In.readStrings(filename);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}