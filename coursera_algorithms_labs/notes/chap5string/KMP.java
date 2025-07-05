/*
* 1. What is interpretation of DFA state after reading in txt[i]?
* State = number of characters in pattern that have been matched.
*
* dfa[][]: an array dfa[][] to record how far to back up the pattern pointer j when a mismatch is detected.
*
* Match transition.
* If in state j (first j characters of pattern have already been matched) and next char c (next char matches) == pat.charAt(j), go to j+1 (now first j +1 characters of pattern have been matched)
*
* Mismatch transition.
* If in state j and next char c != pat.charAt(j), then the last j-1 characters of input are pat[1..j-1], followed by c.
*
* To compute dfa[c][j]: Simulate pat[1..j-1] on DFA and take transition c.
* Running time. Seems to require j steps.
* Running time. Takes only constant time if we maintain state X.
* */
package chap5string;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class KMP {
    private String pat;

    private int[][] dfa;

    public KMP(String pat){
        this.pat= pat;

        int M= pat.length();
        int R=256;
        dfa = new int[R][M];

        // set the state 0
        dfa[pat.charAt(0)][0]=1;
        for(int X=0,j=1;j<M;j++){
            for(int c=0;c<R;c++){
                dfa[c][j]=dfa[c][X]; // copy the mismatch cases
            }
            dfa[pat.charAt(j)][j]=j+1; // set match cases for state j, if match ,go to state j+1
            X= dfa[pat.charAt(j)][X];  // update the restart state X
        }
    }

    public int search(String txt){
        int i, j, N =  txt.length(), M= pat.length();
        for(i=0,j=0;i<N && j<M; i++)
            j= dfa[txt.charAt(i)][j];
        if(j==M) return i-M; // found (hit end of pattern)
        else return N;      // not found (hit end of text)
    }

    public static void main(String[] args)
    {
        String pat =  "AACAA"; //args[0];
        String txt = "AABRAACADABRAACAADABRA";  // args[1];
        KMP kmp = new KMP(pat);
        StdOut.println("text:   " + txt);
        int offset = kmp.search(txt);
        StdOut.print(  "pattern:");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }


}
