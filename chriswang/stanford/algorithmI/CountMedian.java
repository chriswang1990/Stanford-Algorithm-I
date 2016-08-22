package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The goal of this problem is to implement the "Median Maintenance" algorithm (covered in the Week 5
 * lecture on heap applications). The text file contains a list of the integers from 1 to 10000 in
 * unsorted order; you should treat this as a stream of numbers, arriving one by one. Letting xi
 * denote the ith number of the file, the kth median mk is defined as the median of the numbers x1,…,
 * xk. (So, if k is odd, then mk is ((k+1)/2)th smallest number among x1,…,xk; if k is even, then mk
 * is the (k/2)th smallest number among x1,…,xk.)
 * <p>
 * In the box below you should type the sum of these 10000 medians, modulo 10000 (i.e., only the last
 * 4 digits). That is, you should compute (m1+m2+m3+⋯+m10000)mod10000.
 * <p>
 * OPTIONAL EXERCISE: Compare the performance achieved by heap-based and search-tree-based
 * implementations of the algorithm.
 */
public class CountMedian {

    public static int countMedian(int[] input) {
        PriorityQueue<Integer> max = new PriorityQueue<>();
        PriorityQueue<Integer> min = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int ans = 0;
        for (int i : input) {
            if (max.isEmpty() && min.isEmpty()) {
                min.offer(i);
                ans += i;
                continue;
            }
            if (i < min.peek()) {
                min.offer(i);
            } else {
                max.offer(i);
            }
            if (max.size() > min.size()) {
                min.offer(max.poll());
            }
            if (min.size() > max.size() + 1) {
                max.offer(min.poll());
            }
            ans += min.peek();
            ans %= 10000;
            System.out.println(min.size() + ", " + max.size());
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        InputStream in = new FileInputStream(new File("JavaPractice/src/com/Median.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int[] input = new int[10000];
        for (int i = 0; i < 10000; i++) {
            input[i] = Integer.parseInt(reader.readLine());
        }
        System.out.println(countMedian(input));
    }
}
