package com.szh.algorithm.sort;

public class SelectSort {

    public static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int temp = a[low];
            while (low < high) {
                while (low < high && a[high] >= temp)
                    high--;
                a[low] = a[high];
                while (low < high && a[low] <= temp)
                    low++;
                a[high] = a[low];
            }
            a[low] = temp;
            int middle = low;
            // System.out.println(low);
            quickSort(a, low, middle - 1);
            quickSort(a, middle + 1, high);
        }
    }

    public static void quickSort(int[] a) {
        if (a.length > 0)
            quickSort(a, 0, a.length - 1);
    }

    public static void bubbleSort2(int[] array) {
        if (array == null) {
            return;
        }
        int size = array.length;
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (array[i] > array[j])
                    ArrayUtils.exchangeElements(array, i, j);
            }
        }

    }

    public static void main(String[] args) {
        int a[] = new int[]{34, 55, 78, 9, 2, 1, 44, 43, 1, 233};
        quickSort(a);
        ArrayUtils.printArray(a);
        System.out.println();

        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3};
        System.out.println("Before sort:");
        ArrayUtils.printArray(array);
        bubbleSort2(array);
        System.out.println("After sort:");
        ArrayUtils.printArray(array);
    }
}
