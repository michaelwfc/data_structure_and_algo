/**

438. Find All Anagrams in a String
Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.

 

Example 1:

Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".

Example 2:

Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
 

Constraints:

1 <= s.length, p.length <= 3 * 104
s and p consist of lowercase English letters

*/



#include <iostream>
#include <string>
#include <vector>
#include <map>
#include "linkedlist_and_arrays/list_utils.h"
using namespace std;



/**
using sliding window to find substring in s:
    if window_count == target_count:
        return left

sliding window framework:

target_count = count_char(p)
window_count = init_count_char()

while(right<size)
    right ++;
    window_count[right-1] ++

    while(right- left > p.size)
        shrink the window
    
    if(window size = target string size)
        compare the window count and target string count
        if true:
            return left

*/




class Solution {
public:
    vector<int> findAnagrams(string s, string p) {

        vector<int> anagrams_index;
        int left=0 ,right = 0;
        map<char,int> target_count = count_char(p);
        map<char, int> window_count = init_count_char();
        while(right< s.size()){
            right++;
            window_count[s[right-1]] ++;
            
            while(right-left > p.size()){
                window_count[s[left]] --;
                left ++;
            }

            if(right-left == p.size()){
                if( window_count== target_count){
                    anagrams_index.push_back(left); 
                }
            }

        }
        return anagrams_index;
        
    }
};



int main() {
    // string s = "cbaebabacd", p = "abc";
    string s = "abab", p = "ab";

  Solution solution = Solution();
  vector<int> anagrams = solution.findAnagrams(s, p);
  cout <<"Anagrams for p=" << p << " from " << s<< endl;
  for(int i=0;i< anagrams.size(); i++){
    cout<< "start at "<< anagrams[i]<<": "<< s.substr(anagrams[i],p.size())<< endl;
  }

}
