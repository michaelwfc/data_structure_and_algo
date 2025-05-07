#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/7/11 22:26 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/7/11 22:26   wangfc      1.0         None
"""
from typing import List

"""
“求价值最大和”，毋庸置疑，用动态规划。此类题目属于动态规划中的一个特殊类别——“背包问题”。
PS：背包问题可以分为三种——
 0/1背包问题——物品只有选/不选两种状态，求最大价值和
子集背包问题——分割等和子集问题
完全背包问题——物品可以无限选，求可以组合成目标值的选法


链接：https://www.nowcoder.com/questionTerminal/7e157ce9a8c249daa3ddafad322dbf1e
来源：牛客网

有为N件物品，它们的重量w分别是w1,w2,...,wn，它们的价值v分别是v1,v2,...,vn，每件物品数量有且仅有一个，现在给你个承重为M的背包，求背包里装入的物品具有的价值最大总和？

输入描述:
物品数量N=5件
重量w分别是2 2 6 5 4
价值v分别是6 3 5 4 6
背包承重为M=10


输出描述:
背包内物品最大总和为15
示例1
输入
5
10
2 2 6 5 4
6 3 5 4 6
输出
15
"""

def knapsack_problem(values:List[int],weights:List[int],capacity:int):
    num_items= values.__len__()

    # 初始化 value_state
    value_state = []
    for i in range(num_items+1):
        value_state.append([None]*(capacity+1))

    for i in range(num_items+1):
        for j in range(capacity+1):
            if i==0:
                # 0 个 item 的时候，value 都是0
                value_state[i][j]=0
            elif j==0:
                # capacity 为 0 的时候， value 都是0
                value_state[i][j]= 0
            else:
                current_capacity = j
                current_item_weight = weights[i-1]
                current_item_value = values[i-1]

                if current_item_weight > current_capacity:
                    value_state[i][j] = value_state[i-1][j]
                else:
                    current_max_value = max(value_state[i-1][j], value_state[i-1][j-current_item_weight] + current_item_value)
                    value_state[i][j] = current_max_value
        print(value_state[i])
    max_value =value_state[num_items][capacity]
    print(f"max_value with capacity {capacity} = {max_value}")
    return max_value

if __name__ == '__main__':

    num_items=5
    weights= [2, 2, 6, 5 ,4]
    values = [6, 3, 5, 4, 6]
    capacity=10
    max_value = knapsack_problem(values=values,weights=weights,capacity=capacity)