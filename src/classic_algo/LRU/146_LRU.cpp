/**
* https://labuladong.online/zh/algo/data-structure/lru-cache/
* https://leetcode.com/problems/lru-cache/description/

146. LRU Cache
Medium

Design a data structure that follows the constraints of a Least Recently Used
(LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists, otherwise return
-1. void put(int key, int value) Update the value of the key if the key exists.
Otherwise, add the key-value pair to the cache. If the number of keys exceeds
the capacity from this operation, evict the least recently used key. The
functions get and put must each run in O(1) average time complexity.


Example 1:

Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4


Constraints:

1 <= capacity <= 3000
0 <= key <= 104
0 <= value <= 105
At most 2 * 105 calls will be made to get and put.
*/

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */

/**
LRUcache Idea:
You actively maintain the list according to usage.
You don't try to calculate which entry is LRU when eviction happens. Instead,
every get/put updates the ordering immediately.

deliberately define our lined list as:
HEAD → MRU → ... → LRU → TAIL
evict = remove(TAIL.prev) -> This is O(1).


use the idea as Linked hash map:
Hash map -> fast lookup      -> get:  O(1) lookup
Linked   -> ordered  -> then it can evict by least recently used strategy : O(1)
ordering/update

We maintain a linked list whose order represents recency, therefore the LRU is
always at a known position. Each Node/Entry is a double linked node with
(Key,value) pair

             LRU Cache
                 |
        +--------+--------+
        |                 |
        ↓                 ↓
   Hash Table       Doubly Linked List
        |                 |
   find Node           order Node
        |                 |
       O(1)             O(1)

How to implement:

Table: use sperated chain/linear probing collision method to implement hash map
Each Node/Entry in Table: (Key,value) pair

size = capacity
You don't want the hash table itself to have size = capacity.
The cache capacity is the maximum number of entries; the hash table should
normally have some extra slots if you're using linear probing.




Hash Table
    |
    +---- key 1 ──────→ Node(1, 100)
    |
    +---- key 2 ──────→ Node(2, 200)
    |
    +---- key 3 ──────→ Node(3, 300)

And those same Nodes form:

HEAD
 ↓
Node(3) ⇄ Node(1) ⇄ Node(2)
                            ↑
                           TAIL

So there aren't two copies of each entry.

There is one Node, referenced by both structures.

This is exactly the important idea behind LinkedHashMap.


How to put:
key -> hash -> index -> add the Node to hash table(spereate chain/linear probing
) Move the new node at MRU position


how to get(key):
get(key) -> hash(key) -> index -> linear probing -> get Node* : O(1)
Move node at MRU position

capacity = 3
HEAD
 ↓
A ⇄ B ⇄ C
         ↑
        LRU
get(B)
HEAD
 ↓
B ⇄ A ⇄ C
         ↑
        LRU


How to evict:
remove the first node, because it is the least recently used ?
but the first node is the least recently added one, it is not the least recently
used one(such as by get operation) The linked list must represent recency of
access, not merely insertion order.

Remove the node in the linked list -> remove the node in the hash table ?

*/
#include <cstddef>
#include <unordered_map>
#include <vector>

using namespace std;

#include "utils/list_utils.h"

template <typename K, typename V> struct Node {
  K key; // add Keyt used for LinkedHashMap/LRUCache
  V val;
  // Hash table using seperate chain, to point next node
  Node<K, V> *bucket_next; // creates an empty vector. table.size() == 0

  // Double Linked
  Node<K, V> *prev; // add previou link
  Node<K, V> *next;
  // this is the generic C++ way to value-initialize arbitrary template types.
  Node() : key(K{}), val(V{}), bucket_next(nullptr), prev(nullptr), next(nullptr) {}
  Node(K key, V value) : key(key), val(value), bucket_next(nullptr),prev(nullptr), next(nullptr) {}
  Node(K key, V value, Node *next) : key(key), bucket_next(nullptr), val(value), next(next) {}
};

template <typename K, typename V> class LRUCache {
private:
  int capacity;
  int size; // the node in the LRU cache
  int m;    // table size for hash

  vector<Node<K, V> *> table;

  Node<K, V> *head; // head->next pointer to MRU
  Node<K, V> *tail; // tail->prev pointer to LRU

public:
  LRUCache<K, V>(int capacity)
      //   constuctor with  an initializer list
      : capacity(capacity), // initialize the member capacity using the
                            // constructor parameter capacity.
        size(0), m(capacity * 2), table(m, nullptr),
        // table.resize(m); // allocate size=m memeory to table
        //   for (int i = 0; i < m; i++) {
        //   //   table[i] = new Node<K, V>(); // do not need this dummy node
        //   for
        //   //   bucket
        //   table[i] = nullptr; // means the bucket is empty.
        //   }
        head(new Node<K, V>()), tail(new Node<K, V>()) {

    cout << "capacity = " << this->capacity << endl;
    cout << "m = " << m << endl;
    cout << "table.size() = " << table.size() << endl;

    head->next = tail;
    tail->prev = head;
  }

  /**
  1. hash(key) -> index
  2. lookup at hash table
  3. move to MRU;
  */
  V get(int key) {
    int hash_value = hash(key);
    int index = hash_value % m;
    Node<K, V> *bucket_node = table[index];
    while (bucket_node != nullptr && bucket_node->key != key) {
      bucket_node = bucket_node->bucket_next;
    }
    if (bucket_node == nullptr) {
      return -1;
    } else {
      // move the node to MRU
      moveToFront(bucket_node);
      return bucket_node->val;
    }
  }

  /**
  1. hash(key) -> index
  2. add new Node to hash table
  3. add this node to front to the linked list
  */
  void put(int key, int value) {
    int hash_value = hash(key);
    int index = hash_value % m;

    Node<K, V> *node = new Node<K, V>(key, value);

    Node<K, V> *bucket_node = table[index];
    if (bucket_node == nullptr) {
      // Node* bucket_node = table[index]; creates a copy of the pointer:
      //    bucket_node = node;
      table[index] = node;
    } else {
      // find the current node in index(seperate chain)
      while (bucket_node->bucket_next != nullptr) {
        bucket_node = bucket_node->next;
      }
      // add the node to the hash table at index
      bucket_node->bucket_next = node;
    }

    size++;

    // add this node to MRU
    addToFront(node);
    if (size > capacity) {
      evict();
    }
  }

  int hash(K key) {
    // hast key to int:O(1)
    // use key(int) as hash function
    return key;
  }

  void addToFront(Node<K, V> *node) {
    node->next = head->next;
    node->prev = head;

    head->next->prev = node;
    head->next = node;
  }

  void moveToFront(Node<K, V> *node) {
    // head ->  MRU ->   -> ...  node ->
    node->next->prev = node->prev;
    node->prev->next = node->next;

    node->next = head->next;
    node->prev = head;

    head->next->prev = node;

    head->next = node;
  }

  Node<K, V> *evict() {
    // evict from the LRU postion: tail
    if (tail->prev == nullptr) {
      throw("evict a nullptr");
    }

    // move the node from Linked list
    Node<K, V> *node = tail->prev; // get the LRU node :O(1)
    node->next->prev = node->prev;
    node->prev->next = node->next;

    // move it from the hash table
    int index = hash(node->key) % m;
    Node<K, V> *buket_node = table[index];

    if (buket_node->key == node->key) {
      // remove the buket_node
      // buket_node = nullptr;
      table[index] = nullptr;
    } else {
      Node<K, V> *buket_pre = buket_node;
      buket_node = buket_node->next;
      while (buket_node != nullptr && buket_node->key != node->key) {
        buket_pre = buket_node;
        buket_node = buket_node->next;
      }

      if (buket_node != nullptr && buket_node->key == node->key) {
        // remove the buket_node in the hash table
        buket_pre->next = buket_node->next;
      } else {
        throw "node not found in the evict()";
      }
    }
    size--;
    return node;
  }
};



// =========================================================

template <typename K, typename V> class DoubleLinkedList {
private:
  Node<K, V> *head;
  Node<K, V> *tail;
  int size;

public:
  DoubleLinkedList<K, V>()
      : size(0), head(new Node<K, V>()), tail(new Node<K, V>()) {

    head->next = tail;
    tail->prev = head;
  };

  void addToFront(Node<K, V> *node) {
    node->next = head->next;
    node->prev = head;

    head->next->prev = node;
    head->next = node;
    size++;
  };
  Node<K, V> *removeAtLast() {
    //protect against removing from an empty list.
    if(tail->prev ==head){
      return nullptr;
    }

    // move the node from Linked list
    Node<K, V> *node = tail->prev; // get the LRU node :O(1)
    node->next->prev = node->prev;
    node->prev->next = node->next;
    size--;
    return node;
  };

  void moveToFront(Node<K, V> *node) {
    // head ->  MRU ->   -> ...  node ->
    node->next->prev = node->prev;
    node->prev->next = node->next;

    node->next = head->next;
    node->prev = head;

    head->next->prev = node;

    head->next = node;
  };
};

template <typename K, typename V> class LRUCache2 {
private:
  int capacity;
  int size;
  // The hash map tells you WHERE the node is. 
  // The doubly linked list tells you WHERE the node ranks in recency.
  unordered_map<int, Node<K, V> *> map; // unordered_map stores pair<const K, Node*>
  DoubleLinkedList<K, V> list; 

public:
  LRUCache2<K, V>(int capacity)
  :capacity(capacity),size(0),map(), list() // you don't need to explicitly initialize map and list; C++ will default-construct them:
  {};

  void put(K key, V value) {


    Node<K, V> *node = new Node<K, V>(key, value);
    // check whether the key exists:
    auto item = map.find(key);
    if(item!= map.end()){
      // existing key
      Node<K,V>* node = item->second;
      node->val = value;
      //move it to front
      list.moveToFront(node);
      return;

    }

    // add node to map
    // map.insert(key);
    map[key] = node;

    // add to double linked list
    list.addToFront(node);
    size++;
    if (size > capacity) {
      evict();
      
    }
  };

  V get(K key) {
    // check whether the key exists:
    auto item = map.find(key);
    if(item == map.end()){
      return -1;
    }

    // get the node from hash table
    // Node<K, V> *node = map.at(key);
    Node<K,V>* node = item->second;

    // move the node to front
    list.moveToFront(node);
    return node->val;
  };

  void evict() {
    // get the last node from link
    Node<K, V> *node = list.removeAtLast();

    // delete it in hash map
    map.erase(node->key);

    // delte the memory
    delete node;

    size --;

  }
};

int main() {
  // LRUCache<int, int> lRUCache(2);
  cout<<"Test LRUCache2"<< endl;
  LRUCache2<int, int> lRUCache(2);

  lRUCache.put(1, 1); // cache is {1=1}

  lRUCache.put(2, 2); // cache is {1=1, 2=2}

  // lRUCache.get(1);    // return 1
  assert(lRUCache.get(1) == 1);

  lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
  // lRUCache.get(2);    // returns -1 (not found)
  assert(lRUCache.get(2) == -1);

  lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
                      // lRUCache.get(1);    // return -1 (not found)
                      // lRUCache.get(3);    // return 3
                      // lRUCache.get(4);    // return 4
  assert(lRUCache.get(1) == -1);
  assert(lRUCache.get(3) == 3);
  assert(lRUCache.get(4) == 4);
}