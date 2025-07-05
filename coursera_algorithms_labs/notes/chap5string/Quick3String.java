/*
* Overview. Do 3-way partitioning on the dth character.
- Less overhead than R-way partitioning in MSD string sort.
- Does not re-examine characters equal to the partitioning char
(but does re-examine characters not equal to the partitioning char).
*
* */
package chap5string;
//import edu.princeton.cs.algs4.charAt;

import edu.princeton.cs.algs4.In;

import java.awt.*;

public class Quick3String extends MSD {

    public static void sort(String[] a) {
        // start from lo=0,hi= V-1, d=0
        sort(a, 0, a.length - 1, 0);

    }

    public static void sort(String[] a, int lo, int hi, int d) {
        if (hi <=lo) return;

//        i,j = partition(a, lo, hi,d);
//        sort(a, lo,i, d+1);
//        sort(a, i+1,j, d+1);
//        sort(a,j+1, hi, d+1);
        int lt = lo;
        int gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, i, lt++);
            else if (t > v) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1, d);
        if(v>=0) sort(a, lt, gt, d+1);
        sort(a, gt +1, hi, d);

    }

    public static void main(String[] args) {
        String filePath = "./algs4-data/tinyMSD.txt";
        String[] a = In.readStrings(filePath);
        sort(a);
//        assert isSorted(a);
        show(a);
    }

}
