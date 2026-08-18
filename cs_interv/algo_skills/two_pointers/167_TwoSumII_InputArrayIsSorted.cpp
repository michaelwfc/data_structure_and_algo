/**
tutorial:
https://www.bilibili.com/video/BV1iG411W7Wm/?spm_id_from=333.788.videopod.sections&vd_source=b3d4057adb36b9b243dc8d7a6fc41295
leecode:
https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
soution:
https://www.bilibili.com/video/BV1VZ4y1M7eu/?spm_id_from=333.337.search-card.all.click&vd_source=b3d4057adb36b9b243dc8d7a6fc41295

167. Two Sum II - Input Array Is Sorted
Given a 1-indexed array of integers numbers that is already sorted in
non-decreasing order, find two numbers such that they add up to a specific
target number. Let these two numbers be numbers[index1] and numbers[index2]
where 1 <= index1 < index2 <= numbers.length.

Return the indices of the two numbers index1 and index2, each incremented by
one, as an integer array [index1, index2] of length 2.

The tests are generated such that there is exactly one solution. You may not use
the same element twice.

Your solution must use only constant extra space.



Example 1:

Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We
return [1, 2]. Example 2:

Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We
return [1, 3]. Example 3:

Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We
return [1, 2].


Constraints:

2 <= numbers.length <= 3 * 104
-1000 <= numbers[i] <= 1000
numbers is sorted in non-decreasing order.
-1000 <= target <= 1000
The tests are generated such that there is exactly one solution.



Idea:
method 1: 暴力搜索法
for i in range:
    for j= i+1 to range:
        if nums[i] +nums[j] ==target:
            return

method 2:  双指针法
只要数组有序，就应该想到双指针技巧。这道题的解法有点类似二分查找，通过调节 left
和 right 就可以调整 sum 的大小： 一左一右两个指针相向而行
1. 初始条件， left pointer  point to first, right pointer point to last
    left is the smallest possible position,
    right is the largest possible position


2. 进入循环 while loop，循环条件 left < right
3. 每次循环：
    if left# + right# == target, return
    if left# + right# < target, left ++：
        right is the largest possible position,
        index <= left is impossible,
        left ++ become the new smallest possible position

    if left# + right# > target, right --
        left is the smallest possible position
        left >= right is impossible,
        right -- become the new largest possible position


*/
#include <cassert>
#include <vector>

using namespace std;

class Solution {
public:
  vector<int> twoSum(vector<int> &numbers, int target) {
    int left = 0, right = numbers.size() - 1;
    while (left < right) {
      int sum = numbers[left] + numbers[right];
      if (sum == target) {
        return vector({left + 1, right + 1});
      } else if (sum > target) {
        right--;
      } else {
        left++;
      }
    }

    return vector(-1, -1);
  }
};

int main() {
  vector<int> numbers1 = vector({2, 7, 11, 15});
  int target1 = 9;
  Solution solution = Solution();
  vector<int> output1 = solution.twoSum(numbers1, target1);
  assert(output1[0] == 1);
  assert(output1[1] == 2);

  int target2 = 18;
  vector<int> output2 = solution.twoSum(numbers1, target2);
  assert(output2[0] == 2);
  assert(output2[1] == 3);
}
