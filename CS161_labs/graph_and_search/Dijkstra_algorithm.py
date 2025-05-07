#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@time: 2021/1/17 10:20

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/17 10:20   wangfc      1.0         None

"""
import math
import heapq as hq
from collections import defaultdict

class Graph():
    def __init__(self,g:dict):
        # 使用字典表示一个有向图
        self.current2next_nodes_dict = defaultdict(dict)
        self.current2next_nodes_dict.update(g)

        # 找到所有的节点
        node_set = set()
        # 找到 current_node -> last_nodes 的映射
        current2last_nodes_dict = defaultdict(dict)
        for current_node,next_nodes_info in g.items():
            node_set.add(current_node)
            node_set = node_set.union(set(next_nodes_info.keys()))
            for next_node, edge in next_nodes_info.items():
                last_nodes_dict = current2last_nodes_dict[next_node]
                last_nodes_dict.update({current_node:edge})
                current2last_nodes_dict.update({next_node:last_nodes_dict})

        self.node_ls = sorted(node_set)
        self.current2last_nodes_dict = current2last_nodes_dict


    def findShortestPathV1(self,start_node:str,end_node:str):
        """
        每次取出未访问结点中距离最小的，用该结点更新 已经访问的节点， 直到所有的节点遍历完 或者 终点达到。
        """
        # 初始化所有节点 :起始节点的最短距离为 0  + 其他节点的最短距离 为 inf ：
        node2shortest_distance = dict()
        for node in self.node_ls:
            if node == start_node:
                node2shortest_distance.update({node: 0 })
            else:
                node2shortest_distance.update({node:math.inf})

        # 初始化所有节点为 unexpolored =0
        visited_nodes= dict()
        # 从 start_node 开始搜索 最近的邻近节点
        visited_nodes.update({start_node:0})
        # 记录访问过节点的前一个节点
        visited_node_last_node={start_node:'start'}

        # 初始化备选的 邻近节点
        neibor_nodes_set = set(self.current2next_nodes_dict[start_node].keys())

        candidate_visited_node = None
        # 循环遍历所有 X 之外的邻近节点， 直到 neibor_nodes_set 为空
        while (neibor_nodes_set is not None and neibor_nodes_set !=set()):
            # 计算所有 未访问节点 到 访问过的节点列表的 最短路径，默认为 inf
            shortest_distance = math.inf
            print(f"visited_nodes={visited_nodes}\nneibor_nodes_set={neibor_nodes_set}")
            # 对于每个备选的邻近节点：默认 该未访问节点 到  访问过的节点 为 inf
            neibor_node_shortest_distance = math.inf
            # 计算所有 未访问邻近节点 w 到 访问过节点集合的 最短路径，找到最小值，使用该节点更新 访问过的节点列表
            for neibor_node in neibor_nodes_set:
                # 找到该 未访问节点 的所有 上一个状态的访问过的节点
                last_nodes_set = set(self.current2last_nodes_dict[neibor_node].keys())
                last_visited_nodes_set = last_nodes_set.intersection(set(visited_nodes.keys()))
                # 计算该 未访问节点 w 到 访问过的节点列表 X 的距离, 找到最小距离
                for last_visited_node in last_visited_nodes_set:
                    # 访问过的节点的最短距离
                    last_visited_node_shortest_distance = visited_nodes.get(last_visited_node)
                    # 访问过的节点 -> 当前节点 的边
                    last_visited_node2current_node_edge =  self.current2next_nodes_dict.get(last_visited_node).get(neibor_node)
                    # 访问过节点 到 当前节点的 距离
                    last_visited_node2current_node_distance = last_visited_node_shortest_distance + last_visited_node2current_node_edge
                    # 更新 达到当前节点的最短距离
                    if last_visited_node2current_node_distance < neibor_node_shortest_distance:
                        neibor_node_shortest_distance = last_visited_node2current_node_distance
                        # 保留 w最小值的上个节点信息
                        neibor_candidate_last_visited_node = last_visited_node
                print(f"neibor_node={neibor_node},neibor_node_shortest_distance={neibor_node_shortest_distance}")

                # 比较所有的 neibor_node_shortest_distance,找到最小值
                if neibor_node_shortest_distance < shortest_distance:
                    shortest_distance = neibor_node_shortest_distance
                    candidate_visited_node = neibor_node
                    # w* 最小值的上个节点信息
                    candidate_last_visited_node= neibor_candidate_last_visited_node
                    # candidate_neibor_nodes_set =neibor_nodes_set
            print(f"candidate_visited_node={candidate_visited_node},shortest_distance={shortest_distance}")

            # 更新 visited_nodes
            visited_nodes.update({candidate_visited_node:shortest_distance})
            # 记录
            visited_node_last_node.update({candidate_visited_node:candidate_last_visited_node})
            # 更新备选的邻近节点：
            new_neibor_nodes_set = set(self.current2next_nodes_dict[candidate_visited_node].keys())
            neibor_nodes_set = neibor_nodes_set.union(new_neibor_nodes_set)
            # 去除已经访问过的节点
            neibor_nodes_set = neibor_nodes_set.difference(set(visited_nodes.keys()))

            # 更新 node2shortest_distance
            node2shortest_distance.update({candidate_visited_node:shortest_distance})

            if (candidate_visited_node == end_node):
                break
        print(f"The shortest distance from start_node:{start_node} to {end_node} is {node2shortest_distance.get(end_node)} ")

        shortest_path = []
        current_node = end_node
        while current_node !="start":
            shortest_path.append(current_node)
            last_node =visited_node_last_node.get(current_node)
            current_node=last_node
        shortest_path_str = "->".join(shortest_path[::-1])
        print(f"The shortest path:{shortest_path_str}")
        return node2shortest_distance.get(end_node)

    def findShortestPathWithHeap(self):
        """
        每次迭代的时候，都在计算 minimum computation on Dijkstra scores of the edges that cross the frontier 对应的节点
        因此，自然联想到使用 heap 来 计算 最小值

        while loop:
            for neibor_node in neibor_nodes:
                计算 邻近节点 及其对应的 minimum Dijkstra scores (作为 key)
                heap.addNode() 加入 该节点

            计算所有节点的 最小 Dijkstra scores

        :return:
        """


if __name__ == '__main__':
    g1 = {"0": {"1": 4, "2": 1},
         "2": {"1": 2, "3": 5},
         "1": {"3": 1},
         "3": {"4": 3}
         }

    graph = Graph(g1)
    graph.findShortestPathInWeightGraph(start_node='0',end_node='4')

    g2 = {"0": {"1": 1, "2": 4},
          "1": {"2": 2, "3": 6},
         "2": {"4": 3},
          "3": {"5": 1},
          "4": {"5": 1}
         }
    graph = Graph(g2)
    graph.findShortestPathInWeightGraph(start_node='0', end_node='2')
