#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@time: 2021/6/23 21:53 

动态语言：
通俗地说：能够在运行时修改自身程序结构的语言，就属于动态语言。
那怎样才算是“运行时修改自身程序结构”捏？
比如下面这几个例子都 算：
在【运行时】给某个类增加成员函数及成员变量；
在【运行时】改变某个类的父类；
在【运行时】创建出某个函数......

eval() 和 exec() 函数的功能是相似的，都可以执行一个字符串形式的 Python 代码（代码以字符串的形式提供），相当于一个 Python 的解释器。
二者不同之处在于，eval() 执行完要返回结果，而 exec() 执行完不返回结果

eval() 函数用来执行一个字符串表达式，并返回表达式的值。
exec 执行储存在字符串或文件中的 Python 语句，相比于 eval，exec可以执行更复杂的 Python 代码。

那个传递给 eval / exec 的字符串，既可以看成是【数据】，也可以看成是可执行的【代码】。
在动态语言的 eval 手法中，数据和代码得到了完美的结合。有了这种结合，你就获得了【在运行时生成代码的能力】。




@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/6/23 21:53   wangfc      1.0         None
"""

#  Python exec() 执行语句

def eval_operator_on_valuse(sOperator, lstValues) :
    sExpr = "";
    for n in lstValues[1:] :  # 略过第一个元素
        sExpr += (sOperator + str(n));
    return eval(str(lstValues[0]) + sExpr);  # 补上第一个元素并求值


if __name__ == '__main__':

    result = eval_operator_on_valuse("+", [2, 3, 4, 5]);

    i = 1
    j = 2
    exec("res=i*j")
    print("Result is %s" %res)