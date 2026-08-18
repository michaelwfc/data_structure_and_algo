/**

https://leetcode.com/problems/reverse-string/


* 344. Reverse String
Easy
Write a function that reverses a string. The input string is given as an array
of characters s.

You must do this by modifying the input array in-place with O(1) extra memory.


Example 1:
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]


Example 2:
Input: s = ["H","a","n","n","a","h"]
Output: ["h","a","n","n","a","H"]

*/
#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
  void reverseString(vector<char> &s) {
    // using two pointer from left and right
    int left = 0, right = s.size() - 1;
    while (left < right) {
      char temp = s[left];
      s[left] = s[right];
      s[right] = temp;
      left++;
      right--;
    }
  }
};

template <typename T> void printVector(vector<T> &vec) {
  for (int i = 0; i < vec.size(); i++) {
    cout << vec[i] << " ";
  }
  cout << endl;
}

int main() {
  //   'h'    // char
  // "h"    // string literal: const char[2]
  vector<char> s = {'h', 'e', 'l', 'l', 'o'};
  Solution solution = Solution();
  solution.reverseString(s);
  printVector(s);
}
