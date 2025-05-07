#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
问题一，给定一个只包含正数数组，求连续子数组和等于给定目标值的区间。
和2 sum问题有点相同，也是双指针或者叫快慢指针，但是这里双指针初始化都指向头部。
快指针先移动，当快慢指针区间内的和小于给定值时，移动快指针；否则移动慢指针。时间复杂度O(n)，值得注意的是，此时并没有数组排序好的要求。

http://legendtkl.com/2014/04/26/array-problem-trick/
"""
from typing import List
print(f"import abcd in {__file__}")
import abcd


def subArraySum(nums:List[int],target:int):
    # 设计 2 个指针:
    i = 0
    j = 0
    n= nums.__len__()
    array_sum= nums[0]
    while (i<n and j <n):
        # array_sum += nums[j]
        if array_sum == target:
            if j==n-1:
                return nums[i:]
            else:
                return nums[i:j+1]
        elif array_sum< target:
            # 当和小于目标值的时候，右指针向右移动以增加sum
            j += 1
            array_sum += nums[j]
            # array_sum 是包含到j的位置的数
        else:
            array_sum -= nums[i]
            i += 1

    return []


"""
53. 最大子序和
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
示例:
输入: [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
进阶:

如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。

"""
def maxSubArray(nums: List[int]) -> int:
    # 初始化 最大值=0
    ans=0
    # 初始化前一个位置的 最大值 =0
    preindex_max_sum =0
    for num in nums:
        # 状态转移方程
        curindex_max_sum = max(preindex_max_sum + num, preindex_max_sum)
        ans=max(ans,curindex_max_sum)
        # 更新前一个位置的最大值
        preindex_max_sum = curindex_max_sum
    return ans



"""
152. 乘积最大子数组
给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

示例 1:

输入: [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。
示例 2:

输入: [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
"""

def maxProduct( nums: List[int]) -> int:
    pass


if __name__ == '__main__':
    nums = [3, 8,7, 5, 1, 4]
    target = 13
    # subArraySum(nums,target)

    nums =[-2,1,-3,4,-1,2,1,-5,4]
    maxSubArray(nums)