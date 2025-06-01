/*ALGORITHM 2.2 Insertion sort

Algorithm. ↑ scans from left to right.
Invariants.
- Entries to the left of ↑ (including ↑) are in ascending order.
- Entries to the right of ↑ have not yet been seen.

 */
package chap2;

//public class InsertionSort {
//    public static void sort(Comparable[] a) {
//        int N = a.length;
//        for (int i = 0; i < N; i++)
//            for (int j = i; j > 0; j--)
//                if (less(a[j], a[j - 1]))
//                    exch(a, j, j - 1);
//                else break;
//    }
//
//    private static boolean less(Comparable v, Comparable w) { /* as before */ }
//
//    private static void exch(Comparable[] a, int i, int j) { /* as before */ }
//}