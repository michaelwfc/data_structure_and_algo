/* ALGORITHM 2.1 Selection sort

Algorithm. ↑ scans from left to right.
Invariants.
-   Entries the left of ↑ (including ↑) fixed and in ascending order.
-   No entry to right of ↑ is smaller than any entry to the left of ↑.

To maintain algorithm invariants:
- Move the pointer to the right.
- Identify index of minimum entry on right
- Exchange into position.
*/
package chap2;

//public class Selection {
//    public static void sort(Comparable[] a) { // Sort a[] into increasing order.
//        int N = a.length; // array length
//
//        for (int i = 0; i < N; i++) { // Exchange a[i] with smallest entry in a[i+1...N).
//            int min = i; // index of minimal entr.
////
//            for (int j = i + 1; j < N; j++)
//                if (less(a[j], a[min]))
//                    min = j;
//            exch(a, i, min);
//        }
//    }
//
//    private static boolean less(Comparable v, Comparable w) { /* as before */ }
//
//    private static void exch(Comparable[] a, int i, int j) { /* as before */ }
//}