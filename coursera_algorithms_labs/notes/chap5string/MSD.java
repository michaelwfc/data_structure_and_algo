/*
* ## Most-significant-digit-first string sort
MSD string (radix) sort.
- Partition array into R pieces according to first character (use key-indexed counting).
- Recursively sort all strings that start with each character (key-indexed counts delineate subarrays to sort).

### Cutoff to insertion sort
Solution. Cutoff to insertion sort for small subarrays.
- Insertion sort, but start at dth character.
- Implement less() so that it compares starting at dth character.

### MSD string sort: performance
Number of characters examined.
- MSD examines just enough characters to sort the keys.
- Number of characters examined depends on keys.
- Can be sublinear in input size!

MSD string sort vs. quicksort for strings
Disadvantages of MSD string sort.
- Extra space for aux[].
- Extra space for count[].
- Inner loop has a lot of instructions.
- Accesses memory "randomly" (cache inefficient).

Disadvantage of quicksort.
- Linearithmic number of string compares (not linear).
- Has to rescan many characters in keys with long prefix matches.

The main defect with the given MSD radix sort implementation is that it uses too much space to store all of the
count[] count[]start verbatim, count[], end verbatim arrays.
*
* */


package chap5string;

import chap2sorting.InsertionSort;
//import chap2sorting.SelectionSort;
import edu.princeton.cs.algs4.In;

public class MSD extends LSD {
    private static int R = 256; // radix
    private static final int M = 15; // cutoff for small subarrays

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        // Sort from a[lo] to a[hi], starting at the d_th character.
        if (hi <= lo + M){
            InsertionSort.sort(a, lo, hi, d);
            return;
        }
//        if(hi<=lo) return;
        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;  // Compute frequency counts
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];  // Transform counts to indices.
        for (int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d) + 1]++] = a[i]; // Distribute.
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];   // Copy back.

        // Recursively sort for each character value.
        for (int r = 0; r < R; r++)
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }



    public static void main(String[] args) {
        String filePath = "./algs4-data/tinyMSD.txt";
        String[] a = In.readStrings(filePath);
        sort(a);
//        assert isSorted(a);
        show(a);
    }


}
