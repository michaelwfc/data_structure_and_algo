/*ALGORITHM 2.2 Insertion sort

Algorithm. ↑ scans from left to right.
Invariants.
- Entries to the left of ↑ (including ↑) are in ascending order.
- Entries to the right of ↑ have not yet been seen.

Proposition. To sort a randomly-ordered array with distinct keys,
insertion sort uses ~ ¼ N^2 compares and ~ ¼ N^2 exchanges on average.
Pf. Expect each entry to move halfway back.

Best case.
If the array is in ascending order, insertion sort makes N - 1 compares and 0 exchanges.

Worst case.
If the array is in descending order (and no duplicates),
insertion sort makes ~ ½ N 2 compares and ~ ½ N 2 exchanges.

 */
package chap2sorting;

import edu.princeton.cs.algs4.In;

public class InsertionSort extends SelectionSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j - 1]))
                    exch(a, j, j - 1);
                else break;
    }

    public static void sort(Comparable[] a, int lo, int hi) {

        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo; j--)
                if (less(a[j], a[j - 1]))
                    exch(a, j, j - 1);

    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String filename = "./algs4-data/tiny.txt";
        String[] a = In.readStrings(filename);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}