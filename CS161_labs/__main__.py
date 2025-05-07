#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/7/2 8:51 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/7/2 8:51   wangfc      1.0         None
"""
import logging



print(f'import logging in {__file__}')


def main():
    print(f'启动 main,__name__= {__name__},__file__= {__file__}')
    from sort_and_selection.sum import subArraySum
    nums = [3, 8, 7, 5, 1, 4]
    target = 13
    result = subArraySum(nums,target)
    return result

if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        print(f"出现错误 e= {e}")
