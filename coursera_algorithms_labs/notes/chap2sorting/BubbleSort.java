/*
Given an array a[0..N-1]:
- Pass over the array from front to back (or back to front):
- For each pair of adjacent entries (a[i], a[i+1]), if they are out of order (e.g. a[i] > a[i+1] for ascending sort), swap them.
- After the first pass, the largest element is guaranteed to be at the end.

Repeat for the remaining N-1 elements (i.e., do one fewer comparison on each subsequent pass).

Stop early if a pass makes no swaps (already sorted).

*/
package chap2sorting;

import edu.princeton.cs.algs4.In;

public class BubbleSort extends SelectionSort {


    public static void sort(Comparable[] a){
        int N = a.length;

        for(int i=0;i<N; i++){
            for(int j=0;j<N-i-1;j++){
                if(less(a[j+1],a[j]))
                    exch(a,j, j+1);
            }
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
