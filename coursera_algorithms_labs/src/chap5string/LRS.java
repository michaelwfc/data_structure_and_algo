/*
* Longest repeated substring
* Given a string of N characters, find the longest repeated substring.
* */

package chap5string;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
public class LRS {


    public static String lrs(String s){
        int N =  s.length();
        // create suffixes (linear time and space)
        String[] suffixes = new String[N];
        for(int i=0;i<N;i++){
            suffixes[i]= s.substring(i,N);
        }

        Arrays.sort(suffixes); // sort suffixes

        // find LCP between adjacent suffixes in sorted order
        String lrs ="";
        for(int i=0;i<N-1;i++){
            int len= lcp(suffixes[i],suffixes[i+1]);
            if(len>lrs.length())
                lrs = suffixes[i].substring(0,len);
        }
        return lrs;
    }
    public static int lcp(String s, String t){
        int N = Math.min(s.length(), t.length());
        for(int i=0;i<N;i++){
            if(s.charAt(i)!= t.charAt(i)) return i;
        }
        return N;
    }

    public static void main(String[] args) {
        String filePath = "./algs4-data/tinyLRS.txt";
//        String filePath = "./algs4-data/mobydick.txt";

        String[] a = In.readStrings(filePath);

        String s = lrs(a[0]);
        StdOut.println(s);
    }
}
