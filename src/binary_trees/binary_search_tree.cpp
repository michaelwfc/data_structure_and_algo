/**
# 二叉搜索树（Binary Search Tree，简称 BST）
Definition. A BST is a binary tree in symmetric order.

Symmetric order. Each node has a key,
and every node’s key is:
・Larger than all keys in its left subtree.
・Smaller than all keys in its right subtree.

对于树中的每个节点，其左子树的每个节点的值都要小于这个节点的值，右子树的每个节点的值都要大于这个节点的值。你可以简单记为「左小右大」。



BST representation in C++
A BST is a reference to a root Node.
A Node is comprised of four fields:
・A Key and a Value.
・A reference to the left and right subtree.

*/
#include <algorithm>
#include <iostream>
#include <optional>
#include <string>
#include <utility>
#include <vector>

// #include <iterator>

using namespace std;

template <typename K, typename V> class Node {

public:
  K key;
  V value;
  Node *left;   // BST with smaller keys
  Node *right;  // BST with larger keys
  size_t count; // subtree count

  Node(K key, V value)
      : key(key), value(value), left(nullptr), right(nullptr), count(1) {}

  //   V get_value() { return this->value; };

  //   K get_key() { return this->key; }

  //   Node *get_left() { return this->left; }

  //   Node *get_right() { return this->right; }

  //   void set_value(V new_value) { this->value = new_value; };

  //   void set_left(Node *n) { this->left = n; }

  //   void set_right(Node *n) { this->right = n; }
};

template <typename K, typename V> class BST {

public:
  // root can be empty
  BST() : root(nullptr){};

  // binary search
  optional<V> get(const K &key) {
    Node<K, V> *n = _get_node(key);
    if (n == nullptr) {
      return nullopt;
    }
    return n->value;
  };

  Node<K, V> *get_node(K &key) { return _get_node(key); }

  /**

  Associate value with key
search for key, then two cases:
- key in tree     -> reset value
- key not in tree -> add new node

Iterative method
*/
  void put1(K key, V value) {
    if (root == nullptr) {
      root = new Node<K, V>(key, value);
      return;
    }

    Node<K, V> *current_node = root;
    Node<K, V> *last_node = root;
    size_t left_or_right;
    while (current_node != nullptr) {
      K current_key = current_node->key;
      if (key == current_key) {
        // case1: reset value
        current_node->value = value;
        return;
      } else if (key > current_key) {
        last_node = current_node;
        current_node = current_node->right;
        left_or_right = 0;
      } else {
        last_node = current_node;
        current_node = current_node->left;
        left_or_right = 1;
      }
    }
    // case2： create_new node
    Node<K, V> *new_node = new Node<K, V>(key, value);
    if (left_or_right) {
      last_node->left = new_node;
    } else {
      last_node->right = new_node;
    }

    return;
  }

  // recursive BST insertion
  void put(K key, V value) { root = put(root, key, value); }

  size_t size() {
    // use recureive to get size, which can handle when node is nullptr
    size_t count = size(root);
    return count;
  }

  /**
     Floor. Largest key ≤ a given key.
     Ceiling. Smallest key ≥ a given key.
  */
  optional<K> floor(K key) {
    Node<K, V> *n = floor(root, key);
    if (n == nullptr) {
      return nullopt;
    } else {
      return n->key;
    }
  }

  optional<K> ceiling() {}

  //   void delete(K key){}; // delete is C++ keyword
  void remove(K key) {}

  //   Iteratable<K> iteretor() {} // Java
  // Instead, C++ uses the iterator protocol.
  // iterator begin();
  // iterator end();

private:
  Node<K, V> *root;

  // binary search
  Node<K, V> *_get_node(const K &key) {
    if (root == nullptr) {
      return nullptr;
    }

    Node<K, V> *current_node = root;
    while (current_node != nullptr) {
      K current_key = current_node->key;
      if (key == current_key) {
        return current_node;
      } else if (key > current_key) {
        current_node = current_node->right;
      } else {
        current_node = current_node->left;
      }
    }
    return current_node;
  };

  // Don't think: "This function inserts a node."
  // Think: Insert (key, value) into the subtree rooted at n, and return the
  // root of the resulting subtree. “I don't need to insert it myself. I know
  // which subtree it belongs to. I'll ask that subtree to insert it.”
  Node<K, V> *put(Node<K, V> *n, K key, V value) {
    // n represents a subtree

    if (n == nullptr) {
      // 1. Empty subtree: create a new node
      Node<K, V> *new_node = new Node<K, V>(key, value);
      new_node->count = 1;
      return new_node;
    }

    if (n->key == key) {
      // 2. Key already exists: update value
      n->value = value;
    } else if (key > n->key) {
      // 3. Key is larger: insert into right subtree
      // Down the tree → find the position.
      // Back up the tree → reconnect the subtree.
      n->right = put(n->right, key, value);
    } else {
      // 4. Key is smaller: insert into left subtree
      n->left = put(n->left, key, value);
    }
    // 5. Return root of this subtree
    // if (n->left != nullptr) {
    //   n->count += n->left->count;
    // }
    // if (n->right != nullptr) {
    //   n->count += n->right->count;
    // }
    n->count = 1+ size(n->left) + size(n->right);

    return n;
  }

  size_t size(Node<K, V> *n) {
    if (n == nullptr) {
      return 0;
    } else {
      return n->count;
    }
  }

  Node<K, V> *floor(Node<K, V> *n, K key) {
    if (n == nullptr) {
      return nullptr;
    }

    if (key == n->key) {
      return n;
    } else if (key < n->key) {
      // floor is largest key <=given key, so floor < n->key , floor is in the
      // left subtree
      return floor(n->left, key);
    }

    // on the right
    // find the floor  in the right subtree
    Node<K, V> *t = floor(n->right, key);
    if (t != nullptr) {
      return t;
    } else {
      return n;
    }
  }
};

// create a bst from a list of key, value pairs
template <typename K, typename V>
BST<K, V> create_bst(vector<pair<K, V>> &pairs) {
  BST<K, V> bst;
  for (auto &[key, value] : pairs) {
    bst.put(key, value);
  }
  return bst;
}

int main() {
  /*
                    5
                  /   \
                 3     8
                / \   / \
               1   4 6   9

  */
  vector<pair<int, string>> data = {{5, "five"}, {3, "three"}, {8, "eight"},
                                    {1, "one"},  {4, "four"},  {6, "six"},
                                    {9, "nine"}};

  BST<int, string> bst = create_bst(data);

  int key = 5;
  //   optional<string> value = bst.get(key);
  Node<int, string> *n = bst.get_node(key);

  //   if (value.has_value()) {
  //     cout << "when key = " << key << " , value = " << *value << endl;
  //   } else {
  //     cout << "when key = " << key << " , key not found" << endl;
  //   }

  if (n != nullptr) {
    cout << "when key = " << key << " , value = " << n->value << " , count = " << n->count << endl;
  } else {
    cout << "when key = " << key << " , key not found" << endl;
  }

  int key2 = 8;
  optional<int> floor_key = bst.floor(key2);
  if (floor_key.has_value()) {
    cout << "when key = " << key2 << " , floor_key = " << *floor_key << endl;
  } else {
    cout << "when key = " << key2 << " , floor_key not found" << endl;
  }
}
