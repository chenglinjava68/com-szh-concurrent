package com.szh.algorithm.array;

/**
 * Created by zhihaosong on 17-2-22.
 */
public class ArraySolution {
    /*
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，
     * 输入这样的一个二维数组和一个整数，
     * 判断数组中是否含有该整数。
     */
    public boolean find(int target, int[][] array) {
        boolean found = false;
        int rows = array.length;
        int columns = array[rows - 1].length;
        if (rows > 0 && columns > 0) {
            int row = 0;
            int column = columns - 1;
            while (row < rows && column >= 0) {
                if (array[row][column] == target) {
                    found = true;
                    break;
                } else if (array[row][column] > target) {
                    --column;
                } else {
                    ++row;
                }
            }
        }
        return found;
    }

    /*
     *请实现一个函数，
     *将一个字符串中的空格替换成“%20”。
     *例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    public String replaceSpace(StringBuffer str) {
        if (str == null)
            return null;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.replace(i, i + 1, "%20");
            }
        }
        return new String(str);
    }

    public String replaceSpace2(StringBuffer str) {
        if (str == null)
            return null;
        int containCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ')
                containCount++;
        }
        if (containCount <= 0)
            return null;
        int originLen = str.length();
        int afterLen = originLen + 2 * containCount;
        char[] afterArray = new char[afterLen];
        while (originLen > 0) {
            if (str.charAt(originLen - 1) == ' ') {
                afterArray[--afterLen] = '0';
                afterArray[--afterLen] = '2';
                afterArray[--afterLen] = '%';

            } else
                afterArray[--afterLen] = str.charAt(originLen - 1);
            originLen--;
        }
        return new String(afterArray);
    }


    public static void main(String[] args) {
        int[][] array = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        ArraySolution arraySolution = new ArraySolution();
        System.out.println(arraySolution.find(7, array));
        StringBuffer aaa = new StringBuffer("HELLOWORLD ");
        System.out.println(arraySolution.replaceSpace2(aaa));
    }
}
