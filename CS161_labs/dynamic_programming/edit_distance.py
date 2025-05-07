#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/7/11 21:47 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/7/11 21:47   wangfc      1.0         None
"""


"""
72. 编辑距离
https://leetcode-cn.com/problems/edit-distance/


给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：

插入一个字符
删除一个字符
替换一个字符
 

示例 1：
输入：word1 = "horse", word2 = "ros"
输出：3
解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
示例 2：
输入：word1 = "intention", word2 = "execution"
输出：5
解释：
intention -> inention (删除 't')
inention -> enention (将 'i' 替换为 'e')
enention -> exention (将 'n' 替换为 'x')
exention -> exection (将 'n' 替换为 'c')
exection -> execution (插入 'u')
"""

def edit_distance(word1:str,word2:str):
    """
    https://www.bilibili.com/video/BV15h411Z7Qd
    """
    length_word1 = word1.__len__()
    length_word2 = word2.__len__()

    # 初始化 distance_state 状态矩阵
    distance_state = []
    for i in range(length_word1+1):
        distance_state.append([None]* (length_word2+1))

    for i in range(length_word1+1):
        for j in range(length_word2+1):
            # 初始化状态
            if i==0:
                distance_state[i][j] = j
            elif j==0:
                distance_state[i][j] =i
            else:
                # 计算 第 i 个字和 第j 个字的编辑距离
                # 判断
                minimum_edit_distance_in_last_state = min(distance_state[i-1][j],distance_state[i][j-1],distance_state[i-1][j-1])
                if word1[i-1]==word2[j-1]:
                    # 该状态可能从前面的三个状态过来
                    # 找到前一个状态中的最小编辑句子 +1
                    distance_state[i][j] = minimum_edit_distance_in_last_state
                else:
                    distance_state[i][j] = minimum_edit_distance_in_last_state + 1
    minimum_edit_distance = distance_state[length_word1][length_word2]
    print(f"word1= {word1}, word2= {word2},minimum_edit_distance = {minimum_edit_distance}")
    return minimum_edit_distance

if __name__ == '__main__':
    word1 = "horse"
    word2 = "ros"

    word1 = "intention"
    word2 = "execution"
    edit_distance(word1=word1,word2=word2)
