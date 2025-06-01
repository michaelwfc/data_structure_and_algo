#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: 
@time: 2021/1/12 21:55 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/12 21:55   wangfc      1.0         None

https://www.coursera.org/learn/algorithms-part1/lecture/UQxFT/selection

Proposition. Quick-select takes linear time on average.


"""
from typing import List
from .sort_algorithms import randomPartition

def findKthLargest(nums_input:List[int],K:int) ->int:
    """
    https://leetcode-cn.com/problems/kth-largest-element-in-an-array
     215. 数组中的第K个最大元素
    给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
    请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
    你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
    示例 1:
    输入: [3,2,1,5,6,4] 和 k = 2
    输出: 5
    示例 2:

    输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
    输出: 4
    说明:

    你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。


    



    :param nums:
    :return:
    """
    # 转换为第 k 小位置
    target = nums_input.__len__()-K
    nums = nums_input.copy()
    left= 0
    right = nums_input.__len__()-1
    while True:
        # 每次计算 基准
        partition, partitionIndex = randomPartition(nums=nums,left=left,right=right)
        # 如果找到就停止
        if partitionIndex == target:
            return partition
        # 如果没有找到，继续在大的那一部分去找
        if partitionIndex<target:
           left = partitionIndex+1
        if partitionIndex>target:
            right = partitionIndex-1


if __name__ == '__main__':
    nums = [3, 2, 1, 5, 6, 4]
    findKthLargest(nums_input=nums,K=2)