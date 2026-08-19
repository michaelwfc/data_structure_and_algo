/*
* Move-to-front encoding and decoding.
* The main idea of move-to-front encoding is to maintain an ordered sequence of the characters in the alphabet by repeatedly reading a character from the input message;
* printing the position in the sequence in which that character appears; and moving that character to the front of the sequence.
*
* As a simple example, if the initial ordering over a 6-character alphabet is A B C D E F, and we want to encode the input CAAABCCCACCF, then we would update the move-to-front sequence as follows:
* If equal characters occur near one another other many times in the input, then many of the output values will be small integers (such as 0, 1, and 2).
* The resulting high frequency of certain characters (0s, 1s, and 2s) provides exactly the kind of input for which Huffman coding achieves favorable compression ratios.
move-to-front    in   out
-------------    ---  ---
 A B C D E F      C    2
 C A B D E F      A    1
 A C B D E F      A    0
 A C B D E F      A    0
 A C B D E F      B    2
 B A C D E F      C    2
 C B A D E F      C    0
 C B A D E F      C    0
 C B A D E F      A    2
 A C B D E F      C    1
 C A B D E F      C    0
 C A B D E F      F    5
 F C A B D E


*
* Why It’s Useful:
* After BWT, repeated characters often appear together (like aaaa...).
* MTF then converts these to many zeros, which are easy to compress using simple techniques like run-length encoding or Huffman coding.
* */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.LinkedList;

public class MoveToFront {
    private final static int R=256;
    private static LinkedList<Character> alphabet;

    private static void initialAlphabet(){
        // initial the index table is mapping from ASCII character to index
//        char[] indexTable = new char[R];
//        for(int i=0;i<R;i++){
//            indexTable[i]=(char) i;
//        }
        alphabet = new LinkedList<>();
        for(char i=0;i<R;i++){
            alphabet.add(i);
        }

    }
    /* Move-to-front encoding: apply move-to-front encoding, reading from standard input and writing to standard output
     *  Your task is to maintain an ordered sequence of the 256 extended ASCII characters.
     *  Initialize the sequence by making the ith character in the sequence equal to the ith extended ASCII character.
     *  Now, read each 8-bit character c from standard input, one at a time; output the 8-bit index in the sequence where c appears; and move c to the front.
     */
    public static void encode(){
        initialAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(); //read each 8-bit character c from standard input
            // int index = indexTable[c];       // get the index
            int index = alphabet.indexOf(c);
            alphabet.remove(index);
            BinaryStdOut.write(index, 8); //output the 8-bit index in the sequence where c appears;
            // move the c to the front
            alphabet.add(0, c);
        }
        BinaryStdOut.close();
    }

    /* Move-to-front decoding： apply move-to-front decoding, reading from standard input and writing to standard output
     * Initialize an ordered sequence of 256 characters, where extended ASCII character i appears ith in the sequence.
     * Now, read each 8-bit character i (but treat it as an integer between 0 and 255) from standard input one at a time;
     * write the ith character in the sequence; and move that character to the front.
     * Check that the decoder recovers any encoded message.
     */
    public static void decode(){
        initialAlphabet();
        while(!BinaryStdIn.isEmpty()){
            char index = BinaryStdIn.readChar();
            char c = alphabet.get(index);
            BinaryStdOut.write(c,8);
            alphabet.remove(index);
            alphabet.add(0, c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    /*
    $ javac-algs4 MoveToFront.java
    $ java-algs4 MoveToFront -  < burrows/abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16
    $ java-algs4 MoveToFront - < burrows/abra.txt | java-algs4 MoveToFront +
     * */
    public static void main(String[] args){
        if (args[0].equals("-")) {
            encode();
        } else {
            if (!args[0].equals("+")) {
                throw new IllegalArgumentException("Illegal command line argument");
            }
            decode();
        }
    }

}