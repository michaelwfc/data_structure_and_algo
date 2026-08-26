#include <iostream>
#include <vector>

using namespace std;

template <typename T> class MyQueue {
public:
  // 向队尾插入元素，时间复杂度 O(1)
  void push(const T &e) {};

  // 查看队头元素，时间复杂度 O(1)
  T front() const; // peek
  
  // 从队头删除元素，时间复杂度 O(1)
  void pop();

  // 返回队列中的元素个数，时间复杂度 O(1)
  int size() const;
};

template <typename T> class MyStack {
public:
  // 向栈顶插入元素，时间复杂度 O(1)
  void push(const T &e) {};

  // 查看栈顶元素，时间复杂度 O(1)
  T peek();

  // 从栈顶删除元素，时间复杂度 O(1)
  void pop();

  // 返回栈中的元素个数，时间复杂度 O(1)
  size_t size() const;
};
