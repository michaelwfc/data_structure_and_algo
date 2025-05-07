#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""
3. 无重复字符的最长子串
给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

示例 1:
输入: s = "abcabcbb"
输出: 3
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
示例 2:

输入: s = "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
示例 3:

输入: s = "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
示例 4:

输入: s = ""
输出: 0
 

提示：

0 <= s.length <= 5 * 104
s 由英文字母、数字、符号和空格组成

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
"""


def lengthOfLongestSubstring(s: str) -> str:
    # 设计 2个指针进行 “滑动窗口”的方式移动
    N = s.__len__()
    i = 0
    j = 0
    longest_length = 0
    # 记录window中的不重复的字符
    window_word_sets = set()
    while i < N:
        # 指针i 表示窗口开始的位置
        if i > 0:
            # 当左指针向右移动的时候，移出 window当中的左指针左边的字符
            window_word_sets.remove(s[i - 1])
        # 指针 j 表示窗口结束的位置（包含该位置）,# 判断窗口是否含有重复字符
        while (j < N) and s[j] not in window_word_sets:
            # 放入该指针指向的字符到窗口当中
            window_word_sets.add(s[j])
            length = j - i + 1
            longest_length = max(longest_length, length)
            j += 1
        i += 1
    return longest_length

class StringSolution():
    """
    76. 最小覆盖子串
    给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
    注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
    示例 1：
    输入：s = "ADOBECODEBANC", t = "ABC"
    输出："BANC"
    示例 2：

    输入：s = "a", t = "a"
    输出："a"

    提示：
    1 <= s.length, t.length <= 105
    s 和 t 由英文字母组成

    进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？

    """

    def minWindow(self,s:str,t:str)->str:
        """
        思路：

        :param s:
        :return:
        """
        n =  s.__len__()
        # 包含目标字符串的字符的集合
        target_set = set(t)
        i=0
        j=0
        window_words_set = set()
        min_window= ""
        min_window_size = 0
        # 左指针不断往左移动
        while i<n:
            # 如果右指针移动，直到包含所有的 target_set
            while (j<n and not target_set.issubset(window_words_set)):
                window_words_set.add(s[j])
                # 如果j指向的window 和 target_set 不同
                j+=1

            # 如果对应的左指针和右指针对应的窗口window 包含 所有字符,
            if target_set.issubset(window_words_set):
                # j 在窗口为一个位置
                min_window = s[i:j]
                min_window_size = j-i
                # 直到右指针移动到 包含所有的 target_set
                # 我们开始缩减左指针，尽量使得其达到最小
                while (i<j):
                    # 如果左指针移动一位， 窗口需要排除这个字符，
                    window_words_set.remove(s[i])
                    i+=1
                    if target_set.issubset(window_words_set):
                        min_window= s[i:j]
                        min_window_size = j-i
                    else:
                        break
            else:
                # 没有找到对应 window
                min_window =""
                min_window_size=0
                window_words_set= set()
                i +=1
        return min_window


def find(str1:str,target:str):
    target_len = target.__len__()
    length = str1.__len__()
    for start in range(str1.__len__()):
        end = start+target_len-1
        if end>length:
            return False
        elif end==length:
            if str1[start:]== target:
                return start
            else:
                return False
        elif str1[start:end+1]==target:
            return start
    return False



if __name__ == '__main__':
    s = "pwwkew"
    # lengthOfLongestSubstring(s)
    s = "bbbbb"
    # lengthOfLongestSubstring(s)
    s = "ADOBECODEBANC"
    t = "ABC"
    # minWindow(s=s,t=t)
    result = find("abcdefg",target='de')
    print(result)