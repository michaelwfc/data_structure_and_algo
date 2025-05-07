#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Divide and Conquer Paradigm
1. Divide into smaller problems
2. Conquer sub-problems recursively
3. Combine solutions of sub-problems into one for the original problem

"""
import random
from typing import List, Union,Iterable
import heapq as hq

def mergerSort(nums:List[int]):
    """
    经典的合并排序：
    分而治之的思想： 一个长度为n的数组分为 左右 两部分 n/2 长度的数组，如果这n/2的数组排好序，然后合并这两个排好序的数组
    时间复杂度： 6n*log(n) + 6n
    step1:  Divide into

    :param nums:
    :return:
    """
    n = nums.__len__()
    median = n//2
    left = nums[:median]
    right = nums[median:]
    # 确定递归结束的 base case
    if left.__len__()==1:
        left_sorted = left
    else:
        # 左边两部分进行递归
        left_sorted = mergerSort(left)
    if right.__len__() ==1:
        right_sorted = right
    else:
        right_sorted = mergerSort(right)
    # 对排序后的数组进行 merge
    left_length = left_sorted.__len__()
    right_length = right_sorted.__len__()
    i=0
    j=0
    sortedNums = []
    while (i<left_length and j<right_length):
        left_num = left_sorted[i]
        right_num = right_sorted[j]
        if left_num<right_num :
            sortedNums.append(left_num)
            i+=1
        else:
            sortedNums.append(right_num)
            j+=1
    if i== left_length and j<right_length:
        sortedNums.extend(right_sorted[j:])
    elif j==right_length and i<left_length:
        sortedNums.extend(left_sorted[i:])
    return sortedNums



def bubbleSort(nums_input:List[int]) ->List[int]:
    """
    for i in range(N):
        for j in range(N-1-j):
            if nums[j]>nums[j+1]:
                swap(j,j+1)
    :param nums_input:
    :return:
    """
    nums= nums_input.copy()
    # 初始的最大值为第 0 个数
    N = nums.__len__()
    for i in range(N):
        # i=0 : 表示取最大的值， 相邻比较直到 N-2-0= N-2
        # i=1:  表示取第2大值,相邻比较直到 N-2-1=N-3
        for j in range(0,N-1-i):
            # 如果相邻两个位置前者比较大，则交换两个位置
            if nums[j]> nums[j+1]:
                temp = nums[j]
                nums[j] = nums[j+1]
                nums[j+1] = temp
    return nums

def selectSort(nums_input:List[int]):
    """
    选择排序
    首先，找到数组中最小的那个元素，
    其次，将它和数组的第一个元素交换位置(如果第一个元素就是最小元素那么它就和自己交换)。
    其次，在剩下的元素中找到最小的元素，将它与数组的第二个元素交换位置。
    如此往复，直到将整个数组排序。这种方法我们称之为选择排序。

    for i in range(N):
        # find minimum in nums[i:]
        minimum = nums[i]
        for j in range(i,N):
            if nums[j]< minimum:
                minimum = nums[j]
        swap(nums[i],minimum)


    数据结构：array
    running time: O(n^2)

    :return:
    """
    nums = nums_input.copy()
    N= nums.__len__()
    # 使用 i 表示 第 i 个最小值
    # i = 0, 表示第0小 最小的那个值，需要交换到第 0 个位置
    # i = 1, 表示第1小最小的那个值，需要交换到第 1 个位置
    for i in range(N):
        # 总是默认先将最前面的数最为最小值
        minest_index= i
        minest = nums[minest_index]
        # 在 i+1 到N的位置寻找最小值
        for j in range(i+1,N):
            if nums[j] < minest:
                # 记录最小值出现的位置和对应的最小值： 注意防止上一次迭代中的 minest_index 在下一次的迭代中发生作用
                minest_index = j
                minest = nums[j]

        # 直到循环结束，得到最小值
        temp = nums[i]
        nums[i]= minest
        nums[minest_index] =temp
    return nums

def heapSort(nums: Iterable,key:int=None)->Iterable:
    """
    使用选择排序的思想，利用 heap 数据结构的特点：insert=O(), findMin = O(long(n))
    running time： O(nlogn)
    :param nums:
    增加一个 key 参数，使得排序可以根据某个key 来排序
    :return:
    """
    if not nums:
        return []
    # 使用list 方法新建一个小根堆
    heap =[]
    # 当存在key的时候
    if key is not None and key in nums[0]:
        for num in nums:
            # 堆元素可以为元组。
            hq.heappush(heap,(num[key],num))
        # hq.heappop(heap) 抛出的是 （key,value）
        return [hq.heappop(heap)[1] for i in range(len(heap))]
    else:
        # 当不存在key的时候
        for num in nums:
            hq.heappush(heap,num)
        return [hq.heappop(heap) for i in range(len(heap))]


def quickSort(nums_input:List[int]) ->List[int]:
    """
    快速排序的基本思路
　　一、先通过 选择一个基准数 pivot，将数组原地划分为两部分，其中一部分的所有数据都小于另一部分的所有数据。原数组被划分为2份
　　二、通过递归的处理， 再对原数组分割的两部分分别划分为两部分，同样是使得其中一部分的所有数据都小于另一部分的所有数据。 这个时候原数组被划分为了4份
　　三、就1,2被划分后的最小单元子数组来看，它们仍然是无序的，但是！ 它们所组成的原数组却逐渐向有序的方向前进。
　　四、这样不断划分到最后，数组就被划分为多个由一个元素或多个相同元素组成的单元，这样数组就有序了。


    two cool facts about partition
    1. linear (O(n)) time, no extra memory
    2. reduces problem size

    QuickSort(nums A)
    if n=1 return
    p = choose pivot(A)
    partition A around p
    recursively sort 1st part
    recusively sort 2nd part



    :param nums:
    :return:
    """
    def recursive_sort(array:List[int],left:int,right:int):
        """
        创建一个递归函数，每次找到 基准元素后进行分割为 2部分继续迭代
        :param array:
        :param left:
        :param right:
        :return:
        """
        # 迭代停止条件：当 left >=right的时候,我们停止迭代，不对数组做任何操作
        if left >= right:
            return
        else:
            # 寻找 基准值和 基准位置
            partition, partitionIndex = randomPartition(nums=array, left=left, right=right)
            # 按照基准位置分割左右两部分：
            # 左半部分
            recursive_sort(array=array,left=left,right=partitionIndex-1)
            # 右半部分
            recursive_sort(array=array,left=partitionIndex+1,right= right)
    nums = nums_input.copy()
    recursive_sort(array=nums,left=0,right=nums.__len__()-1)
    return nums


def partitionV1(nums:List[int], left:int, right:int)->(int,int):
    """
    Key Idea: partion array around a pivot element
    Note: put pivot in its "rightful position"

    method 1: using O(n) extra memeory ,easy to partition around pivot in O(n) time
    method 2:
    总是以数组最左边作为基准，
    从数组左边和右边开始遍历
    1） 直到左边的位置移动到首次大于基于，右边的位置移动到小于基准：进行置换
    2)  直到 当 i ==j 的时候停止：判断该
    获取
    返回 分割的基准及其位置
    :param array:
    :param left:
    :param right:
    :return:
    """
    # 将 最左边的数作为基准数
    partition = nums[left]

    i= left+1
    j = right
    # 直到 当 i ==j 的时候停止

    while (i<j):
        # 移动左边的位置，直到  array[i]>= partition：使得左边部分小于 partition
        if nums[i] < partition:
            i += 1
        # 移动右边的位置，直到  array[j]<= partition：使得右边部分大于等于 partition
        elif nums[j]>=partition:
            j -= 1
        # 当移动完左右位置的 i <j 的时候，交换两个的位置
        else:
            temp = nums[i]
            nums[i] = nums[j]
            nums[j] = temp
            # 因为右边位置指向的数一定 严格小于 partition，故置换后 i+=1
            i+=1
    # 当 i ==j 的时候停止，判断 该位置的数与partition的大小
    if nums[i]< partition:
        # 如果该数小于partition ,放在左边部分
        nums[left] = nums[i]
        nums[i] = partition
        partition_index = i
    else:
        # 如果 该数大于等于 partition,将 partition 与 i-1 那个数置换
        nums[left] = nums[i-1]
        nums[i-1] = partition
        partition_index = i-1
    return partition,partition_index

#  循环不变量：[left + 1, j] < pivot
    #  (j, i) >= pivot
def partitionV2(nums, left, right):
    """
    idea:  inplace implement
    数组分为： pivot | partitioned  | unpartitioned
    使用2个指针，i 指向左半边的边界，j 指向右半边的边界
    线性遍历 pivot 后面的元素，使已经被遍历的元素分为 两部分
    ex:
    38251476
    step 0: pivot =3,
    for i=1,j=1
    step 1: 3 8  251476
    step 2: 3 82  51764
    swap(i,j) : 3 28 51476, i++
    step 3: 3 285 1476
    step 4: 3 2851 476
    swap(i,j):  3 2158 476   i++

    :param nums:
    :param left: 因为 partition 是 递归作用在 array  的某个 左右 区间的
    :param right:
    :return:
    """
    # 记录基准值
    pivot = nums[left]
    # 记录最左边区间的边界位置
    i = left + 1
    # 右边部分的指针遍历整个数组，和 pivot 进行比较
    for j in range(left + 1, right ):
        if nums[j] < pivot:
            # 交换 位置，使得前 j个 数都小于 pivot
            nums[i], nums[j] = nums[j], nums[i]
            # 当前右边届的位置前向走一步
            i += 1
    # 交换边界和 pivot
    nums[left], nums[i-1] = nums[i-1], nums[left]
    return pivot,i-1

def randomPartition(nums, left, right):
    """
    The importance of the Pivot
    running time of quickSort depends on the quality of Pivot

    Ex: worst case：
    [1,2,3,4,5]
    O(n^2)

    Best pivot：
    最优的 pivot： median
    O(nlogn)
    master theroem : T(n) <= 2T(n/2) + O(n)

    How to choose pivot?
    Big idea: Random Pivots!

    QuickSort Theorem:
    for every input array of length n, the average running time of QuickSOrt (with random pivots) is O(nlogn)

    作者：liweiwei1419
    链接：https: // leetcode - cn.com / problems / kth - largest - element - in -an - array / solution / partitionfen - er - zhi - zhi - you - xian - dui - lie - java - dai - /
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    :param nums:
    :param left:
    :param right:
    :return:
    """
    # randint 是 随机挑选一个左右区间的位置，将初始位置与其交换，通过随机化避免最差的情况发生
    random_index = random.randint(left, right)
    nums[random_index], nums[left] = nums[left], nums[random_index]

    # 记录基准值
    pivot = nums[left]
    # 记录左边区间的最右侧边界位置，也是 pivot 最后的 index
    i = left +1
    # i指针指向需要移动比较的位置直到结束
    for j in range(left + 1, right):
        if nums[j] < pivot:
            # 交换 位置，使得前 j个 数都小于 pivot
            nums[i], nums[j] = nums[j], nums[i]
            # 当前右边届的位置前向走一步
            j += 1
    # 交换边界和 pivot
    nums[left], nums[i-1] = nums[i-1], nums[left]
    return pivot,i-1






if __name__ == '__main__':
    nums = [3,2,1,5,6,4]
    bubbleSort(nums)
    nums = [3, 2, 1, 5, 6, 4]
    # 选择排序
    selectSort(nums)
    # 选择排序： 堆实现
    heapSort(nums)


    quickSort(nums)
    # findKthLargest(nums_input=nums,K=2)