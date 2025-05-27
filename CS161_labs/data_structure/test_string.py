#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: 
@time: 2021/1/4 22:01 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/4 22:01   wangfc      1.0         None

 * 密级：秘密
 * 版权所有：******股份有限公司 2019
 * 注意：本内容仅限于******股份有限公司内部传阅，禁止外泄以及用于其他的商业目的

"""
import unittest
from data_structure.string_type import lengthOfLongestSubstring

class TestStringSolution(unittest.TestCase):
    def test_lengthOfLongestSubstring(self):
        s = "pwwkew"
        length = lengthOfLongestSubstring(s)
        self.assertEqual(length,3)

if __name__ == '__main__':
    unittest.main()
