/**
Left-leaning red-black BSTs (Guibas-Sedgewick 1979 and Sedgewick 2007)
1. Represent 2–3 tree as a BST.
2. Use "internal" left-leaning links as "glue" for 3–nodes.

An equivalent definition
A BST such that:
・No node has two red links connected to it.
・Every path from root to null link has the same number of black links.
・In an LLRB tree, Red links lean left.

Key property. 1–1 correspondence between 2–3 tree and Left-leaning red-black
BSTs

Observation. Search is the same as for elementary BST (ignore color).
Remark. Most other ops (e.g., floor, iteration, selection) are also identical.

*/
#include "binary_trees/binary_search_tree.cpp"
#include <cassert>

template <typename K, typename V> class RBNode : Node<K, V> {
  bool color; // color of parent link
};

template <typename K, typename V> bool is_read(const RBNode<K, V> &n) {
  if (n == nullptr) {
    return false; // null links are black
  }
  return n->color;
};

template <typename K, typename V> 
class RedBlackTree : BST<K, V> {

    private:
    // Left rotation. Orient a (temporarily) right-leaning red link to lean left.
    // Invariants. Maintains symmetric order and perfect black balance.
    RBNode<K,V>* rotate_left(RBNode<K,V>* n){
        assert(is_red(n->right));
        RBNode<K,V>* x = n->right;
        n->right = x->left;
        x->left = n;
        x->color = n->color;
        n->color = true;
        return x;
    }

    // Right rotation. Orient a left-leaning red link to (temporarily) lean right.
    RBNode<K,V>* rotate_right(RBNode<K,V>* n){
        assert(is_red(n->left));
        RBNode<K,V>* x = n->left;
        n->left = x->right;
        x->right = n;
        x->color = n->color;
        n->color = true;
        return x;
    }

    //Color flip. Recolor to split a (temporary) 4-node.
    // split a temporary 4-node into smaller valid pieces while preserving the black-height of the tree
    // A red link represents two 2–3-tree nodes merged together. Two red children under one black node represent a temporary 4-node.

    void flip_colors(RBNode<K,V> n){
        assert (! is_red(n));
        assert(is_red(n->left));
        assert(is_red(n->right));

    }

    /**
    ・Right child red, left child black: rotate left.
    ・Left child, left-left grandchild red: rotate right.
    ・Both children red: flip colors.
    */
    RBNode<K,V>* put(RBNode<K,V> n, K key, V value){



    }
};
