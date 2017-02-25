package com.szh.algorithm.tree;


import java.util.Arrays;

/**
 * 前序遍历+中序遍历 重构二叉树
 */
public class Solution {
    public TreeNode reConstructBinaryTree1(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0)
            return null;
        return reConstructBin(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    TreeNode reConstructBin(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn)
            return null;
        TreeNode root = new TreeNode(pre[startPre]);
        for (int i = 0; i < in.length; i++) {
            if (in[i] == root.val) {
                root.left = reConstructBin(pre, startPre + 1, i + startPre - startIn, in, startIn, i - 1);
                root.right = reConstructBin(pre, i + startPre - startIn + 1, endPre, in, i + 1, endIn);
            }
        }
        return root;
    }


    public TreeNode reConstructBinaryTree3(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                node.left = reConstructBinaryTree3(Arrays.copyOfRange(pre, 1, i + 1),
                        Arrays.copyOfRange(in, 0, i));
                node.right = reConstructBinaryTree3(Arrays.copyOfRange(pre, i + 1, pre.length),
                        Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return node;
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0)
            return null;
        TreeNode root = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (root.val == in[i]) {
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1),
                        Arrays.copyOfRange(in, 0, i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length),
                        Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return root;
    }


    public void prePrint(TreeNode root) {
        System.out.print(root.val + "\t");
        if (root.left != null)
            prePrint(root.left);
        if (root.right != null)
            prePrint(root.right);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode aaa = solution.reConstructBinaryTree
                (
                        new int[]{1, 2, 4, 7, 3, 5, 6, 8},
                        new int[]{4, 7, 2, 1, 5, 3, 8, 6}
                );
        solution.prePrint(aaa);

        TreeNode bbb = solution.reConstructBinaryTree1(
                new int[]{1, 2, 4, 7, 3, 5, 6, 8},
                new int[]{4, 7, 2, 1, 5, 3, 8, 6}
        );
        solution.prePrint(bbb);

    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}