package Assignment2_DAA;

import java.util.*;

public class EdmondsKarp {

    static int n = 7;
    static String[] name = {"A", "B", "C", "D", "E", "F", "G"};
    static int[][] capacity = new int[n][n];

    public static void main(String[] args) {

        capacity[0][3] = 3;
        capacity[0][2] = 3;
        capacity[0][1] = 3;
        capacity[3][5] = 6;
        capacity[2][3] = 1;
        capacity[3][4] = 2;
        capacity[5][6] = 9;
        capacity[2][1] = 4;
        capacity[2][4] = 2;
        capacity[4][1] = 1;
        capacity[4][6] = 1;

        System.out.println("Graph connections (edge : capacity):");
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (capacity[u][v] > 0) {
                    System.out.println(name[u] + " -> " + name[v] + " : " + capacity[u][v]);
                }
            }
        }
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            graph[i] = capacity[i].clone();
        }

        int source = 0;
        int sink = 6;
        int maxFlow = 0;
        int pathCount = 1;

        int[] parent = new int[n];

        System.out.println("\nFinding augmenting paths:");

        while (bfs(graph, source, sink, parent)) {

            int pathFlow = 999999;
            int v = sink;
            while (v != source) {
                int u = parent[v];
                if (graph[u][v] < pathFlow)
                    pathFlow = graph[u][v];
                v = u;
            }

            StringBuilder path = new StringBuilder(name[sink]);
            v = sink;
            while (v != source) {
                v = parent[v];
                path.insert(0, name[v] + " -> ");
            }

            System.out.println("Path " + pathCount + ": " + path + "   | Flow pushed = " + pathFlow);

            v = sink;
            while (v != source) {
                int u = parent[v];
                graph[u][v] = graph[u][v] - pathFlow;
                graph[v][u] = graph[v][u] + pathFlow;
                v = u;
            }

            maxFlow = maxFlow + pathFlow;
            pathCount++;
        }
        System.out.println("\nFinal Maximum Flow from A to G = " + maxFlow);

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
        System.out.println("\nMinimum Cut edges (capacity):");
        int cutTotal = 0;
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (visited[u] && !visited[v] && capacity[u][v] > 0) {
                    System.out.println(name[u] + " -> " + name[v] + " : " + capacity[u][v]);
                    cutTotal = cutTotal + capacity[u][v];
                }
            }
        }
        System.out.println("Min Cut Value = " + cutTotal);
    }
    static boolean bfs(int[][] graph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                if (visited[v] == false && graph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return visited[sink];
    }
}