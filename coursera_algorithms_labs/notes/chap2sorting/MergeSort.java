/*
* Basic plan.
- Divide array into two halves.
- Recursively sort each half.
- Merge two halves.
*
* Goal. Given two sorted subarrays a[lo] to a[mid] and a[mid+1] to a[hi],
replace with sorted subarray a[lo] to a[hi].
*
* Proposition. Mergesort uses at most N lg N compares and 6 N lg N array
accesses to sort any array of size N.
*
* */

package chap2sorting;

import edu.princeton.cs.algs4.In;




public class MergeSort extends SelectionSort {
    //public static: makes the variable accessible without needing to instantiate the class.
    //final: make it constant (optional):
    public static final int CUTOFF = 7;

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // Use insertion sort for small subarrays.
        if(hi<= lo+ CUTOFF-1){
            InsertionSort.sort(a, lo, hi);
        }
        if (hi <= lo) return;
        int mid = lo+ (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);

        //Stop if already sorted.
        if(less(a[mid],a[mid+1])) return ;

        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
        assert isSorted(a, lo, hi);
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String filename = "./algs4-data/tiny.txt";
        String[] a = In.readStrings(filename);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
