/*
* Basic plan.
- Shuffle the array.
- Partition so that, for some j
    – entry a[j] is in place
    – no larger entry to the left of j
    – no smaller entry to the right of j
- Sort each piece recursively.
*
*Quicksort partitioning: 
* Repeat until i and j pointers cross.
- Scan i from left to right so long as (a[i] < a[lo]).
- Scan j from right to left so long as (a[j] > a[lo]).
- Exchange a[i] with a[j].
When pointers cross.
- Exchange a[lo] with a[j].
* 
* */

package chap2sorting;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;


public class Quicksort extends SelectionSort{
    public static final int CUTOFF=7;

    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo ,int hi){
        if(hi<= lo+CUTOFF){
            InsertionSort.sort(a,lo, hi);
            return;
        }

        // shuffle the array
        if(hi<=lo)return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a,j+1,hi);
    }
    public static int partition(Comparable[] a, int lo, int hi){
        Comparable v = a[lo];
        int i = lo;
        int j = hi+1;
        while(true){
            while(less(a[++i],v)) if(i==hi) break;
            while(less(v,a[--j])) if(j==lo) break;
            if(i>=j) break;
            exch(a,i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String filename = "./algs4-data/tiny.txt";
        String[] a = In.readStrings(filename);
        sort(a);
        assert isSorted(a);
        show(a);
    }

}
