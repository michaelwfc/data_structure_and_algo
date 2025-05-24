
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
* Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time.
That is, any intermixed sequence of m randomized queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c.
A randomized queue containing n items must use at most 48n + 192 bytes of memory.
Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time;
and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
*
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
//    private static int CAPACITY;
    private Item[] queue; //

    private int n = 0; // Use a resizing array for storage
    private int head_index = 0;
    private int tail_index = -1; // the index for the last item


    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) (new Object[INIT_CAPACITY]);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) (new Object[capacity]);
        for (int i = 0; i < n; ++i) {
            copy[i] = queue[(head_index + i) % queue.length];
        }
        queue = copy;
        head_index = 0;
        tail_index = n-1;
//        CAPACITY = capacity;
    }

    // add the item
    public void enqueue(Item item) {
        if (n == queue.length) {
            this.resize(2 * queue.length);
        }
        // increment tail_index first and then add item
        ++tail_index;
        if (tail_index == queue.length) {
            tail_index = 0;
        }
        queue[tail_index] = item;
        ++n;


    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        } else {
            int rand = StdRandom.uniformInt(n);
            int index = (rand + head_index) % n;
            Item item = queue[index];
            //swap the last item to the removed index
            if (index != (tail_index)) {
                queue[index] = queue[tail_index];
            }
            queue[tail_index] = null;
            --tail_index;
            if (tail_index < 0) {
                tail_index = queue.length - 1;
            }
            --n;
            // resize the array when n is small
            if (n > INIT_CAPACITY * 2 && n == queue.length / 4) {
                this.resize(queue.length / 2);
            }
            return item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int count = StdRandom.uniformInt(n ); // returns 0 to n
        Item item = queue[(head_index + count) % queue.length];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(queue, n, head_index);
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final Item[] iteratorArray; // to store for the iterator
        private int index = 0;

        public RandomizedQueueIterator(Item[] q, int N, int first_index) {
            iteratorArray = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                iteratorArray[i] = q[(i + first_index) % N];
            }
            StdRandom.shuffle(iteratorArray);
        }

        public boolean hasNext() {
            return index < iteratorArray.length;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Item item = iteratorArray[index];
            ++index;
            return item;
        }
    }

    // unit testing (required)
    // Your main() method must call directly every public constructor and method to verify that they work as prescribed (e.g., by printing results to standard output).
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        System.out.println(randomizedQueue.isEmpty());
        System.out.println(randomizedQueue.size());
        System.out.println("iterating:");
        for (int i : randomizedQueue) {
            System.out.println(i);
        }
        System.out.println("sampling:");
        for (int i = 0; i < 10; i++) System.out.println(randomizedQueue.sample());
        System.out.println("dequeue:");
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());


    }

}
