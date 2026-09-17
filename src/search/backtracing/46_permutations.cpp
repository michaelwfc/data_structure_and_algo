/**
https://leetcode.com/problems/permutations/description/

46. Permutations
Medium
Topics
premium lock icon
Companies
Given an array nums of distinct integers, return all the possible permutations.
You can return the answer in any order.



Example 1:

Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
Example 2:

Input: nums = [0,1]
Output: [[0,1],[1,0]]
Example 3:

Input: nums = [1]
Output: [[1]]


Constraints:

1 <= nums.length <= 6
-10 <= nums[i] <= 10
All the integers of nums are unique.


*/

#include <algorithm>
#include <iostream>
#include <queue>
#include <string>
#include <vector>

using namespace std;

/**
brute force
find all the permutation of s1, check if it is a substring of s2
how to calculate the permutation of s1

What your current code is doing
For:
string s1 = "abc";

You first generate position permutations:
{0}
{1}
{2}

Then extend them:
{0, 1}
{0, 2}
{1, 0}
{1, 2}
{2, 0}
{2, 1}

Finally:
{0, 1, 2} -> "abc"
{0, 2, 1} -> "acb"
{1, 0, 2} -> "bac"
{1, 2, 0} -> "bca"
{2, 0, 1} -> "cab"
{2, 1, 0} -> "cba"

This is essentially BFS over a permutation tree.
Your queue is storing the partial permutations from the previous level:
queue<vector<int>> q;

Then you extend every partial permutation with each unused position.
That algorithm is logically sound. The issue is the amount of bookkeeping.


Your BFS-style approach
Explicitly stores partial permutations in a queue.

Queue: {0}, {1}, {2}
      |
Queue: {0,1}, {0,2}, ...
      |
Complete permutations


2. Simpler solution: Recursive backtracking

Backtracking approach
Maintains only the current path and recursively explores choices.

current = {}
      |
current = {0}
      |
current = {0,1}
      |
current = {0,1,2}
      |
Then undo the last choice and try another branch.


Instead of explicitly maintaining:
- position_permutations
- queue
- range
- contains()
- get_permutation()
- permutations.clear()

we can use:
- current: the permutation being built.
- used: which positions have already been selected.
- backtrack(): generate the next character.
*/

class Solution1 {
public:
  vector<vector<char>> get_permutations(string s1) {
    size_t size = s1.size();

    vector<int> positions;
    for (int i = 0; i < size; i++) {
      positions.push_back(i);
    }

    vector<vector<int>> position_permutations = permute(positions);
    vector<vector<char>> permutations;

    for (auto positions : position_permutations) {
      vector<char> permutation;
      for (int p : positions) {
        permutation.push_back(s1[p]);
      }
      permutations.push_back(permutation);
    }
    return permutations;
  }

private:
  vector<vector<int>> permute(vector<int> positions) {
    size_t size = positions.size();
    vector<vector<int>> permuatations;
    for (int digit = 0; digit < size; digit++) {
      // find the range for digit
      vector<int> range;
      // when digit=0, all the positions
      // when digit =1, all positions expect the ones in permuatation
      if (digit == 0) {
        range = positions;
        for (int p : range) {
          permuatations.push_back({p});
        }
      } else {
        // create a queue from origial permuations
        queue<vector<int>> q;
        for (int p = 0; p < permuatations.size(); p++) {
          auto it = permuatations.begin() + p;
          auto permuation = *it;
          q.push(permuation);
        }

        permuatations.clear(); // clear the permuation from permuatations

        // get the current permuation from the queue
        while (!q.empty()) {
          vector<int> permuation = q.front();
          q.pop();

          // get the position range expect the ones in the current permuation
          range.clear();
          for (int i = 0; i < size; i++) {
            if (!contains(permuation, i)) {
              range.push_back(i);
            }
          }

          for (int p : range) {
            // create a new permutation each pending
            vector<int> new_p = permuation;
            new_p.push_back(p);
            permuatations.push_back(new_p);
          }
        }
      }
    }
    return permuatations;
  }

  bool contains(const vector<int> &v, int x) {
    return find(v.begin(), v.end(), x) != v.end();
  }
};

/**

Backtracking is one of the most important algorithmic techniques for solving
problems that involve making a sequence of choices. You have already implemented
a BFS-style permutation generator using a queue. Backtracking solves the same
problem by exploring one path at a time, then undoing choices to explore other
paths.


The core idea is:    Choose → Explore → Undo

Let's understand it from first principles.

1. What problem does backtracking solve?
Suppose you want to generate all permutations of:
string s = "abc";

You need to arrange the three characters in every possible order:
abc
acb
bac
bca
cab
cba

At the first position, you have three choices:
Choose 'a'
Choose 'b'
Choose 'c'

After choosing 'a', you have two remaining choices:
a
├── ab
│   └── abc
└── ac
    └── acb

After finishing all permutations starting with 'a', you go back and try 'b',
then 'c'. This process of going back and trying another choice is backtracking.




2. Think of it as exploring a decision tree
For "abc", the complete decision tree is:
Start
""

Each node represents a partial solution. Each branch represents a choice.

The algorithm must visit every leaf to generate all permutations.
The difference between ordinary recursion and backtracking is that backtracking
explicitly restores the state after exploring a choice.


3. The three steps
Let's use a smaller example:
s1 = "ab";

We want:
ab
ba

Step 1: Choose
Start with an empty permutation:
current = "";
used = {false, false};

Choose the first character 'a':
current.push_back('a');
used[0] = true;

Now:
current = "a"
used    = [true, false]

We have committed to using 'a' as the first character.
Step 2: Explore
Recursively generate all valid completions of "a".
The remaining unused character is 'b'.
current.push_back('b');
used[1] = true;

Now:
current = "ab"
used    = [true, true]

The permutation is complete. Save it:
permutations.push_back(current);

We have found:
ab

Step 3: Undo
Now we return from the recursive call.
We must restore the state:
current.pop_back();
used[1] = false;

State becomes:
current = "a"
used    = [true, false]

Then we return to the previous recursive call and undo the choice of 'a':
current.pop_back();
used[0] = false;

State becomes:
current = ""
used    = [false, false]

Now we can choose 'b' first and generate:
ba

*/

class Solution2 {
public:
  vector<string> get_permutations(string s) {

    vector<string> permutations; // store all the reuslts

    string current = ""; // the permutation being built.
    vector<bool> used(s.size(),
                      false); // which positions have already been selected.
    backtrack(s, current, used, permutations);
    return permutations;
  }

private:
  // generate the next character.
  void backtrack(const string & s, string &current, vector<bool> &used,
                 vector<string> &results) {
    size_t string_size = s.size();
    // if path get the end, stop explore
    if (current.size() == string_size) {
      results.push_back(current);
      return;
    }
    // otherwise loop all the choices
    for (int i = 0; i < string_size; i++) {
      // if not used add to current path
      if (!used[i]) {
        //choose
        current.push_back(s[i]);
        used[i] = true; // mark position i used
        
        // explore
        backtrack(s, current, used, results);

        // undo
        current.pop_back(); // get back to the original path
        used[i] = false;  // get back to the original choices
      }
    }
  }
};

int main() {
  string s1 = "abcde";
  //   Solution1 solution = Solution1();
  //   vector<vector<char>> permutations = solution.get_permutations(s1);

  Solution2 solution = Solution2();
  vector<string> permutations = solution.get_permutations(s1);
  cout << "permuations size = " << permutations.size() << endl;

  for (auto permuation : permutations) {
    for (auto c : permuation) {
      cout << c;
    }
    cout << endl;
  }

  return 0;
}
