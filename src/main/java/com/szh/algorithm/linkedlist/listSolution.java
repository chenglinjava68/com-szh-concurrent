package com.szh.algorithm.linkedlist;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zhihaosong on 17-2-23.
 */
public class listSolution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null)
            return new ArrayList();
        ArrayList<Integer> reversePrintList = new ArrayList<Integer>();
        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (listNode != null) {
            stack.addFirst(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.isEmpty()) {
            reversePrintList.add(stack.removeFirst());
        }
        return reversePrintList;
    }

    public ArrayList<Integer> printListFromTailToHead1(ListNode listNode) {


        ArrayList<Integer> al = new ArrayList<Integer>();
        ListNode p = listNode;
        xx(al, listNode);
        return al;
    }


    public void xx(ArrayList<Integer> al, ListNode n) {
        if (n != null) {
            xx(al, n.next);
            al.add(n.val);
        }
    }

    public static void main(String[] args) {

    }

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

}
