/**
3. Longest Substring Without Repeating Characters
Given a string s, find the length of the longest substring without duplicate characters.


Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.


Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.


Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

using sliding window to find substring without no duplcatate char

longest_substring =""
window_count = {}
while( right < size)
    right ++
    if(no_duplicate)
        window.add()
        if(window>logest_substring)
            update longest_substring
        
        countinue
    
    shring the window
    window_count[left] --;
    left ++
*/
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include "linkedlist_and_arrays/list_utils.h"

using namespace std;


class Solution {
public:
    string lengthOfLongestSubstring(string s) {
        int left=0, right = 0;
        string longest_substring = "";
        string window ="";
        map<char,int> window_count =init_count_char();
        while(right < s.size()){
            right ++;
            window.push_back(s[right-1]);
            window_count[s[right-1]] ++;
            cout << "right ++, [left,right)= [" << left << ',' << right << "), "
                << "window: " << window << endl;

            // if no duplcated
            if(window_count[s[right-1]]==1){
                // if window > longest_substring
                if(window.size() > longest_substring.size()){
                    longest_substring = window;
                    cout << "update longest_substring, [left,right)= [" << left << ',' << right << "), "
                    << "window: " << window << endl;

                }
                continue;
            }
            
            // while duplicated, shrink the window
            while(window_count[s[right-1]] >1)
            {
                window_count[s[left]] --;
                window.erase(0,1);
                left ++;
                cout << "left ++, [left,right)= [" << left << ',' << right << "), "
                << "window: " << window << endl;

            }
           
        }
        return longest_substring;
        
    }
};

int main(){
    // string s = "abcabcbb";
    string s = "bbbbb";
    // string s = "pwwkew";

    Solution solution = Solution();
    string longest_substring = solution.lengthOfLongestSubstring(s);
    cout<< "Longest substring without duplicate chars in: "<< endl << s << endl<<"length= "<< longest_substring.size()<<endl << longest_substring<< endl;

}