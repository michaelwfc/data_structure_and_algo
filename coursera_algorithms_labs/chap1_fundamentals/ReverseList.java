

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Bag;

public class ReverseList <Item> implements Iterable<Item> {
    private List<Item>  list = new ArrayList<>();
    public void add(Item item) {
        list.add(item);
    }

    // provide an iterator() method.
    // The iterator() method itself simply returns an object from a class that implements the Iterator interface:
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    //  a class that implements the Iterator interface: implements the hasNext(), next(),
    class ReverseArrayIterator implements Iterator<Item> {
        private int i =list.size();

        public boolean hasNext() {
            return i > 0;
        }
        public Item next() {
            i--;
            return list.get(i);
        }
    }

    public static void main(String[] args) {
        ReverseList<String> list = new ReverseList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
