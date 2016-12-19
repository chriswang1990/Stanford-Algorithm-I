package com.chriswang.stanford.algorithmI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 In this programming problem you'll code up Prim's minimum spanning tree algorithm.
 Download the text file below.

 edges.txt
 This file describes an undirected graph with integer edge costs. It has the format

 [number_of_nodes] [number_of_edges]
 [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
 [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]

 ...

 For example, the third line of the file is "2 3 -8874", indicating that there is an edge connecting vertex #2 and
 vertex #3 that has cost -8874.
 You should NOT assume that edge costs are positive, nor should you assume that they are distinct.
 Your task is to run Prim's minimum spanning tree algorithm on this graph. You should report the overall cost of a
 minimum spanning tree --- an integer, which may or may not be negative --- in the box below.

 IMPLEMENTATION NOTES: This graph is small enough that the straightforward O(mn) time implementation of Prim's
 algorithm should work fine. OPTIONAL: For those of you seeking an additional challenge, try implementing a heap-based
 version. The simpler approach, which should already give you a healthy speed-up, is to maintain relevant edges in a
 heap (with keys = edge costs). The superior approach stores the unprocessed vertices in the heap, as described in
 lecture. Note this requires a heap that supports deletions, and you'll probably need to maintain some kind of mapping
 between vertices and their positions in the heap.
 */
public class MST {

    public static void main(String[] args) throws IOException{
        InputStream in = new FileInputStream(new File("JavaPractice/src/com/edges.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String[] tuple = reader.readLine().split(" ");
        int n = Integer.valueOf(tuple[0]);
        int e = Integer.valueOf(tuple[1]);
        HashMap<Integer, ArrayList<int[]>> nodeMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            nodeMap.put(i, new ArrayList<>());
        }
        for (int i = 0; i < e; i++) {
            String[] edge = reader.readLine().split(" ");
            int a = Integer.valueOf(edge[0]);
            int b = Integer.valueOf(edge[1]);
            int l = Integer.valueOf(edge[2]);
            nodeMap.get(a).add(new int[] {b, l});
            nodeMap.get(b).add(new int[] {a, l});
        }
        HashSet<Integer> visited = new HashSet<>();
        visited.add(1);
        int ans = 0;
        while (visited.size() < n) {
            int min = Integer.MAX_VALUE;
            int nextNode = 0;
            for (int i : visited) {
                ArrayList<int[]> list = nodeMap.get(i);
                for (int[] arr : list) {
                    if (visited.contains(arr[0])) {
                        continue;
                    }
                    if (arr[1] < min) {
                        min = arr[1];
                        nextNode = arr[0];
                    }
                }
            }
            ans += min;
            visited.add(nextNode);
        }
        System.out.println(ans);
    }
}
