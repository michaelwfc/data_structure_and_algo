#include <cassert>
#include <iostream>
#include <stdexcept>
#include <vector>
#include <cassert>

/**
1. 自动扩缩容
我们这里就实现一个简单的扩缩容的策略：
当数组元素个数达到底层静态数组的容量上限时，扩容为原来的 2 倍；
当数组元素个数缩减到底层静态数组的容量的 1/4 时，缩容为原来的 1/2。

2. 索引越界的检查

3. 删除元素谨防内存泄漏
*/


template<typename E>
class MyArrayList{
    private:
        E* data;   // data is just a pointer.
        int size;   // current size,logical number of elements
        int capacity; // max capacity,physical storage
        static const int INIT_CAPACITY = 1;
    
    public:
        MyArrayList(){
            this->data = new E[INIT_CAPACITY]; //allocated array with a fixed size.
            this->size = 0;
            this->capacity = INIT_CAPACITY;
        }

        MyArrayList(int init_capacity){
            this->data = new E[init_capacity];
            this->size = 0;
            this->capacity = init_capacity;
        }

        ~MyArrayList(){
            delete[] data;
        }

        void addLast(E e){
            if (size == capacity){
                // resize to  double size for the array
                resize(2*capacity);  
            }   
            data[size] = e;
            size ++;

        }

        void add(int index, E e){
            checkPositionIndex(index);
            // check current size
            if(size == capacity){
                resize(2*capacity);  
            }
            // copy the elements from index to next position
            for(int i=size-1; i>=index; i--){
                data[i+1] = data[i];
            }
            // add the new element at positon index
            data[index] = e;
            size ++;
        }

        void addFirst(E e){
            add(0, e);

        }

        E removeLast(){
            if(size ==0){
                throw std::out_of_range("Empty array can not remove");
            }
            E deletedElement = data[size-1];

            // delete data[size-1]; 
            //wrong , Because delete is only for memory obtained through new.  E* p = new E; delete p;
            // data[size - 1] is an object/value inside an array, not a pointer returned by new.

            // data[size-1] = std::null/nullptr;
            //wrong: data[size - 1] an object of type E, not necessarily a pointer.
            data[size - 1] = E(); //It resets that slot to a default-constructed value.
            size --;

            if(size == capacity/4){
                resize(capacity/2);
            }
            return deletedElement;
        }

        E remove(int index){
            checkElementIndex(index);

            //copy the position after index to previous position
            E old_element = data[index];
            for(int i=index; i<size;i++){
                data[i] = data[i+1];
            }
            data[size-1] =E(); // set the last element to default-constructed value.
            size --;
            if(size == capacity/4){
                resize(capacity/2);
            }
            return old_element;
        }
        
        E removeFirst(){
            remove(0);

        }

        E get(int index){
            checkElementIndex(index);
            return data[index];

        }

        E set(int index, E element){
            checkElementIndex(index);
            E  old_element = data[index];
            data[index] = element;
            return old_element;
        }


        int getSize(){
            return size;
        }

        bool isEmpty(){
            return size == 0;
        }


        void resize(int new_capacity){
            // alocate new capacity fixed size array
            E* temp = new E[new_capacity];
            // copy the data from original to new array
            for(int i=0;i<size; i++){
                temp[i] = data[i];
            }
            // delete the original array
            // If data was allocated with new[], you must use delete[], not delete.
            delete[] data; 
            // point to new array
            data = temp;
            // update capacity
            capacity = new_capacity;

        }

        bool isElementIndex(int index){
            return index >=0 && index < size;
        }

        bool isPositionIndex(int index){
            return index >=0 && index <= size;
        }

        void checkElementIndex(int index){
            if(! isElementIndex(index)){
                throw std::out_of_range("Index out of bound");
            }

        }

        // checkPositionIndex 是专门用来处理在数组中插入元素的情况。
        // 这些空隙都是合法的插入位置，所以说 index == size 也是合法的。这就是 checkPositionIndex 和 checkElementIndex 的区别。
        void checkPositionIndex(int index){

            if(!isPositionIndex(index)){
                throw std::out_of_range("Index out of bound");
            }

        }

        void display(){
            std::cout << "size = " << size << "cap = " << capacity <<std::endl;
            for(int i=0; i<size ; i++){
                std::cout << data[i] << " ";
            }
            std:: cout <<std::endl;
        }
};


int main() {
    // 初始容量设置为 3
    MyArrayList<int> arr(3);

    // 添加 5 个元素
    for (int i = 1; i <= 5; i++) {
        arr.addLast(i);
    }
    assert(arr.get(4)== 5);

    arr.remove(3);
    assert(arr.get(3)==5);

    arr.add(1, 9);
    arr.addFirst(100);
    int val = arr.removeLast();

    // 100 1 9 2 3
    for (int i = 0; i < arr.getSize(); i++) {
        std::cout << arr.get(i) << std::endl;
    }

    return 0;
}