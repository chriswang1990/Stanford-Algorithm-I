package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 The file contains the adjacency list representation of a simple undirected graph. There are 200
 vertices labeled 1 to 200. The first column in the file represents the vertex label, and the
 particular row (other entries except the first column) tells all the vertices that the vertex is
 adjacent to. So for example, the 6th row looks like : "6	155	56	52	120	......". This just means
 that the vertex with label 6 is adjacent to (i.e., shares an edge with) the vertices with labels
 155,56,52,120,......,etc

 Your task is to code up and run the randomized contraction algorithm for the min cut problem and
 use it on the above graph to compute the min cut (i.e., the minimum-possible number of crossing
 edges). (HINT: Note that you'll have to figure out an implementation of edge contractions.
 Initially, you might want to do this naively, creating a new graph from the old every time there's
 an edge contraction. But you should also think about more efficient implementations.) (WARNING: As
 per the video lectures, please make sure to run the algorithm many times with different random
 seeds, and remember the smallest cut that you ever find.) Write your numeric answer in the space
 provided. So e.g., if your answer is 5, just type 5 in the space provided.
 */

public class CountMinCut {

    public static int contract (HashMap<Integer, ArrayList<Integer>> graph) {

        if (graph.size() == 1 || graph.size() == 0) {
            return 0;
        }

        ArrayList<Integer> vertexArrayList = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            vertexArrayList.add(i + 1);
        }

        while (graph.size() >= 3) {

            Integer ind1 = vertexArrayList.get(new Random().nextInt(vertexArrayList.size()));
            vertexArrayList.remove(ind1);
            ArrayList<Integer> vertex1 = graph.get(ind1);
            graph.remove(ind1);

            Integer ind2 = vertexArrayList.get(new Random().nextInt(vertexArrayList.size()));
            ArrayList<Integer> vertex2 = graph.get(ind2);
            Iterator<Integer> iterator = vertex2.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(ind1)) {
                    iterator.remove();
                }
            }

            for (int i = 0; i < vertex1.size(); i++) {
                Integer num = vertex1.get(i);
                if (num.equals(ind2)) {
                    continue;
                }

                ArrayList<Integer> neighbour = graph.get(num);
                for (int j = 0; j < neighbour.size(); j++) {
                    if (neighbour.get(j).equals(ind1)) {
                        neighbour.set(j, ind2);
                    }
                }
                vertex2.add(num);
            }
        }
        return graph.get(vertexArrayList.get(0)).size();
    }


    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream(new File("./src/com/kargerMinCut.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            String[] line = reader.readLine().split("\\s+");
            ArrayList<Integer> vertex = new ArrayList<>();
            for (int j = 1; j < line.length; j++) {
                vertex.add(Integer.parseInt(line[j]));
            }
            graph.put(i + 1, vertex);
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            HashMap<Integer, ArrayList<Integer>> newGraph = new HashMap<>(graph);
            int curMin = contract(newGraph);
            System.out.println(curMin);
            min = Math.min(min, curMin);
        }
        System.out.println(min);
    }
}
