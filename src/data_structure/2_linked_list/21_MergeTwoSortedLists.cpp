/**
https://leetcode.com/problems/merge-two-sorted-lists/description/

You are given the heads of two sorted linked lists list1 and list2.

Merge the two lists into one sorted list. The list should be made by splicing
together the nodes of the first two lists.

Return the head of the merged linked list.


Example 1:

Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
Example 2:

Input: list1 = [], list2 = []
Output: []
Example 3:

Input: list1 = [], list2 = [0]
Output: [0]


Constraints:

The number of nodes in both lists is in the range [0, 50].
-100 <= Node.val <= 100
Both list1 and list2 are sorted in non-decreasing order.

*/

//  * Definition for singly-linked list.
#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

struct ListNode {
  int val;
  ListNode *next;
  ListNode() : val(0), next(nullptr) {}
  ListNode(int x) : val(x), next(nullptr) {}
  ListNode(int x, ListNode *next) : val(x), next(next) {}
};

ListNode *createLinkedList(std::vector<int> arr) {
  if (arr.empty()) {
    return nullptr;
  }

  ListNode *head = new ListNode(arr[0]);
  ListNode *current = head;
  for (int i = 1; i < arr.size(); i++) {
    current->next = new ListNode(arr[i]);
    current = current->next;
  }
  return head;
};

void display(ListNode *list_node) {
  for (ListNode *p = list_node; p != nullptr; p = p->next) {
    std::cout << p->val << " -> ";
  }
  std::cout << "nullptr" << std::endl;
}

class Solution1 {
public:
  ListNode *mergeTwoLists(ListNode *list1, ListNode *list2) {
    // using while loop until current_node.next is nullptr
    ListNode *head;
    ListNode *current_list1 = list1;
    ListNode *current_list2 = list2;
    // get the head
    if (list1 == nullptr && list2 == nullptr) {
      return nullptr;
    } else if (list1 != nullptr && list2 == nullptr) {
      return list1;
    } else if (list1 == nullptr && list2 != nullptr) {
      return list2;
    } else {
      // build head from list1
      if (list1->val < list2->val) {
        head = list1;
        // update current_list1
        current_list1 = list1->next;
        head->next = nullptr;
      } else {
        head = list2;
        // update current_list2
        current_list2 = list2->next;
        head->next = nullptr;
      }
    }
    // current node should be head
    ListNode *current_node = head;
    // loop util all list both are nullptr
    while (current_list1 != nullptr || current_list2 != nullptr) {
      if (current_list1 == nullptr) {
        // connect current_list2 to current_node
        current_node->next = current_list2;
        break;
      } else if (current_list2 == nullptr) {
        current_node->next = current_list1;
        break;
      } else {
        if (current_list1->val < current_list2->val) {
          current_node->next = current_list1;
          // update current_list1;
          current_list1 = current_list1->next;
        } else {
          current_node->next = current_list2;
          current_list2 = current_list2->next;
        }

        // update current_node
        current_node = current_node->next;
        current_node->next = nullptr;
      }
    }
    return head;
  }
};

class Solution2 {
public:
  ListNode *mergeTwoLists(ListNode *list1, ListNode *list2) {
    // 创建一个虚拟头节点，这样不需要在去找 head
    ListNode *dummy = new ListNode(-1);

    // two pointer
    ListNode *p1 = list1;
    ListNode *p2 = list2;
    ListNode *p = dummy;
    while (p1 != nullptr || p2 != nullptr) {
      if (p1 == nullptr) {
        p->next = p2;
        break;
      } else if (p2 == nullptr) {
        p->next = p1;
        break;
      } else {
        if (p1->val < p2->val) {
          p->next = p1;      // conenct p1 to p->next;
          p1 = p1->next;     // update p1
          p = p->next;       // update p
          p->next = nullptr; // set p->next to null
        } else {
          p->next = p2;
          p2 = p2->next;
          p = p->next;
          p->next = nullptr;
        }
      }
    }
    return dummy->next;
  }
};

int main() {

  vector<int> list1 = {1, 3, 4}, list2 = {1, 2, 4};
  // vector<int> list1 = {2}, list2 = {1};

  ListNode *list_node1 = createLinkedList(list1);
  ListNode *list_node2 = createLinkedList(list2);

  //   Solution1 solution = Solution1();
  Solution2 solution = Solution2();

  ListNode *merged_list = solution.mergeTwoLists(list_node1, list_node2);
  display(merged_list);
}