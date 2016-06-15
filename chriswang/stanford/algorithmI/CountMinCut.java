package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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


/*  *****NOTES*******
When you want to deep copy a ArrayList with mutable Objects, remember to iterate and deep copy
all the objects. With only constructor (new ArrayList<>(oldList)) is not enough !!!!!
*/
public class CountMinCut {

    public static int contract (ArrayList<int[]> edges) {

        //create a HashSet for the vertex
        HashSet<Integer> vertex = new HashSet<>();
        for (Integer i = 1; i < 201; i++) {
            vertex.add(i);
        }

        //loop until only two vertex left
        while (vertex.size() >= 3) {

            //randomly select a edge from the edges ArrayList
            int random = new Random().nextInt(edges.size());
            int[] edge = edges.get(random);
            Integer ind1 = edge[0];
            Integer ind2 = edge[1];

            //remove ind1 in the vertex ArrayList
            vertex.remove(ind1);

            //redirect all the edges from or to ind1 as ind2
            for (int[] i : edges) {
                if (i[1] == ind1) {
                    i[1] = ind2;
                }
                if (i[0] == ind1) {
                    i[0] = ind2;
                }
            }

            //Iterate the edges to remove all self direct edges
            Iterator<int[]> iterator = edges.iterator();
            while (iterator.hasNext()) {
                int[] curEdge = iterator.next();
                if (curEdge[0] == curEdge[1]) {
                    iterator.remove();
                }
            }
        }
        //the edge information in edges ArrayList is double size of the actually edges
        return edges.size() / 2;
    }


    public static void main(String[] args) throws IOException {

        //read from the text file
        InputStream in = new FileInputStream(new File("./src/com/kargerMinCut.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        ArrayList<int[]> edges = new ArrayList<>();
        //from 1 to 200, vertex index
        for (int i = 1; i < 201; i++) {
            //split string with any white space
            String[] line = reader.readLine().split("\\s+");
            for (int j = 1; j < line.length; j++) {
                int[] edge = new int[2];
                int neighbour = Integer.parseInt(line[j]);
                edge[0] = i;
                edge[1] = neighbour;
                edges.add(edge);
            }
        }

        //initiate min with a impossible result
        int min = Integer.MAX_VALUE;
        //run the contraction 100 times for the best result
        for (int i = 0; i < 100; i++) {
            //deep copy the original ArrayList for the random selection contraction algorithm
            ArrayList<int[]> newEdges = new ArrayList<>();
            for (int[] j : edges) {
                newEdges.add(new int[] {j[0], j[1]});
            }
            int curMin = contract(newEdges);
            min = Math.min(min, curMin);
        }
        System.out.println("final min: " + min);
    }
}
