/**
 * https://labuladong.online/zh/algo/data-structure-basic/hashtable-with-linked-list/
 * 
 * 
 * Basic Seperate Chain Hash table:
 * 1. key -> hash -> index in buckets
 * 2. linear search by key -> node key
 * 3. a= n/m 当适当的时候扩容，从而 avg(LinedList) = const
 * 
 * LinkedHashMap: Java 标准库提供的 LinkedHashMap 就可以按照键的插入顺序来遍历
 * How to implement this: build a LinedList to store key ? 
 * 
The key idea is:
HashMap solves fast lookup; linked list solves predictable iteration order.

                 LinkedHashMap
                       │
            ┌──────────┴──────────┐
            ↓                     ↓
       Hash Table            Doubly Linked List
            │                     │
            │                     │
       fast lookup          ordered iteration
            │                     │
            ↓                     ↓
        O(1) avg.                O(N)
 */
// import java.util.LinkedHashMap;


package java.myutil;

public class Node<K,V>{
    K key;
    V value;

    Node<K,V> bucketNext; // hash table chain
    Node<K,V> prev;
    Node<K,V> next;

}


static class Entry<K,V> extends Node<K,V>{
    Entry<K,V> before;
    Entry<K,V> after;
}


public class Main {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println(map.keySet()); // [a, b, c]

        map.put("y", 4);
        System.out.println(map.keySet()); // [a, b, c, y]

        map.put("d", 5);
        System.out.println(map.keySet()); // [a, b, c, y, d]
    }
}