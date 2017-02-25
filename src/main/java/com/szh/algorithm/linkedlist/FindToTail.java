package com.szh.algorithm.linkedlist;

/**
 * Created by zhihaosong on 17-2-25.
 */
public class FindToTail {
    public ListNode FindKthToTail1(ListNode head, int k) {
        if (head == null || k <= 0)
            return null;
        ListNode fast = head, slow = head;
        for (int i = 1; i < k; i++) {
            if (fast.next != null) {
                fast = fast.next;
            } else
                return null;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public static ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k <= 0)
            return null;
        ListNode fast = head, slow = head;
        while (fast != null) {
            fast = fast.next;
            if (k < 1)
                slow = slow.next;
            k--;
        }
        return k < 1 ? slow : null;
    }

    public static ListNode ReverseList(ListNode head) {
        if (head == null)
            return head;
        ListNode reverseList = null;
        ListNode current = head;
        ListNode preNode = null;
        while (current != null) {
            ListNode temp = current.next;
            current.next = preNode;
            if (temp == null) {
                reverseList = current;
                break;
            }
            preNode = current;
            current = temp;
        }
        return reverseList;
    }


    public static ListNode ReverseList1(ListNode head) {
        if (head == null)
            return null;
        ListNode newHead = null;
        ListNode pNode = head;
        ListNode pPrev = null;
        while (pNode != null) {
            ListNode pNext = pNode.next;
            if (pNext == null)
                newHead = pNode;
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        return newHead;
    }

    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null)
            return null;
        else if (list1 == null)
            return list2;
        else if (list2 == null)
            return list1;
        ListNode mergeList = null;
        while (list1 != null) {
            int currentVal1 = list1.val;
            while (list2 != null) {
                int currentVal2 = list2.val;
                if (currentVal2 < currentVal1) {
                    mergeList = list2;
                    mergeList = mergeList.next;
                } else break;
                list2 = list2.next;
            }
            if (currentVal1 < list2.val) {
                mergeList = list1;
                mergeList = mergeList.next;
            }

        }
        return null;
    }

    public static void main(String[] args) {
        ListNode first1 = new ListNode(1);
        ListNode first2 = new ListNode(2);
        ListNode first3 = new ListNode(3);
        ListNode first4 = new ListNode(4);
        ListNode first5 = new ListNode(5);

        first1.next = first2;
        first2.next = first3;
        first3.next = first4;
        first4.next = first5;
        ListNode aaa = ReverseList(first1);
        while (aaa != null) {
            System.out.println(aaa.val);
            aaa = aaa.next;
        }
    }
}

