package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * The goal of this problem is to implement a variant of the 2-SUM algorithm (covered in the Week 6
 * lecture on hash table applications).
 * <p>
 * The file contains 1 million integers, both positive and negative (there might be some repetitions!)
 * .This is your array of integers, with the ith row of the file specifying the ith entry of the array.
 * <p>
 * Your task is to compute the number of target values t in the interval [-10000,10000] (inclusive)
 * such that there are distinct numbers x,y in the input file that satisfy x+y=t. (NOTE: ensuring
 * distinctness requires a one-line addition to the algorithm from lecture.)
 * <p>
 * Write your numeric answer (an integer between 0 and 20001) in the space provided.
 * <p>
 * OPTIONAL CHALLENGE: If this problem is too easy for you, try implementing your own hash table for
 * it. For example, you could compare performance under the chaining and open addressing approaches
 * to resolving collisions.
 */
public class Count2SumTarget {

    public static int countTarget(long[] input) {


        for (int t = -10000; t < 10001; t++) {
            HashSet<Long>set = new HashSet<>();
            for (long l : input) {
                set.add(t - l);
            }
            for (long l : input) {
                if (set.contains(l)) {
                    return t + 10000;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        InputStream in = new FileInputStream(new File("JavaPractice/src/com/2sum.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        long[] input = new long[1000000];
        for (int i = 0; i < 1000000; i++) {
            input[i] = Long.parseLong(reader.readLine());
        }
        System.out.println(countTarget(input));
    }
}
