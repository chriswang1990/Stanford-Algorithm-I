package com.chriswang.stanford.algorithmI;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 In this programming problem and the next you'll code up the greedy algorithms from lecture for minimizing the weighted
 sum of completion times..

 Download the text file below.

 jobs.txt
 This file describes a set of jobs with positive and integral weights and lengths. It has the format

 [number_of_jobs]

 [job_1_weight] [job_1_length]

 [job_2_weight] [job_2_length]

 ...

 For example, the third line of the file is "74 59", indicating that the second job has weight 74 and length 59.

 You should NOT assume that edge weights or lengths are distinct.

 Your task in this problem is to run the greedy algorithm that schedules jobs in decreasing order of the difference
 (weight - length). Recall from lecture that this algorithm is not always optimal. IMPORTANT: if two jobs have equal
 difference (weight - length), you should schedule the job with higher weight first. Beware: if you break ties in a
 different way, you are likely to get the wrong answer. You should report the sum of weighted completion times of the
 resulting schedule --- a positive integer --- in the box below.

 ADVICE: If you get the wrong answer, try out some small test cases to debug your algorithm (and post your test cases
 to the discussion forum).
 */
public class WorkScheduler {

    private static class Job {
        int weight;
        int length;
        int diff;
        float quot;
        private Job(int weight, int length) {
            this.weight = weight;
            this.length = length;
            this.diff = weight - length;
            this.quot = weight / (float) length;
        }
    }

    public static void main(String[] args) throws IOException {
        PriorityQueue<Job> queue1 = new PriorityQueue<>(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if (o1.diff != o2.diff) {
                    return o2.diff - o1.diff;
                } else {
                    return o2.weight - o1.weight;
                }
            }
        });
        PriorityQueue<Job> queue2 = new PriorityQueue<>(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if (o2.quot > o1.quot) {
                    return 1;
                } else if (o2.quot == o1.quot) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        InputStream in = new FileInputStream(new File("JavaPractice/src/com/jobs.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int size = Integer.valueOf(reader.readLine());
        for (int i = 0; i < size; i++) {
            String[] tuple = reader.readLine().split(" ");
            queue1.offer(new Job(Integer.valueOf(tuple[0]), Integer.valueOf(tuple[1])));
            queue2.offer(new Job(Integer.valueOf(tuple[0]), Integer.valueOf(tuple[1])));
        }
        long sum1 = 0;
        int time1 = 0;
        long sum2 = 0;
        int time2 = 0;
        while (!queue1.isEmpty()) {
            Job cur = queue1.poll();
            time1 += cur.length;
            sum1 += time1 * cur.weight;
        }
        while (!queue2.isEmpty()) {
            Job cur = queue2.poll();
            time2 += cur.length;
            sum2 += time2 * cur.weight;
        }
        System.out.println("sum1: " + sum1);
        System.out.println("sum2: " + sum2);
    }
}


