/*
* Intuition.
􀉾Scan characters in pattern from right to left.
􀉾Can skip as many as M text chars when finding one not in the pattern.
*
* Case 1. Mismatch character not in pattern.
* mismatch character 'T' not in pattern: increment i one character beyond 'T'
*
* Case 2a. Mismatch character in pattern.
* mismatch character 'N' in pattern: align text 'N' with rightmost pattern 'N'
*
* Case 2b. Mismatch character in pattern (but heuristic no help).
* mismatch character 'E' in pattern: align text 'E' with rightmost pattern 'E' ?
* */
package chap5string;

import edu.princeton.cs.algs4.StdOut;

public class BoyerMoore {
    private String pat;
    private int[] right;

    public BoyerMoore(String pat) {
        this.pat = pat;
        int R = 256;
        int M = pat.length();
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < M; j++)
            right[pat.charAt(j)] = j;
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            // scan from right-most position
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    // when text mismatch with patten on right-most position,  update the skip
                    skip = Math.max(1, j- right[txt.charAt(i+j)]); //in case other term is nonpositive, increment by 1
                    break;
                }
            }
            if(skip==0) return i;
        }
        return N;
    }

    public static void main(String[] args)
    {
        String pat =  "NEEDLE"; //args[0];
//        String txt = "FINDINAHAYSTACKNEEDLEINA";  // args[1];
        String txt = "FINDINAHELEAHAYSTACKNEEDLEINA";
        BoyerMoore bm = new BoyerMoore(pat);
        StdOut.println("text:   " + txt);
        int offset = bm.search(txt);
        StdOut.print(  "pattern:");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
