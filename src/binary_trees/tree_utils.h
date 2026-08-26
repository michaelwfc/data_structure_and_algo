#include <cstddef>
#include <optional>
#include <queue>
#include <vector>

using namespace std;

class TreeNode {
public:
  int val;
  TreeNode *left;
  TreeNode *right;
  TreeNode() : val(0), left(nullptr), right(nullptr) {}
  TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
  TreeNode(int x, TreeNode *left, TreeNode *right)
      : val(x), left(left), right(right) {}
};

TreeNode *createSubTree(TreeNode *root_node, const vector<optional<int>> &v,
                        int i);

TreeNode *createTreeV1(const vector<optional<int>> &v);

TreeNode *createTree(const vector<optional<int>> &v);