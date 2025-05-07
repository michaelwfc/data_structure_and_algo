#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@time: 2021/1/17 23:11

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/17 23:11   wangfc      1.0         None


适用于 heap 的 application：
1） 经常进行 insert 操作
2） 经常进行 extractMin 操作

堆总是一棵完全树。即除了最底层，其他层的节点都被元素填满，且最底层尽可能地从左到右填入。

堆包括最大堆和最小堆：
最大堆的每一个节点（除了根结点）的值不大于其父节点；
最小堆的每一个节点（除了根结点）的值不小于其父节点。


           application                         why
queue: breadth-first search                sequentially organizes data so that removing an object from the front or adding an object to the back takes constant time
stack:   depth-first search                remove an object from or add an object to the front in constant time
heap:  Dijkstra’s shortest-path algorithm

数据结构堆（ heap）：
A heap is a data structure that keeps track of an evolving set of objects with keys
and can quickly identify the object with the smallest key.
insert:      O(logn)   given a heap H and a new object x, add x to H.
extractMin:  O(logn)   given a heap H, remove and return from H  an object with the smallest key (or a pointer to it).
                       因为需要保持 extract之后的 heap结构




"""
import heapq as hq
import math
from queue import PriorityQueue
from typing import List
from sort_and_selection.sort_algorithms import heapSort
import numpy as np
from copy import deepcopy


from typing import Iterable


def test_heapq():
    """
    这个模块提供了的堆是一个最小堆，索引值从0开始。而很多教材中都使用最大堆作为教学的例子，因为其排序是稳定的，而最小堆排序是不稳定的。
    Python中创建一个堆可以直接使用list的创建方式H = [], 或者使用heapify()函数将一个存在的列表转为堆。

    这个模块提供了下面几种堆的操作：
    heapq.heappush(heap, item)
    往堆中插入一个值，同时要保持为最小堆。

    heapq.heappop(heap)
    返回堆中的最小值，并把它从堆中删除，同时保持为最小堆；如果堆为空，发生 IndexError。
    直接通过heap[0]可以获取最小值并不从堆中把它删除。

    heapq.heappushpop(heap, item)
    向堆中插入值后再弹出堆中的最小值，这个函数的速度比直接使用 heappush() 和 heappop()的效率更加高。

    heapq.heapreplace(heap, item)
    弹出和返回堆中的最小值再插入一个新的值。堆的大小没有改变。如果堆为空，产生 IndexError。
    这一个操作也比直接使用heappush() 和heappop()的效率更加高，尤其适合使用在固定堆大小不变的情况。
    与上一个函数相比，这个函数返回的值可能要比将要插入到堆的值大。

    heapq.heapify(x)
    将一个list转为最小堆，线性时间复杂度，O(n).

    :return:
    """
    # 1、heappush(heap,n)数据堆入
    # data = np.arange(10)
    # np.random.shuffle(data)
    # # 创建一个空堆
    # heap = []
    #
    # for i in data:
    #     hq.heappush(heap,i)
    # print(f"type(heap)={type(heap)},heap={heap}")

    nums = [3, 2, 1, 2, 6, 4]
    num_copy = deepcopy(nums)
    hq.heapify(num_copy)
    num_copy[0]

def heapsort(items:Iterable):
    """
    https://docs.python.org/zh-cn/3/library/heapq.html
    这类似于 sorted(iterable)，但与 sorted() 不同的是这个实现是不稳定的。

    :param items:
    :return:
    """
    # 使用list 初始化 heap
    h = []
    # 使用 heapq.heappush()  插入到heap 中
    for i  in items:
        hq.heappush(h,i)
    # 使用 heapq.heappop() 方法找到输出最小值
    sorted_items = [hq.heappop(h) for _ in range(h.__len__())]
    return sorted_items


def canAttendMeetings(intervals:List[List[int]]) ->bool:
    """
    [LeetCode] 252. Meeting Rooms 会议室
    Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
    determine if a person could attend all meetings.
    Example 1:
    Input: [[0,30],[5,10],[15,20]]
    Output: false

    Example 2:
    Input: [[7,10],[2,4]]
    Output: true
    NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

    将 区间按照 start_time 进行排序，
    如果相邻的两个区间有交叉（后一个区间的 start_time 比前一个区间的 end_time 小），则返回false
    如果相邻的两个区间没有交叉，则后面所有的区间都不可能与当前的区间有交叉
    running time: O(nlogn)
    """
    sorted_intervals = heapSort(nums=intervals,key=0)
    for i in range(0,len(sorted_intervals)-1):
        current_interval = sorted_intervals[i]
        next_interval = sorted_intervals[i+1]
        if next_interval[0] <current_interval[1]:
            return False
    return True


def minMeetingRooms(intervals:List[List[int]])->int:
    """
    [LeetCode] 253. Meeting Rooms II 会议室之二
    Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
    find the minimum number of conference rooms required.
    Example 1:
    Input: [[0, 30],[5, 10],[15, 20]]
    Output: 2
    Example 2:

    Input: [[7,10],[2,4]]
    Output: 1
    NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

    首先按照 开始时间进行排序，
    然后当我们要进行下个会议的时候，我们应该比较该会议的开始时间 start 和 当前正在进行的会议中的结束时间 end 最小值进行比较，
    如果 该会议的开始 start >= end 最小值 ,则可以使用该会议室，因为可以剔除 这个 end
    可以循环和 当前正在进行的会议结束时间做比较， 直到 start < end ，说明需要增加一个会议室，记录当前会议室的数量
    因为需要每次比较 end 最小值 和 start， 因此，我们可以使用 最小堆的数据结构 进行算法设计

    sorted_intervals = sorted(intervals,key = start)
    min_rooms =1
    end_times_heap.add(first_interval[1])
    for interval in sorted_intervals:
        min_end_time = end_times_head.extract_min()
        end_times_heap_size = end_times_heap.size
        while min_end_time<= interval[1] and  end_times_heap_size>0:
            min_end_time = end_times_head.extract_min()
        if min_end_time> interval[1]:
           end_times_heap_size.add(min_end_time)
           end_times_heap_size.add(interval[1])
        else:
           end_times_heap_size.add(interval[1])
        if end_times_heap_size.size > min_rooms:
            min_rooms = end_times_heap_size.size

    :return:
    """
    # 首先按照 开始时间进行排序，
    sorted_intervals = heapSort(intervals)
    # 初始化一个 heap，存放当前会议的结束时间
    end_times_heap = []
    min_rooms = 1
    hq.heappush(end_times_heap,sorted_intervals[0][1])
    # 依次判断新增的会议开始时间 与 当前 end_times_heap 的最小值
    for i in range(1,len(sorted_intervals)):
        current_start = sorted_intervals[i][0]
        # 取出 end_times_heap 中最小值
        min_end_time = hq.heappop(end_times_heap)
        # 如果 该会议的开始 start >= end 最小值 ,则可以使用该会议室，因为可以剔除 这个 end
        while min_end_time is not None and  current_start >= min_end_time:
            if end_times_heap.__len__()>0:
                min_end_time = hq.heappop(end_times_heap)
            else:
                min_end_time = None
        if min_end_time is not None:
            hq.heappush(end_times_heap,min_end_time)
        hq.heappush(end_times_heap,sorted_intervals[i][1])
        if end_times_heap.__len__()>min_rooms:
            min_rooms = end_times_heap.__len__()
    return min_rooms


class MedianFinderV1():
    """
    295. 数据流的中位数
    中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
    例如，
    [2,3,4] 的中位数是 3
    [2,3] 的中位数是 (2 + 3) / 2 = 2.5

    设计一个支持以下两种操作的数据结构：

    void addNum(int num) - 从数据流中添加一个整数到数据结构中。
    double findMedian() - 返回目前所有元素的中位数。
    示例：
    addNum(1)
    addNum(2)
    findMedian() -> 1.5
    addNum(3)
    findMedian() -> 2
    进阶:
    如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
    如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
    """
    def __init__(self):
        """
        将数据流维护为一个 heap
        addNum: 使用 heappush: O(logn)
        findMedian： 使用  heappop: O(nlogn)
        """
        self.nums =[]

    def addNum(self,num:int):
        # 使用 最小堆的方式
        hq.heappush(self.nums,num)

    def findMedian(self):
        """
        :return:
        """
        nums = deepcopy(self.nums)
        size = nums.__len__()
        if size % 2==1:
            median_index = int(size/2)
            for i in range(median_index+1):
                median = hq.heappop(nums)
        else:
            median_index = int(size/2)
            for i in range(median_index):
                median = hq.heappop(nums)
            median_next = hq.heappop(nums)
            median = (median+median_next)/2
        return median


class MedianFinderV2():
    """
    使用 2个 heap 来保存数据流：
    大根堆：maxHeap
    小根堆： minHeap
    大根堆中的元素保持和小根堆的相同或者多一个

    addNum:  O(logn)
    比较元素大小:num, max_from_maxHeap , min_from_minHeap,
    if num< max_from_maxHeap:
        if maxHeap.size == minHeap.size:
            num, max_from_maxHeap -> maxHeap
        if maxHeap.size ==  minHeap.size +1:
            num -> maxHeap ,  max_from_maxHeap,min_from_minHeap -> minHeap
    elif num> min_from_minHeap

    else:

    findMedian: O(logn)
    if maxHeap.size == minHeap.size:
    elif maxHeap.size ==  minHeap.size +1:
    """
    def __init__(self):
        self.maxHeap =[]
        self.minHeap = []
        # self.maxHeap_size =0
        # self.minHeap_size = 0
        # self.max_from_maxHeap = None
        # self.min_from_minHeap = None

    def addNum(self,num:int):
        maxHeap_size = self.maxHeap.__len__()
        minHeap_size = self.minHeap.__len__()
        if maxHeap_size>0:
            max_from_maxHeap = - hq.heappop(self.maxHeap)
        else:
            max_from_maxHeap = None
        if minHeap_size>0:
            min_from_minHeap = hq.heappop(self.minHeap)
        else:
            min_from_minHeap = None

        if max_from_maxHeap is None:
            hq.heappush(self.maxHeap,-num)
        elif max_from_maxHeap is not None and min_from_minHeap is None:
            if num<max_from_maxHeap:
                sorted_two_nums = [num,max_from_maxHeap]
            else:
                sorted_two_nums = [max_from_maxHeap,num]
            hq.heappush(self.maxHeap,-sorted_two_nums[0])
            hq.heappush(self.minHeap,sorted_two_nums[1])
        else:
            if num<max_from_maxHeap:
                sorted_three_nums = [num,max_from_maxHeap,min_from_minHeap]
            elif num>min_from_minHeap:
                sorted_three_nums = [max_from_maxHeap, min_from_minHeap,num]
            else:
                sorted_three_nums = [max_from_maxHeap, num,min_from_minHeap]
            if maxHeap_size == minHeap_size:
                hq.heappush(self.maxHeap,-sorted_three_nums[0])
                hq.heappush(self.maxHeap,-sorted_three_nums[1])
                hq.heappush(self.minHeap,sorted_three_nums[2])

            elif maxHeap_size ==minHeap_size+1:
                hq.heappush(self.maxHeap, -sorted_three_nums[0])
                hq.heappush(self.minHeap, sorted_three_nums[1])
                hq.heappush(self.minHeap, sorted_three_nums[2])

    def findMedian(self):
        maxHeap_size = self.maxHeap.__len__()
        minHeap_size = self.minHeap.__len__()
        if maxHeap_size == minHeap_size +1:
            return -self.maxHeap[0]
        else:
            return (-self.maxHeap[0]  + self.minHeap[0])/2






if __name__ == '__main__':

    tasks = [(5, 'write code'), (1, 'any'),(7, 'release product'), (1, 'write spec'), (3, 'create tests'), ]
    heapsort(items=tasks)

    # 选择排序： 堆实现
    nums = [3, 2, 1, 2, 6, 4]
    sorted_nums = heapSort(nums)

    intervals =[[0,30],[15,20],[5,10]]
    # heapSort(intervals,key=1)
    intervals = [[7, 10], [2, 6]]
    canAttendMeetings(intervals=intervals)

    minMeetingRooms(intervals=intervals)

    # median_finder = MedianFinderV1()
    median_finder = MedianFinderV2()
    median_finder.addNum(1)
    median_finder.addNum(2)
    median_finder.findMedian()
    median_finder.addNum(3)
    median_finder.findMedian()



    # customers = PriorityQueue()  # we initialise the PQ class instead of using a function to operate upon a list.
    # customers.put((2, "Harry"))
    # customers.put((3, "Charles"))
    # customers.put((1, "Riya"))
    # customers.put((4, "Stacy"))
