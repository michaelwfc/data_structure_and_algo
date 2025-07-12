/*
* javac-algs4 chap5string/BinaryDump.java
*
* $ java-algs4 chap5string/BinaryDump < chap5string/abra.txt
* $ java-algs4 chap5string.BinaryDump < chap5string/abra.txt

 * */

package chap5string;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
//import edu.princeton.cs.algs4.Huffman;

/*
dump:
This question faced early programmers when the only way to find a bug was
to examine each of the bits in memory, and the term dump has been used since the early
days of computing to describe a human-readable view of a bitstream.


the program BinaryDump at left is a BinaryStdIn client that prints out the bits from standard input, encoded with the characters
0 and 1.


*
*
* You're entering two characters:
'1' (ASCII code 49 → binary: 00110001)
newline '\n' (ASCII code 10 → binary: 00001010)
* Only 15 bits printed due to EOF detection / buffering
* */
public class BinaryDump
{

    public static void main(String[] args){
//        convertToASCII();
        binaryDump(args);

    }

    public static void convertToASCII(){
        while(!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar(); // reads 8 bits
            int asciiInt = (int) c;
            System.out.println("ASCII of " + c + " is " + asciiInt);
        }
    }

    public static void test(){
       int month =12;
        int day = 31;
        int year =1999;
        // a character stream (StdOut)
        StdOut.print(month + "/" + day + "/" + year);

        BinaryStdOut.write((char) month);
        BinaryStdOut.write((char) day);
        BinaryStdOut.write((short) year);
    }


    public static void binaryDump(String[] args)
    {
         int width = Integer.parseInt(args[0]);
//        int width = 8;

        int cnt;
        for (cnt = 0; !BinaryStdIn.isEmpty(); cnt++)
        {

            if (width == 0) continue;
            if (cnt != 0 && cnt % width == 0)
                StdOut.println();
            if (BinaryStdIn.readBoolean())
                StdOut.print("1");
            else StdOut.print("0");
        }
        StdOut.println();
        StdOut.println(cnt + " bits");
    }
}
