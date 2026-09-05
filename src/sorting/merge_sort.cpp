/**
Basic plan.
・Divide array into two halves.
・Recursively sort each half.
・Merge two halves.

Goal. Given two sorted subarrays a[lo] to a[mid] and a[mid+1] to a[hi],  replace
with sorted subarray a[lo] to a[hi].

https://leetcode.com/problems/merge-sorted-array/description/
https://leetcode.com/problem-list/merge-sort/


*/

#include <cassert>
#include <vector>
#include "linkedlist_and_arrays/list_utils.h"

using namespace std;

class MergeSort {

public:
  vector<int> sortV1(vector<int> nums) {
    vector<int> aux(nums.size());
    // for (int i = 0; i < nums.size(); i++) {
    //   aux[i] = nums[i];
    // }

    // size_t start = 0;
    // size_t end = nums.size();
    sort_and_merge(nums, aux, 0, nums.size());
    // return aux;
    return nums;
  };

  /**
  recursive method
  */
  void sort_and_merge(vector<int> &nums, vector<int> &aux, size_t start,
                      size_t end) {
    // if (start >= end) {
    //   return;
    // }
    // if (end == start + 1) {
    //   if (nums[start] < nums[end]) {
    //     aux[start] = nums[start];
    //     aux[end] = nums[end];
    //   } else {
    //     aux[start] = nums[end];
    //     aux[end] = nums[start];
    //   }
    //   return;
    // }

    if (end - start <= 1) {
      return;
    }

    size_t mid = start + (end - start) / 2;
    sort_and_merge(nums, aux, start, mid);
    sort_and_merge(nums, aux, mid, end);
    merge(nums, aux, start, mid, end);

    // // after sorted the left and right part, merge the 2 sorted array with
    // two pointer size_t left = start; size_t right = mid + 1;

    // while (left < mid && right < end) {
    //   if (aux[left] < aux[right]) {
    //     left++;
    //   } else {
    //     // [* i * *]   [* * j *]
    //     // how to merge in place
    //   }
    // }
  };

  void merge(vector<int> &nums, vector<int> &aux, size_t start, size_t mid,
             size_t end) {
    // precondition
    // assert left is sorted
    // assert right is sorted

    // copy the nums to aux
    for (int i = start; i < end; i++) {
      aux[i] = nums[i];
    }
    size_t left = start;
    size_t right = mid;
    for (int i = start; i < end; i++) {
      if (left >= mid) {
        nums[i] = aux[right];
        right++;
      } else if (right >= end) {
        nums[i] = aux[left];
        left++;
      } else if (aux[left] < aux[right]) {
        nums[i] = aux[left];
        left++;
      } else {
        nums[i] = aux[right];
        right++;
      }
    }

    // postcondtion
  }
};

int main() {
  MergeSort merge_sort = MergeSort();
  vector<int> nums = {5, 2, 3, 1};
  vector<int> expected = {1, 2, 3, 5};
  vector<int> output = merge_sort.sortV1(nums);
  assert(output == expected);

  vector<int> nums2 = {5,1,1,2,0,0};
  vector<int> expected2 = {0,0,1,1,2,5};
  vector<int> output2 = merge_sort.sortV1(nums2);
  assert(output2 == expected2);
  
  printArrary(output2);
}