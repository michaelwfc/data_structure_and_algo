/**
https://leetcode.com/problems/number-of-islands/description/

Given an m x n 2D binary grid grid which represents a map of '1's (land) and
'0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands
horizontally or vertically. You may assume all four edges of the grid are all
surrounded by water.



Example 1:

Input: grid = [
  ['1','1','1','1','0'],
  ['1','1','0','1','0'],
  ['1','1','0','0','0'],
  ['0','0','0','0','0']
]
Output: 1


Example 2:

Input: grid = [
  ['1','1','0','0','0'],
  ['1','1','0','0','0'],
  ['0','0','1','0','0'],
  ['0','0','0','1','1']
]
Output: 3


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] is '0' or '1'.


for position in positions:
    if (position is explored){
        continur
    }
    if (postion is water)
        explored
        continure
    if (position is land)
        mark it explored
        explore(position) -> 4 directotions
        island ++

dfs(position)
    // for loop each dierectations
    loop for 4 dierectations
        // explore
        if(is_water)
            mark it explored
            contiure
        if(unexplored and is_land)
            explore(next_position)

DFS（Depth-First Search）的核心思想只有一句： 沿着一条路径一直往下走，走到底再回来。
void dfs(Node* node) {
    if (node == nullptr) {
        return;
    }

    cout << node->val << endl;

    dfs(node->left);
    dfs(node->right);
}


回溯可以理解成： DFS + 对路径/状态进行修改和恢复。
*/

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
  int numIslands(vector<vector<char>> &grid) {
    size_t length = grid.size();
    size_t width = grid[0].size();

    vector<vector<bool>> explored(length, vector<bool>(width, false));
    int num_island = 0;

    // loop all the positions
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        if (explored[i][j]) {
          continue;
        }
        if (grid[i][j] == '0') {
          explored[i][j] = true;
          continue;
        }
        explored[i][j] = true;
        dfs({i, j}, grid, explored);
        num_island++;
        cout << "explore at " << i <<  "," << j <<" , num_island = "<< num_island<< endl;
      }
    }
    return num_island;
  };

private:
  void dfs(vector<int> position, const vector<vector<char>> &grid,
               vector<vector<bool>> &explored) {
    size_t length = grid.size();
    size_t width = grid[0].size();
    vector<vector<int>> moves = get_moves(position, length, width);
    // loop all the choices
    for (vector<int> move : moves) {
      int l = move[0];
      int r = move[1];
      if (explored[l][r]) {
        continue;
      }
      if (grid[l][r] == '0') {
        explored[l][r] = true;
        continue;
      }
      // explore if to deep
      explored[l][r] = true;
      dfs({l, r}, grid, explored);
    }
  }

  vector<vector<int>> get_moves(vector<int> position, size_t length,
                                size_t width) {
    // move up
    vector<vector<int>> valid_moves;
    vector<int> up = {position[0], position[1] - 1};
    vector<int> down = {position[0], position[1] + 1};
    vector<int> left = {position[0] - 1, position[1]};
    vector<int> right = {position[0] + 1, position[1]};
    vector<vector<int>> moves = {up, down, left, right};
    for (vector<int> move : moves) {
      if (is_valid(move[0], move[1], length, width)) {
        valid_moves.push_back(move);
      }
    }
    return valid_moves;
  }

  bool is_valid(int i, int j, size_t length, size_t width) {
    if (i >= 0 && i < length && j >= 0 && j < width) {
      return true;
    }
    return false;
  }
};

int main() {
    vector<vector<char>> grid1 = {
        {'1', '1', '1', '1', '0'},
        {'1', '1', '0', '1', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '0', '0', '0'},
    };

  vector<vector<char>> grid2 = {{'1', '1', '0', '0', '0'},
                               {'1', '1', '0', '0', '0'},
                               {'0', '0', '1', '0', '0'},
                               {'0', '0', '0', '1', '1'}};

  Solution solution = Solution();
  int answer = solution.numIslands(grid1);
  cout << "answer = " << answer << endl;
}
