/*
 * */


package chap5string;

import chap2sorting.InsertionSort;
import chap2sorting.SelectionSort;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class LSD extends InsertionSort {
    //fixed-length W strings
    public static void sort(String[] a, int W) {
        int R = 256; //radix R
        int N = a.length;
        String[] aux = new String[N];

        // do key-indexed counting
        //for each digit from right to left
        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }

    }

    protected  static void show(String[] a) { // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i] );
        StdOut.println();
    }

    // Variable-length strings
    // Treat strings as if they had an extra char at end (smaller than any char).
    public static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    public static void main(String[] args) {
        String filePath = "./algs4-data/tinyLSD.txt";
        String[] a = In.readStrings(filePath);
        int W =  a[0].length();
        sort(a,W);
//        assert isSorted(a);
        show(a);
    }


}
