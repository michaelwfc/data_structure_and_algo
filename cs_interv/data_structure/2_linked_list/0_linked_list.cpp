/**
https://labuladong.online/zh/algo/essential-technique/linked-list-skills-summary/
*/
#include <cassert>
#include <cerrno>
#include <cmath>
#include <cstddef>
#include <iostream>
#include <stdexcept>
#include <vector>

// A linked list uses Node* because the nodes themselves are dynamically created
// and connected through memory addresses.

template <typename E> class SingleLinkedLisk {
private:
  // why use pointer Nonde* instead of instance Node:
  // A Node contains another Node, which contains another Node, which contains
  // another... There is no finite size. The compiler can't determine:
  // sizeof(Node) The pointer doesn't contain the next Node. It contains only
  // the address of the next Node. the size of a pointer is fixed.
  struct Node {
    E val;
    Node *next;

    Node(E value) : val(value), next(nullptr) {}
  };
  // 你以前要把在头部插入元素、在尾部插入元素和在中间插入元素几种情况分开讨论，现在有了头尾虚拟节点，无论链表是否为空，
  // 都只需要考虑在中间插入元素的情况就可以了，这样代码会简洁很多。
  // head and tail are not nodes; they are pointers that tell us where the
  // first/last nodes are.
  Node *head;
  Node *tail;
  int size;

public:
  SingleLinkedLisk() {
    this->head = new Node(E());
    this->tail = this->head;
    size = 0;
  }

  SingleLinkedLisk(std::vector<int> arr) { Node *head = createLinkedList(arr); }

  ~SingleLinkedLisk() {
    Node *current = head;
    while (current != nullptr) {
      Node *next = current->next;
      delete current;
      current = next;
    }
  }

  Node *createLinkedList(std::vector<int> arr) {
    if (arr.empty()) {
      return nullptr;
    }

    Node *current = head;
    for (int i = 0; i < arr.size(); i++) {
      current->next = new Node(arr[i]);
      current = current->next;
    }
    return head;
  };

  void display() {
    std::cout << "size = " << size << std::endl;
    for (Node *p = head->next; p != nullptr; p = p->next) {
      std::cout << p->val << " -> ";
    }
    std::cout << "nullptr" << std::endl;
  }

  // O(1)
  void addFirst(E element) {
    // create a new node
    Node *new_node = new Node(element);

    // use vitual head
    new_node->next = head->next;
    head->next = new_node;
    if (size == 0) {
      tail = new_node;
    }
    size++;
  }

  void addLast(E element) {
    // if no tail node, we use while loop from head util node next point to
    // nullptr 这个操作的时间复杂度是  O(n)，因为需要先遍历到链表尾部。
    // 当然，如果我们持有对链表尾节点的引用，那么在尾部插入新节点的操作就会变得非常简单，不用每次从头去遍历了。这个优化会在后面具体实现双链表时介绍。

    // Node *p = head;
    // while (p->next != nullptr) {
    //   p = p->next;
    // }

    // create a new node and insert at the next of p
    // Node *new_node = new Node(element);
    // p->next = new_node;

    Node *new_node = new Node(element);
    tail->next = new_node;
    tail = new_node;
    size++;
  }

  // 这个操作的时间复杂度是 O(n)，因为需要先找到插入位置的前驱节点。
  void add(int index, E element) {
    checkPositionIndex(index);

    if (index == 0) {
      addFirst(element);
      return;
    } else if (index == size) {
      addLast(element);
    }
    // start from the 虚拟 head
    Node *pre_node = head;
    // move pre_node with index step to get pre_node
    for (int i = 0; i < index; i++) {
      pre_node = pre_node->next;
    }

    Node *new_node = new Node(element);
    new_node->next = pre_node->next;
    pre_node->next = new_node;
  }

  E removeFirst() {
    if (isEmpty()) {
      throw std::out_of_range("No element to remove");
    }
    Node *first_element = head->next;
    head->next = first_element->next;
    size--;
    if (size == 0) {
      tail = head;
    }
    E val = first_element->val;
    delete first_element;
    return val;
  }

  E removeLast() {
    if (isEmpty()) {
      throw std::out_of_range("No element to remove");
    }

    Node *pre_node = head;
    // Node* current_node = head->next;

    // compare  current_node->next with nullptr, loop util the current node get
    // at the end of the list while(current_node->next!=nullptr){
    //     pre_node = current_node;
    //     current_node = current_node->next;
    // }

    // compare pre_node with tail
    while (pre_node != tail) {
      pre_node = pre_node->next;
    }
    // E val= current_node->val;
    // delete current_node;
    E val = tail->val;
    delete tail;

    pre_node->next = nullptr;
    tail = pre_node;
    size--;
    return val;
  }

  E remove(int index) {
    checkElementIndex();

    if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    }

    // move index step
    Node *pre_node = head;
    for (int i = 0; i < index; i++) {
      pre_node = pre_node->next;
    }
    // move the element at index
    Node *delete_element = pre_node->next;
    E val = delete_element->val;
    pre_node->next = delete_element->next;
    delete delete_element;
    size--;
  }

  E getFirst() {
    if (isEmpty()) {
      throw std::out_of_range("No Elements in the list");
    }
    return head->next->val;
  }

  E getLast() {
    if (isEmpty()) {
      throw std::out_of_range("No Elements in the list");
    }
    return tail->val;
  }

  Node *getNode(int index) {
    // get the current node with loop util get the index position
    checkElementIndex(index);
    Node *current_node = head;
    for (int i = 0; i < index; i++) {
      current_node = current_node->next;
    }
    return current_node;
  }
  E get(int index) {
    checkElementIndex(index);
    // loop util get the index position
    Node *current_node = getNode(index);
    return current_node->val;
  }

  // 改
  E set(int index, E val) {
    Node *current_node = getNode(index);
    E old_val = current_node->val;
    current_node->val = val;
    return old_val;
  }
  bool getSize() const { return size; }

  bool isEmpty() { return size == 0; }

  bool isEelementIndex(int index) { return index >= 0 && index < size; }
  void checkElementIndex(int index) {
    if (!isEelementIndex(index)) {
      throw std::out_of_range("Index is not in the bound");
    }
  }

  bool isPositionIndex(int index) { return index >= 0 && index <= size; }
  void checkPositionIndex(int index) {
    if (!isPositionIndex(index)) {
      throw std::out_of_range("Index is not in the bound");
    }
  }
};

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList* obj = new MyLinkedList();
 * int param_1 = obj->get(index);
 * obj->addAtHead(val);
 * obj->addAtTail(val);
 * obj->addAtIndex(index,val);
 * obj->deleteAtIndex(index);
 */

template <typename E> class MyLinkedList {
  struct Node {
    E val;
    Node *prev;
    Node *next;
    Node(E value) : val(value), prev(nullptr), next(nullptr) {}
  };

public:
  MyLinkedList() {}

  int get(int index) {}

  void addAtHead(int val) {}

  void addAtTail(int val) {}

  void addAtIndex(int index, int val) {}

  void deleteAtIndex(int index) {}
};

int main() {
  SingleLinkedLisk<int> list;
  list.addLast(1);
  list.addLast(2);
  list.addLast(3);
  list.addFirst(0);
  list.add(2, 100);

  list.display();
  // size = 5
  // 0 <-> 1 <-> 100 <-> 2 <-> 3 <-> null

  return 0;
}
