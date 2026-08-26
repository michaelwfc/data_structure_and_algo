/**
https://labuladong.online/zh/algo/data-structure-basic/binary-tree-traverse-basic/
https://leetcode.com/problems/binary-tree-preorder-traversal/description/
https://leetcode.com/problems/binary-tree-inorder-traversal/description/
https://leetcode.com/problems/binary-tree-postorder-traversal/description/

https://leetcode.com/problems/binary-tree-level-order-traversal/description/


### 满二叉树  Perfect Binary Tree

### 完全二叉树 Complete Binary Tree
完全二叉树是指，二叉树的每一层的节点都紧凑靠左排列，且除了最后一层，其他每层都必须是满的：


### 二叉搜索树（Binary Search Tree，简称 BST）
是一种很常见的二叉树，它的定义是：

对于树中的每个节点，其左子树的每个节点的值都要小于这个节点的值，右子树的每个节点的值都要大于这个节点的值。你可以简单记为「左小右大」。


### 高度平衡二叉树（Height-Balanced Binary Tree）
是一种特殊的二叉树，它的「每个节点」的左右子树的高度差不超过 1。

要注意是每个节点，而不仅仅是根节点。


### 自平衡二叉树（Self-Balanced Binary Tree）

二叉搜索树的操作效率取决于树高，树结构越平衡，树高就接近 logN，增删查改的效率就比较高。而普通二叉搜索树最关键的问题是它不会自动对树进行平衡，特殊的情况下会退化成链表，增删查改的时间复杂度退化为 O(N)。

如果我们可以在增删二叉树节点时对树的结构进行一些调整，那么就可以让树的高度始终是平衡的，这就是自平衡二叉树（Self-Balanced Binary Tree）。

自平衡的二叉树有很多种实现方式，最经典的就是 红黑树, 一种自平衡的二叉搜索树。

保持树的平衡性，最关键的就是「旋转」操作 

### 红黑树

红黑树是自平衡的二叉搜索树，它的树高在任何时候都能保持在 O(logN)（完美平衡），这样就能保证增删查改的时间复杂度都是 O(logN)。

*/

#include "binary_trees/tree_utils.h"
#include <cstddef>
#include <iostream>
#include <optional>
#include <queue>
#include <vector>

using namespace std;

/**
递归遍历二叉树

单链表和数组的遍历可以是迭代的，也可以是递归的，二叉树这种结构无非就是二叉链表，它没办法简单改写成
for 循环的迭代形式， 所以我们遍历二叉树一般都使用递归形式。


traverse
函数的遍历顺序就是一直往左子节点走，直到遇到空指针不能再走了，才尝试往右子节点走一步；
然后再一直尝试往左子节点走，如此循环；如果左右子树都走完了，则返回上一层父节点。

1. Visit the node itself
2. Traverse the left subtree
3. Traverse the right subtree


理解前/中/后序遍历
递归遍历的顺序，即 traverse 函数访问节点的顺序确实是固定的。
在 traverse
函数中不同位置写代码，效果是可以不一样的。前中后序遍历的结果不同，原因是因为你把代码写在了不同位置，所以产生了不同的效果。
特别强调，三种位置的关键区别在于执行时机不同。

实际的算法题中不会简单的让你计算前中后序的遍历结果，而是需要你把正确的代码写到正确的位置，所以你必须准确理解三个位置的代码产生的不同效果，才能写出准确的代码。

A. The three fundamental DFS traversal orders for a binary tree.
preorder： Root → Left → Right
inorder ： Left → Root → Right
postorder： Left → Right → Root , Visit the node only after both children have
been processed.

B. 层序遍历（BFS）： levelOrderTraverse
上面讲的递归遍历是依赖函数堆栈递归遍历二叉树的，遍历顺序是从最左侧开始，一列一列地走到最右侧。
二叉树的层序遍历，顾名思义，就是一层一层地遍历二叉树：
层序遍历需要借助队列来实现，而且根据不同的需求，可以有三种不同的写法
*/
void traverse(TreeNode *root) {
  if (root == nullptr) {
    return;
  }
  // 前序位置 preorder traversal ： 前序位置的代码会在进入节点时立即执行；
  traverse(root->left);
  // 中序位置 ： 中序位置的代码会在左子树遍历完成后，遍历右子树之前执行；
  traverse(root->right);
  // 后序位置： 后序位置的代码会在左右子树遍历完成后执行
}

// 1. Visit the node itself
// 2. Traverse the left subtree
// 3. Traverse the right subtree
void preorder(TreeNode *root, vector<int> &v) {
  if (root == nullptr) {
    return;
  }
  v.push_back(root->val);
  preorder(root->left, v);
  preorder(root->right, v);
}

vector<int> preorderTraversal(TreeNode *root) {
  vector<int> v = {};
  if (root == nullptr) {
    return v;
  }
  preorder(root, v);
  return v;
};

void inorder(TreeNode *root, vector<int> &v) {
  if (root == nullptr) {
    return;
  }
  // go to left subtree
  inorder(root->left, v);
  // handle the root node
  v.push_back(root->val);
  // go to right subtree
  inorder(root->right, v);
}

vector<int> inorderTraversal(TreeNode *root) {
  vector<int> v = {};
  if (root == nullptr) {
    return v;
  }
  inorder(root, v);
  return v;
}

void postorder(TreeNode *root, vector<int> &v) {
  if (root == nullptr) {
    return;
  }
  // go to left subtree
  postorder(root->left, v);

  // go to right subtree
  postorder(root->right, v);

  // handle the root node
  v.push_back(root->val);
}

vector<int> postorderTraversal(TreeNode *root) {
  vector<int> v = {};
  if (root == nullptr) {
    return v;
  }
  postorder(root, v);
  return v;
}

/**
depth=1;
q.push(root)

vector<int> v;
while(!q.empty()){
  n = q.front()
  q.pop()
  // visit current node
  v.push_back(n)

  if(n->left != nullptr){
    q.push(n->left)
    }

  if(n->right != nullptr){
    q.push(n->right)
  }

  // at the end of the depth
  depth++;
  v= {};
}
*/
vector<vector<int>> levelorderTraversal(TreeNode *root) {
  if (root == nullptr) {
    return {};
  }
  queue<TreeNode *> q;
  q.push(root);

  // 记录当前遍历到的层数（根节点视为第 1 层）
  size_t depth = 1;
  vector<vector<int>> output;
  vector<int> v;

  while (!q.empty()) {
    v = {};
    // get the size for each level
    size_t sz = q.size();
    // iterate all the node in the queue
    for (int i = 0; i < sz; i++) {
      TreeNode *n = q.front();
      q.pop();

      // visit current node
      v.push_back(n->val);

      if (n->left != nullptr) {
        q.push(n->left);
      }
      if (n->right != nullptr) {
        q.push(n->right);
      }
    }
    // update the depth and push each layer to output
    depth++;
    output.push_back(v);
  }
  return output;
}

void printVector(const vector<int> &v, const optional<string> order = nullopt) {
  if (order.has_value()) {
    // Think of optional<string> as a box,order = the box,  *order = the string
    // inside the box:
    cout << *order << " " << "traversal:" << endl;
  }
  for (size_t i = 0; i < v.size(); i++) {
    cout << v[i] << " ";
  }
  cout << endl;
};

void printVectorOfV(const vector<vector<int>> &v, const string order) {
  cout << order << " " << "traversal:" << endl;
  for (int i = 0; i < v.size(); i++) {
    cout << "depth " << i + 1 << " :";
    printVector(v[i]);
  }
};

int main() {
  // vector<optional<int>> v = {1, nullopt, 2, 3};
  vector<optional<int>> v = {1, 2,       3,       4, 5, nullopt,
                             8, nullopt, nullopt, 6, 7, 9};
  TreeNode *root = createTree(v);
  vector<int> preorder_output = preorderTraversal(root);
  printVector(preorder_output, "preorder");

  vector<int> inorder_output = inorderTraversal(root);
  printVector(inorder_output, "inorder");

  vector<int> postorder_output = postorderTraversal(root);
  printVector(postorder_output, "postorder");

  vector<vector<int>> levelorder_output =  levelorderTraversal(root);
  printVectorOfV(levelorder_output, "levelorder");
}
