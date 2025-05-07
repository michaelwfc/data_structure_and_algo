#!/usr/bin/env python
# -*- coding: utf-8 -*-
import math
from typing import List, Union
from collections import OrderedDict


def route_count(i, j):
    """
    Problem Description
    小兔的叔叔从外面旅游回来给她带来了一个礼物，小兔高兴地跑回自己的房间，拆开一看是一个棋盘，小兔有所失望。
    不过没过几天发现了棋盘的好玩之处。从起点(0，0)走到终点(n,n)的最短路径数是C(2n,n),现在小兔又想如果不穿越对角线(但可接触对角线上的格点)，
    这样的路径数有多少?小兔想了很长时间都没想出来，现在想请你帮助小兔解决这个问题，对于你来说应该不难吧!
    """
    # 我们只考虑 上半边的情况
    # 第1行的点，因为只能从左边走过来只有1中方式，所以都等于0
    if i == 0:
        return 1
    if i == j:
        # 当处于对角线上的那个点，因为是最短路径，所有只能从上面的点
        return route_count(i - 1, j)
    else:
        #  当不处于对角线上的那个点，因为是最短路径，可以从上面的点 和 左边的点达到
        return route_count(i - 1, j) + route_count(i, j - 1)


def short_route_count(n):
    result = 2 * route_count(n, n)
    print("方格数量为 %d 的时候，最短路径有 %d 条" % (n, result))
    return result


def climbStairsV1(n: int):
    """
    70. 爬楼梯
    假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    注意：给定 n 是一个正整数。
    示例 1：

    输入： 2
    输出： 2
    解释： 有两种方法可以爬到楼顶。
    1.  1 阶 + 1 阶
    2.  2 阶
    示例 2：

    输入： 3
    输出： 3
    解释： 有三种方法可以爬到楼顶。
    1.  1 阶 + 1 阶 + 1 阶
    2.  1 阶 + 2 阶
    3.  2 阶 + 1 阶

    """
    if n == 0:
        return 1
    elif n == 1:
        return 1
    else:
        return climbStairsV1(n - 1) + climbStairsV1(n - 2)


def climbStairsV2(n: int):
    if n == 0:
        return 1
    elif n == 1:
        return 1
    else:
        # 到达第n个阶梯的方法 == 到达第 n-1再达到n的方法 + 到达第 n-2 个阶梯再达到n 方法
        a = 1
        b = 1
        for i in range(2, n + 1):
            # 记录前一项
            temp = b
            # 更新当前项 i
            b = a + b
            # 更新之前的相
            a = temp
        return b


def minPathSum(grid: List[List[int]]):
    """
    给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
    说明：每次只能向下或者向右移动一步。
    示例 1：

    输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
    输出：7
    解释：因为路径 1→3→1→1→1 的总和最小。
    示例 2：

    输入：grid = [[1,2,3],[4,5,6]]
    输出：12
     
    提示：
    m == grid.length
    n == grid[i].length
    1 <= m, n <= 200
    0 <= grid[i][j] <= 100

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/minimum-path-sum
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    :return:

    # 动态规划问题：
    # 找到终点的最短路径，可以先找到 前一个状态的所有位置的最短路径，

    使用递归的思想：

    shortestPath(node):
    初始的节点 (0,0) 最短距离
    if node = (0,0)
        return grid[0][0]
    else：
        先找到当前节点的 前一个状态的所有位置的最短路径，
        last_nodes = get_last_node(current_node =node)
        current_shortest_path = inf
        for last_node in last_nodes:
            使用递归找到上一个状态的 最短路径，加上边后，比较达到当前节点的最短距离
            candidate = shortestPath(last_node) + edge(last_node,current_node)
            # 如果找到前一个状态的所有节点 到当前距离的最短路径，那这个最短距离就是初始节点到当前节点的最短距离
            if candidate < current_shortest_path:
                current_shortest_path = candidate
        return current_shortest_path
    """
    row_num = grid.__len__()
    column_num = grid[0].__len__()

    def get_last_node(current_node, row_num=row_num, column_num=column_num):
        last_nodes = []
        x, y = current_node
        # 左边节点
        if -1 < x - 1 < column_num:
            last_nodes.append((x - 1, y))
        if -1 < y - 1 < row_num:
            last_nodes.append((x, y - 1))
        return last_nodes

    # 记录当前节点的上一个节点，该节点是前一个状态节点中的 最短距离
    shortest_path_dict = OrderedDict()
    # 初始化
    start_node = (0, 0)

    # shortest_path_dict.update(dict(START=start_node))

    def shortestPath(current_node) ->int:
        # 初始的节点 (0,0) 最短距离
        if current_node == (0, 0):
            return grid[start_node[0]][start_node[1]]
        else:
            # 先找到当前节点的 前一个状态的所有位置的最短路径，
            last_nodes = get_last_node(current_node=current_node)
            current_shortest_path = math.inf
            # 记录当前节点的上一个节点，该节点是前一个状态节点中的 最短距离
            current_node2last_node = {current_node: None}

            for last_node in last_nodes:
                candidate = shortestPath(last_node) + grid[current_node[0]][current_node[1]]
                # 前一个状态节点中的 最短距离 + 边的距离最小的距离
                if candidate < current_shortest_path:
                    # # 更新当前节点对应的 最短距离
                    current_shortest_path = candidate
                    # 更新当前节点对应的 最短距离的 上一个节点
                    current_node2last_node.update({current_node: last_node})
            # 记录当前节点对应的 最短距离的 上一个节点
            shortest_path_dict.update(current_node2last_node)

            return current_shortest_path

    end_node = (row_num - 1, column_num - 1)
    shortest_path = shortestPath(current_node=end_node)

    shortest_path_ls = []
    current_node = end_node
    shortest_path_ls.append(end_node)
    while current_node:
        last_node = shortest_path_dict.get(current_node)
        if last_node:
            shortest_path_ls.append(last_node)
        current_node = last_node
    print(f"shortest_path={shortest_path_ls[::-1]}")
    return shortest_path


if __name__ == "__main__":
    for n in [1, 3, 12]:
        result = short_route_count(n)

    n = 10
    result1 = climbStairsV1(n)
    result2 = climbStairsV2(n)
    print(f"n={n},result1={result1},result2={result2}")
    grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]
    grid = [[1, 2, 3], [4, 5, 6]]
    minPathSum(grid=grid)
