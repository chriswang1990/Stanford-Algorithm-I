package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * In this programming problem you'll code up Dijkstra's shortest-path algorithm.
 Download the following text file:
 dijkstraData.txt
 The file contains an adjacency list representation of an undirected weighted graph with 200
 vertices labeled 1 to 200. Each row consists of the node tuples that are adjacent to that
 particular vertex along with the length of that edge. For example, the 6th row has 6 as the first
 entry indicating that this row corresponds to the vertex labeled 6. The next entry of this row
 "141,8200" indicates that there is an edge between vertex 6 and vertex 141 that has length 8200.
 The rest of the pairs of this row indicate the other vertices adjacent to vertex 6 and the lengths
 of the corresponding edges.

 Your task is to run Dijkstra's shortest-path algorithm on this graph, using 1 (the first vertex)
 as the source vertex, and to compute the shortest-path distances between 1 and every other vertex
 of the graph. If there is no path between a vertex v and vertex 1, we'll define the shortest-path
 distance between 1 and v to be 1000000.

 You should report the shortest-path distances to the following ten vertices, in order:
 7,37,59,82,99,115,133,165,188,197. You should encode the distances as a comma-separated string of
 integers. So if you find that all ten of these vertices except 115 are at distance 1000 away from
 vertex 1 and 115 is 2000 distance away, then your answer should be 1000,1000,1000,1000,1000,2000,
 1000,1000,1000,1000. Remember the order of reporting DOES MATTER, and the string should be in the
 same order in which the above ten vertices are given. The string should not contain any spaces.
 Please type your answer in the space provided.

 IMPLEMENTATION NOTES: This graph is small enough that the straightforward O(mn) time implementation
 of Dijkstra's algorithm should work fine. OPTIONAL: For those of you seeking an additional
 challenge, try implementing the heap-based version. Note this requires a heap that supports
 deletions, and you'll probably need to maintain some kind of mapping between vertices and their
 positions in the heap.
 */

/*
 NOTES: Used straightforward method.
 */
public class CountShortestPath {

    public static HashMap<Integer, Integer> countShortestPath(HashMap<Integer, ArrayList> map) {
        HashMap<Integer, Integer> sdMap = new HashMap<>();
        for (int i = 1; i < map.size() + 1; i++) {
            sdMap.put(i, 1000000);
        }
        sdMap.put(1, 0);

        HashSet<Integer> visited = new HashSet<>();
        visited.add(1);
        while (true) {
            int min = 1000000;
            int Tail = 0;
            int Head = 0;
            for (int i : map.keySet()) {
                if (visited.contains(i)) {
                    ArrayList<int[]> pathList = map.get(i);
                    for (int[] hdTuple : pathList) {
                        if (!visited.contains(hdTuple[0])) {
                            int tailMin = sdMap.get(i);
                            int curLen = tailMin + hdTuple[1];
                            if (curLen < min) {
                                Tail = i;
                                Head = hdTuple[0];
                                min = curLen;
                            }
                        }
                    }
                }
            }
            if (Tail == 0) {
                break;
            }
            sdMap.put(Head, min);
            visited.add(Head);
        }
        return sdMap;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        InputStream in = new FileInputStream(new File("JavaPractice/src/com/dijkstraData.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HashMap<Integer, ArrayList> map = new HashMap<>();
        HashMap<Integer, Integer> sdMap;
        for (int i = 1; i < 201; i++) {
            String line = reader.readLine();
            String[] tupleArray = line.split("\\s+");
            ArrayList<int[]> pathList = new ArrayList<>();
            for (String tuple : tupleArray) {
                if (tuple.split(",").length == 1) {
                    continue;
                }
                int[] hdTuple = new int[2];
                hdTuple[0] = Integer.parseInt(tuple.split(",")[0]);
                hdTuple[1] = Integer.parseInt(tuple.split(",")[1]);
                pathList.add(hdTuple);
            }
            map.put(i, pathList);
        }
        sdMap = countShortestPath(map);
        for (int i : sdMap.keySet()) {
            System.out.println(i + " : " + sdMap.get(i));
        }
    }
}
