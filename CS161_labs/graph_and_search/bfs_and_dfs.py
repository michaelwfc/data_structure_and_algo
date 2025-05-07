#!/usr/bin/env python
# -*- coding: utf-8 -*-
from typing import List

import random
import numpy as np
from collections import deque
from collections import defaultdict

def bfs(grid:List[List[int]],x:int,y:int):
    """
    Persedu code

    BFS(graph G, start vertex s)
    [all node initially unexplored]

    let Q =queue data structure (FIFO) ,initilized with s,mark s as explored
    while Q!= None:
        remove the first node of Q,call  it v
        for each edge (v,w):
            if w unexplored
                mark w as explored
                add w to Q (at the end)

    Application1: shortest Path
    Extra code:
    initial dist(v) = 0 if v==s else math.inf
    when

    Application 2: undirected connectivity

    all node unexplored
    [assume labeled 1 to n]
    for i = 1 to n
        if i not yet explored
            BFS(G,i)

    :param grid:
    :param r:
    :param c:
    :return:
    """
    # 生成一个队列
    queue = deque()
    queue.append((x,y))
    grid[x][y] =2

    while queue.__len__()>0:
        # 弹出最上面的元素
        (r,c) = queue.pop()
        # 搜索四个方向
        for (r,c) in [(r,c-1),(r,c+1),(r+1,c),(r-1,c)]:
            # 判断是岛屿并且未走过
            if inArea(r,c,grid) and grid[r][c]!=1:
                # 标记为已经走过
                grid[r][c] = 2
                queue.append((r,c))



def dfs(grid:List[List[int]],r:int,c:int):
    """
    DFS:
    explore aggressively only backtrack when necessary

    Stack Implementation:
    mimic BFS code, use a stack instead of a queue
    mark all node unexplored
    build a stack,initilized with s，mark s as explored
    while stack is not None:
        node v = stack.pop() #从栈尾取出元素进行迭代
        for edge (v,w):
            if w unexplored:
                mark node w explored
                stack.append(w)



    Recursive version:
    DFS(graph G,start vertex s)
    mark s as explored
    for every edge(s,v):
        if v unexplores:
            DFS(G,v)


    Application: Topological sort

    :param grid:
    :param r:
    :param c:
    :return:
    """

    # 判断 base case
    if not inArea(r,c,grid):
        return

    # 如果不是岛屿则直接返回
    if grid[r][c] !=1:
        return

    # 标记已经走过的地方
    grid[r][c] =2

    # 从这个地方向 4个方向 走
    dfs(grid,r,c-1)
    dfs(grid,r,c+1)
    dfs(grid,r+1,c)
    dfs(grid,r-1,c)


class Node(object):
    # 定义节点，属性为 x,y 坐标
    def __init__(self,x,y,count):
        self.x = x
        self.y = y
        self.count = count

    def __repr__(self):
        return "node({},{}),count={}".format(self.x,self.y,self.count)



def shortestPathInMaze(maze):
    """
    1.题目描述
    给定一个n*m的二维整数数组，用来表示一个迷宫，数组中只包含0或1，其中0表示可以走的路，1表示不可通过的墙壁。
    最初，有一个人位于左上角(1, 1)处，已知该人每次可以向上、下、左、右任意一个方向移动一个位置。
    请问，该人从左上角移动至右下角(n, m)处，至少需要移动多少次。
    数据保证(1, 1)处和(n, m)处的数字为0，且一定至少存在一条通路。
    输入格式： 第一行包含两个整数n和m。接下来n行，每行包含m个整数（0或1），表示完整的二维数组迷宫。
    输出格式：输出一个整数，表示从左上角移动至右下角的最少移动次数。
    数据范围：1≤n,m≤1001≤n,m≤100


    求最短路径常用算法：
    戴克斯特拉算法（Dijkstra algorithm）：该算法解决的是有向图中单个源点到其他顶点的最短路径问题。
    弗洛伊德算法（Floyd algorithm）：该算法解决的是有向带权图中两顶点之间最短路径的问题。
    A*搜索算法：这是一种在图平面上，有多个节点的路径，求出最低通过成本的算法。常用于游戏中的NPC的移动计算，或线上游戏的BOT的移动计算上。
    该算法像Dijkstra算法一样，可以找到一条最短路径；也像BFS一样，进行启发式的搜索。
    Bellman-ford算法 : 权重为负的时候
    SPFA算法：中国人发明的算法，该算法是求单源最短路径的一种算法，在Bellman-ford算法的基础上加上一个队列优化，减少了冗余的松弛操作。


    宽度优先搜索（BFS，Breadth-First Search）也是搜索的手段之一，
    从某个状态出发搜索所有可以到达的状态。
    搜索的顺序: 宽度优先搜索总是先搜索距离初始状态最近的状态。、
    按照开始状态→只需一次转移就能到达的所有状态→只需2次就可以到达的所有状态→…按照这样的顺序进行搜索。
    对于同一个状态，宽度优先搜索只经过一次，因此时间复杂度为O（状态数×转移的方式）。

    实现的数据结构： 宽度优先搜索则利用了队列进行计算。
    搜索时首先将状态添加进队列里，此后从队列的最前端不断取出状态，把从该状态可以转移到的状态尚未访问过的部分加入到队列中，
    如此往复，直至队列被取空或找到了问题的解。通过观察这个队列，我们就可以知道所有的状态都是按照距初始状态由近及远的顺序遍历的。


    深度优先搜索（DFS，Death-First Search）
    搜索的顺序:
    实现的数据结构： 深度优先搜索利用了栈进行计算，
    但是递归函数可以很简短地编写，而且状态的管理也更简单，所以大多数情况下都是用深搜实现。

    object node = (x,y),count
    initial stack = [0,0]
    while stack is not None:
        current_node = stack.pop()
        for move in moves:
            next_node  = move(current_node)
            if next_node is in area and is not explored:
                stack.insert(next_node)
                break


    """
    node_queue = deque()
    move_steps = [(-1, 0), (1, 0), (0, -1),(0, 1) ] #
    # 标记位置是否已经访问过
    visited_maze = np.zeros_like(maze)

    start_x =0
    start_y = 0
    start_count =0
    start_node = Node(x=start_x, y=start_y, count=start_count)
    # 添加 start_node 到 queue中
    node_queue.append(start_node)
    print("初始化node_queue={}".format(node_queue))
    # 标记 初始节点已经访问过
    visited_maze[start_x][start_y] =1
    target_x = maze.shape[0]-1
    target_y = maze.shape[1]-1

    while node_queue.__len__() != 0:

        # 弹出最右端的 node
        node = node_queue.pop()

        print("使用node={}进行搜索".format(node))
        # 当 节点到达目标位置的时候
        if node.x==target_x and node.y== target_y:
            return node.count

        # 对当前节点四个方向做搜索
        for step in move_steps:
            temp_x = node.x + step[0]
            temp_y = node.y + step[1]
            temp_count = node.count +1
            # 当下个节点满足：属于迷宫内 & 非墙 & 没有被访问过
            if (-1<temp_x<maze.shape[0]) and (-1<temp_y<maze.shape[1]) and (maze[temp_x][temp_y] !=1) and (visited_maze[temp_x][temp_y]==0):
                # 可以移动，添加到队列
                node_queue.append(Node(temp_x,temp_y,temp_count))
                # 标记该节点已经访问过
                visited_maze[temp_x][temp_y]=1
                print("更新node_queue={}".format(node_queue))
    return -1



def inArea(r:int,c:int,grid):
    row_num= grid.__len__()
    column_num = grid[0].__len__()
    if (-1<r<row_num) and (-1<c<column_num):
        return True
    else:
        return False
















def numIslands_dfs(grid:List[List[int]]):
    """
    200. 岛屿数量
    给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算*网格中岛屿的数量。

    岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

    此外，你可以假设该网格的四条边均被水包围。

    示例 1：

    输入：grid = [
      ["1","1","1","1","0"],
      ["1","1","0","1","0"],
      ["1","1","0","0","0"],
      ["0","0","0","0","0"]
    ]
    输出：1
    示例 2：

    输入：grid = [
      ["1","1","0","0","0"],
      ["1","1","0","0","0"],
      ["0","0","1","0","0"],
      ["0","0","0","1","1"]
    ]
    输出：3

    """
    row_num = grid.__len__()
    column_num = grid.__len__()



    island_num =0
    for r in range(row_num):
        for c in range(column_num):
            if grid[r][c]==1:
                island_num +=1
                dfs(grid,r,c)
    return island_num


def numIslands_bfs(grid:List[List[int]]):
    pass


def maxAreaOfIsland(grid:List[List[int]]):
    """
    例题 1：岛屿的最大面积
    LeetCode 695. Max Area of Island （Medium）
    给定一个包含了一些 0 和 1 的非空二维数组 grid，一个岛屿是一组相邻的 1（代表陆地），这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。
    你可以假设 grid 的四个边缘都被 0（代表海洋）包围着。
    找到给定的二维数组中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。

    作者：nettee
    链接：https://leetcode-cn.com/problems/number-of-islands/solution/dao-yu-lei-wen-ti-de-tong-yong-jie-fa-dfs-bian-li-/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    """
    row_num = grid.__len__()
    column_num = grid[0].__len__()
    if row_num ==0 or column_num==0:
        return 0

    def dfs(grid: List[List[int]], r: int, c: int):
        # 判断 base case
        if not inArea(r, c, grid):
            return 0

        # 如果不是岛屿则直接返回
        if grid[r][c] != 1:
            return 0

        # 标记已经走过的地方
        grid[r][c] = 2

        # 从这个地方向 4个方向 走
        area1= dfs(grid, r, c - 1)
        area2= dfs(grid, r, c + 1)
        area3 =dfs(grid, r + 1, c)
        area4 =dfs(grid, r - 1, c)
        return 1+area1+area2+area3+area4

    max_area_of_island = 0
    for r in range(row_num):
        for c in range(column_num):
            if grid[r][c]==1:
                area = dfs(grid,r,c)
                if area> max_area_of_island:
                    max_area_of_island=area
    return max_area_of_island


def findOrder(numCourses: int, prerequisites: List[List[int]]) -> List[int]:
    """
    210. 课程表 II
    现在你总共有 n 门课需要选，记为 0 到 n-1。
    在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
    给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
    可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
    示例 1:
    输入: 2, [[1,0]]
    输出: [0,1]
    解释: 总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
    示例 2:

    输入: 4, [[1,0],[2,0],[3,1],[3,2]]
    输出: [0,1,2,3] or [0,2,1,3]
    解释: 总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
         因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
    说明:

    输入的先决条件是由边缘列表表示的图形，而不是邻接矩阵。详情请参见图的表示法。
    你可以假定输入的先决条件中没有重复的边。
    提示:
    这个问题相当于查找一个循环是否存在于有向图中。如果存在循环，则不存在拓扑排序，因此不可能选取所有课程进行学习。
    通过 DFS 进行拓扑排序 - 一个关于Coursera的精彩视频教程（21分钟），介绍拓扑排序的基本概念。
    拓扑排序也可以通过 BFS 完成。

    :return:

    Persudo code: recursive version

    mark all node unexplored
    build a stack for topological sort
    for node s in G:
        # 标记 s 为 exploring
        DFS(G,v)

    DFS(G,v): recursive version
    base case:
    if none outgoing node:
        # 迭代终止的时候，标记该节点为 explored 并且加入 栈中
        return
    for edge (v,w):
        if w unexplored:
            DFS(G,w)
    # 直到该节点所有的 连接的节点都是 explored 的时候，将其加入 栈并标注为已经 explored



    DFS(G,v): stack version
    while stack is not None:
        node v = stack.pop() #从栈尾取出元素进行迭代
        for edge (v,w):
            if w unexplored:
                mark node w explored
                stack.append(w)
        # 当 本节点 v 的所有 outgoing node 都不存在的时候， 我们将其保存到 全局的 stack中

    """
    #节点使用 数字来表示
    verteies = np.arange(numCourses).tolist()

    # 从 prerequisites 建立有向图的所有的边
    edges = defaultdict(list)
    for tail,head in prerequisites:
        tails = edges[head]
        tails.append(tail)
        edges.update({head:tails})

    # # 用数组来模拟栈，下标 0 为栈底，n-1 为栈顶 ,初始化一个栈来存放已经 explored 的节点
    stack = []
    # 标记所有的 vertex 为三种状态： unexplored, exploring,explored
    # 标记每个节点的状态：0 = 未搜索，1 = 搜索中，2 = 已完成
    # 初始化所有的 vertex 为 unexplored=0 :
    vertex_state = [0]*numCourses

    # 判断有向图中是否有环
    valid = True

    def dfs(graph:dict, vertex:int):
        nonlocal valid

        # 标记该节点正在 exploring
        vertex_state[vertex] =1

        outgoing_verteies = graph[vertex]

        # 遍历所有的outgoing 的节点
        for outgoing_vertex in outgoing_verteies:
            # 如果存在 outgoing 的节点是 unexplored，继续迭代
            if vertex_state[outgoing_vertex] ==0:
                dfs(graph,outgoing_vertex)
                #判断
                if not valid:
                    return
            # 如果存在 outgoing 的节点是 exploring，则表示出现环
            elif vertex_state[outgoing_vertex] == 1:
                valid = False # 标记这个全局变量为 False
                return
        # 如果所有的 outgoing 的节点 都已经遍历过,标记该节点为 explored
        vertex_state[vertex] = 2
        stack.append(vertex)


    for vertex in verteies:
        if vertex_state[vertex]==0 and valid:
            dfs(graph=edges,vertex=vertex)

    if not valid:
        return list()
    # stack 中从栈顶到栈低的排序
    print(f"numCourses={numCourses},prerequisites={prerequisites}\n order={stack[::-1]}")
    return stack[::-1]







if __name__ ==  '__main__':
    SEED = 1234
    random.seed(SEED)
    np.random.seed(SEED)


    # # print(random_maze)
    # maze = np.array([[0, 1, 0, 1, 1],
    #                  [0, 0, 0, 0, 1],
    #                  [0, 0, 0, 0, 0],
    #                  [0, 1, 0, 0, 1],
    #                  [0, 0, 0, 0, 0]])
    # print("maze={}".format(maze))
    # count = bfs_search(maze=maze)
    # print("count={}".format(count))

    # grid = np.random.randint(low=0, high=2, size=[5, 5], dtype=int)
    # island_num1 = numIslands_dfs(grid)
    # print("grid={}\n island_num1={}".format(grid,island_num1))

    # max_area_of_island = maxAreaOfIsland(grid)
    # print("grid={}\n max_area_of_island={}".format(grid, max_area_of_island))

    numCourses= 4
    prerequisites = [[1, 0], [2, 0], [3, 1], [3, 2]]
    findOrder(numCourses=numCourses,prerequisites=prerequisites)
