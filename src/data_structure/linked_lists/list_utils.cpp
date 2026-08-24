#include "list_utils.h"
#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

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

ListNode *createCycleLinkedList(std::vector<int> arr, int pos) {
  if (arr.empty()) {
    return nullptr;
  }

  ListNode *dummy = new ListNode();
  ListNode *pos_node;
  ListNode *tail;
  ListNode *p = dummy;
  for (int i = 0; i < arr.size(); i++) {
    ListNode *n = new ListNode(arr[i]);
    // record the tail
    if (i == arr.size() - 1) {
      tail = n;
    }
    // record the position node
    if (i == pos) {
      pos_node = n;
    }

    // link p to n;
    p->next = n;
    // update p
    p = p->next;
  }
  // at the end of list, link p to pos_node
  if (pos != -1) {
    tail->next = pos_node;
  }

  return dummy->next;
};

void display(ListNode *list_node) {
  for (ListNode *p = list_node; p != nullptr; p = p->next) {
    std::cout << p->val << " -> ";
  }
  std::cout << "nullptr" << std::endl;
}