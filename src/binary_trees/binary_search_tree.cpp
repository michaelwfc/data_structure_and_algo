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
  Node *left;  // BST with smaller keys
  Node *right; // BST with larger keys

  Node(K key, V value)
      : key(key), value(value), left(nullptr), right(nullptr) {}

  //   V get_value() { return this->value; };

  //   K get_key() { return this->key; }

  //   Node *get_left() { return this->left; }

  //   Node *get_right() { return this->right; }

  //   void set_value(V new_value) { this->value = new_value; };

  //   void set_left(Node *n) { this->left = n; }

  //   void set_right(Node *n) { this->right = n; }
};

template <typename K, typename V> class BST {
private:
  Node<K, V> *root;

public:
  // root can be empty
  BST() : root(nullptr){};

  // binary search
  optional<V> get(const K &key) {
    if (root == nullptr) {
      return nullopt;
    }

    Node<K, V> *current_node = root;
    while (current_node != nullptr) {
      K current_key = current_node->key;
      if (current_key == key) {
        return current_node->value;
      } else if (current_key < key) {
        current_node = current_node->right;
      } else {
        current_node = current_node->left;
      }
    }
    return nullopt;
  };

  /**
Associate value with key
search for key, then two cases:
- key in tree     -> reset value
- key not in tree -> add new node

*/
  void put(K key, V value) {
    if (root == nullptr) {
      root = new Node<K, V>(key, value);
      return;
    }

    Node<K, V> *current_node = root;
    Node<K, V> *last_node = root;
    size_t left_or_right;
    while (current_node != nullptr) {
      K current_key = current_node->key;
      if (current_key == key) {
        // case1: reset value
        current_node->value = value;
        return;
      } else if (current_key < key) {
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

  //   void delete(K key){}; // delete is C++ keyword
  void remove(K key) {};

  //   Iteratable<K> iteretor() {}
  // Instead, C++ uses the iterator protocol.
  // iterator begin();
  // iterator end();
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
  vector<pair<int, string>> data = {{5, "five"}, {3, "three"}, {8, "eight"},
                                    {1, "one"},  {4, "four"},  {6, "six"},
                                    {9, "nine"}};

  BST<int, string> bst = create_bst(data);

  int key = 10;
  optional<string> value = bst.get(key);
  if (value.has_value()) {
    cout << "when key = " << key << " , value = " << *value << endl;
  } else {
    cout << "when key = " << key << " , key not found" << endl;
  }
}
