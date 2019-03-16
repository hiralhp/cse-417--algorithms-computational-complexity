/*
 * Hiral Patel
 * 1521665
 * hiralp@uw.edu
 * */

package biconnected_components;
import java.io.*;
import java.util.*;

public class undirectedGraph {
    private LinkedList<Integer>[] adjacencyList;
    private int nodes;
    private int edges;

    public undirectedGraph(int n) {
        adjacencyList = (LinkedList<Integer>[]) new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new LinkedList<Integer>();
        }
        nodes = n;
        edges = 0;
    }

    public undirectedGraph(undirectedGraph g) {
        this(g.nodes);
        this.edges = g.edges;
        for (int i = 0; i < g.nodes; i++) {
            adjacencyList[i] = new LinkedList<Integer>(g.adjacencyList[i]);
        }
    }

    public void addEdge(int a, int b) {
        adjacencyList[a].add(b);
        adjacencyList[b].add(a);
        edges++;
    }

    public int numberOfNodes() {
        return nodes;
    }

    public int numberOfEdges() {
        return edges;
    }

    public List<Integer> neighbors(int v) {
        return new LinkedList<Integer>(adjacencyList[v]);
    }

    public static undirectedGraph createGraph(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        int nodes = sc.nextInt();
        undirectedGraph graph = new undirectedGraph(nodes);
        while (sc.hasNextInt()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.addEdge(a, b);
        }
        return graph;
    }
}
