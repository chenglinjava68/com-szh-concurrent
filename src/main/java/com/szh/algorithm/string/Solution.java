package com.szh.algorithm.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Solution {
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> result = new ArrayList<String>();
        if (str == null || str.length() == 0)
            return result;
        PermutationHelper(str.toCharArray(), 0, result);
        Collections.sort(result);
        return result;
    }

    private void PermutationHelper(char[] cs, int i, ArrayList<String> list) {
        if (i == cs.length - 1) {
            list.add(new String(cs));
            return;
        }
        for (int j = i; j < cs.length; j++) {
            if (cs[i] == cs[j])
                continue;
            swap(cs, i, j);
            PermutationHelper(cs, i + 1, list);
            swap(cs, j, i);
        }

    }

    public boolean isNumeric(char[] str) {
        return new String(str).matches("[\\+-]?[0-9]*(\\.[0-9]*)?([eE][\\+-][0-9]+)?");
    }

    private void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}