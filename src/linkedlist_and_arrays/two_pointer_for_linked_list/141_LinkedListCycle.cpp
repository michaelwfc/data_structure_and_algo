/**
 * 141. Linked List Cycle
 * https://leetcode.com/problems/linked-list-cycle/description/
 * 
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. 
Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.

Return true if there is a cycle in the linked list. Otherwise, return false.

Example 1:
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
Example 2:


Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
Example 3:


Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
 

Constraints:

The number of the nodes in the list is in the range [0, 104].
-105 <= Node.val <= 105
pos is -1 or a valid index in the linked-list.
 

Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */

#include "linkedlist_and_arrays/list_utils.h"

using namespace std;



// class Solution1 {
// public:
//     bool hasCycle(ListNode *head) {
//         // tow pointer: slow, fast
//         // let fast +2, slow +1 for each loop, if there is cycle, fast will get slow
//         ListNode* fast = head->next;
//         ListNode* slow = head;
//         while(fast!=nullptr && slow!=nullptr){
//             if(fast == nullptr){
//             return false;
//             }else if(fast ==slow){
//                 return true;
//             }else{
//                 // fast move 2 step, slow move 1 step
//                 fast = fast->next;
//                 if(fast==nullptr){return false;}
//                 fast = fast->next;
//                 slow= slow->next;
//             } 
//         }
//         return false;
//     }
// };


class Solution {
public:
    bool hasCycle(ListNode *head) {
        // tow pointer: slow, fast
        // let fast +2, slow +1 for each loop, if there is cycle, fast will get slow
        ListNode* fast = head;
        ListNode* slow = head;
        while(fast!=nullptr && fast->next!=nullptr){
            // fast move 2 step, slow move 1 step
            fast = fast->next->next;
            slow= slow->next;
            if(fast ==slow){
                return true;
            }
        }
        return false;
    }
};

int main(){
    ListNode* list = createCycleLinkedList({3,2,0,-4}, 1);
    // ListNode* list = createCycleLinkedList({1,2}, 0);
    // ListNode* list = createCycleLinkedList({1,2},-1);


    Solution solution = Solution();
    bool has_cycle = solution.hasCycle(list);
    cout<< "has_cycle: " << has_cycle<< endl;

}