/**
https://labuladong.online/zh/algo/data-structure-basic/binary-tree-traverse-basic/
https://leetcode.com/problems/binary-tree-preorder-traversal/description/
https://leetcode.com/problems/binary-tree-inorder-traversal/description/
https://leetcode.com/problems/binary-tree-postorder-traversal/description/

https://leetcode.com/problems/binary-tree-level-order-traversal/description/
*/

#include <iostream>
#include <vector>
#include "binary_trees/tree_utils.h"


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
在 traverse 函数中不同位置写代码，效果是可以不一样的。前中后序遍历的结果不同，原因是因为你把代码写在了不同位置，所以产生了不同的效果。
特别强调，三种位置的关键区别在于执行时机不同。

实际的算法题中不会简单的让你计算前中后序的遍历结果，而是需要你把正确的代码写到正确的位置，所以你必须准确理解三个位置的代码产生的不同效果，才能写出准确的代码。

A. The three fundamental DFS traversal orders for a binary tree.
preorder： Root → Left → Right
inorder ： Left → Root → Right
postorder： Left → Right → Root , Visit the node only after both children have been processed.

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
void preorder(TreeNode* root, vector<int> &v){
  if(root==nullptr){
    return ;
  }
  v.push_back(root->val);
  preorder(root->left, v);
  preorder(root->right, v);
}

vector<int> preorderTraversal(TreeNode* root){
  vector<int> v ={};
  if(root==nullptr){
    return v;
  }
  preorder(root, v);
  return v;
};


void inorder(TreeNode* root, vector<int> &v){
  if(root==nullptr){
    return;
  }
  // go to left subtree
  inorder(root->left,v);
  // handle the root node
  v.push_back(root->val);
  // go to right subtree
  inorder(root->right, v);
}

vector<int> inorderTraversal(TreeNode* root){
  vector<int> v={};
  if(root==nullptr){
    return v;
  }
  inorder(root,v);
  return v;

}

void postorder(TreeNode* root, vector<int> &v){
  if(root==nullptr){
    return;
  }
  // go to left subtree
  postorder(root->left,v);

  // go to right subtree
  postorder(root->right, v);

  // handle the root node
  v.push_back(root->val);
}



vector<int> postorderTraversal(TreeNode* root){
  vector<int> v={};
  if(root==nullptr){
    return v;
  }
  postorder(root,v);
  return v;
}





void printVector(vector<int> &v, string order){
    cout<<order <<" " << "tranversal:"<< endl;
    for(size_t i=0;i< v.size();i++){
      cout<< v[i]<<" ";
    }
    cout<<endl;
}

int main() {
  // vector<optional<int>> v = {1, nullopt, 2, 3};
  vector<optional<int>> v = {1,2,3,4,5,nullopt,8,nullopt,nullopt,6,7,9};
  TreeNode *root = createTree(v);
  vector<int> preorder_output = preorderTraversal(root);
  printVector(preorder_output, "preorder");

  vector<int> inorder_output = inorderTraversal(root);
  printVector(inorder_output, "inorder");

  vector<int> postorder_output = postorderTraversal(root);
  printVector(postorder_output, "postorder");
  



}