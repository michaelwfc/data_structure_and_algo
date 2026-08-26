#include <cstddef>
#include <optional>
#include <queue>
#include <vector>
#include "binary_trees/tree_utils.h"


using namespace std;


/**
There are two different ways we might serialize/deserialize this tree into an array.
serialize:  tree -> array
deserialize: array -> tree

Heap/complete-array representation uses the index to determine the parent-child
relationship. 
Heap representation asks "Where does index i live in the tree?"
Queue-based level-order asks "Which node should receive the next value?"

Level-order + queue representation uses the null values and a
queue of actual nodes to determine the relationship.


Method A — heap-style array
We reserve an array position for every possible tree position.
For the heap-style representation:

index 0 → root

index 1 → root.left
index 2 → root.right

index 3 → root.left.left
index 4 → root.left.right

index 5 → root.right.left
index 6 → root.right.right

The index itself tells you where the node belongs.

int left_index = 2 * i + 1;
int right_index = 2 * i + 2;
The array position has a fixed structural meaning.
The array is a map of positions:
array index
     ↓
tree position


That's why your original recursive code can work for this representation.

* convert a array to a tree
root_node: i=2^0
1st layer: i=2^1
2nd layer: i=2^2

parent index: 2^j+i
       left:  2^(j+1) +2i   right: 2^(j+1) +2i+1

it is a heap/complete-array representation and is a different serialization
scheme. which is actually not the same tree if you treat the array as a
heap-style array.

index:  0   1      2      3
value:  1  null    2      3

         1
        / \
     null  2
          /
         3
*/

TreeNode *createSubTree(TreeNode *root_node, const vector<optional<int>> &v,
                        int i) {

  // left node 2i, right node 2i+1
  if (root_node == nullptr) {
    return nullptr;
  }
  size_t left_index = 2 * i;
  size_t right_index = 2 * i + 1;
  if (left_index < v.size() && v[left_index].has_value()) {
    TreeNode *left = new TreeNode(*v[left_index]);
    root_node->left = left;
    createSubTree(left, v, left_index);
  }

  if (right_index < v.size() && v[right_index].has_value()) {
    TreeNode *right = new TreeNode(*v[right_index]);
    root_node->right = right;
    createSubTree(right, v, right_index);
  }

  return root_node;
};

TreeNode *createTreeV1(const vector<optional<int>> &v) {
  if (v.empty() || !v[0].has_value()) {
    return nullptr;
  }
  TreeNode *root = new TreeNode(*v[0]);
  //  Convention A: root index = 1
  createSubTree(root, v, 1);
};


/**
Method B: Level-order construction using a queue.

What does "level-order" mean?
Level-order means: Visit the tree one level at a time, from left to right.

The vector position means:
the next value encountered during a breadth-first traversal.

Where does the queue come from?
This is the beautiful part.  When we process a node, we need to remember:
Which nodes still need their children assigned?
That's exactly what a queue is good at.

That's exactly breadth-first search (BFS).
And level-order traversal is essentially BFS
*/

TreeNode* createTree(const vector<optional<int>> &v) {
  if (v.empty() || !v[0]) {
    return nullptr;
  }
  TreeNode *root = new TreeNode(*v[0]);

  queue<TreeNode *> q;
  q.push(root);

  size_t i=1;
  while(!q.empty() && i< v.size()){
    TreeNode* current_node = q.front(); // get the front node from the queue
    q.pop(); // remove the front node from the queue

    // if the next index has value, add to left 
    if(i<v.size() && v[i].has_value()){
        TreeNode* left = new TreeNode(*v[i]);
        current_node->left = left;
        // add this node to queue
        q.push(left);
    }
    i++; //move to next index

    // if the next index has value, add to right
    if(i< v.size() && v[i].has_value()){
        TreeNode* right = new TreeNode(*v[i]);
        current_node->right = right;
        q.push(right);
    }
    i++;
    // move to next node in the queue
  }
  return root;
};