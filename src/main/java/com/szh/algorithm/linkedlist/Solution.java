package com.szh.algorithm.linkedlist;

import java.util.Stack;

public class Solution {

    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer> minStack = new Stack<Integer>();

    public void push(int node) {
        stack.push(node);
        if (minStack.isEmpty() || node < minStack.peek())
            minStack.push(node);
        else {
            minStack.push(minStack.peek());
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null) return null;
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = pHead;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) return pHead;
        ListNode pre = new ListNode(-1);
        ListNode newNode = pre;
        pre.next = pHead;
        ListNode current = pHead;
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                int val = current.val;
                while (current != null && current.val == val) {
                    current = current.next;
                }
                pre.next = current;
            } else {
                pre = current;
                current = current.next;
            }
        }
        return newNode.next;
    }

    public static ListNode deleteDuplication1(ListNode pHead) {

        ListNode first = new ListNode(-1);//设置一个trick

        first.next = pHead;

        ListNode p = pHead;
        ListNode last = first;
        while (p != null && p.next != null) {
            if (p.val == p.next.val) {
                int val = p.val;
                while (p != null && p.val == val)
                    p = p.next;
                last.next = p;
            } else {
                last = p;
                p = p.next;
            }
        }
        return first.next;
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        ListNode first1 = new ListNode(1);
        ListNode first2 = new ListNode(2);
        ListNode first3 = new ListNode(3);
        ListNode first4 = new ListNode(3);
        ListNode first5 = new ListNode(9);

        first1.next = first2;
        first2.next = first3;
        first3.next = first4;
        first4.next = first5;

        Solution solution = new Solution();
        ListNode node = solution.deleteDuplication(first1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}