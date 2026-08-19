/*
* Basic plan for in-place sort.
􀉾Create max-heap with all N keys.
􀉾Repeatedly remove the maximum key.
*
*
* */

package chap2sorting;

public class HeapSort {
    public static void sort(Comparable[] a) {
        int N = a.length;

        // 创建一个新的数组，索引从 1 到 N
//        Comparable[] aux = new Comparable[N + 1];
//        System.arraycopy(a, 0, aux, 1, N); // 忽略 aux[0]

        //Heap construction.
        // Build max heap using bottom-up method.
        // (we assume array entries are indexed 1 to N)
        for (int k = N / 2; k >= 1; k--)
            sink(a, k, N);

        // Sortdown
        // Remove the maximum, one at a time
        // Leave in array, instead of nulling out.
        while (N > 1) {
            exch(a, 1, N);
            sink(a, 1, --N);
        }
//        System.arraycopy(aux, 1, a, 0, a.length);
    }

    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a,j, j+1)) j++;
            if(!less(a,k,j)) break;
            exch(a, k,j);
            k = j;


        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        // but convert from 1-based indexing to 0-base indexing
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        // but convert from 1-based indexing to 0-base indexing
        Comparable temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

    public static void main(String[] args) {
        Integer[] a = {3, 2, 1, 4, 5};
        sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}