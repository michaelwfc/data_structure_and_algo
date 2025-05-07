#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/7/11 21:52 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/7/11 21:52   wangfc      1.0         None
"""
from typing import Union, List


def findLength(sequence1: Union[List[int],str], sequence2: Union[List[int],str]) -> int:
    """
    718. 最长重复子数组
    给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
    示例：
    输入：
    A: [1,2,3,2,1]
    B: [3,2,1,4,7]
    输出：3
    解释：
    长度最长的公共子数组是 [3, 2, 1] 。

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    https://www.bilibili.com/video/BV1aK411J7b8?from=search&seid=16485564189704232882


    https://www.nowcoder.com/practice/f33f5adc55f444baa0e0ca87ad8a6aac?tpId=191&&tqId=36137&rp=1&ru=/ta/job-code-high-algorithm&qru=/ta/job-code-high-algorithm/question-ranking
    描述
    给定两个字符串str1和str2,输出两个字符串的最长公共子串
    题目保证str1和str2的最长公共子串存在且唯一。
    示例1
    输入：
    "1AB2345CD","12345EF"
    复制
    返回值：
    "2345"

    定义 longest_length[i,j] 为 以 i,j 位置结束的数组 A[:i],B[:j] 中的最长公共数组的长度
    递推关系： 画出 sequence1 纵轴方向 + sequence2 横轴方向

    longest_length[i+1,j+1] =   longest_length[i,j] +1 , if A[i]==B[j]
                                longest_length[i,j],     if A[i]!=B[j]

    1）初始化状态矩阵：
        longest_length.shape = (length_sequence1+1) * (length_sequence2 +1)

    2）设置初始状态： 假设当 sequence1 或者 sequence2 为空的时候
    longest_length[0][j] = 0
    longest_length[i][0] = 0

    :param self:
    :param nums1:
    :param nums2:
    :return:
    """
    length_sequence1 = sequence1.__len__()
    length_sequence2= sequence2.__len__()

    longest_state = []
    for i in range(length_sequence1+1):
        new_row = [None]*(length_sequence2+1)
        longest_state.append(new_row)
        # longest_state = [[None]*(length_sequence2+1)] * (length_sequence1+1)

    longest_length = 0
    longest_substring = ''
    for i in range(length_sequence1+1):

        for j in range(length_sequence2+1):
            if j==0:
                longest_state[i][0] = (0,'') # 记录最大长度值和 最大长度子串
            elif i==0:
                longest_state[0][j] = (0,'')
            else:
                if sequence1[i-1]==sequence2[j-1]:
                    # 查看 longest_state[i-1][j-1] 的状态
                    length = longest_state[i-1][j-1][0] +1
                    substring =  longest_state[i-1][j-1][1] + str(sequence1[i-1])
                    longest_state[i][j]= tuple([length,substring])
                    # 更新最大值
                    if length> longest_length:
                        longest_length = length
                        longest_substring = substring
                else:
                    longest_state[i][j] = tuple([0, ''])
        print(longest_state[i])
    print(f"最长子串长度 = {longest_length} : {longest_substring}")
    return longest_length,longest_substring


if __name__ == "__main__":

    A = [1,2,3,2,1]
    B =  [3,2,1,4,7]

    A= "1AB2345CD"
    B= "12345EF"
    longest_length,longest_substring = findLength(sequence1=A,sequence2=B)