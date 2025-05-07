#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。

进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
"""
from typing import List


def findMedianSortedArrays(nums1:List,nums2:List):
    # 确定 num1< nums2: 减少二分查找的为 O(log(min(m,n))))
    if nums1.__len__() > nums2.__len__():
        temp = nums1
        nums1 = nums2
        nums2 = temp
    print("nums1={},nums2={}".format(nums1, nums2))
    m = nums1.__len__()
    n = nums2.__len__()

    # 对 nums1 进行二分查找：
    # 在nums1中找到分割线的位置 i，对应 nums2 中的分割线的位置 j = (m+n+1)/2
    # 使得 分割线两边满足中位数的条件：
    # 1. 中位数两边的数量相等（偶数的情况） 或者中位数的左边比右边多1个数（奇数的情况）
    # 2. 分割线的左边的数 都小于或者等于 右边的数： 即 nums1[i-1] <= nums2[j] && nums2[j-1] <= nums1[i]
    # 前一部分包含 nums1[0 .. i-1] 和 nums2[0 .. j-1]
    # 后一部分包含 nums1[i .. m-1] 和 nums2[j .. n-1]
    totalLeft= int((m+n+1)/2)
    left = 0
    right = m
    # 开始二分查找，直到 right == left 停止
    while (left<right):
        # 取 nums1 的中位数
        i = left+ int((right -left+1)/2)
        j = totalLeft -i
        # 查找 nums1 中的中间点,如果不满足停止条件，就继续二分：
        # 第1不满足的情况： nums1的左边部分的最大值比较大,下一轮搜索区间为 [left,i-1]
        if (nums1[i-1] > nums2[j]):
            temp= right
            right = i-1
            print("left={},right={}--->{}".format(left, temp, right))
        # 第2不满足的情况： nums1的右边部分的最小值比较小
        # elif (nums2[j-1] > nums1[i]):
        #     left = i+1
        else:
            # 下一轮搜索区间为[i, right]
            temp =left
            left = i
            print("left={}--->{},right={}".format(temp,left, right))

    # 当调出循环的时候： right =left
    i =  left
    j = totalLeft -i

    infinity = 2**20
    nums1LeftMax = -infinity if i==0 else nums1[i-1]
    nums1RightMin = infinity if i==m else nums1[i]
    nums2LeftMax = -infinity if j==0 else nums2[j-1]
    nums2RightMin = infinity if j== n else nums2[j]

    if (m+n)%2 ==0:
        return (max(nums1LeftMax,nums2LeftMax) + min(nums1RightMin,nums2RightMin))/2
    else:
        return max(nums1LeftMax, nums2LeftMax)


if __name__ == '__main__':
    nums1 = [1, 2,5]
    nums2 = [3, 4]
    result = findMedianSortedArrays(nums1,nums2)
    print("nums1={},nums2={},median ={}".format(nums1,nums2,result))