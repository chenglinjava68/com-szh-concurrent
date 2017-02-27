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
        if (list1 == null)
            return list2;
        else if (list2 == null)
            return list1;
        ListNode mergeHead = null;
        if (list1.val < list2.val) {
            mergeHead = list1;
            mergeHead.next = Merge(list1.next, list2);
        } else {
            mergeHead = list2;
            mergeHead.next = Merge(list1, list2.next);
        }
        return mergeHead;
    }

    public ListNode Merge1(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        else if (list2 == null)
            return list1;
        ListNode mergeHead = null, current = null;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                if (current == null) {
                    mergeHead = current = list1;
                } else {
                    current.next = list1;
                    current = current.next;
                }
                list1 = list1.next;
            } else {
                if (current == null) {
                    mergeHead = current = list2;
                } else {
                    current.next = list2;
                    current = current.next;
                }
                list2 = list2.next;
            }
        }
        if (list1 != null)
            current.next = list1;
        if (list2 != null)
            current.next = list2;
        return mergeHead;
    }

    public static void main(String[] args) {
        FindToTail findToTail = new FindToTail();
        ListNode first1 = new ListNode(1);
        ListNode first2 = new ListNode(3);
        ListNode first3 = new ListNode(5);
        ListNode first4 = new ListNode(7);
        ListNode first5 = new ListNode(9);

        first1.next = first2;
        first2.next = first3;
        first3.next = first4;
        first4.next = first5;


        ListNode xfirst1 = new ListNode(2);
        ListNode xfirst2 = new ListNode(4);
        ListNode xfirst3 = new ListNode(6);
        ListNode xfirst4 = new ListNode(8);
        ListNode xfirst5 = new ListNode(10);


        xfirst1.next = xfirst2;
        xfirst2.next = xfirst3;
        xfirst3.next = xfirst4;
        xfirst4.next = xfirst5;
       /* ListNode aaa = ReverseList(first1);
        while (aaa != null) {
            System.out.println(aaa.val);
            aaa = aaa.next;
        }*/


        ListNode bbb = findToTail.Merge1(first1, xfirst1);
        while (bbb != null) {
            System.out.print(bbb.val + "\t");
            bbb = bbb.next;
        }
    }
}

