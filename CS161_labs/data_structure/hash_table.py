#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
1. 两数之和
给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

你可以按任意顺序返回答案。
示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
示例 2：

输入：nums = [3,2,4], target = 6
输出：[1,2]
示例 3：

输入：nums = [3,3], target = 6
输出：[0,1]

"""
from typing import List


def get_two_sumV1(nums:List,target:int):
    """
    以空间换时间的做法
    :param nums:
    :param target:
    :return:
    """
    # 生成一个字典:O(n)
    num2index_dict = {num: index for index,num in enumerate(nums)}
    # 对 nums 进行遍历，查找 target -num 这个key是否存在于 字典当中:因为字典中查找为 O(1)
    for index, num in enumerate(nums):
        another_num = target-num
        another_num_index = num2index_dict.get(another_num)
        if another_num_index is not None:
            print("nums[{}]={}+nums[{}]={} == target={}".format(index,num,another_num_index,another_num,target))
            return [index,another_num_index]
    return [-1,-1]

def get_two_sumV2(nums:List,target:int):
    # 不新建一个完整的字典，只是在遍历的时候创建字典
    num2index_dict = {}
    for index,num in enumerate(nums):
        another_num = target - num
        another_num_index = num2index_dict.get(another_num)
        if another_num_index is not None:
            print("nums[{}]={}+nums[{}]={} == target={}".format(index, num, another_num_index, another_num, target))
            return [index,another_num_index]
        # 如果不存在对应的数字，增更新字典
        num2index_dict.update({num:index})


if __name__ == '__main__':

    nums = [2, 7, 11, 15]
    target = 9
    get_two_sumV1(nums,target=26)
    get_two_sumV2(nums,target=26)


