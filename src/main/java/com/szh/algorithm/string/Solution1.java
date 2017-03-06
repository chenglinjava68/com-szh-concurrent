package com.szh.algorithm.string;

public class Solution1 {
    private int[] count = new int[256];
    int index = 1;

    public void Insert(char ch) {
        if (count[ch] == 0)
            count[ch] = index++;
        else count[ch] = -1;
    }

    public char FirstAppearingOnce() {
        int temp = Integer.MAX_VALUE;
        char result = '#';
        for (int i = 0; i < 256; i++) {
            if (count[i] > 0 && count[i] < temp) {
                temp = count[i];
                result = (char)i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        String str = "google";
        for (int i = 0; i < str.length(); i++) {
            solution1.Insert(str.charAt(i));
        }
        System.out.println(solution1.FirstAppearingOnce());

    }
}