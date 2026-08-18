/**
https://labuladong.online/zh/algo/practice-in-action/nsum/

https://leetcode.com/problems/3sum/description/

Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.

Notice that the solution set must not contain duplicate triplets.



Example 1:

Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation:
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
Notice that the order of the output and the order of the triplets does not
matter.

Example 2:
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.


Example 3:
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.


Constraints:

3 <= nums.length <= 3000
-105 <= nums[i] <= 105


Hint 1
So, we essentially need to find three numbers x, y, and z such that they add up
to the given value. If we fix one of the numbers say x, we are left with the
two-sum problem at hand!

Hint 2
For the two-sum problem, if we fix one of the numbers, say x, we have to scan
the entire array to find the next number y, which is value - x where value is
the input parameter. Can we change our array somehow so that this search becomes
faster?

Hint 3
The second train of thought for two-sum is, without changing the array, can we
use additional space somehow? Like maybe a hash map to speed up the search?

Idea:
sort + two pointer

nums = [-1,0,1,2,-1,-4]
sort:
nums = [-4,-1,-1,0,1,2]

target = 0,
let x= -4, find [-1,-1,0,1,2] , target 4
then convert to two sums


*/
#include <algorithm>
#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
  vector<vector<int>> threeSum(vector<int> &nums) {
    sort(nums.begin(), nums.end());
    vector<vector<int>> output;
    for (int i = 0; i < nums.size(); i++) {
      int target = nums[i];
      // convert to twosum problem
      // sercha from i+1, size
      int left = i + 1;
      int right = nums.size() - 1;
      vector<int> output_index = twosum(nums, -target, left, right);
      if (output_index[0] != -1) {
        vector<int> output_triple = {target, nums[output_index[0]],
                                     nums[output_index[1]]};
        output.push_back(output_triple);
      }
    }
    return output;
  };

  vector<int> twosum(vector<int> &new_nums, int target, int left, int right) {
    while (left < right) {
      int sum = new_nums[left] + new_nums[right];
      if (sum == target) {
        return {left, right};
      } else if (sum < target) {
        left++;
      } else {
        right--;
      }
    }
    return {-1, -1};
  };
};

void printVector(vector<int> &nums) {
  for (int i = 0; i < nums.size(); i++) {
    cout << nums[i] << " ";
  }
  cout << endl;
}
int main() {
  Solution solution = Solution();
  vector<int> nums = {-1, 0, 1, 2, -1, -4};
  vector<vector<int>> output = solution.threeSum(nums);
  printVector(output[0]);
  printVector(output[1]);
  return 0;
}
