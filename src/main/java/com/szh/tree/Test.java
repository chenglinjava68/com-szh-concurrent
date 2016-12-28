package com.szh.tree;

public class Test {
    public static void main(String[] args) {
        System.out.println(new ReconstructBinaryTree().reConstructBinaryTree
                (
                        new int[]{1, 2, 4, 7, 3, 5, 6, 8},
                        new int[]{4, 7, 2, 1, 5, 3, 8, 6}
                ));
    }
}