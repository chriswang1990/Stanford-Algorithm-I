package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

//not completed. need structure to record when the dfs is finished in stack to determine the
// finish time

/**
 * The file contains the edges of a directed graph. Vertices are labeled as positive integers from 1
 * to 875714. Every row indicates an edge, the vertex label in first column is the tail and the
 * vertex label in second column is the head (recall the graph is directed, and the edges are directed
 * from the first column vertex to the second column vertex). So for example, the 11th row looks liks
 * : "2 47646". This just means that the vertex with label 2 has an outgoing edge to the vertex with
 * label 47646
 * <p>
 * Your task is to code up the algorithm from the video lectures for computing strongly connected
 * components (SCCs), and to run this algorithm on the given graph.
 * <p>
 * Output Format: You should output the sizes of the 5 largest SCCs in the given graph, in decreasing
 * order of sizes, separated by commas (avoid any spaces). So if your algorithm computes the sizes of
 * the five largest SCCs to be 500, 400, 300, 200 and 100, then your answer should be "500,400,300,200
 * ,100". If your algorithm finds less than 5 SCCs, then write 0 for the remaining terms. Thus, if
 * your algorithm computes only 3 SCCs whose sizes are 400, 300, and 100, then your answer should be
 * "400,300,100,0,0".
 * <p>
 * WARNING: This is the most challenging programming assignment of the course. Because of the size of
 * the graph you may have to manage memory carefully. The best way to do this depends on your
 * programming language and environment, and we strongly suggest that you exchange tips for doing
 * this on the discussion forums.
 */

public class SCCNonRe {

    public static ArrayList<Integer> countSCC(HashMap<Integer, ArrayList<Integer>> graph,
                                              HashMap<Integer, ArrayList<Integer>> reversed) {
        ArrayList<Integer> componentsArray = new ArrayList<>();
        HashMap<Integer, Boolean> isVisited = new HashMap<>();
        HashMap<Integer, Integer> fTimeMap = new HashMap<>();
        Integer fTime = 1;

        for (Integer i = 1; i < graph.size() + 1; i++) {
            isVisited.put(i, false);
        }

        for (Integer i = 1; i < reversed.size() + 1; i++) {
            if (isVisited.get(i)) {
                continue;
            }
            isVisited.put(i, true);
            Stack<Integer> stack = new Stack<>();
            stack.push(i);

            while (!stack.isEmpty()) {
                Integer vertex = stack.pop();
                isVisited.put(vertex, true);
                for (Integer neighbour : reversed.get(vertex)) {
                    if (isVisited.get(neighbour)) {
                        continue;
                    }
                    stack.push(neighbour);
                }
                fTimeMap.put(fTime, vertex);
                fTime++;
            }
        }

        return componentsArray;
    }

    public static void main(String[] args) throws IOException {

        InputStream in = new FileInputStream(new File("./src/com/SCC.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> reversed = new HashMap<>();

        for (Integer i = 1; i < 875715; i++) {
            graph.put(i, new ArrayList<>());
            reversed.put(i, new ArrayList<>());
        }

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            Integer tail = Integer.valueOf(line.split(" ")[0]);
            Integer head = Integer.valueOf(line.split(" ")[1]);

            graph.get(tail).add(head);
            reversed.get(head).add(tail);
        }

        countSCC(graph, reversed);
    }
}
