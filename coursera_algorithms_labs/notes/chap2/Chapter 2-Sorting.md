# 2.1 ELEMENTARY SORTS

## Selection sort

1. find the smallest item in the array and exchange it with the first entry (itself if the first entry is already the smallest).
2. Then, find the next smallest item and exchange it with the second
   entry.
3. Continue in this way until the entire array is sorted.

Algorithm. ↑ scans from left to right.
Invariants.
-   Entries the left of ↑ (including ↑) fixed and in ascending order.
-   No entry to right of ↑ is smaller than any entry to the left of ↑.


## Insertion sort

The algorithm that people often use to sort bridge hands is to consider  the cards one at a time, inserting each into its proper place among those already
considered (keeping them sorted).


Algorithm. ↑ scans from left to right.
Invariants.
- Entries to the left of ↑ (including ↑) are in ascending order.
- Entries to the right of ↑ have not yet been seen.


## Shellsort

Idea. Move entries more than one position at a time by h-sorting the array.

# 2.2 MERGESORT

# 2.3 QuickSort


# 2.4 PRIORITY QUEUES

Collections. Insert and delete items. Which item to delete?
Stack. Remove the item most recently added.
Queue. Remove the item least recently added.
Randomized queue. Remove a random item.
Priority queue. Remove the largest (or smallest) item.

## 1. Priority queue
### Priority queue API

A Priority Queue is a special type of queue in which each element is associated with a priority, and elements are served (or removed) based on **_priority order_** rather than insertion order.

```JAVA
public class MaxPQ<Key extends Comparable<Key>>
    MaxPQ() create an empty priority queue
    MaxPQ(Key[] a) create a priority queue with given keys
    void insert(Key v) insert a key into the priority queue
    Key delMax() return and remove the largest key
    boolean isEmpty() is the priority queue empty?
    Key max() return the largest key
    int size() number of entries in the priority queue
```

### Priority queue client example

Challenge. Find the largest M items in a stream of N items.

| implementation | time    | space |
| -------------- | ------- | ----- |
| sort           | n log n | n     |
| elementary pq  | m n     | m     |
| binary heap    | n log m | m     |
| best in theory | n       | m     |

## Priority queue implementation

### Array representation (unordered)

-   insert：the same as for push in the stack.
-   remove the maximum： we can add code like the inner loop of selection sort to exchange the maximum item with the item at the end and then delete that one, as we did with pop() for stacks.

### ordered array implementation

### Linked-list representations

| implementation | insert | delete Max | max |
| -------------- | ------ | ---------- | --- |



----------
----------
## 2. Heap definitions

A **heap** is a special tree-based data structure that satisfies the **heap-order property**. It's commonly used to implement **priority queues**.

### Heap-order Property

There are two types:
#### 1. **Max-Heap**
* The value of each node is **greater than or equal to** its children.
* So, the **maximum value** is always at the root.
```
        50
      /    \
    30      20
   /  \    / 
  10  15  5
```

### 2. **Min-Heap**

* The value of each node is **less than or equal to** its children.
* So, the **minimum value** is at the root.

```
        5
      /   \
    10     15
   /  \   / 
  30  50 20
```

---

### Heap as an Array (Binary Heap)

Definition:   
A binary heap is a collection of keys arranged in a **complete heap-ordered binary tree**, represented in level order in an array (not using the first entry).

A binary heap is usually stored in an array. For any node at index `k`:

* **Left child**: index `2k`
* **Right child**: index `2k + 1`
* **Parent**: index `floor(k/2)`

This allows for **efficient implementation** with no pointers.

we can travel up and down by doing simple arithmetic on array indices: 
- to move up the tree from a[k] we set k to k/2; 
- to move down the tree we set k to 2*k or 2*k+1.

[!image](../../../images/Heap%20representations.png)

---

### Time Complexities

| Operation      | Time     |
| -------------- | -------- |
| Insert         | O(logN) |
| Remove max/min | O(logN) |
| Peek max/min   | O(1)     |

These are **logarithmic** because the tree has height logN.

---

###  Uses of Heaps

* **Priority Queues**
* **Heap Sort** (a comparison-based sorting algorithm using a heap)
* **Dijkstra's Algorithm** (for shortest paths)
* **Median Maintenance**
* **A* search*\* (for pathfinding)

---

###  PriorityQueue In Java

Java's `PriorityQueue` uses a **min-heap** under the hood.

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(10);
pq.add(1);
pq.add(5);

System.out.println(pq.remove()); // 1 (the smallest)
```

To make it a max-heap:

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

---


## Heapsort

Basic plan for in-place sort.
- Create max-heap with all N keys.
  Heap construction. Build max heap using bottom-up method.
- Repeatedly remove the maximum key.