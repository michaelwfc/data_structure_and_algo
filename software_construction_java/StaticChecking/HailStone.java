package StaticChecking;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class HailStone {
    /**
     * 1. 输入一个正整数n
     * 2. 输出n的HailStone序列
     * 3. 输出序列的末尾数字1
     */
    public static void printSequence(int n) {

        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            System.out.print(n + " ");
        }
    }

    /**
     * Compute a hailstone sequence.
     * @param n  Starting number for sequence.  Assumes n > 0.
     * @return hailstone sequence starting with n and ending with 1.
     */
    public static List<Integer> hailstoneSequence(int n) {
        final List<Integer> list = new ArrayList<Integer>();
        while (n != 1) {
            list.add(n);
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }
        list.add(n);
        return list;
    }

    public static void main(String[] args) {
        //        int n = Integer.parseInt(args[0]);
        StdOut.println("please input n:");
        int n = Integer.parseInt(StdIn.readString());
        printSequence(n);
    }
}
