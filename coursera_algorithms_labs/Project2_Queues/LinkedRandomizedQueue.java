
/*
 Randomized queue.
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random among items in the data structure.
 * Create a generic data type RandomizedQueue that implements the following API:
 *
 Iterator.
 * Each iterator must return the items in uniformly random order. The order of two or more iterators to the same randomized queue must be mutually independent;
 * each iterator must maintain its own random order.

Corner cases.
* Throw the specified exception for the following corner cases:
Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.

Performance requirements.
* Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time. That is, any intermixed sequence of m randomized queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c. A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
*
 */

import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;


public class LinkedRandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;
//    private static final Random rand = new Random();

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // construct an empty randomized queue
    public LinkedRandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldlast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        n++;
    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) throw new NoSuchElementException();

        Node<Item> current = first;
        if (n == 1) {
            first = null;
        } else {
            int count = StdRandom.uniformInt(n - 1);
            // 0 means the first, n-1 means the last
            Node<Item> lastNode = null;
            while (count > 0) {
                lastNode = current;
                current = current.next;
                count--;
            }
            // remove the node
            if (lastNode != null) lastNode.next = current.next;
            else first = current.next;  // removing the first node
        }
        n--;
        return current.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int count = StdRandom.uniformInt(n + 1); // returns 0 to n
        Node<Item> current = first;
        Item item = current.item;
        while (count > 0) {
            item = current.item;
            current = current.next;
            count--;
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(first);
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public RandomizedQueueIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    // Your main() method must call directly every public constructor and method to verify that they work as prescribed (e.g., by printing results to standard output).
    public static void main(String[] args) {
        LinkedRandomizedQueue<Integer> linkedRandomizedQueue = new LinkedRandomizedQueue<Integer>();
        linkedRandomizedQueue.enqueue(1);
        linkedRandomizedQueue.enqueue(2);
        linkedRandomizedQueue.enqueue(3);
        linkedRandomizedQueue.enqueue(4);
        System.out.println(linkedRandomizedQueue.isEmpty());
        System.out.println(linkedRandomizedQueue.size());
        System.out.println("iterating:");
        for (int i : linkedRandomizedQueue) {
            System.out.println(i);
        }
        System.out.println("sampling:");
        for (int i = 0; i < 10; i++) System.out.println(linkedRandomizedQueue.sample());
        System.out.println("dequeue:");
        System.out.println(linkedRandomizedQueue.dequeue());
        System.out.println(linkedRandomizedQueue.dequeue());
        System.out.println(linkedRandomizedQueue.dequeue());
        System.out.println(linkedRandomizedQueue.dequeue());
        System.out.println(linkedRandomizedQueue.dequeue());


    }

}
