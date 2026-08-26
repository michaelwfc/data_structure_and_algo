# Reference
## CS

- https://labuladong.online/en/algo/home/
- https://github.com/labuladong/fucking-algorithm
- https://leetcode.com/
- https://leetcode.cn/
- https://leetcode.com/studyplan/top-100-liked/
- https://leetcode.cn/studyplan/top-100-liked/
- https://www.nowcoder.com/exam/intelligent?questionJobId=10&subTabName=intelligent_page&tagId=273590

## AI

- https://www.xiaolincoding.com/project/xiaolinnote.html
- https://github.com/guocong-bincai/ai-interview-guide

# 1. 数据结构

## 1. 静态数组 vs 动态数组
- https://labuladong.online/zh/algo/essential-technique/linked-list-skills-summary/
 

我认为暂且可以把「数组」分为两大类，一类是「静态数组」，一类是「动态数组」。

「静态数组」就是一块连续的内存空间，我们可以通过索引来访问这块内存空间中的元素，这才是数组的原始形态。

而「动态数组」是编程语言为了方便我们使用，在静态数组的基础上帮我们添加了一些常用的 API，比如 push, insert, remove 等等方法，这些 API 可以让我们更方便地操作数组元素，不用自己去写代码实现这些操作。

本章的内容就是带大家仅仅使用最原始的静态数组，自己实现一个动态数组，实现增删查改的常见 API。以后你在使用标准库提供的数据结构时，就知道它们的底层运行原理了。

有了动态数组，后面讲到的队列、栈、哈希表等复杂数据结构都会依赖它进行实现。

### 静态数组

```c++
// 定义一个大小为 10 的静态数组
int arr[10];

// 用 memset 函数把数组的值初始化为 0
memset(arr, 0, sizeof(arr));

// 使用索引赋值
arr[0] = 1;
arr[1] = 2;

// 使用索引取值
int a = arr[0];
```

我梳理一下上面的因果逻辑，静态数组本质上就是一块连续的内存空间，int arr[10] 这个语句我们可以得知：

1、我们知道这块内存空间的首地址（数组名 arr 就指向这块内存空间的首地址）。

2、我们知道了每个元素的类型（比如 int），也就是知道了每个元素占用的内存空间大小（比如一个 int 占 4 字节，32 bit）。

3、这块内存空间是连续的，其大小为 10 \* sizeof(int) 即 40 字节。

所以，我们获得了数组的超能力「随机访问」：只要给定任何一个数组索引，我可以在 O(1) 的时间内直接获取到对应元素的值。

因为我可以通过首地址和索引直接计算出目标元素的内存地址。计算机的内存寻址时间可以认为是 O(1)，所以数组的随机访问时间复杂度是 O(1)。

但是，一个人最大的优势往往也是他的最大劣势。数组连续内存的特性给了他随机访问的超能力，但它也因此吃了不少苦，下面介绍。

#### 增删查改

数据结构的职责就是增删查改，再无其他。

那么刚刚介绍数组这种数据结构的底层原理，我们其实只介绍了「查」和「改」的部分，也就是通过索引修改和访问对应元素的值。那么「增删」这两个操作又是如何实现的呢？

总结
综上，静态数组的增删查改操作的时间复杂度是：

增：
在末尾追加元素：O(1)。
在中间（非末尾）插入元素：O(N)。

删：
删除末尾元素：O(1)。
删除中间（非末尾）元素：O(N)。

查：给定指定索引，查询索引对应的元素的值，时间复杂度 O(1)。
改：给定指定索引，修改索引对应的元素的值，时间复杂度 O(1)。

有读者可能问，刚才不是还探讨过数组的扩容操作吗，扩容涉及到新数组空间的开辟和数据的复制，时间复杂度是 O(N)，这个复杂度为什么没有算到「增」的复杂度里面呢？

这个问题很好，但并不是每次增加元素的时候都会触发扩容，所以扩容的复杂度要用「均摊时间复杂度」来分析，这个概念我在
时空复杂度分析方法
中有详细的讲解，这里就不展开了。

还有个问题初学者要注意，我们说数组的查、改复杂度是 O(1)，这个仅仅适用于给定索引的情况。如果反过来，比方说给你一个值，让你去找这个值在数组中对应的索引，那你只能遍历整个数组去寻找对吧，这个复杂度就是 O(N) 了。

所以说要搞清楚原理，而不要去背概念。原理懂了，概念你自己都能推导出来的。

### 动态数组

首先，你不要以为动态数组可以解决静态数组在中间增删元素效率差的问题，不可能解决的。数组随机访问的超能力源于数组连续的内存空间，而连续的内存空间就不可避免地面对数据搬移和扩缩容的问题。

动态数组底层还是静态数组，只是自动帮我们进行数组空间的扩缩容，并把增删查改操作进行了封装，让我们使用起来更方便而已。

```c++
// 创建动态数组
// 不用显式指定数组大小，它会根据实际存储的元素数量自动扩缩容
vector<int> arr;

for (int i = 0; i < 10; i++) {
    // 在末尾追加元素，时间复杂度 O(1)
    arr.push_back(i);
}

// 在中间插入元素，时间复杂度 O(N)
// 在索引 2 的位置插入元素 666
arr.insert(arr.begin() + 2, 666);

// 在头部插入元素，时间复杂度 O(N)
arr.insert(arr.begin(), -1);

// 删除末尾元素，时间复杂度 O(1)
arr.pop_back();

// 删除中间元素，时间复杂度 O(N)
// 删除索引 2 的元素
arr.erase(arr.begin() + 2);

// 根据索引查询元素，时间复杂度 O(1)
int a = arr[0];

// 根据索引修改元素，时间复杂度 O(1)
arr[0] = 100;

// 根据元素值查找索引，时间复杂度 O(N)
int index = find(arr.begin(), arr.end(), 666) - arr.begin();
```

## 2. 链表 LinkedLisk

## 3. HashMap


## 4. Queue and Stack


## 5. Binary Tree






# 2. 算法

## TWO_pointers
- https://labuladong.online/zh/algo/essential-technique/array-two-pointers-summary/
- https://www.bilibili.com/video/BV1iG411W7Wm?spm_id_from=333.788.videopod.sections&vd_source=b3d4057adb36b9b243dc8d7a6fc41295



# Classic data-structure interview questions
Absolutely. If you're working through **LRU Cache**, you're at a very useful point in data-structure preparation: instead of memorizing LeetCode solutions, you can start studying the **design patterns behind the classic problems**.

Given that you're already working through hash tables, linked lists, `LinkedHashMap`, and LRU, I'd organize the classics like this.

## 1. The core data-structure classics

| Problem                          | Main data structure | Key idea                   | Target                     |
| -------------------------------- | ------------------- | -------------------------- | -------------------------- |
| **Two Sum**                      | Hash Table          | `value → index`            | O(N)                       |
| **Valid Parentheses**            | Stack               | Matching/open-close        | O(N)                       |
| **Min Stack**                    | Stack               | Maintain auxiliary minimum | O(1)                       |
| **Queue using Stacks**           | 2 Stacks            | Amortized analysis         | O(1) amortized             |
| **Stack using Queues**           | Queue               | Rearrangement              | O(1)/O(N) depending design |
| **Design HashMap**               | Hash Table          | Buckets / collision        | O(1) avg                   |
| **Design HashSet**               | Hash Table          | Same                       | O(1) avg                   |
| **LRU Cache**                    | Hash Table + DLL    | Fast lookup + recency      | O(1)                       |
| **LFU Cache**                    | Hash Table + DLLs   | Frequency + recency        | O(1)                       |
| **RandomizedSet**                | Hash Table + Array  | O(1) insert/delete/random  | O(1)                       |
| **Insert/Delete/GetRandom O(1)** | Array + Hash Map    | Swap-with-last             | O(1)                       |

I'd particularly recommend **RandomizedSet** after LRU. It teaches a different but extremely important combination:

```text
HashMap + Array
```

rather than:

```text
HashMap + LinkedList
```

---

## 2. Linked-list classics

These are especially important because they teach pointer manipulation.

### Level 1

1. **Reverse Linked List**
2. **Merge Two Sorted Lists**
3. **Linked List Cycle**
4. **Linked List Cycle II**
5. **Remove Nth Node From End**
6. **Middle of the Linked List**

You should be able to implement these without much thought.

The important patterns are:

```text
slow / fast pointers
two pointers
dummy node
in-place pointer manipulation
```

### Level 2

7. **Intersection of Two Linked Lists**
8. **Palindrome Linked List**
9. **Reorder List**
10. **Copy List with Random Pointer**
11. **Add Two Numbers**

`Copy List with Random Pointer` is particularly good because it combines:

```text
linked list + hash map
```

---

## 3. Stack / monotonic-stack classics

This is one of the most important interview patterns.

### Basic

* Valid Parentheses
* Min Stack
* Evaluate Reverse Polish Notation

### Monotonic stack

Then move to:

* **Daily Temperatures**
* **Next Greater Element**
* **Largest Rectangle in Histogram**
* **Trapping Rain Water**

The key insight is that these problems aren't really about stacks.

They're about maintaining something like:

```text
monotonically increasing
```

or:

```text
monotonically decreasing
```

stack.

For example:

```text
temperatures:

73 74 75 71 69 72 76 73
       ↑
```

The stack stores unresolved candidates.

This pattern appears constantly in interviews.

---

## 4. Queue / deque classics

Important problems:

### Sliding Window Maximum

This is a particularly good one.

You need:

[
O(N)
]

not:

[
O(NK)
]

The solution uses a **monotonic deque**.

Conceptually:

```text
window:
[1, 3, -1]

deque:
3 → -1
↑
maximum
```

When the window moves, expired elements are removed from the front and useless smaller elements are removed from the back.

This is another excellent example of:

> choosing the right data structure to make an operation O(1).

---

## 5. Heap / Priority Queue classics

Once you're comfortable with hash tables and linked lists, definitely learn heaps.

Classic problems:

| Problem                      | Pattern        |
| ---------------------------- | -------------- |
| Kth Largest Element          | Min Heap       |
| Top K Frequent Elements      | HashMap + Heap |
| Merge K Sorted Lists         | Min Heap       |
| Find Median from Data Stream | Two Heaps      |
| K Closest Points to Origin   | Heap           |
| Task Scheduler               | Heap + Greedy  |

### Find Median from Data Stream

This is particularly classic.

Maintain:

```text
        max heap
       /        \
 smaller half   larger half
                  \
                 min heap
```

Example:

```text
1 2 3 4 5
```

Conceptually:

```text
maxHeap          minHeap

  2                4
 /                  \
1                    5

       median = 3
```

More precisely, the two heaps partition the values, with sizes differing by at most one.

This is a great data-structure design problem.

---

## 6. Binary Search classics

Don't just study:

```text
binarySearch(target)
```

The more interesting interview questions are variations.

### Classic problems

* Binary Search
* Search Insert Position
* First Bad Version
* Find First and Last Position
* Search in Rotated Sorted Array
* Find Minimum in Rotated Sorted Array
* Koko Eating Bananas
* Capacity to Ship Packages Within D Days

The last two teach an extremely important pattern:

> **Binary Search on the Answer**

For example:

```text
minimum speed?
```

You're not searching an array.

You're searching the answer space:

```text
1 2 3 4 5 ... max
↑
binary search
```

This is a very common interview technique.

---

## 7. Tree classics

This is a huge category.

Start with:

### Traversal

* Binary Tree Preorder Traversal
* Inorder Traversal
* Postorder Traversal
* Level Order Traversal

Understand both:

```text
recursive
```

and:

```text
iterative using stack/queue
```

---

Then:

### Fundamental tree problems

* Maximum Depth of Binary Tree
* Same Tree
* Invert Binary Tree
* Symmetric Tree
* Diameter of Binary Tree
* Balanced Binary Tree
* Lowest Common Ancestor
* Path Sum
* Binary Tree Right Side View

These teach the fundamental recursive pattern:

```text
solve(left)
solve(right)
combine(left, right)
```

---

## 8. BST classics

Very important for interviews.

* Search in BST
* Insert into BST
* Delete Node in BST
* Validate BST
* Lowest Common Ancestor of BST
* Kth Smallest Element in BST

One particularly beautiful connection:

> **Inorder traversal of a BST produces sorted order.**

Therefore:

```text
BST
 ↓
inorder traversal
 ↓
sorted sequence
```

So `kth smallest` becomes:

```text
inorder + count
```

---

## 9. Trie classics

Since you're a programmer and work with NLP, **Trie is especially worth learning**.

Classic problems:

* Implement Trie
* Design Add and Search Words
* Word Search II
* Replace Words
* Longest Common Prefix

Basic structure:

```text
             root
           /  |   \
          c   d    a
          |   |    |
          a   o    p
          |   |    |
          t   g    p
```

The key idea:

```text
string
 ↓
character-by-character path
```

Search complexity:

[
O(L)
]

where (L) is the length of the word.

---

## 10. Graph classics

This is where data structures start becoming algorithms.

### BFS / DFS

You should know:

* Number of Islands
* Clone Graph
* Flood Fill
* Rotting Oranges
* Course Schedule
* Pacific Atlantic Water Flow

Then:

### Topological Sort

**Course Schedule** is probably the classic interview problem.

You have:

```text
A → B
A → C
B → D
C → D
```

Need to determine whether all courses can be completed.

This introduces:

```text
indegree
queue
topological ordering
cycle detection
```

---

## 11. Union-Find / DSU

This is another very important data structure.

Classic problems:

* Number of Provinces
* Redundant Connection
* Accounts Merge
* Number of Connected Components
* Kruskal's MST

The structure is basically:

```text
parent[]
rank[] / size[]
```

with:

```text
find(x)
union(x, y)
```

and optimizations:

```text
path compression
union by rank/size
```

giving almost constant amortized complexity:

[
O(\alpha(N))
]

where (\alpha) is the inverse Ackermann function.

In practical terms:

> basically O(1).

---

## 12. A particularly good progression for you

Given what you've been studying recently, I wouldn't recommend randomly doing 100 LeetCode problems.

I'd follow the **data-structure progression**:

```text
1. Array
   ↓
2. Linked List
   ↓
3. Stack / Queue
   ↓
4. Hash Table
   ↓
5. Heap
   ↓
6. Binary Search
   ↓
7. Binary Tree
   ↓
8. BST
   ↓
9. Trie
   ↓
10. Graph
   ↓
11. Union-Find
```

And learn a few **combination patterns**:

```text
HashMap + LinkedList
        ↓
      LRU


HashMap + Array
        ↓
 RandomizedSet


HashMap + Heap
        ↓
    Top K


Two Heaps
        ↓
Running Median


Deque + Sliding Window
        ↓
Sliding Window Maximum


Trie + DFS
        ↓
Word Search II
```

---

## 13. If your goal is "really understand data structures"

I'd actually recommend these **12 design problems** as a mini curriculum:

|  # | Problem                          | What it teaches          |
| -: | -------------------------------- | ------------------------ |
|  1 | **Design Linked List**           | Pointer manipulation     |
|  2 | **Design HashMap**               | Hashing + collision      |
|  3 | **Min Stack**                    | Auxiliary data structure |
|  4 | **Queue using Stacks**           | Amortized analysis       |
|  5 | **LRU Cache**                    | HashMap + DLL            |
|  6 | **Insert/Delete/GetRandom O(1)** | HashMap + Array          |
|  7 | **Min Heap / Priority Queue**    | Heap invariant           |
|  8 | **Median from Data Stream**      | Two heaps                |
|  9 | **Implement Trie**               | Tree-like indexing       |
| 10 | **Number of Islands**            | DFS/BFS                  |
| 11 | **Course Schedule**              | Topological sort         |
| 12 | **Redundant Connection**         | Union-Find               |

If you can **implement these from scratch and explain why each operation has its stated complexity**, you'll have a very solid data-structure foundation.

And I'd put **LRU Cache right around the middle**, because it teaches a particularly valuable interview lesson:

> **Don't ask "what data structure should I use?" Ask "what operations must be O(1), and what data structures can each requirement provide?"**

That's the mindset that turns these from LeetCode puzzles into actual data-structure design.

 