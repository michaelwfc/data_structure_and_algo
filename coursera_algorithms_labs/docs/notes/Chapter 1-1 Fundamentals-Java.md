# Tutorials

[liaoxuefeng.com](https://liaoxuefeng.com/books/java/introduction/index.html)
[java-tutorial](https://www.runoob.com/java/java-tutorial.html)
[tutorialspoint.com](https://www.tutorialspoint.com/java/index.htm)

# Courses

[CS106A - Programming Methodology](https://see.stanford.edu/course/cs106a)
[Introduction to Programming in Java](https://ocw.mit.edu/courses/6-092-introduction-to-programming-in-java-january-iap-2010/)

# 1.1 BASIC PROGRAMMING MODEL

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

-   **Primitive data types** precisely define the meaning of terms like integer, real number, and boolean value within a computer program. Their definition includes the set of possible values and operations on those values, which can be combined into expressions like mathematical expressions that define values.
-   **Statements** allow us to define a computation by creating and assigning values to variables, controlling execution flow, or causing side effects. We use six types of statements: declarations, assignments, conditionals, loops, calls, and returns.
-   **Arrays** allow us to work with multiple values of the same type.
-   **Static methods** allow us to encapsulate and reuse code and to develop programs
    as a set of independent modules.
-   **Strings** are sequences of characters. Some operations on them are built in to Java.
-   **Input/output** sets up communication between programs and the outside world.
-   **Data abstraction** extends encapsulation and reuse to allow us to define nonprimitive data types, thus supporting object-oriented programming.

## Data types

### Primitive data types and expressions

A data type is a set of values and a set of operations on those values. We begin by considering the following four primitive data
types that are the basis of the Java language:

-   Integers, with arithmetic operations (int)
    -   short
    -   int
    -   long
-   Real numbers, again with arithmetic operations (double)
    -   float
    -   double
-   Booleans, the set of values { true, false } with logical operations (boolean)
-   Characters, the alphanumeric characters and symbols that you type (char)
-   byte

### Wapper types

#### Wrapper Class : Integer

想要把 int 基本类型变成一个引用类型，我们可以定义一个 Integer 类，它只包含一个实例字段 int，这样，Integer 类就可以视为 int 的包装类（Wrapper Class）

```Java
public class Integer {
    private int value;

    public Integer(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }
}


```

### Arrays

An array stores a sequence of values that are all of the same type. We want not only to store values but also to access each individual value. The method that we use to refer to individual values in an array is numbering and then indexing them. If we have N values, we think of them as being numbered from 0 to N-1. Then, we can
unambiguously specify one of them in Java code by using the notation a[i] to refer to the ith value for any value of i from 0 to N-1. This Java construct is known as a onedimensional array.

#### creating and initializing an array

```Java
// long form
double[] a;        // declare an array of doubles
a = new double[N]; // create an array of N doubles
for (int i = 0; i < N; i++) // initialize
    a[i] = 0.0;

// short form
double[] a = new double[N];

// initialization Declaring,
int[] a = { 1, 1, 2, 3, 5, 8 };


```

### Strings

## Input and output

## Unit testing

## External libraries.

-   The standard system libraries java.lang.\*.

    -   Math
    -   Integer
    -   Double
    -   String
    -   StringBuilder
    -   System

-   Imported system libraries such as java.util.Arrays
-   The standard libraries Std\* that we have developed for use in this book
    (and our introductory book An Introduction to Programming in Java: An Interdisciplinary Approach).
    -   StdIn
    -   StdOut
    -   StdDraw
    -   StdRandom
    -   StdStats
    -   In
    -   Out

## Modular programming

Of critical importance in this model is that libraries of **static methods** enable modular programming where we build libraries of static methods (modules) and a static method in one library can call static methods defined in other libraries. This approach has many important advantages. It allows us to

-   Work with modules of reasonable size, even in program involving a large amount of code
-   Share and reuse code without having to reimplement it
-   Easily substitute improved implementations
-   Develop appropriate abstract models for addressing programming problems
-   Localize debugging (see the paragraph below on unit testing)
    For example, BinarySearch makes use of three other independently developed libraries,
    our StdIn and In library and Java’s Arrays library. Each of these libraries, in turn,
    makes use of several other libraries.

### Static methods

### APIs (application programming interfaces)

A critical component of modular programming is **documentation** that explains the operation of library methods that are intended for use by others. We will consistently describe the library methods that we use in this book in **application programming interfaces(APIs)** that list the library name and the signatures and short descriptions of each of the methods that we use.

-   **client**
    We use the term client to refer to a program that calls a method in another library
-   **implementation**
    the term implementation to describe the Java code that implements the methods in an API.

### Example: API for Java’s mathematics library

(excerpts)
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

### Client code.

As with modular programming based on static methods, the API allows us to write client code without knowing details of the implementation (and to write implementation code without knowing details of any particular client).
The mechanisms introduced on page 28 for organizing programs as independent modules are useful for all Java classes, and thus are effective for modular programming with ADTs as well as for libraries of static methods.

Accordingly, we can use an ADT in any program provided that the source code is in a .java file in the same directory, or in the standard Java library, or accessible through an import statement, or through one of the classpath mechanisms described on the booksite.

All of the benefits of modular programming follow. By encapsulating all the code that implements a data type within a single Java class, we enable the development of client code at a higher level of abstraction.

To develop client code, you need to be able to declare variables, create objects to hold datatype values, and provide access to the values for instance methods to operate on them. These processes are different from the corresponding processes for primitive types, though you will notice many similarities.

# 1.2 DATA ABSTRACTION

A **data type** is a set of values and a set of operations on those values.

### Reference types

Programming in Java is largely based on building data types known as **reference types** with the familiar Java class. This style of programming is known as **object-oriented programming**, as it revolves around the concept of an object, an entity that holds a data type value.
With Java’s primitive types we are largely confined to programs that operate on numbers, but with reference types we can write programs that operate on strings, pictures, sounds, any of hundreds of other abstractions that are available in Java’s standard libraries or on our booksite

## Using abstract data types

An **abstract data type (ADT)** is a data type whose representation is hidden from the client.

Abstract data types are important because they support encapsulation in program
design. In this book, we use them as a means to

-   Precisely specify problems in the form of APIs for use by diverse clients
-   Describe algorithms and data structures as API implementations

### API for an abstract data type.

To specify the behavior of an abstract data type, we use
an a pplication programming interface (API), which is a list of c onstructors and i nstance
methods (operations), with an informal description of the effect of each, as in this API
for Counter:

An API for a counter

```Java
public class Counter
    Counter(String id)      //constructor:      create a counter named id
    void increment()        //instance methods: increment the counter by one
    int tally()             //instance methods:  number of increments since creation
    String toString()       //Inherited methods: string representation
```

### Objects

Naturally, you can declare that a variable heads is to be associated with data of type Counter with the code Counter heads;
but how can you assign values or specify operations?

```Java
Counter heads;
```

Objects are characterized by three essential properties: state, identity, and behavior.

-   **state**
    The state of an object is a value from its data type.
-   **identity**
    The identity of an object distinguishes one object from another. It is useful to think of an object’s identity as the place where its value is stored in memory.
-   **behavior**
    The behavior of an object is the effect of data-type operations. The implementation has the sole responsibility for maintaining an object’s identity, so that client code can use a data type without regard to the representation of its state by conforming to an API that describes an object’s behavior

#### references

A **reference** is a mechanism for accessing an object.
Java nomenclature makes clear the distinction from primitive types (where variables are associated with values) by using the term reference types for nonprimitive types.

The details of implementing references vary in Java implementations, but it is useful to think of a
reference as **a memory address**, as shown at right (for brevity, we use three-digit memory addresses in the diagram).

#### Creating objects

Each data-type value is stored in an **object**.
To create (or instantiate) an individual object, we invoke a **constructor** by using the keyword **new**, followed by the class name, followed by () (or a list of argument values enclosed in parentheses, if the constructor takes arguments).
A constructor has no return type because it always returns a reference to an object of its data type. Each time that a client uses new(), the system
■ Allocates memory space for the object
■ Invokes the constructor to initialize its value
■ Returns a reference to the object

```Java
// Creating an object
// Counter heads : declaration to associate variable with object reference
// new Counter("heads"): call on constructor to create an object
Counter heads =  new Counter("heads");
```

### Invoking instance methods

The purpose of an instance method is to operate on datatype values, so the Java language includes a special mechanism to invoke instance methods that emphasizes a connection to an object.
each invocation is associated with an object.

# 1.3 BAGS, QUEUES, AND STACKS

## APIs

for fundamental generic iterable collections

```Java
// Bag
public class Bag<Item> implements Iterable<Item>
    Bag()                   //create an empty bag
    void add(Item item)     //add an item
    boolean isEmpty()       //is the bag empty?
    int size()              // number of items in the bag


// FIFO queue

public class Queue<Item> implements Iterable<Item>
    Queue()                 // create an empty queue
    void enqueue(Item item) // add an item
    Item dequeue()          // remove the least recently added item
    boolean isEmpty()       // is the queue empty?
    int size()              // number of items in the queue


// Pushdown (LIFO) stack

public class Stack<Item> implements Iterable<Item>
    Stack()                     // create an empty stack
    void push(Item item)        // add an item
    Item pop()                  // remove the most recently added item
    boolean isEmpty()           // is the stack empty?
    int size()                  // number of items in the stack


```

### Generics(泛型)

An essential characteristic of collection ADTs is that we should be able to use
them for any type of data. A specific Java mechanism known as generics, also known
as parameterized types, enables this capability

```Java
Stack<String> stack = new Stack<String>();
stack.push("Test");
...
String next = stack.pop();


Queue<Date> queue = new Queue<Date>();
queue.enqueue(new Date(12, 31, 1999));
...
Date next = queue.dequeue();

```

### Autoboxing

Type parameters have to be instantiated as **reference types**, so Java has
special mechanisms to allow generic code to be used with primitive types.

```Java
Stack<Integer> stack = new Stack<Integer>();
stack.push(17); // auto-boxing (int -> Integer)
int i = stack.pop(); // auto-unboxing (Integer -> int)

```

### Iterable collections

```Java
Queue<Transaction> collection = new Queue<Transaction>();
for (Transaction t : collection)
{ StdOut.println(t); }

```

## Bags

bag is a collection where **removing items is not supported** - its purpose is to
provide clients with the ability to collect items and then to iterate through the collected items (the client can also test if a bag is empty and find its number of items).

```JAVA
public class Stats {
    public static void main(String[] args) {
        Bag<Double> numbers = new Bag<Double>();
        while (!StdIn.isEmpty())
            numbers.add(StdIn.readDouble());
        int N = numbers.size();
        double sum = 0.0;
        for (double x : numbers)
            sum += x;
        double mean = sum / N;
        sum = 0.0;
        for (double x : numbers)
            sum += (x - mean) * (x - mean);
        double std = Math.sqrt(sum / (N - 1));
        StdOut.printf("Mean: %.2f\n", mean);
        StdOut.printf("Std dev: %.2f\n", std);
    }
}
```

## FIFO queues

FIFO queue (or just a queue) is a collection that is based on the first-in-first-out (FIFO) policy. The policy of doing tasks in the same order that they arrive is one that we encounter frequently in everyday life:
from people waiting in line at a theater, to cars waiting in line at a toll booth, to tasks waiting to be serviced by an application on your computer.

One bedrock principle of any service policy is the perception of **fairness**.
The first idea that comes to mind when most people think about fairness is that whoever has been waiting the longest should be served first.
That is precisely the FIFO discipline. Queues are a natural model for many everyday phenomena, and they play a central role in numerous applications.

```JAVA
import java.util.Queue;

public static int[] readInts(String name) {
    In in = new In(name);
    Queue<Integer> q = new Queue<Integer>();
    while (!in.isEmpty())
        q.enqueue(in.readInt());
    int N = q.size();
    int[] a = new int[N];
    for (int i = 0; i < N; i++)
        a[i] = q.dequeue();
    return a;
}
```

## Pushdown stacks

# 1.4 ANALYSIS OF ALGORITHMS

# 1.5 UNION-FIND

## Steps to developing a usable algorithm.

-   Model the problem.
-   Find an algorithm to solve it.
-   Fast enough? Fits in memory?
-   If not, figure out why.
-   Find a way to address the problem.
-   Iterate until satisfied.

## Dynamic connectivity

We start with the following problem specification: The
input is a sequence of pairs of integers, where each integer represents an object of some
type and we are to interpret the pair p q as meaning “p is connected to q.” We assume
that “is connected to” is an equivalence relation, which means that it is
■ Reflexive : p is connected to p.
■ Symmetric : If p is connected to q, then q is connected to p.
■ Transitive : If p is connected to q and q is connected to r, then p is connected to r.

An equivalence relation partitions the objects into **equivalence classes**.
