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

ListNode *createLinkedList(std::vector<int> arr);

ListNode *createCycleLinkedList(std::vector<int> arr, int pos);

void display(ListNode *list_node);



template <typename T>
void printArrary(vector<T> v){
  for(int i=0;i< v.size();i++){
    cout<< v[i] <<" ";
  }
  cout <<endl;
}