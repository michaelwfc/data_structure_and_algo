/**
计算机的两种存储方式，顺序存储（数组）和链式存储（链表）都讲完了，之后的所有数据结构都是基于这两种存储方式之上玩花活。

队列只能在一端插入元素，另一端删除元素；栈只能在某一端插入和删除元素。
队列只允许在队尾插入元素，在队头删除元素，栈只允许在栈顶插入元素，从栈顶删除元素。

*/
#include <iostream>
#include <list> // 用链表作为底层数据结构实现栈
#include <type_traits>
#include <vector>

using namespace std;

template <typename E> class MyQueue {
public:
  // 向队尾插入元素，时间复杂度 O(1)
  void push(const E &e) {};

  // 查看队头元素，时间复杂度 O(1)
  E front() const; // peek in java

  // 从队头删除元素，时间复杂度 O(1)
  E pop();

  // 返回队列中的元素个数，时间复杂度 O(1)
  int size() const;
};

template <typename E> class MyStack {
public:
  // 向栈顶插入元素，时间复杂度 O(1)
  void push(const E &e) {};

  // 查看栈顶元素，时间复杂度 O(1)
  E peek();

  // 从栈顶删除元素，时间复杂度 O(1)
  E pop();

  // 返回栈中的元素个数，时间复杂度 O(1)
  size_t size() const;
};


template <typename E> class MyLinkedQueue : MyQueue<E> {
private:
  list<E> _list;

public:
  // 在队尾插入数据
  void push(const E &e) { _list.push_back(e); }
  
  // 在对头删除数据
  E pop(){
    E front = _list.front();
    _list.pop_front();
    return front;
  }

  // 在对头查看数据
  E front() const{
    return _list.front();
  }

  // 返回队列中的元素个数
  size_t size(){
    return _list.size();
  }
};


template <typename E> class MyArrayQueue:MyQueue<E>{
private:
  vector<E> _array;
};