package chap2sorting;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Selection;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.MergeBU;
import edu.princeton.cs.algs4.Quick;
//import edu.princeton.cs.algs4
import edu.princeton.cs.algs4.Heap;





/*
Shellsort

Shellsort is a simple extension of insertion sort that gains speed by allowing exchanges of array entries that are far apart,
to produce partially sorted arrays that can be efficiently sorted, eventually by insertion sort.

The idea is to rearrange the array to give it the property that taking every hth entry (starting anywhere) yields a sorted subsequence.
Such an array is said to be h-sorted.

 */


public class TestSorting {
    public static void main(String[] args) {
//        String file = args[0];
//        In inFile = new In(file);
//        String[] inArray = inFile.readAllStrings();
        Integer[] inArray = genRandInt(20,100);


//        StdOut.println("Input:");
//        for (int s : inArray) {
//            StdOut.println(s);
//        }

        StdOut.println("Sorted:");
        Shell.sort(inArray);
        Merge.sort(inArray );
        MergeBU.sort(inArray);
        for (int s : inArray) {
            StdOut.println(s);
        }
    }

    public static Integer[] genRandInt(int N, Integer max) {
        if (max == null) {
            max = 100;
        }
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniformInt(max);
        }
        return a;
    }

    public static void SortCompare(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T); // total for alg1
        double t2 = timeRandomInput(alg2, N, T); // total for alg2
        StdOut.printf("For %d random Doubles\n %s is", N, alg1);
        StdOut.printf(" %.1f times faster than %s\n", t2 / t1, alg2);
    }

    public static double timeRandomInput(String alg, int N, int T) { // Use alg to sort T random arrays of length N.
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) { // Perform one experiment (generate and sort an array).
            for (int i = 0; i < N; i++)
                a[i] = StdRandom.uniform();
            total += time(alg, a);
        }
        return total;
    }



    public static double time(String alg, Comparable[] a) {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Insertion")) Insertion.sort(a);
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Shell")) Shell.sort(a);
        if (alg.equals("Merge")) Merge.sort(a);
        if (alg.equals("Quick")) Quick.sort(a);
        if (alg.equals("Heap")) Heap.sort(a);
        return timer.elapsedTime();
    }
}
