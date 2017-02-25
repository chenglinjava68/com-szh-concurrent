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

    public int minNumberInRotateArray(int[] array) {
        int len = array.length;
        if (len == 0)
            return 0;
        int low = 0, high = len - 1, mid = low + (high - low) >> 1;

        if (array[low] == array[high] && array[low] == array[mid]) {
            int min = array[low];
            for (int i = 0; i < len; i++) {
                if (array[i] < min)
                    min = array[i];
            }
            return min;
        }
        while (low < high) {
            if (high - low == 1) {
                mid = high;
                break;
            }
            mid = low + ((high - low) >> 1);

            if (array[mid] >= array[high])
                low = mid;
            else
                high = mid;
        }
        return array[mid];
    }


    public int minNumberInRotateArray1(int[] array) {
        int len = array.length;
        if (len == 0)
            return 0;
        int low = 0;
        int high = len - 1;
        while (low < high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > array[high])
                low = mid + 1;
            else if (array[mid] < array[high]) {
                high = mid;
            } else {
                high = high - 1;
            }
        }
        return array[low];
    }

    public int Fibonacci(int n) {
        if (n == 1 || n == 0)
            return 1;
        return Fibonacci(n - 1) + Fibonacci(n - 2);

    }

    public int FibonacciNoStack(int n) {
        int first = 0, next = 1, result = 0;
        if (n == 1)
            result = next;
        for (int i = 1; i < n; i++) {
            result = first + next;
            first = next;
            next = result;
        }
        return result;
    }

    public int JumpFloor(int target) {
        int first = 1, next = 2, result = 0;
        if (target == 1)
            result = first;
        else if (target == 2)
            result = next;
        for (int i = 2; i < target; i++) {
            result = first + next;
            first = next;
            next = result;
        }
        return result;
    }

    public int JumpFloorII(int target) {
        int next = 1, result = 0;
        if (target == 1)
            result = next;
        for (int i = 1; i < target; i++) {
            result = 2 * next;
            next = result;
        }
        return result;
    }

    public int NumberOf1(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            if ((n & flag) != 0)
                count++;
            flag = flag << 1;
        }
        return count;
    }


    public int NumberOf2(int n) {
        int count = 0;
        if (n > Integer.MAX_VALUE || n < Integer.MIN_VALUE)
            return count;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    public double Power(double base, int exponent) {
        if (base == 0)
            throw new RuntimeException("base is not 0");
        double result = 1, incrBase = base;
        int exTemp = exponent > 0 ? exponent : -exponent;
        while (exTemp != 0) {
            if ((exTemp & 1) == 1)
                result *= incrBase;
            incrBase *= incrBase;
            exTemp >>= 1;
        }
        return exponent > 0 ? result : (1 / result);
    }

    public void reOrderArray(int[] array) {
        int len = array.length, step = 0;
        int[] resultArr = new int[len];
        if (len == 0)
            return;
        for (int i = 0; i < len; i++) {
            int val = array[i];
            if ((val & 1) == 0) {
                resultArr[step] = val;
                step++;
            } else
                array[i - step] = val;
        }
        System.arraycopy(resultArr, 0, array, len - step,
                step);
    }

    public void reOrderArray1(int[] array) {
        if (array == null || array.length == 0)
            return;
        int len = array.length;
        for (int i = 0, j; i < len; i++) {
            while (i < len && (array[i] & 1) == 1)
                i++;
            j = i + 1;
            while (j < len && (array[j] & 1) == 0)
                j++;
            if (j < len) {
                int odd = array[j];
                System.arraycopy(array, i, array, i + 1, j - i);
                array[i] = odd;
            } else break;
        }
    }

    public static void main(String[] args) {
        int[][] array = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        ArraySolution arraySolution = new ArraySolution();
        System.out.println(arraySolution.find(7, array));
        StringBuffer aaa = new StringBuffer("HELLOWORLD ");
        System.out.println(arraySolution.replaceSpace2(aaa));
        int[] arrays = new int[]{1, 2, 3, 4, 5, 6, 7};
        arraySolution.reOrderArray1(
                arrays);
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + "\t");
        }
        System.out.println(arraySolution.Power(10, -5));
    }
}
