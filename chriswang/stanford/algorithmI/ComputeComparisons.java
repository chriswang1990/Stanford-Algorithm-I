package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
Question:
The file contains all of the integers between 1 and 10,000 (inclusive, with no repeats) in unsorted
order. The integer in the ith row of the file gives you the ith entry of an input array.
Your task is to compute the total number of comparisons used to sort the given input file by
QuickSort. As you know, the number of comparisons depends on which elements are chosen as pivots,
so we'll ask you to explore three different pivoting rules.
You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray
of length m, you should simply add m−1 to your running total of comparisons. (This is because the
pivot element is compared to each of the other m−1 elements in the subarray in this recursive call.)

WARNING: The Partition subroutine can be implemented in several different ways, and different
implementations can give you differing numbers of comparisons. For this problem, you should
implement the Partition subroutine exactly as it is described in the video lectures (otherwise you
might get the wrong answer).

DIRECTIONS FOR THIS PROBLEM:
For the first part of the programming assignment, you should always use the first element of the
array as the pivot element.

For the first part of the programming assignment, you should always use the first element of the
array as the pivot element.

Compute the number of comparisons (as in Problem 1), always using the final element of the given
array as the pivot element. Again, be sure to implement the Partition subroutine exactly as it is
described in the video lectures. Recall from the lectures that, just before the main Partition
subroutine, you should exchange the pivot element (i.e., the last element) with the first element.

Compute the number of comparisons (as in Problem 1), using the "median-of-three" pivot rule.
[The primary motivation behind this rule is to do a little bit of extra work to get much better
performance on input arrays that are nearly sorted or reverse sorted.] In more detail, you should
choose the pivot as follows. Consider the first, middle, and final elements of the given array.
(If the array has odd length it should be clear what the "middle" element is; for an array with
even length 2k, use the kth element as the "middle" element. So for the array 4 5 6 7, the "middle"
element is the second one ---- 5 and not 6!) Identify which of these three elements is the median
(i.e., the one whose value is in between the other two), and use this as your pivot. As discussed
in the first and second parts of this programming assignment, be sure to implement Partition exactly
 as described in the video lectures (including exchanging the pivot element with the first element
 just before the main Partition subroutine).
*/


public class ComputeComparisons {
    private static int QuickSort(int[] array, int start, int end) {
        
        //base case handling
        if (start >= end) {
            return 0;
        }

        //find the pivot(start, end or median of three)
        int pivot;
        if (end - start == 1) {
            pivot = array[start];
        } else {
            int[] pivotArray = new int[3];
            pivotArray[0] = array[start];
            pivotArray[2] = array[end];
            pivotArray[1] = array[start + (end - start) / 2];
            Arrays.sort(pivotArray);
            pivot = pivotArray[1];
            if (pivot == array[end]) {
                swap(array, start, end);
            } else if (pivot == array[start + (end - start) / 2]) {
                swap(array, start, start + (end - start) / 2);
            }
        }

        //quick sort with the pivot
        int j = start + 1;
        for (int i = start + 1; i <= end; i++) {
            if (array[i] > pivot) {
                continue;
            }
            swap(array, j, i);
            j++;
        }
        swap(array, start, j - 1);

        //recursion call;
        return QuickSort(array, start, j - 2) + QuickSort(array, j, end) + end - start;
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream(new File("./src/com/QuickSort.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int[] array = new int[10000];
        for (int i = 0; i < 10000; i++) {
            array[i] = Integer.parseInt(reader.readLine());
        }
        System.out.println(QuickSort(array, 0, 9999));
        for (int i : array) {
            System.out.println(i);
        }
    }
}