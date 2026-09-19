/**
Given two strings s1 and s2, return true if s2 contains a permutation of s1, or
false otherwise.

In other words, return true if one of s1's permutations is the substring of s2.

Example 1:

Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
Example 2:

Input: s1 = "ab", s2 = "eidboaoo"
Output: false


Constraints:

1 <= s1.length, s2.length <= 104
s1 and s2 consist of lowercase English letters.


*/

#include <algorithm>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#include "linkedlist_and_arrays/list_utils.h"

using namespace std;



class Solution1 {
public:
  bool checkInclusion(string s1, string s2) {
    int left = 0, right = 0;
    map<char, int> required_count = count_char(s1);
    map<char, int> window_count = init_count_char();
    ;

    for (int left = 0; left < s2.size() - s1.size(); left++) {
      // it rebuilds the character-count map from scratch for every window:
      window_count = count_char(s2.substr(left, s1.size()));
      if (window_count == required_count) {
        return true;
      }
    }
    return false;
  }
};

/**
滑动窗口可以归为快慢双指针，一快一慢两个指针前后相随，中间的部分就是窗口。
滑动窗口算法技巧主要用来解决子数组问题，比如让你寻找符合某个条件的最长/最短子数组。

Sliding widows framework
The two pointers:  left, right
window = s2[left ... right)
right is responsible for: expanding the window by adding a character.
What does left do?  Once the window becomes too large, left removes the
character on the left.

left= 0, right=0

required_count = count(s1)

while( rigth < size)
    // add to windows
    right++
    add s2[right] to window_count

    while( window is too large)
        //move left
        remove th s2[left] to window_count
        left ++

    if(window size = s1 size)
        compare the window_count to required_count
        if same:
            return true


*/

class Solution {
public:
  bool checkInclusion(string s1, string s2) {
    int left = 0, right = 0;
    map<char, int> required_count = count_char(s1);
    map<char, int> window_count = init_count_char();

    string window = "";

    while (right < s2.size()) {
      // expand windows from right
      right++;
      window_count[s2[right - 1]]++;
      window = s2.substr(left, right - left);
      cout << "right ++, [left,right)= [" << left << ',' << right << "), "
           << "window: " << window << endl;

      // while window need shrink from left
      while (right - left > s1.size()) {
        // move the left
        window_count[s2[left]]--;
        left++;
        window = s2.substr(left, right - left);
        cout << "left ++, [left,right)= [" << left << ',' << right << "), "
             << "window: " << window << endl;
      }

      // while window size equat to target window
      if (right - left == s1.size()) {
        // compare the count
        if (window_count == required_count) {
          return true;
        }
      }
    }

    return false;
  }
};

int main() {
  string s1 = "ab", s2 = "eidbaooo";
  //   string s1 = "ab", s2 = "eidboaoo";

  //   Solution1 solution = Solution1();
  Solution solution = Solution();
  bool is_in = solution.checkInclusion(s1, s2);
  cout << "s1: " << s1 << endl << "s2: " << s2 << endl;
  cout << "is or not : " << is_in << endl;
}
