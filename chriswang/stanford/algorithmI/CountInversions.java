package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by 1990c on 5/5/2016.
 * Question 1
Download the text file here. (Right click and save link as)
This file contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.

Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
Because of the large size of this array, you should implement the fast divide-and-conquer algorithm covered in the video lectures. The numeric answer for the given input file should be typed in the space below.
So if your answer is 1198233847, then just type 1198233847 in the space provided without any space / commas / any other punctuation marks. You can make up to 5 attempts, and we'll use the best one for grading.
(We do not require you to submit your code, so feel free to use any programming language you want --- just type the final numeric answer in the following space.)

[TIP: before submitting, first test the correctness of your program on some small test files or your own devising. Then post your best test cases to the discussion forums to help your fellow students!]
 */
public class CountInversions {

    private static long mergeCount (int[] intArray, int start, int end) {
        int mid = start + (end - start) / 2;

        if (start == end) {
            return 0;
        }
        if (end - start == 1) {
            if (intArray[end] < intArray[start]) {
                int temp = intArray[end];
                intArray[end] = intArray[start];
                intArray[start] = temp;
                return 1;
            } else {
                return 0;
            }
        }
        long left = mergeCount(intArray, start, mid);
        long right = mergeCount(intArray, mid + 1, end);
        long split = countSplit(intArray, start, end);
        return left + right + split;
    }

    private static long countSplit (int[] intArray, int start, int end) {
        int mid = start + (end - start) / 2;
        int[] sortedArray = new int[end - start + 1];
        int count = 0;
        int i = start;
        int j = mid + 1;
        long inversions = 0;
        while (i <= mid || j <= end) {
            if (i > mid) {
                sortedArray[count] = intArray[j];
                count++;
                j++;
                continue;
            }
            if (j > end) {
                sortedArray[count] = intArray[i];
                count++;
                i++;
                continue;
            }
            if (intArray[i] < intArray[j]) {
                sortedArray[count] = intArray[i];
                count++;
                i++;
            } else {
                sortedArray[count] = intArray[j];
                inversions += (mid - i + 1);
                count++;
                j++;
            }
        }
        for (int k = 0; k < end - start + 1; k++) {
            intArray[start + k] = sortedArray[k];
        }
        return inversions;
    }

    public static void main(String[] args) throws IOException{
        InputStream in = new FileInputStream(new File("D:/Programming/Coursera-Algorithms-" +
              "I/IntegerArray.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int intNum = 100000;
        int i = 0;
        int[] intArray = new int[intNum];
        while (i < intNum) {
            intArray[i] = Integer.parseInt(reader.readLine());
            i++;
        }
        System.out.println(mergeCount(intArray, 0, 99999));
    }
}

/*
When use recursion, pay attention to the start and end point in every recursion
e.g:
        long left = mergeCount(intArray, start, mid);
        long right = mergeCount(intArray, mid + 1, end); // mid + 1 here

 */
