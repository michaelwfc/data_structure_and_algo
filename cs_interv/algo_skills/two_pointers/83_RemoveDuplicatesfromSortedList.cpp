/**
https://leetcode.com/problems/remove-duplicates-from-sorted-list/description/

Given the head of a sorted linked list, delete all duplicates such that each
element appears only once. Return the linked list sorted as well. Example 1:

Input: head = [1,1,2]
Output: [1,2]
Example 2:

Input: head = [1,1,2,3,3]
Output: [1,2,3]

Constraints:

The number of nodes in the list is in the range [0, 300].
-100 <= Node.val <= 100
The list is guaranteed to be sorted in ascending order.

*/

#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

// Definition for singly-linked list.
struct ListNode {
  int val;
  ListNode *next;
  ListNode() : val(0), next(nullptr) {}
  ListNode(int x) : val(x), next(nullptr) {}
  ListNode(int x, ListNode *next) : val(x), next(next) {}
};

class Solution {
public:
  ListNode *deleteDuplicates(ListNode *head) {
    // two pointer method: slow, fast
    ListNode *slow = head;
    ListNode *fast = head;
    while (fast != nullptr) {
      if (slow->val != fast->val) {
        // move fast node after slow
        slow->next = fast;
        // update fast and slow
        slow = slow->next;
      }
      fast = fast->next;
    }
    return head;
  }
};

ListNode *createLinkedList(std::vector<int> arr) {
  // without 虚拟 head

  if (arr.empty()) {
    return nullptr;
  }

  ListNode *head = new ListNode(arr[0]);
  ListNode *prev = head;
  for (int i = 1; i < arr.size(); i++) {
    ListNode *current = new ListNode(arr[i]);
    prev->next = current;
    prev = current;
  }
  return head;
};

int main() {
  Solution solution;
  vector<int> nums1 = {1, 1, 2};

  ListNode *head1 = createLinkedList(nums1);
  ListNode *output1 = solution.deleteDuplicates(head1);
  assert(output1->val == 1);
  assert(output1->next->val == 2);
  cout << "completed test 1" << endl;

  vector<int> nums2 = {1, 1, 2, 3, 3};
  ListNode *head2 = createLinkedList(nums2);
  ListNode *output2 = solution.deleteDuplicates(head2);
  assert(output2->val == 1);
  assert(output2->next->val == 2);
  assert(output2->next->next->val == 3);
  cout << "completed test 2" << endl;
}
