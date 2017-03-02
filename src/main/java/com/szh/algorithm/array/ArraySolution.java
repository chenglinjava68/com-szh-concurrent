package com.szh.algorithm.array;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        if (matrix == null)
            return null;
        ArrayList<Integer> printArr = new ArrayList<Integer>();
        int rows = matrix.length;
        int columns = matrix[0].length;
        int circles = (((rows < columns ? rows : columns) - 1) >> 1) + 1;//圈数
        for (int i = 0; i < circles; i++) {
            for (int j = i; j < columns - i; j++) {
                printArr.add(matrix[i][j]);
            }
            for (int j = i + 1; j < rows - i; j++) {
                printArr.add(matrix[j][columns - i - 1]);
            }
            for (int j = columns - i - 2; j > i - 1 && rows - i - 1 != i; j--) {
                printArr.add(matrix[rows - i - 1][j]);
            }
            for (int j = rows - i - 2; j > i && columns - i - 1 != i; j--) {
                printArr.add(matrix[j][i]);
            }
        }
        return printArr;
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0)
            return 0;
        int temp = array[0];
        int len = array.length, count = 1;
        for (int i = 1; i < len; i++) {
            if (array[i] == temp) count++;
            else count--;
            if (count == 0) {
                temp = array[i];
                count = 1;
            }
            count = 0;
            for (int j = 0; j < len; j++) {
                if (array[j] == temp)
                    count++;
            }
        }
        return 2 * count > len ? temp : 0;
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (input == null || input.length == 0 ||
                k <= 0 || input.length < k)
            return result;
        Arrays.sort(input);
        for (int i = 0; i < k; i++) {
            result.add(input[i]);
        }
        return result;
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) return 0;
        int sum = 0, tempSum = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (sum <= 0)
                sum = array[i];
            else sum += array[i];
            if (sum > tempSum)
                tempSum = sum;
        }
        return tempSum;
    }

    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        int len = numbers.length;
        String[] strArr = new String[numbers.length];
        for (int i = 0; i < len; i++) {
            strArr[i] = numbers[i] + "";
        }
        Arrays.sort(strArr, new Comparator<String>() {
            @Override
            public int compare(String before, String next) {
                return (before + next).compareTo(next + before);
            }
        });
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < len; i++) {
            result.append(strArr[i]);

        }
        return new String(result);
    }

    public int GetNumberOfK(int[] array, int k) {
        int count = 0;
        if (array == null || array.length == 0) return count;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == k)
                count++;
            else if (array[i] > k)
                break;
        }
        return count;
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if (sum < 3) return res;
        int mid = (1 + sum) >> 1;
        int small = 1;
        int big = 2;
        int current = small + big;
        while (small < mid && big < sum) {
            while (current > sum) {
                current -= small;
                small++;
            }
            if (current == sum) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = small; i <= big; i++) {
                    list.add(i);
                }
                res.add(list);
            }
            big++;
            current += big;
        }
        return res;
    }

    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (array.length < 3)
            return result;
        int low = 0, high = array.length - 1;
        while (low < high) {
            int val = array[low] + array[high];
            if (val == sum) {
                result.add(array[low]);
                result.add(array[high]);
                break;
            } else if (val > sum)
                high--;
            else
                low++;
        }
        return result;
    }

    public String LeftRotateString(String str, int n) {
        if (str == null || str.length() == 0 || n <= 0)
            return new String();
        return str.substring(n) + str.substring(0, n);
    }

    public String ReverseSentence(String str) {
        if (str == null || str.trim().length() == 0) return str;
        StringBuffer bf = new StringBuffer();
        String[] strArr = str.split(" ");
        for (int i = strArr.length - 1; i >= 0; i--) {
            bf.append(strArr[i]).append(" ");
        }
        System.out.println(bf);
        return new String(bf.substring(0, bf.length() - 1));
    }

    public boolean isContinuous(int[] numbers) {
        if (numbers == null || numbers.length < 1)
            return false;
        return true;
    }

    public static void main(String[] args) {
        int[][] array22 = {{1}, {5}, {9}, {13}};

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
        System.out.println("tets");

        System.out.println(0 >> 1);
        System.out.println(arraySolution.Power(10, -5));
        ArrayList<Integer> printArr = arraySolution.printMatrix(array);
        for (int i = 0; i < printArr.size(); i++) {
            System.out.print(printArr.get(i) + "\t");
        }
        int[] a = null;
        System.out.println();

        System.out.println();
        System.out.println(arraySolution.ReverseSentence(" ") + "sss");
    }
}
