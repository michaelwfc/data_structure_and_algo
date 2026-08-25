/**
https://labuladong.online/zh/algo/data-structure-basic/binary-tree-traverse-basic/
https://leetcode.com/problems/binary-tree-preorder-traversal/description/

*/
#include <optional>
#include <vector>

using namespace std;

class TreeNode {
public:
  int val;
  TreeNode *left;
  TreeNode *right;
  TreeNode() : val(0), left(nullptr), right(nullptr) {}
  TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
  TreeNode(int x, TreeNode *left, TreeNode *right)
      : val(x), left(left), right(right) {}
};

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

/**
* convert a array to a tree
root_node: i=2^0
1st layer: i=2^1
2nd layer: i=2^2

parent index: 2^j+i
       left:  2^(j+1) +2i   right: 2^(j+1) +2i+1

it is a heap/complete-array representation and is a different serialization scheme.
which is actually not the same tree if you treat the array as a heap-style array.

index:  0   1      2      3
value:  1  null    2      3

         1
        / \
     null  2
          /
         3
*/
TreeNode *createTreeV1(const vector<optional<int>> &v) {
  if (v.empty() || !v[0].has_value()) {
    return nullptr;
  }
  TreeNode *root = new TreeNode(*v[0]);
  //  Convention A: root index = 1
  createSubTree(root, v, 1);
};

class Tree {
private:
  TreeNode *root;

public:
  Tree(TreeNode *root) : root(root){};

  vector<TreeNode *> preorderTraversal() {};

  vector<TreeNode *>;
};


/**
the better construction algorithm is level-order construction using a queue.
*/



/**
递归遍历二叉树

单链表和数组的遍历可以是迭代的，也可以是递归的，二叉树这种结构无非就是二叉链表，它没办法简单改写成
for 循环的迭代形式， 所以我们遍历二叉树一般都使用递归形式。


traverse
函数的遍历顺序就是一直往左子节点走，直到遇到空指针不能再走了，才尝试往右子节点走一步；
然后再一直尝试往左子节点走，如此循环；如果左右子树都走完了，则返回上一层父节点。

理解前/中/后序遍历
递归遍历的顺序，即 traverse 函数访问节点的顺序确实是固定的。
在 traverse
函数中不同位置写代码，效果是可以不一样的。前中后序遍历的结果不同，原因是因为你把代码写在了不同位置，所以产生了不同的效果。

特别强调，三种位置的关键区别在于执行时机不同。

实际的算法题中不会简单的让你计算前中后序的遍历结果，而是需要你把正确的代码写到正确的位置，所以你必须准确理解三个位置的代码产生的不同效果，才能写出准确的代码。

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

/**
层序遍历（BFS）： levelOrderTraverse
上面讲的递归遍历是依赖函数堆栈递归遍历二叉树的，遍历顺序是从最左侧开始，一列一列地走到最右侧。
二叉树的层序遍历，顾名思义，就是一层一层地遍历二叉树：
层序遍历需要借助队列来实现，而且根据不同的需求，可以有三种不同的写法
*/
int main() {
  vector<optional<int>> v = {1, nullopt, 2, 3};
  TreeNode *root = createTree(v);
}