#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/7/11 22:16 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/7/11 22:16   wangfc      1.0         None
"""
import math
from collections import OrderedDict
from typing import List


def minPathSum(grid:List[List[int]]):
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

    def get_last_node(current_node,row_num=row_num,column_num=column_num):
        last_nodes = []
        x,y = current_node
        # 左边节点
        if -1<x-1<column_num:
            last_nodes.append((x-1,y))
        if -1<y-1<row_num:
            last_nodes.append((x,y-1))
        return last_nodes

    # 记录当前节点的上一个节点，该节点是前一个状态节点中的 最短距离
    shortest_path_dict = OrderedDict()
    # 初始化
    start_node = (0,0)
    # shortest_path_dict.update(dict(START=start_node))

    def shortestPath(current_node):
        # 初始的节点 (0,0) 最短距离
        if current_node == (0,0):
            return grid[start_node[0]][start_node[1]]
        else:
            # 先找到当前节点的 前一个状态的所有位置的最短路径，
            last_nodes = get_last_node(current_node =current_node)
            current_shortest_path = math.inf
            # 记录当前节点的上一个节点，该节点是前一个状态节点中的 最短距离
            current_node2last_node = {current_node:None}

            for last_node in last_nodes:
                candidate = shortestPath(last_node) + grid[current_node[0]][current_node[1]]
                # 前一个状态节点中的 最短距离 + 边的距离最小的距离
                if candidate < current_shortest_path:
                    # # 更新当前节点对应的 最短距离
                    current_shortest_path = candidate
                    # 更新当前节点对应的 最短距离的 上一个节点
                    current_node2last_node.update({current_node:last_node})
            # 记录当前节点对应的 最短距离的 上一个节点
            shortest_path_dict.update(current_node2last_node)


            return current_shortest_path

    end_node = (row_num-1,column_num-1)
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

    grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]
    grid = [[1, 2, 3], [4, 5, 6]]
    minPathSum(grid=grid)