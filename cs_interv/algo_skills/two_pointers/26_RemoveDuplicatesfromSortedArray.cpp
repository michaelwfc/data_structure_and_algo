/**
https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/

Given an integer array nums sorted in non-decreasing order,
remove the duplicates in-place such that each unique element appears only once.
The relative order of the elements should be kept the same.

Consider the number of unique elements in nums to be
k​​​​​​​​​​​​​​. After removing duplicates, return
the number of unique elements k.

The first k elements of nums should contain the unique numbers in sorted order.
The remaining elements beyond index k - 1 can be ignored.

Custom Judge:

The judge will test your solution with the following code:

int[] nums = [...]; // Input array
int[] expectedNums = [...]; // The expected answer with correct length

int k = removeDuplicates(nums); // Calls your implementation

assert k == expectedNums.length;
for (int i = 0; i < k; i++) {
    assert nums[i] == expectedNums[i];
}
If all assertions pass, then your solution will be accepted.



Example 1:

Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of
nums being 1 and 2 respectively. It does not matter what you leave beyond the
returned k (hence they are underscores). Example 2:

Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of
nums being 0, 1, 2, 3, and 4 respectively. It does not matter what you leave
beyond the returned k (hence they are underscores).


Constraints:

1 <= nums.length <= 3 * 104
-100 <= nums[i] <= 100
nums is sorted in non-decreasing order.


Hint 1
In this problem, the key point to focus on is the input array being sorted. As
far as duplicate elements are concerned, what is their positioning in the array
when the given array is sorted? Look at the image below for the answer. If we
know the position of one of the elements, do we also know the positioning of all
the duplicate elements?

Hint 2
We need to modify the array in-place and the size of the final array would
potentially be smaller than the size of the input array. So, we ought to use a
two-pointer approach here. One, that would keep track of the current element in
the original array and another one for just the unique elements. Hint 3
Essentially, once an element is encountered, you simply need to bypass its
duplicates and move on to the next unique element.

*/

#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
  int removeDuplicates(vector<int> &nums) {
    if (nums.size() == 0) {
      return 0;
    }

    int size = nums.size();
    // slow to mark the unique value, fast is to explore
    int slow = 0, fast = 0;
    while (fast < size) {

      if (nums[fast] != nums[slow]) {
        // if not duplicate, move the slow point forward, swap the value at fast
        // and slow +1
        slow++;
        // this keep the [0,..., slow]  values are unique values
        nums[slow] = nums[fast];
        fast++;
      }
      // if duplicate , make the  fast pointer move forward
      fast++;
    }
    return slow + 1;
  }
};

void printSolution(vector<int> &nums, int output, vector<int> &expect_nums) {
  for (int i = 0; i < nums.size(); i++) {
    if (i < output) {
      cout << nums[i] << " ";
      assert(nums[i] == expect_nums[i]);
    } else {
      cout << "_ ";
    }
  }
  cout << endl;
}

int main() {
  vector<int> nums1 = vector({1, 1, 2});
  Solution solution = Solution();
  int output1 = solution.removeDuplicates(nums1);
  vector<int> expect_nums1 = vector({1, 2, 1});
  printSolution(nums1, output1, expect_nums1);

  vector<int> nums2 = vector({0, 0, 1, 1, 1, 2, 2, 3, 3, 4});
  int output2 = solution.removeDuplicates(nums2);
  vector<int> expect_nums2 = vector{0, 1, 2, 3, 4, 2, 2, 3, 3, 4};
  printSolution(nums2, output2, expect_nums2);

  return 0;
}