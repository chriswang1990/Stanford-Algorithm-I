package com.chriswang.stanford.algorithmI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

//Need to use Xss10m for the stack size

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

public class SCCRe {
    public static class Node {
        boolean visited = false;
        ArrayList<Integer> neighbours = new ArrayList<>();
        int label = -1;
        Node (int i) {
            label = i;
        }
    }

    public static void countSCC(HashMap<Integer, Node> graph,
                                              HashMap<Integer, Node> graphRev) {
        Stack<Node> order = new Stack<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(5, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        for (Integer i = 1; i < graphRev.size() + 1; i++) {
            Node node = graphRev.get(i);
            if (node.visited) {
                continue;
            }
            countFTime(graph, graphRev, node.label, order);
        }
        while (!order.empty()) {
            Node cur = order.pop();
            if (!cur.visited) {
                int size = 0;
                size = dfs(cur, size, graph);
                pq.offer(size);
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(pq.poll());
        }
    }

    private static int dfs(Node node, int size, HashMap<Integer, Node> graph){
        node.visited = true;
        size++;
        for (Integer i : node.neighbours) {
            Node neighbour = graph.get(i);
            if (!neighbour.visited) {
                size = dfs(neighbour, size, graph);
            }
        }
        return size;
    }

    private static void countFTime(HashMap<Integer, Node> graph, HashMap<Integer,Node> graphRev,
                                   Integer nodeLabel, Stack<Node> order) {
        Node node = graphRev.get(nodeLabel);
        node.visited = true;
        for (Integer neighbour : node.neighbours) {
            Node neighbourNode = graphRev.get(neighbour);
            if (!neighbourNode.visited) {
                countFTime(graph, graphRev, neighbourNode.label, order);
            }
        }
        order.push(graph.get(node.label));
    }

    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream(new File("SCC.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HashMap<Integer, Node> graph = new HashMap<>();
        HashMap<Integer, Node> graphRev = new HashMap<>();

        for (Integer i = 1; i < 875715; i++) {
            graph.put(i, new Node(i));
            graphRev.put(i, new Node(i));
        }

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            Integer tail = Integer.valueOf(line.split(" ")[0]);
            Integer head = Integer.valueOf(line.split(" ")[1]);
            graph.get(tail).neighbours.add(head);
            graphRev.get(head).neighbours.add(tail);
        }
        countSCC(graph, graphRev);
    }
}
