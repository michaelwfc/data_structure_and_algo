/**
二叉堆（Binary Heap）是一棵满足特定顺序关系的完全二叉树（Complete Binary Tree）
这里有两个关键词：
1. 完全二叉树： 除最后一层外，每一层都是满的；最后一层的节点从左向右连续排列。
2. 满足堆序性质（Heap Order Property）
   Max Heap ： parent >= child

Heap 非常漂亮的地方： 逻辑上是一棵树，物理上是一个数组。
如果使用 0-based indexing：parent = i
那么：
left  = 2 * i + 1;
right = 2 * i + 2;

Heap 最核心的两个操作：swim 和 sink
swim ： 假设我们插入， 从底部向上恢复 Heap Property。
sink：  从顶部向下恢复 Heap Property。
*/