package com.szh.algorithm.linkedlist;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zhihaosong on 17-2-23.
 */
public class ListSolution {
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

    /*
    递归实现
     */
    public ArrayList<Integer> printListFromTailToHead1(ListNode listNode) {
        ArrayList<Integer> reversePrintList = new ArrayList<Integer>();
        deepReverse(reversePrintList, listNode);
        return reversePrintList;
    }


    public void deepReverse(ArrayList<Integer> reversePrintList, ListNode initListNode) {
        if (initListNode != null) {
            deepReverse(reversePrintList, initListNode.next);
            reversePrintList.add(initListNode.val);
        }
    }

    public static void main(String[] args) {

    }
}
