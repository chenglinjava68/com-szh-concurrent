package com.szh.algorithm.tree;

/**
 * 前序遍历+中序遍历 重构二叉树
 */
public class ReconstructBinaryTree {

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        return reConstructBinaryTree(pre, 0, pre.length - 1, in, 0,
                in.length - 1);
    }

    public TreeNode reConstructBinaryTree
            (int[] pre, int startPre, int endPre,
             int[] in, int startIn, int endIn) {

        if (startPre > endPre || startIn > endIn)
            return null;

        TreeNode root = new TreeNode(pre[startPre]);// 构造树根
        for (int i = startIn; i <= endIn; i++) {
            // 中序遍历中找树根
            if (in[i] == pre[startPre]) {

                root.left = reConstructBinaryTree(pre, startPre + 1, i
                        - startIn + startPre, in, startIn, i - 1);
                root.right = reConstructBinaryTree(pre, i - startIn + startPre
                        + 1, endPre, in, i + 1, endIn);
                break;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println(new ReconstructBinaryTree().reConstructBinaryTree
                (
                        new int[]{1, 2, 4, 7, 3, 5, 6, 8},
                        new int[]{4, 7, 2, 1, 5, 3, 8, 6}
                ));
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
