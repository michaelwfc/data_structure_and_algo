#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
剑指 Offer 04. 二维数组中的查找
在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
"""

def findNunmberIn2DArrayV1(matrix,target):
    num_rows = matrix.__len__()
    num_columns = matrix[0].__len__()
    steps = 0
    row = 0
    column=0
    while (-1<row < num_rows):
        current = matrix[row][column]
        if current==target:
            print("steps={}".format(steps))
            return True
        elif current>target:
            return False
        # 如果当前行比 target 小， 观察当前行的下一个值
        new_column = column +1
        # 如果是最后一列，则换行
        if new_column> num_columns-1:
            row +=1
            steps += 1
        else:
            # 如果不是最后一列，观察当前行的下一个值
            next = matrix[row][new_column]
            # 1) 当前行的下一个值比target继续往右
            if next == target:
                steps += 1
                print("steps={}".format(steps))
                return True
            elif next < target:
                # 向右移动
                column +=1
                steps += 1
            else:
                # 向下移动
                row +=1
                steps += 1

"""
思路1： 存在错误： 当 next < target 一定向右移动吗？ 可能要向下移动呢！！！
思路2： 从右上角开始搜索，因为只要根据当前的 current 与 target 进行比较就可以决定下一步搜索的方向

"""

def findNunmberIn2DArrayV2(matrix,target):
    num_rows = matrix.__len__()
    num_columns = matrix[0].__len__()
    steps = 0
    row = 0
    column=num_columns-1
    current = matrix[row][column]
    while (row<num_rows) and (column>-1):
        if current == target:
            steps += 1
            print("steps={}".format(steps))
            return True
        elif current > target:
            # 当前值比 target大的时候，向左移动
            column-=1
            steps +=1
        else:
            # 当前值比 target小的时候，只能向下查找
            row+=1
            steps += 1
    return False

"""
240. 搜索二维矩阵 II
"""
if __name__ == '__main__':
    matrix = [
      [1,   4,  7, 11, 15],
      [2,   5,  8, 12, 19],
      [3,   6,  9, 16, 22],
      [10, 13, 14, 17, 24],
      [18, 21, 23, 26, 30]
    ]
    resultV1 = findNunmberIn2DArrayV1(matrix,target=18)
    resultV2 = findNunmberIn2DArrayV2(matrix, target=18)


