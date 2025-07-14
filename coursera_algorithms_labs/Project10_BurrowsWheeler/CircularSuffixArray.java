/*
 *
 * Circular suffix array.
 * To efficiently implement the key component in the Burrows–Wheeler transform,
 * you will use a fundamental data structure known as the circular suffix array,
 * which describes the abstraction of a sorted array of the n circular suffixes of a string of length n.
 *
 * Note:
 * this code is from https://github.com/PKUFlyingPig/Princeton-Algorithm/blob/master/projects/burrows/CircularSuffixArray.java
 * */

import java.util.Arrays;

public class CircularSuffixArray {
    private final int N;
    private final int[] indexes; // record the sorted suffixes to index of the original circular suffix

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("null string");

        N = s.length();

        // Be sure not to create new String objects when you sort the suffixes. That would take quadratic space.
        // Beginning with Java 7, Update 6, the substring() method takes time and space proportional to the length of the substring&mdash.
        // So, explicitly forming the n circular suffixes in this way would take both quadratic time and space.
//        String[] suffixes = new String[N];
//        for (int i = 0; i < N; i++)
//            suffixes[i] = s.substring(i, N);

        //  A natural approach is to define a nested class CircularSuffix that represents a circular suffix implicitly
        //  (via a reference to the input string and a pointer to the first character in the circular suffix).
        CircularSuffix[] suffixes = new CircularSuffix[N];
        String text = s + s;
        for (int i = 0; i < N; i++) {
            suffixes[i] = new CircularSuffix(text, i); // build CircularSuffix start from index i
        }
        // sort the suffixes, then we get the index
        Arrays.sort(suffixes);
        indexes = new int[N];
        for (int i = 0; i < N; i++) {
            // for each sorted suffixes at i, we will mapping it to  original circular suffix index
            indexes[i] = suffixes[i].index;
        }
    }

    private static class CircularSuffix implements Comparable<CircularSuffix> {
        private final String text;
        private final int index; // the start index for string

        private CircularSuffix(String s, int idx) {
            text = s;
            index = idx;
        }

        private int length() {
            return text.length() / 2;
        }

        private char charAt(int i) {
            return text.charAt(index + i);// start from index for i
        }

        // Use lexicographic order to sort the suffixes
        public int compareTo(CircularSuffix that) {
            if (this == that) return 0;
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                else if (this.charAt(i) > that.charAt(i)) return +1;
            }
            // when compare all i, then compare the length, the short the smaller
            return this.length() - that.length();
        }

    }

    // length of s
    public int length() {
        return N;
    }


    /* returns index of ith sorted suffix
     * We define index[i] to be the index of the original suffix that appears ith in the sorted array.
     * For example, index[11] = 2 means that the 2nd original suffix appears 11th in the sorted order (i.e., last alphabetically).
     */
    public int index(int i) {
        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        System.out.println("length of s : " + CSA.length());
        for (int i = 0; i < CSA.length(); i++) {
            System.out.println(CSA.index(i));
        }
    }
}