package chap2sorting;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {
    /**
     * 3-SUM. Given N distinct integers, how many triples sum to exactly zero?
     */
    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                    }
                }
            }
        }
        return  cnt;
    }
    public static void main(String[] args) {
//        In in = new In(args[0]);      // input file
//        int n = in.readInt();         // n numbers to process

        String filename = args[0];
        In in = new In(filename);  // 创建 In 实例
        int[] a = in.readAllInts();

        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println("\n");
        Stopwatch stopwatch = new Stopwatch();
        StdOut.println(count(a));
        StdOut.println("elapsed time = " + stopwatch.elapsedTime());

    }
}
