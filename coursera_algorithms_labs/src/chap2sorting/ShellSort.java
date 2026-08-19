/*
* Idea. Move entries more than one position at a time by h-sorting the array
* an h-sorted array is h interleaved sorted subsequences
* h-sorting:  How to h-sort an array? Insertion sort, with stride length h.
*
* Why insertion sort?
􀉾Big increments ⇒ small subarray.
􀉾Small increments ⇒ nearly in order
*
* Proposition. A g-sorted array remains g-sorted after h-sorting it.
* */


package chap2sorting;

import edu.princeton.cs.algs4.In;

public class ShellSort extends SelectionSort {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, ...
        while (h >= 1) { // h-sort the array.
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            }
            h = h / 3;
        }
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String filename = "./algs4-data/tiny.txt";
        String[] a = In.readStrings(filename);
        sort(a);
        assert isSorted(a);
        show(a);
    }
}