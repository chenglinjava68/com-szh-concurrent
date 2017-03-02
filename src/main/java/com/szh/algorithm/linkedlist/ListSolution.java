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

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null)
            return null;
        int count1 = 0, count2 = 0;
        ListNode pTemp = pHead1;
        while (pTemp != null) {
            count1++;
            pTemp = pTemp.next;
        }
        pTemp = pHead2;
        while (pTemp != null) {
            count2++;
            pTemp = pTemp.next;
        }
        int flag = count1 - count2;
        pTemp = flag > 0 ? pHead1 : pHead2;
        ListNode nexNode = flag < 0 ? pHead1 : pHead2;
        for (int i = 0; i < flag; i++) {
            pTemp = pTemp.next;
        }
        while (pTemp != nexNode) {
            pTemp = pTemp.next;
            nexNode = nexNode.next;
        }
        return pTemp == null ? null : pTemp;
    }

    public static void main(String[] args) {

    }
}
