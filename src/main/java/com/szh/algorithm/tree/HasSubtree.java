package com.szh.algorithm.tree;

/**
 * Created by zhihaosong on 17-2-26.
 * 输入两棵二叉树A，B，判断B是不是A的子结构。
 * （ps：我们约定空树不是任意一个树的子结构）
 */
public class HasSubtree {
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean hasSubtree = false;
        if (root1 != null && root2 != null) {
            if (root1.val == root2.val)
                hasSubtree = isSubtree(root1, root2);
        }
        if (!hasSubtree)
            hasSubtree = isSubtree(root1.left, root2);
        if (!hasSubtree)
            hasSubtree = isSubtree(root1.right, root2);
        return hasSubtree;

    }

    public boolean isSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null)
            return true;
        if (root1 == null)
            return false;
        if (root1.val != root2.val)
            return false;
        return isSubtree(root1.left, root2.left) && isSubtree(root1.right, root2.right);
    }
    public void Mirror(TreeNode root) {
        if(root==null)
            return;
        TreeNode temp = root.left;
        root.right = root.left;
        root.left=temp;
        Mirror(root.left);
        Mirror(root.right);
    }
    public static void main(String[] args) {

    }
}
