package chap5string;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZW {
    private static final int R = 256;
    private static final int L= 4096;
    private static final int W =12;
    
    
    /*
    - Create ST associating W-bit codewords with string keys.
    - Initialize ST with codewords for single-char keys.
    - Find longest string s in ST that is a prefix of unscanned part of input.
    - Write the W-bit codeword associated with s.
    - Add s + c to ST, where c is next char in the input.
    * */
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();
        //codewords for singlechar, radix R keys
        for (int i = 0; i < R; i++)
            st.put(""+(char)i,i);

        int code =R+1;

        while(input.length()>0)
        {
            //find longest prefix match s
            String s= st.longestPrefixOf(input);
            // write W-bit codeword for s
            BinaryStdOut.write(st.get(s), W);
            int t= s.length();
            if(t<input.length() && code <L)
                // add s to symbol table
                st.put(input.substring(0,t+1), code++);
            input = input.substring(t);
        }
        BinaryStdOut.write(R,W);
        BinaryStdOut.close();
    }

    /*
    - Create ST associating string values with W-bit keys.
    - Initialize ST to contain single-char values.
    - Read a W-bit key.
    - Find associated string value in ST and write it out.
    - Update ST.
    * */
//    public static String expansion(){
//
//    }

}
