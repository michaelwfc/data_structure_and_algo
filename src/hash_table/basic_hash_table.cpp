
#include <functional>
#include <vector>

using namespace std;

/**
哈希表的底层实现就是一个数组（我们不妨称之为 table）



Hash Table
   |
   +-- bucket 0 -> linked list
   |
   +-- bucket 1 -> linked list
   |
   +-- bucket 2 -> linked list
   |
   +-- bucket 3 -> linked list
   |
   ...

  hash(key)
    ↓
  bucket
    ↓
  linked list
    ↓
  search

1. 它先把这个 key 通过一个哈希函数（我们不妨称之为 hash）转化成数组里面的索引
   哈希函数的设计 -> O(1)
2. 然后增删查改操作和数组基本相同：
   哈希冲突的处理
   A. seperate chain(拉链法)
   B. Linear probing


为什么 哈希表能做到增删查改 O(1) 复杂度?
SeparateChainingHashST:  O(hash)+O(链表搜索)
如果链表长度为 K，那么一次操作就是： O(1)+O(K)=O(K)


Load Factor（负载因子）= n/m
  int n; // num of key-value pairs
  int m; // hash table size,bucket / chain 的数量

如果保持 n/m 固定（如果n 增加就扩容m 来保持固定），
通过 resize，让每条链的平均长度保持在一个常数范围内。 平均链长度是 K 为固定的，
因此 O(hash) + O(K) = O(1) + O(1) =O(1)


所以以后看到：Hash Table insertion/search/deletion: O(1)

你应该自动在脑子里补充： Average / expected O(1), assuming a good hash function and controlled load factor.
最坏情况下确实可以退化成 O(N)

*/
template <typename K, typename V>
class SeperateChainHashMap {

private:
  int INIT_CAPACITY =4;
  int n; // num of key-value pairs
  int m; // hash table size,bucket / chain 的数量
  // Load Factor（负载因子）= n/m

  vector<void *> table; // array of lined-list symbol tables


  // 哈希函数，把 key 转化成 table 中的合法索引
  // 时间复杂度必须是 O(1)，才能保证上述方法的复杂度都是 O(1)
  int hash(K key){
    int h = key.hashCode();
    // make sure it is non-negtive
    h = h & 0x7fffffff;
    // mold to the range of table index
    return h%table.size();
  }

public:



  void put(K key, V value) {
    int index = hash(key);
    table[index] = value;
  }

  auto get(K key){
    int index = hash(key);
    return table[index];
  }

  void remove(K key){
    int index = hash(key);

  }
};