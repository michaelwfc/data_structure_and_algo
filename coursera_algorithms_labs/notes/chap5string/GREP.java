/*
* Grep. Take a RE as a command-line argument and print the lines
from standard input having some substring that is matched by the RE.
* */

package chap5string;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class GREP {
    public static void main(String[] args){
        String re= "(.*" +args[0] + ".*";
        NFA nfa = new NFA(re);
        while(StdIn.hasNextLine()){
            String line =  StdIn.readLine();
            if(nfa.recognize(line))
                StdOut.println(line);
        }
    }
}
