// If you use IntelliJ and the provided project folder, IntelliJ will automatically add and remove import statements as needed,
// so you won’t need to type them.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
Write a program RandomWord.java that reads a sequence of words from standard input and prints one of those words uniformly at random.
Do not store the words in an array or list. Instead, use Knuth’s method: when reading the ith word, select it with probability 1/i
 to be the champion, replacing the previous champion. After reading all of the words, print the surviving champion.

 Use the following library functions from algs4.jar:
StdIn.readString(): reads and returns the next string from standard input.
StdIn.isEmpty(): returns true if there are no more strings available on standard input, and false otherwise.
StdOut.println(): prints a string and terminating newline to standard output. It’s also fine to use System.out.println() instead.
StdRandom.bernoulli(p): returns true with probability p and false with probability 1−p
 */
public class RandomWord {
    public static void main(String[] args) {
        // // 在 Java 中使用 Scanner 从标准输入读取单词（如本程序中），默认情况下会持续等待输入，直到遇到 EOF（End Of File） 标志为止。
        // Scanner scanner = new Scanner(System.in);
        // Random random = new Random();
        // String champion = null;
        // int count = 0;
        // while (scanner.hasNext()) {
        //     String word = scanner.next();
        //     count++;
        //
        //     // Replace the current champion with probability 1 / count
        //     if (random.nextInt(count) == 0) {
        //         champion = word;
        //     }
        // }
        // if (champion != null) {
        //     System.out.println(champion);
        // }
        // else {
        //     System.out.println("No words entered.");
        // }

        String champion = null;
        int count = 0;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = word;
            }
        }
        if (champion != null) {
            StdOut.println(champion);
        }
        else {
            StdOut.println("No words entered.");
        }

    }
}
