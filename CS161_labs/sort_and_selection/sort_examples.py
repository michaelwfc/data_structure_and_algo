from typing import List
from sort_and_selection.sort_algorithms import mergerSort


def mergeSortedArrays(nums1:List,nums2:List):
    """
    88. 合并两个有序数组
    给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。

    说明：

    初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
    你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/merge-sorted-array
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    """
    sortedArray = []
    # 创建两个指针
    index1=0
    index2=0
    # 循环直到两个指针都移动数组的最末端
    while(index1< nums1.__len__()-1) or (index2 < nums2.__len__()-1):
        # 当 index1  已经越界的时候：
        if (index1 > nums1.__len__()-1):
            sortedArray.append(nums2[index2])
            index2 += 1
        elif (index2 > nums2.__len__()-1):
            sortedArray.append(nums1[index1])
            index1 += 1
        # 当 index1指向的数小于等于 index2指向的数 ：
        elif nums1[index1] <= nums2[index2]:
            sortedArray.append(nums1[index1])
            index1 += 1
        else:
            sortedArray.append(nums2[index2])
            index2 += 1
    return sortedArray

def merge(self, nums1, m, nums2, n):
    """
    :type nums1: List[int]
    :type m: int
    :type nums2: List[int]
    :type n: int
    :rtype: void Do not return anything, modify nums1 in-place instead.
    """
    # Make a copy of nums1.
    nums1_copy = nums1[:m]
    nums1[:] = []

    # Two get pointers for nums1_copy and nums2.
    p1 = 0
    p2 = 0

    # Compare elements from nums1_copy and nums2
    # and add the smallest one into nums1.
    # 这儿循环的条件严格限制，后面将剩余的数补全，更加简单一些
    while p1 < m and p2 < n:
        if nums1_copy[p1] < nums2[p2]:
            nums1.append(nums1_copy[p1])
            p1 += 1
        else:
            nums1.append(nums2[p2])
            p2 += 1

    # if there are still elements to add
    if p1 < m:
        nums1[p1 + p2:] = nums1_copy[p1:]
    if p2 < n:
        nums1[p1 + p2:] = nums2[p2:]


def countInversions(nums:List[int]):
    """
    Motivation: numerical similarity measure between two ranked list
    场景： 协同过滤
    Key Idea： Divide & Conquer
    Call an inversion (i,j) with i<j
        left if i,j <=n/2
        right if i,j> n/2
        split if i<=n/2,j>n/2

    Pseudo code
    Count(arrya A, length n)
    if n=1: return 0
    else:
        x = Count(1st half of A, n/2)
        y = Count(2nd half of A,n/2)
        z = CountSplit(A,n) # 类似于 merge sort 中merge
    return x+y+z

    Goal:
    Implement CountSplitInversion in linear O(n)


    Key Idea2： have recursive calls both count inversion and sort
    Pseudo code
    Sort_and_Count(arrya A, length n)
    if n=1: return 0
    else:
        (B,x） = Sort_and_Count(1st half of A, n/2)
        （C,y) = Sort_and_Count(2nd half of A,n/2)
        (D,z) = Merge_and_CountSplit(B,C) # 类似于 merge sort 中merge
    return x+y+z


    :param nums:
    :return:
    """

    n = nums.__len__()
    # 确定递归停止条件
    if n==1:
        return (nums,0)
    else:
        # 对左右两边进行递归:
        left_half_nums = nums[:n//2]
        right_half_nums = nums[n//2:]
        left_half_sorted_nums,left_half_count = countInversions(left_half_nums)
        right_half_sorted_nums,right_half_count = countInversions(right_half_nums)
        sorted_nums,split_count = merge_and_count_split_inversion(left_half_sorted_nums,right_half_sorted_nums)
        return sorted_nums,left_half_count+right_half_count+split_count


def merge_and_count_split_inversion(left_sorted_nums:List[int],right_sorted_nums:List[int]):
    left_size = left_sorted_nums.__len__()
    right_size = right_sorted_nums.__len__()
    i=0
    j=0
    split_count =0
    sorted_nums = []
    for index in range(left_size+right_size):
        # 当左边数组已经遍历完
        if i>left_size-1 and j<right_size:
            sorted_nums.append(right_sorted_nums[j])
            j+=1
        # 当右边数组已经遍历完，因为所有左边的数都已经计算 spilt_conversion,直接将所有左边的数放入排序的数组
        elif i< left_size and j>right_size-1:
            sorted_nums.append(left_sorted_nums[i])
            # 严格判断 左边的元素 > 右边数组最后一个元素,则将剩余都个数都记录为split_count
            # if left_sorted_nums[i]> right_sorted_nums[-1]:
            #     split_count += left_size-i
            i+=1
        else:
            left_num = left_sorted_nums[i]
            right_num  = right_sorted_nums[j]
            if left_num<= right_num:
                sorted_nums.append(left_num)
                i+=1
            else:
                # 如果左边的数 > 右边的数
                sorted_nums.append(right_num)
                split_count+= left_size-i
                j+=1
    return sorted_nums,split_count



if __name__ == '__main__':
    from random import random
    random.seed(1234)
    nums1 = [1, 2, 3]
    nums2 = [2, 5, 6]
    sortedArray = mergeSortedArrays(nums1,nums2)
    print("nums1={},nums2={},sortedArray={}".format(nums1,nums2,sortedArray))
    random.shuffle(sortedArray)

    mergerSort(nums= sortedArray)

    nums = [3, 2, 1, 5, 6, 4]
    sorted_nums,count = countInversions(nums=nums)
