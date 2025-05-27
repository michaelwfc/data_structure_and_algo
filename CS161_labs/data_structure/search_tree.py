#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: 
@time: 2021/1/23 9:34 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/23 9:34   wangfc      1.0         None


Sorted Array  (static)                  Balanced Search Tree  (dynamic)       Heap               Hash Table
search                       O(logn)            O(logn)
select                       O(1)               O(logn)
min                          O(1)               O(logn)                      O(logn)
max                          O(1)               O(logn)                      O(logn)
Predecessor (Successor)      O(logn)            O(logn)
OutputSorted                 O(n)               O(n)
Rank                         O(logn)            O(logn)

insert                       O(n)               O(logn)                       O(logn)
delete                       O(n)               O(logn)                       O(logn)

***
the TreeMap class in Java and the map class template in the C++ Standard Template Library are built on top of balanced search trees.
This assumes no two objects have the same key. To accommodate duplicate
keys, change the “smaller than” in the first condition to “smaller than or equal to.”


The Search Tree Property
1. For every object x, objects in x’s left subtree have keys smaller than  or equal to that of x.
2. For every object x, objects in x’s right subtree have keys larger than that of x.9


"""
from typing import List
from collections import defaultdict



class BinTreeNode(object):
    """
    BinTreeNode
    an object (with a key)
    three pointers: a parent pointer,
                    a left child pointer,
                    a right child pointer.
    """
    def __init__(self, key, data=None, left=None, right=None):
        self.key,self.data = key,data
        self.left, self.right = left, right

    def __repr__(self):
        return "Node(key={},data={})".format(self.key,self.data)

def isBalanced(root: BinTreeNode) -> bool:
    """
    剑指 Offer 55 - II. 平衡二叉树
    输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
    如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。

    """
    def recur(root):
        # 当没有子节点的时候，深度为0
        if not root: return 0
        left = recur(root.left)
        # 当有节点为非平衡的节点的时候，直接返回
        if left == -1: return -1
        right = recur(root.right)
        if right == -1: return -1
        return max(left, right) + 1 if abs(left - right) <= 1 else -1

    return recur(root) != -1


class BinTree(object):
    def __init__(self, nums:List[int]=None, node_list = List[dict]):
        if nums:
            sorted_nums = sorted(nums)
            self.root = self.build_from_list(nums=sorted_nums)
        elif node_list:
            self.root = self.build_from_node_list(node_list=node_list)
        else:
            self.root = None

    def build_from_list(self ,list):
        node_list = []
        for i in range(len(list)):
            if i == 0:
                root = BinTreeNode(key=list[i])
                node_list.append(root)
            else:
                # 根据 索引获取 父节点:
                # 深度=k,第s个节点的父节点： 深度为 k-1，第s//2个节点
                # 转换为 child_index = (2^0+2^1+ 2^k-2  -1)+1+s=  , parent_index = (2^0+2^1+ 2^k-3   -1) +1+ s//2
                # 所以  child_index-1 //2 = (2^0+2^1+ 2^k-2) +1+s//2 = parent_index
                current_node = BinTreeNode(key=list[i])
                node_list.append(current_node)
                parent_index =  (i-1)//2
                parent_node = node_list[parent_index]
                if i%2 ==1:
                    # 左节点的index 都是奇数
                    parent_node.left = current_node
                    print("current_node={},parent_node={},is_left".format(current_node,parent_node))
                else:
                    # 右节点的index 都是偶数
                    parent_node.right = current_node
                    print("current_node={},parent_node={},is_right".format(current_node,parent_node))
        return root


    def build_from_node_list(self,node_list = List[dict]):
        """通过节点信息构造二叉树
        第一次遍历我们构造 node 节点
        第二次遍历我们给 root 和 孩子赋值

        :param node_list: {'data': 'A', 'left': None, 'right': None, 'is_root': False}
        """
        node_dict = {}
        for node_data in node_list:
            data = node_data['data']
            node_dict[data] = BinTreeNode(key = data)
        for node_data in node_list:
            data = node_data['data']
            node = node_dict[data]
            if node_data['is_root']:
                root = node
            node.left = node_dict.get(node_data['left'])
            node.right = node_dict.get(node_data['right'])
        return root


    def preorder_trav(self, subtree):
        """ 前序遍历:
        """

        if subtree is not None:
            print(subtree.data)    # 递归函数里先处理根
            self.preorder_trav(subtree.left)   # 递归处理左子树
            self.preorder_trav(subtree.right)    # 递归处理右子树

    def inorder_trav(self, subtree):
        """ 中序遍历: 左子树->根节点->右子树
        """

        if subtree is not None:
            self.inorder_trav(subtree.left)   # 递归处理左子树
            print(subtree.key)  # 递归函数里先处理根
            self.inorder_trav(subtree.right)    # 递归处理右子树

    def postorder_trav(self, subtree):
        """ 后序遍历: 左子树->右子树->根节点
        """

        if subtree is not None:
            self.postorder_trav(subtree.left)   # 递归处理左子树
            self.postorder_trav(subtree.right)    # 递归处理右子树
            print(subtree.key)  # 递归函数里先处理根

    @classmethod
    def preorderTraversal(cls,root:BinTreeNode):
        """ 前序遍历:
        首先我们需要了解什么是二叉树的前序遍历：
        按照访问根节点——左子树——右子树的方式遍历这棵树，而在访问左子树或者右子树的时候，我们按照同样的方式遍历，直到遍历完整棵树。
        因此整个遍历过程天然具有递归的性质，我们可以直接用递归函数来模拟这一过程。
        作者：LeetCode-Solution
        链接：https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/er-cha-shu-de-qian-xu-bian-li-by-leetcode-solution/
        来源：力扣（LeetCode）
        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

        :param subtree:
        """
        result = []
        def preorder(root:BinTreeNode):
            # 如果根节点为 None，返回为 None
            if root is None:
                return
            # 如果根节点不为空，则放入遍历的序列
            result.append(root.key)
            # 继续遍历根节点的左子树
            preorder(root.left)
            # 遍历完根节点的左子树后，遍历右子树
            preorder(root.right)
        preorder(root)
        return result

    def preorderTraversal_by_stack(self,root:BinTreeNode):
        result = []
        if not root:
            return result

        # 创建一个 stack 用于存储所有的 左节点
        stack = []
        # 使用 root 初始化 node
        node = root
        while stack or node:
            while node:
                # 将根节点加入遍历结果
                result.append(node)
                # 在 stack 中加入 左节点，用于返回的时候根据它去搜索右节点
                stack.append(node.left)
                # 设置 当前节点为 左节点，进行迭代,直到左节点为 None
                node = node.left
            # 当左节点为 None 的时候，从栈中取出左节点，开始其右节点的搜索
            node = stack.pop()
            node = node.right

    def inorderTraversal(self):
        """
        使用递归思想做 中序遍历
        :param root:
        :return:
        """
        root = self.root
        result = []
        if root is None:
            return result

        def inorder(root:BinTreeNode):
            if root is None:
                return
            inorder(root.left)
            result.append(root)
            inorder(root.right)
        inorder(root)
        return result

    def inorderTraversal_by_stack(self,root:BinTreeNode):
        """
        使用栈的思想来“显示”的中序遍历数
        :param root:
        :return:
        """
        result= []
        stack = []

        node = root
        while stack or node:
            # 1）更新栈的部分
            while node:
                # 将节点加入栈中
                stack.append(node)
                # 设置当前节点 为 左节点，直到 左节点为空的时候停止迭代
                node = node.left
            # 2）取出栈的部分：
            # 取出 stack中最后一个加入的元素
            node = stack.pop()
            # 将其加入到比遍历的结果
            result.append(node.data)
            # 设置当前节点为 其右节点重新开始迭代
            node = node.right
        return result

    def postorderTraversal(self,root:BinTreeNode):
        """
        首先我们需要了解什么是二叉树的后序遍历：
        按照访问左子树——右子树——根节点的方式遍历这棵树，而在访问左子树或者右子树的时候，我们按照同样的方式遍历，直到遍历完整棵树。
        因此整个遍历过程天然具有递归的性质，我们可以直接用递归函数来模拟这一过程。

        作者：LeetCode-Solution
        链接：https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/er-cha-shu-de-hou-xu-bian-li-by-leetcode-solution/
        来源：力扣（LeetCode）
        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        :param root:
        :return:
        """
        result = []
        if root is None:
            return []

        def postorder(root:BinTreeNode):
            if root is None:
                return
            postorder(root.left)
            postorder(root.right)
            result.append(root.data)
        postorder(root)
        return result




    def findLowestCommonAncestorV1(self, p: BinTreeNode, q: BinTreeNode, ):
        root = self.root
        # 左节点含有 p or q
        def dfsV1(root: BinTreeNode, p: BinTreeNode, q: BinTreeNode):
            """
            递归设计：
            定义函数 f_x: 表示 root 节点的左右子节点是否包含 p 或者 q
            递归为子问题： 左 or 右 子节点 是否满足该条件
            判断 root是否满足最近公告根节点的条件 如果满足，则返回该节点

            (使用 后序遍历： 左子节点 -> 右子节点 -> 根节点)

            停止条件：

            """
            # 左节点含有 p or q
            left_node = self.dfs(self, root.left, p, q)
            # 右节点 含有 p or q
            right_node = self.dfs(self, root.right, p, q)

            if (left_node and right_node) or (
                    (root.data == p.data or root.data == q.data) and (left_node or right_node)):
                return root.data

        node = dfsV1(root, p, q)
        return node



    def findLowestCommonAncestorV2(self, p: BinTreeNode, q: BinTreeNode, ):
        root = self.root
        # 建立 一个 p的负节点的 dict
        parent_node_dict = {}

        def dfsV2(root, father_node_dict):
            if root.left is not None:
                father_node_dict.update({root.left.data: root})
                dfsV2(root.left, father_node_dict)
            if root.right is not None:
                father_node_dict.update({root.right.data: root})
                dfsV2(root.right, father_node_dict)

        dfsV2(root, parent_node_dict)

        # 建立 一个访问过p节点的所有父节点
        visited_node = set()
        while (p is not None):
            visited_node.add(p.data)
            parent = parent_node_dict.get(p.data)
            p = parent
        # 依次返回 q节点的父节点
        while (q is not None):
            if q.data in visited_node:
                return q
            else:
                parent = parent_node_dict.get(q.data)
                q = parent




class BinarySearchTree(BinTree):
    """
    二叉搜索树：
    二叉搜索树（Binary Search Tree）是指一棵空树或具有如下性质的二叉树：

    若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值
    若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值
    任意节点的左、右子树也分别为二叉搜索树
    没有键值相等的节点
    基于以上性质，我们可以得出一个二叉搜索树的特性：二叉搜索树的中序遍历结果为递增序列。

    作者：jalan
    链接：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/solution/tu-jie-er-cha-sou-suo-shu-gou-zao-di-gui-python-go/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    """
    def __init__(self,nums:List[int]=None,node_list:List[dict]=None):
        if nums:
            sorted_nums = sorted(nums)
            self.root = self.sortedArrayToBST(nums=sorted_nums)
        elif node_list:
            self.root = self.build_from_node_list(node_list=node_list)
        else:
            self.root = None


    # @classmethod
    def sortedArrayToBST(self,nums:List[int]) -> BinTreeNode:
        # 递归调用的停止条件： 当 nums 为 空的时候
        if nums.__len__()==0:
            return None
        # 首先将中位值作为 root 节点
        median_index = nums.__len__()//2
        median = nums[median_index]
        node = BinTreeNode(key=median)
        left = nums[0:median_index]
        right = nums[median_index+1:]
        # 对左边部分作为左子树,不断进行递归
        node.left = self.sortedArrayToBST(left)
        # 对右边部分作为右子树,不断进行递归
        node.right = self.sortedArrayToBST(right)
        return node

    def search(self,key:int)->BinTreeNode:
        """使用二分搜索进行查询"""
        root =self.root
        while root:
            if root.key ==key:
                return root
            elif root.key >key:
                root = root.left
            else:
                root = root.right
        return None


    def min(self)->BinTreeNode:
        """
        二叉搜索树的最小值
        :return:
        """
        root = self.root
        while root.left:
            root = root.left
        return root

    def max(self)->BinTreeNode:
        """
        二叉搜索树的最大值
        :return:
        """
        root =self.root
        while root.right:
            root = root.right
        return root

    def predecessor(self,key:int)->BinTreeNode:
        """
        Predecessor: given a pointer to an object in the data
        structure, return a pointer to the object with the nextsmallest
        key. (If the object has the minimum key, report
        “none.”)
        :param key:
        :return:
        """
        # 找到目标key所在的节点
        object_node = self.search(key)
        left_subtree = object_node.left
        #如果存在 左子树 ,则左子树的最大值
        if left_subtree:
            return self.max(left_subtree)
        else:
            return None


    def successor(self,key:int)->BinTreeNode:
        object_node = self.search(key)
        right_subtree = object_node.right
        if right_subtree:
            return self.min(right_subtree)
        else:
            return None


    def insert(self,key:int):
        if not self.root:
            self.root = BinTreeNode(key=key)
        else:
            parent_node = None
            root = self.root
            if_ignore =False

            while root:
                is_right = False
                is_left =False
                if key == root.key:
                    print("key={}重复,ignore".format(key))
                    if_ignore =True
                    break
                if key< root.key:
                    parent_node = root
                    is_left =True
                    root = root.left
                elif key>root.key:
                    parent_node = root
                    is_right =True
                    root = root.right
            if if_ignore:
                pass
            elif is_left:
                parent_node.left = BinTreeNode(key=key)
            elif is_right:
                parent_node.right = BinTreeNode(key=key)


    def delete(self,key:int):
        pass

    def select(self,index:int):
        """
        Select: given a number i, between 1 and the number of
        objects, return a pointer to the object in the data structure
        with the ith-smallest key.
        :param index:
        :return:
        """
        pass

    def rank(self,key:int):
        pass








class TrieNode:
    def __init__(self):
        # 记录该节点的所有子节点： 字典的形式
        # defaultdict 接受一个工厂函数作为参数,factory_function 可以是list、set、str等等，
        # 作用是当key不存在时，返回的是工厂函数的默认值，比如list对应[ ]，str对应的是空字符串，set对应set( )，int对应
        self.children = defaultdict(TrieNode)
        # 标记该节点是否为一个词的结束位置
        self.word = False

class TrieTree():
    """
    208. 实现 Trie (前缀树)
    实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。

    示例:
    Trie trie = new Trie();

    trie.insert("apple");
    trie.search("apple");   // 返回 true
    trie.search("app");     // 返回 false
    trie.startsWith("app"); // 返回 true
    trie.insert("app");
    trie.search("app");     // 返回 true
    说明:

    你可以假设所有的输入都是由小写字母 a-z 构成的。
    保证所有输入均为非空字符串

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/implement-trie-prefix-tree
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    """

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.root = TrieNode()


    def insert(self, word: str) -> None:
        """
        Inserts a word into the trie.
        """
        # 获取根节点
        current_node = self.root
        # 遍历所有的字符
        for w in word:
            # 判断当前节点的所有children 中是否有 w 的key， 必须使用 [] 来取值
            current_node = current_node.children[w]
            # 如果存在该 key,则获取对应的node，继续向下迭代
            # 如果不存在该 key，则会自动生成 该 key ---> node,因为是 defaultdict(factory_function) 类型，然后继续向下迭代，

        # 迭代完成的时候，标记该节点为一个词
        current_node.word = True


    def search(self, word: str) -> bool:
        """
        Returns if the word is in the trie.
        """
        # 获取 root 节点为当前节点
        current_node = self.root
        # 对word 的每个字符做迭代判断
        # 如果直到迭代完成的时候的那个节点的word为true,则说明该 word 在这个 tire 树中
        for w in word:
            # 如果 w 不在 当前节点的 children当中
            if w not in current_node.children:
                return False
        # 判断最后的节点的word
        # 迭代完成的时候，标记该节点为一个词
        if current_node.word == True:
            return True
        else:
            return False


    def startsWith(self, prefix: str) -> bool:
        """
        Returns if there is any word in the trie that starts with the given prefix.
        """
        current_node = self.root
        for w in prefix:
           if w not in current_node.children:
                return False
        return True


if __name__ == '__main__':
    node_list = [
        {'data': 'A', 'left': 'B', 'right': 'C', 'is_root': True},
        {'data': 'B', 'left': None, 'right': None, 'is_root': False},
        # {'data': 'B', 'left': 'D', 'right': 'E', 'is_root': False},
        # {'data': 'D', 'left': None, 'right': None, 'is_root': False},
        # {'data': 'E', 'left': 'H', 'right': None, 'is_root': False},
        # {'data': 'H', 'left': None, 'right': None, 'is_root': False},
        {'data': 'C', 'left': 'F', 'right': 'G', 'is_root': False},
        {'data': 'F', 'left': None, 'right': None, 'is_root': False},
        {'data': 'G', 'left': 'I', 'right': 'J', 'is_root': False},
        {'data': 'I', 'left': None, 'right': None, 'is_root': False},
        {'data': 'J', 'left': None, 'right': None, 'is_root': False},
    ]

    # btree = BinTree.build_from(node_list)
    # root = btree.root
    # result = isBalanced(root)
    # print(result)


    # node_list = [
    #     {'data': 'A', 'left': 'B', 'right': 'C', 'is_root': True},
    #     {'data': 'B', 'left': 'D', 'right': 'E', 'is_root': False},
    #     {'data': 'D', 'left': None, 'right': None, 'is_root': False},
    #     {'data': 'E', 'left': 'H', 'right': None, 'is_root': False},
    #     {'data': 'H', 'left': None, 'right': None, 'is_root': False},
    #     {'data': 'C', 'left': 'F', 'right': 'G', 'is_root': False},
    #     {'data': 'F', 'left': None, 'right': None, 'is_root': False},
    #     {'data': 'G', 'left': 'I', 'right': 'J', 'is_root': False},
    #     {'data': 'I', 'left': None, 'right': None, 'is_root': False},
    #     {'data': 'J', 'left': None, 'right': None, 'is_root': False},
    # ]


    # btree = BinTree.build_from_node_list(node_list)


    ls =[0,1, 2, 3, 4, 5, 6, 7, 8, 9]
    # btree = BinTree.build_from_list(list=ls)

    # btree.preorder_trav(btree.root)
    # 输出 A, B, D, E, H, C, F, G, I, J
    # btree.inorder_trav(btree.root)

    # result = btree.preorderTraversal(btree.root) # [0, 1, 3, 7, 8, 4, 9, 2, 5, 6]
    # result = btree.inorderTraversal(btree.root) # [7, 3, 8, 1, 9, 4, 0, 5, 2, 6]
    # result = btree.postorderTraversal(btree.root) # [7, 8, 3, 9, 4, 1, 5, 6, 2, 0]
    # result = btree.inorderTraversal_by_stack(btree.root)
    #
    node_list = [
        {'data': 3, 'left': 1, 'right': 5, 'is_root': True},
        {'data': 1, 'left': None, 'right': 2, 'is_root': False},
        {'data': 2, 'left': None, 'right': None, 'is_root': False},
        {'data': 5, 'left': 4, 'right': None, 'is_root': False},
        {'data': 4, 'left': None, 'right': None, 'is_root': False}
    ]
    binary_search_tree = BinarySearchTree(node_list=node_list)
    # binary_search_tree = BinarySearchTree(nums=ls)
    binary_search_tree.min()
    binary_search_tree.max()
    binary_search_tree.search(key=5)
    binary_search_tree.insert(key=6)
    binary_search_tree.inorderTraversal()

    # BinTree.inorderTraversal(BST_root_node)

    p = BinTreeNode(data=3)
    q = BinTreeNode(data=9)
    # result = btree.findLowestCommonAncestorV2(p=p,q=q)









    ####################################

    d = defaultdict(list)
    d["a"] =1
    # 当 "b" 不存在字典当中的时候，默认会生成一个 list： "b" ---> list
    print(d['b'])

    tire_node = TrieNode()
    tire_node.children['a'].__class__
    tire_node.word


    # Your Trie object will be instantiated and called as such:
    obj = TrieTree()
    word = "apple"
    obj.insert(word)
    obj.insert("angle")
    param_2 = obj.search(word)
    param_2 = obj.search("app")
    obj.search("angle")

    param_3 = obj.startsWith("app")
    obj.insert("app")
    param_4 = obj.startsWith("app")

