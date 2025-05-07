#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
74. 搜索二维矩阵
编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：

每行中的整数从左到右按升序排列。
每行的第一个整数大于前一行的最后一个整数。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/search-a-2d-matrix

"""


def search_matrix(matrix,target):
    row_num = matrix.__len__()
    column_num = matrix[0].__len__()
    left = 0
    right = row_num*column_num
    # 循环迭代，知道 left > right 的时候停止
    while left<=right:
        # 二分法找到中间的点
        mid_index = int((left+right)/2)
        mid = matrix[mid_index//column_num][mid_index%column_num]
        if target ==mid:
            return True
        elif target<mid:
            right = mid_index-1
        else:
            # 左边的点往右移动，直到 left> right的时候停止
            left = mid_index+1
    return False


"""
题目：
长度为n的数组中，总是存在一个断点（下标记为i），数组中断点前面的数字是有序的，断点后的数字也是有序的，且断点后边的数字总是小于前面的数字。
如果直接把断点后边的数字移动到数组的前边，那么这个数组将是有序的，具体描述如下所示。求这个数组中的第n/2大的数。

原数组：
6,8,10,13,1,2,4
找到断点移动之后的数组：
1，2,4,6,8,10
#############################
原数组：
5,1,2,3,4,
找到断点移动之后的数组：
1,2,3,4,5
##############################
原数组：
2,3,4,5,1
找到断点移动之后的数组：
1,2,3,4,5

"""
if __name__ == "__main__":
    matrix = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 50]]
    target = 8
    result = search_matrix(matrix,target)
    print("matrix={},target={},is_in_matrix={}".format(matrix,target,result))






