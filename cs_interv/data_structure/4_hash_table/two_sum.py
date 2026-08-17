"""
You are given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.



Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]
Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]


Constraints:

2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
Only one valid answer exists.


Follow-up: Can you come up with an algorithm that is less than O(n^2) time complexity?

"""

from typing import List


class Solution1:
    """
    Current: Array / Brute-Force Search
    Suggested:Array / Hash Table
    Key Idea: Find two indices in an array whose values sum to a specific target using efficient lookup.
    """

    def twoSum(self, nums: List[int], target: int) -> List[int]:
        for i in range(len(nums)):
            diff = target - nums[i]
            for j in range(i + 1, len(nums)):
                if diff == nums[j]:
                    return [i, j]
        return [None, None]


class Solution:
  def twoSum(self, nums: List[int], target: int) -> List[int]:
      # remember the number in a hash mapping
      hash_map = {}
      for  i in range(len(nums)):
        hash_map.update({nums[i]:i})
      
      for i in range(len(nums)):
        diff = target -nums[i]
        if diff in hash_map and i!= hash_map[diff]:
          j = hash_map[diff]
          return [i,j]
      return [None, None]

class TestSolution:
    def test_two_sum(self):
        solution = Solution()
        assert solution.twoSum([2, 7, 11, 15], 9) == [0, 1]
        assert solution.twoSum([3, 2, 4], 6) == [1, 2]
        assert solution.twoSum([3, 3], 6) == [0, 1]


if __name__ == "__main__":
    test_solution = TestSolution()
    test_solution.test_two_sum()
    print("All tests passed!")
