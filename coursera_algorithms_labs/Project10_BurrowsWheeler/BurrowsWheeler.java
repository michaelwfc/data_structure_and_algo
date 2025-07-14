import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Arrays;

/*
* https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php
*
   The Burrows–Wheeler data compression algorithm consists of three algorithmic components, which are applied in succession:
1. Burrows–Wheeler transform.
   Given a typical English text file, transform it into a text file in which sequences of the same character occur near each other many times.
   * Intuition:
   if you see the letters hen in English text, then, most of the time, the letter preceding it is either t or w.
   If you could somehow group all such preceding letters together (mostly t’s and some w’s),
   then you would have a propitious opportunity for data compression.
   * Goal:
   After BWT, repeated characters often appear together (like aaaa...).

2. Move-to-front encoding.
   Given a text file in which sequences of the same character occur near each other many times, convert it into a text file in which certain characters appear much more frequently than others.
   Goal:
   MTF then converts these to many zeros, which are easy to compress using simple techniques like run-length encoding or Huffman coding.

3. Huffman compression.
   Given a text file in which certain characters appear much more frequently than others, compress it by encoding frequently occurring characters with short codewords and infrequently occurring characters with long codewords.
   Step 3 is the only one that compresses the message: it is particularly effective because Steps 1 and 2 produce a text file in which certain characters appear much more frequently than others.
*
* To expand a message, apply the inverse operations in reverse order:
* first apply the Huffman expansion,
* then the move-to-front decoding,
* and finally the inverse Burrows–Wheeler transform.
* Your task is to implement the Burrows–Wheeler and move-to-front components.
*
*
* Burrows–Wheeler transform.
* The Burrows–Wheeler transform of a string s of length n is defined as follows:
* Consider the result of sorting the n circular suffixes of s.
* The Burrows–Wheeler transform is the last column in the sorted suffixes array t[], preceded by the row number first in which the original string ends up.
*
* Continuing with the "ABRACADABRA!" example above, we highlight the two components of the Burrows–Wheeler transform in the table below.
* Since the original string ABRACADABRA! ends up in row 3, we have first = 3. Thus, the Burrows–Wheeler transform is
*
* Burrows–Wheeler inverse transform.
* next[] array:
* If the jth original suffix (original string, shifted j characters to the left) is the ith row in the sorted order,
* we define next[i] to be the row in the sorted order where the (j + 1)st original suffix appears.
*
* For example, if first is the row in which the original input string appears,
* then next[first] is the row in the sorted order where the 1st original suffix (the original string left-shifted by 1) appears;
* next[next[first]] is the row in the sorted order where the 2nd original suffix appears;
* next[next[next[first]]] is the row where the 3rd original suffix appears; and so forth.
*
*
* Note:
* code is from https://github.com/PKUFlyingPig/Princeton-Algorithm/blob/master/projects/burrows/BurrowsWheeler.java
* */
public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        ArrayList<Character> t = new ArrayList<>();
        // build CircularSuffixArray
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        int N = s.length();
        for (int i = 0; i < N; i++) {
            // get the original suffix index at sorted suffixes i
            int index = CSA.index(i);
            // t[i] is the last char of circular suffix start at index, t_position - index =N-1
            // so t[i] is at t_position = index+N-1
            int t_position = (index + N - 1) % N;
            char t_i = s.charAt(t_position);
            t.add(t_i);
            // for the first char, print the row in the sorted suffixes as first
            if (index == 0) BinaryStdOut.write(i);
        }
        for (char x : t) {
            BinaryStdOut.write(x);
        }
        BinaryStdOut.close();
    }


    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        // read first and t
        int first = BinaryStdIn.readInt();
        ArrayList<Character> t = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            t.add(BinaryStdIn.readChar());
        }
        //build first column
        char[] firstColumn = new char[t.size()];
        for (int i = 0; i < t.size(); i++) {
            firstColumn[i] = t.get(i);
        }
        Arrays.sort(firstColumn);

        // TODO:  Do not quite understand this logic of building next array
        // Constructing the next[] array from t[] and first.
        // Amazingly, the information contained in the Burrows–Wheeler transform suffices to reconstruct the next[] array, and, hence, the original message!
        // Here’s how. It is easy to deduce a next[] value for a character that appears exactly once in the input string.
        //
        // For example, consider the suffix that starts with 'C'. By inspecting the first column, it appears 8th in the sorted order.
        // The next original suffix after this one will have the character 'C' as its last character.
        // By inspecting the last column, the next original appears 5th in the sorted order. Thus, next[8] = 5.
        // Similarly, 'D' and '!' each occur only once, so we can deduce that next[9] = 2 and next[0] = 3.
        int[] next = new int[t.size()];

        for (int i = 0; i < t.size(); ) {
            int j = i + 1;
            while (j < t.size() && firstColumn[j] == firstColumn[j - 1]) j++;
            for (int k = 0; k < t.size(); k++) {
                if (t.get(k) == firstColumn[i]) {
                    next[i++] = k;
                    if (i == j) break;
                }
            }
        }
        int pointer = first;
        for (int i = 0; i < next.length; i++) {
            BinaryStdOut.write(firstColumn[pointer]);
            pointer = next[pointer];
        }
        BinaryStdOut.close();


    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}