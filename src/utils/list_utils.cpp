#include <cassert>
#include <iostream>
#include <vector>
#include "list_utils.h"

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

void display(ListNode *list_node) {
  for (ListNode *p = list_node; p != nullptr; p = p->next) {
    std::cout << p->val << " -> ";
  }
  std::cout << "nullptr" << std::endl;
}