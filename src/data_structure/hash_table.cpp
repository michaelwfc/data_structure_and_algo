
#include <functional>
#include <vector>

using namespace std;

template <typename K, typename V>
class MyHashMap {

private:
  vector<void *> table;
  // 哈希函数，把 key 转化成 table 中的合法索引
  // 时间复杂度必须是 O(1)，才能保证上述方法的复杂度都是 O(1)
  int hash(K key){
    int h = key.hashCode();
    // make sure it is non-negtive
    h = h & 0x7fffffff
    // mold to the range of table index
    return h%table.length;
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