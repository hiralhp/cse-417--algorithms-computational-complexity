//input test file to stdin: src/tests/n16d3s17.txt


/*
* Hiral Patel
* 1521665
* hiralp@uw.edu
* */

package biconnected_components;

import java.io.*;
import java.util.*;


public class DFS {
    public static Set<Integer> articulationPoints;
    private undirectedGraph graph;
    private List<Integer>[] backEdges;
    private List<Integer>[] children;
    private int[] dfsNums;
    private int[] lowVal;
    private int root;
    private int count;

    public DFS(undirectedGraph g) {
        graph = new undirectedGraph(g);
        dfsNums = new int[g.numberOfNodes()];
        for (int i = 0; i < dfsNums.length; i++)
            dfsNums[i] = -1;

        backEdges = (LinkedList<Integer>[]) new LinkedList[g.numberOfNodes()];
        for (int i = 0; i < backEdges.length; i++)
            backEdges[i] = new LinkedList<Integer>();

        children = (LinkedList<Integer>[]) new LinkedList[g.numberOfNodes()];
        for (int i = 0; i < children.length; i++)
            children[i] = new LinkedList<Integer>();

        lowVal = new int[g.numberOfNodes()];
        articulationPoints = new HashSet<Integer>();
        root = -1;
        count = 0;
    }

    public void depthFirstSearch(int n, int vParent) {

        if (count == graph.numberOfNodes()) {
            if(root == n){
                return;
            }
            resetDFS();
        }

        count++;

        if (count == 1) {
            root = n;
        }

        dfsNums[n] = count;
        lowVal[n] = dfsNums[n];

        List<Integer> neighbors = graph.neighbors(n);
        for (int x : neighbors) {
            if (dfsNums[x] == -1) {
                children[n].add(x);
                depthFirstSearch(x, n);
                lowVal[n] = Math.min(lowVal[n], lowVal[x]);

                if (dfsNums[n] == 1) {
                    if (children[n].size() > 1)
                        articulationPoints.add(n);
                } else if (lowVal[x] >= dfsNums[n]) {
                    articulationPoints.add(n);
                }
            } else if (x != vParent) {
                if (dfsNums[x] < dfsNums[n]) {
                    backEdges[n].add(x);
                }
                lowVal[n] = Math.min(lowVal[n], dfsNums[x]);
            }
        }
    }

    private void resetDFS() {
        for (int i = 0; i < children.length; i++) {
            children[i] = new LinkedList<Integer>();
        }
        for (int i = 0; i < dfsNums.length; i++) {
            dfsNums[i] = -1;
        }
        for (int i = 0; i < backEdges.length; i++) {
            backEdges[i] = new LinkedList<Integer>();
        }

        lowVal = new int[graph.numberOfNodes()];
        articulationPoints = new HashSet<Integer>();
        root = -1;
        count = 0;
    }

    public List<LinkedList<getEdge>> getBiconnectedComponenets() {
        List<LinkedList<getEdge>> comps = new LinkedList<LinkedList<getEdge>>();
        for (int i = 0; i < graph.numberOfNodes(); i++) {
            for (int x : children[i]) {
                if (lowVal[x] >= dfsNums[i]) {
                    LinkedList<getEdge> comp = new LinkedList<getEdge>();
                    comp.add(new getEdge(i, x));
                    helper(comp, x);
                    comps.add(comp);
                }
            }
        }
        return comps;
    }


    private void helper(LinkedList<getEdge> list, int a) {
        for (int b : backEdges[a])
            list.add(new getEdge(a, b));

        for (int x : children[a]) {
            if (lowVal[x] < dfsNums[a]) {
                list.add(new getEdge(a, x));
                helper(list, x);
            }
        }
    }

    class getEdge {
        int a;
        int b;
        getEdge (int x, int y) {
            this.a = x;
            this.b = y;
        }

        public String toString() {
            return a + "-" + b;
        }
    }

    private static void runInputTest() throws FileNotFoundException {
        System.out.println("enter file path: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        undirectedGraph g = undirectedGraph.createGraph(path);
        DFS dfsd = new DFS(g);
        dfsd.depthFirstSearch(0, -1);
        Set<Integer> set = articulationPoints;

        long total = 0;
        List<LinkedList<getEdge>> biconectedList = null;
        for (int i = 0; i < 500; i++) {
            long start = System.currentTimeMillis();
            biconectedList = dfsd.getBiconnectedComponenets();
            long end = System.currentTimeMillis();
            total += end - start;
        }
        double averageTime = total * 1.0 / 500;

        System.out.println("# of nodes: " + g.numberOfNodes());
        System.out.println("# of edges: " + g.numberOfEdges());
        System.out.println("# of articulation points: " + set.size());
        System.out.println("# of biconnected components: " + biconectedList.size());
        System.out.println();

        System.out.println("Articulation Points: ");
        for (int n : set) {
            System.out.print(n+" ");
        }
        System.out.println("\n");

        System.out.print("Biconnected components: ");
        for (LinkedList<getEdge> list : biconectedList) {
            System.out.println();
            for (getEdge curr : list)
                System.out.print(curr+" ");
        }
        System.out.println("\n");

        System.out.printf("Algorithm run time: %.3f milliseconds\n",  averageTime);
    }

    public static void main(String[] args) throws FileNotFoundException {
        runInputTest();
    }
}
