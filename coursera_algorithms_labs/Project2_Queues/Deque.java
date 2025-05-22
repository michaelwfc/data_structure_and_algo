/*
 * https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 * Write a generic data type for a deque and a randomized queue.
 * The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce you to generics and iterators.
 * Dequeue.
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and
 * removing items from either the front or the back of the data structure.
 * Create a generic data type Deque that implements the following API:
 *
 * Corner cases.
 * Throw the specified exception for the following corner cases:
    Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
    Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
    Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
    Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.

 * Performance requirements.
 * Your deque implementation must support each deque operation (including construction) in constant worst-case time.
    * A deque containing n items must use at most 48n + 192 bytes of memory.
*   * Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque(){
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()) {
            // set the last node as first
            last = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        // when the deque is empty
        if (isEmpty()) last = null;
        else first.prev = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(this.first);
    }

    private static class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    /*
     * Unit testing.
     * Your main() method must call directly every public constructor and method to help verify that they work as prescribed
     * (e.g., by printing results to standard output).
    */

    public static void main(String[] args){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(0);
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        for(Integer e : deque){
            System.out.println(e);
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }

}