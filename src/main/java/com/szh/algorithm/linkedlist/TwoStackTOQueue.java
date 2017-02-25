package com.szh.algorithm.linkedlist;

import java.util.Stack;

/**
 * Created by zhihaosong on 17-2-23.
 */
public class TwoStackTOQueue {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.empty()) {
            while (!stack1.isEmpty())
                stack2.push(stack1.pop());
        }
        if (stack2.isEmpty())
            throw new RuntimeException("queue is empty!");
        return stack2.pop();
    }
}
