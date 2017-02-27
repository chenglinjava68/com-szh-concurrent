package com.szh.algorithm.stack;

import java.util.ArrayList;

public class Solution {
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null || popA.length != pushA.length)
            return false;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int aIndex = 0;
        for (int i = 0; i < pushA.length; i++) {
            temp.add(pushA[i]);
            while (aIndex < popA.length && temp.get(temp.size() - 1) == popA[aIndex]) {
                temp.remove(temp.size() - 1);
                aIndex++;
            }
        }
        return temp.size() == 0;
    }

    public static void main(String[] args) {
        int[] pushA = new int[]{1,2,3,4,5,6};
        int[] popA = new int[]{5,1,4,3,2,6};
        System.out.println(new Solution().IsPopOrder(pushA,popA));
    }
}