package com.szh.algorithm.tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by zhihaosong on 17-3-4.
 */
public class Solutions {
    String Serialize(TreeNode root) {
        StringBuffer str = new StringBuffer();
        if (root == null) {
            str.append("#,");
            return str.toString();
        }
        str.append(root.val + ",");
        str.append(Serialize(root.left));
        str.append(Serialize(root.right));
        return str.toString();
    }

    private int index = -1;

    TreeNode Deserialize(String str) {
        if (str.length() == 0)
            return null;
        String[] strs = str.split(",");
        return Deserialize2(strs);
    }

    TreeNode Deserialize2(String[] strs) {
        index++;
        if (index >= strs.length) {
            return null;
        }
        if (!strs[index].equals("#")) {
            TreeNode root = new TreeNode(0);
            root.val = Integer.parseInt(strs[index]);
            root.left = Deserialize2(strs);
            root.right = Deserialize2(strs);
            return root;
        }
        return null;
    }

    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) return pRoot;
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode current = pRoot;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            if (!stack.isEmpty()) {
                current = stack.pop();
                if (--k == 0) {
                    return current;
                }
                current = current.right;
            }
        }
        return null;
    }

    public static int StrToInt(String str) {
        if (str == null || str.length() == 0) return 0;
        char zero = 48, nine = 57;
        int result = 0;
        char[] arr = str.toCharArray();
        int i = (arr[0] == '-' || arr[0] == '+') ? 1 : 0;
        int isMinus = arr[0] == '-' ? -1 : 1;
        for (; i < arr.length; i++) {
            if (arr[i] < zero || arr[i] > nine) {
                return 0;
            }
            result = result * 10 + arr[i] - zero;
        }
        return isMinus * result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);
        TreeNode root1 = new TreeNode(6);
        TreeNode root2 = new TreeNode(10);
        TreeNode root3 = new TreeNode(5);
        TreeNode root4 = new TreeNode(7);
        TreeNode root5 = new TreeNode(9);
        TreeNode root6 = new TreeNode(11);
        root.left = root1;
        root.right = root2;
        root1.left = root3;
        root1.right = root4;
        root2.left = root5;
        root2.right = root6;
        Solutions solutions = new Solutions();
        String str = solutions.Serialize(root);
        System.out.println(str);
        TreeNode node = solutions.Deserialize("1,2,3,#");
        int a = 1;
        System.out.println(--a == 0);
        int res = 5;
        System.out.println(res << 1);
        System.out.println(res << 3);

        System.out.println(StrToInt("+2147483647"));
    }
}
