#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: http://www.hundsun.com
@time: 2021/1/1 22:41 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/1 22:41   wangfc      1.0         None

 * 密级：秘密
 * 版权所有：恒生电子股份有限公司 2019
 * 注意：本内容仅限于恒生电子股份有限公司内部传阅，禁止外泄以及用于其他的商业目的

"""

import numpy as np

def distance(point_a,point_b):
    pass


def computer_J(k_cluster_dict,k_centorids):
    pass


def k_means(K,points ,dimension,epsion= 10e-8, max_iterations = 10000):

    # 初始化k个质心: 随机生成 K 个数
    init_k_points = np.random.randint(low=0,high=100,size=(K,dimension))

    k_centorids = init_k_points
    # 停止迭代的条件:
    # 1. 每个聚类内部元素不在变化，这是最理想的情况了。
    # 2. 前后两次迭代，J的值相差小于某个阈值。
    # 3. 迭代超过一定的次数。
    iteration = 0
    J= 0
    new_J = 10e10
    while (np.abs(J-new_J) < epsion or iteration >max_iterations):
        # 生成 K 簇 不同的数据
        k_cluster_dict = {}
        # 计算 各个point 到 这 k个点的距离,最近的距离标注为属于该簇
        for i in range(points.__len__()):
            current_point = points[i]
            current_point_distances = {}
            for k in range(K):
                d = distance(current_point,init_k_points[k])
                current_point_distances.update((k,d))
            # 按照 distance 排序，找到最短的距离对应的 k
            sorted_current_point_distances = sorted(current_point_distances,key=lambda x:x[1])
            k = sorted_current_point_distances[0][0]
            # 将该点加入到
            cluster = k_cluster_dict.get(k)
            cluster.append(current_point)
            k_cluster_dict.update({k:cluster})

        # 计算 各个簇的数据对应原来的质心的 距离之和
        J = computer_J(k_cluster_dict,k_centorids)

        # 更新 质心
        k_centorids= []
        for k,value in k_cluster_dict.items():
            k_centorids.append(np.mean(value))

        # 计算各个簇的数据对应新的质心的 距离之和
        new_J = computer_J(k_cluster_dict,k_centorids)
    return k_cluster_dict






