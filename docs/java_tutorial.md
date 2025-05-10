# Tutorials
[liaoxuefeng.com](https://liaoxuefeng.com/books/java/introduction/index.html)
[](https://www.runoob.com/java/java-tutorial.html)
[tutorialspoint.com](https://www.tutorialspoint.com/java/index.htm)


# Courses
[CS106A - Programming Methodology](https://see.stanford.edu/course/cs106a)
[Introduction to Programming in Java](https://ocw.mit.edu/courses/6-092-introduction-to-programming-in-java-january-iap-2010/)

## Exmaple: BinarySearch.java
```Java
package edu.princeton.cs.algs4;

import java.util.Arrays;

/**
 *  The {@code BinarySearch} class provides a static method for binary
 *  searching for an integer in a sorted array of integers.
 *  <p>
 *  The <em>indexOf</em> operations takes logarithmic time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BinarySearch {

    /**
     * This class should not be instantiated.
     */
    private BinarySearch() { }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    /**
     * Returns the index of the specified key in the specified array.
     * This function is poorly named because it does not give the <em>rank</em>
     * if the array has duplicate keys or if the key is not in the array.
     *
     * @param  key the search key
     * @param  a the array of integers, must be sorted in ascending order
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     * @deprecated Replaced by {@link #indexOf(int[], int)}.
     */
    @Deprecated
    public static int rank(int key, int[] a) {
        return indexOf(a, key);
    }

    /**
     * Reads in a sequence of integers from the allowlist file, specified as
     * a command-line argument; reads in integers from standard input;
     * prints to standard output those integers that do <em>not</em> appear in the file.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // read the integers from a file
        In in = new In(args[0]);
        int[] allowlist = in.readAllInts();

        // sort the array
        Arrays.sort(allowlist);

        // read integer key from standard input; print if not in allowlist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (BinarySearch.indexOf(allowlist, key) == -1)
                StdOut.println(key);
        }
    }
}

```

## Basic structure of a Java program 
A Java program ( class) is either a library of
static methods (functions) or a data type definition. To create libraries of static methods and data-type definitions, we use the following five components, the basis of programming in Java and many other modern languages:
- **Primitive data types** precisely define the meaning of terms like integer, real number, and boolean value within a computer program. Their definition includes the set of possible values and operations on those values, which can be combined into expressions like mathematical expressions that define values.
- **Statements** allow us to define a computation by creating and assigning values to variables, controlling execution flow, or causing side effects. We use six types of statements: declarations, assignments, conditionals, loops, calls, and returns.
-  **Arrays** allow us to work with multiple values of the same type.
-  **Static methods** allow us to encapsulate and reuse code and to develop programs
as a set of independent modules.
-  **Strings** are sequences of characters. Some operations on them are built in to Java.
-  **Input/output** sets up communication between programs and the outside world.
- **Data abstraction** extends encapsulation and reuse to allow us to define nonprimitive data types, thus supporting object-oriented programming.

# Data types 
## Primitive data types and expressions 
A data type is a set of values and a set of operations on those values. We begin by considering the following four primitive data
types that are the basis of the Java language:
■ Integers, with arithmetic operations (int)
■ Real numbers, again with arithmetic operations (double)
■ Booleans, the set of values { true, false } with logical operations (boolean)
■ Characters, the alphanumeric characters and symbols that you type (char)

## Arrays


## Strings




# External libraries.

- The standard system libraries java.lang.*.
  - Math
  - Integer
  - Double
  - String
  - StringBuilder
  - System

- Imported system libraries such as java.util.Arrays
  
- The standard libraries Std* that we have developed for use in this book 
  (and our introductory book An Introduction to Programming in Java: An Interdisciplinary Approach). 
  -  StdIn
  - StdOut
  - StdDraw
  - StdRandom
  - StdStats
  - In
  - Out
  

# Modular programming

## Static methods



## APIs (pplication programming interfaces)
A critical component of modular programming is **documentation** that explains the operation of library methods that are intended for use by others. We will consistently describe the library methods that we use in this book in **application programming interfaces(APIs)** that list the library name and the signatures and short descriptions of each of the methods that we use. 
- client
We use the term client to refer to a program that calls a method in another library 
- implementation
 the term implementation to describe the Java code that implements the methods in an API.


### Example: API for Java’s mathematics library (excerpts)
The following example, the API for commonly used static methods from the standard Math library in java.lang, illustrates our conventions for APIs:

```Java
public class Math

static double abs(double a)           //absolute value of a
static double max(double a, double b) //maximum of a and b
static double min(double a, double b) //minimum of a and b
// Note 1: abs(), max(), and min() are defined also for int, long, and float.

static double sin(double theta)         //sine function
static double cos(double theta)         //cosine function
static double tan(double theta)         // tangent function
// Note 2: Angles are expressed in radians. Use toDegrees() and toRadians() to convert.
// Note 3: Use asin(), acos(), and atan() for inverse functions.
static double exp(double a)             //exponential (e a)
static double log(double a)             // natural log (loge a, or ln a)
static double pow(double a, double b)   //raise a to the bth power (ab )
static double random()                  //random number in [0, 1)
static double sqrt(double a)            // square root of a
static double E                         // value of e (constant)
static double PI                        // value of pi (constant)
// See booksite for other available functions.


```

# Unit testing


# Input and output