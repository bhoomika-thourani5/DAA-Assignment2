package Assignment2_DAA;

import java.util.*;

public class EdmondsKarp {

    static int n = 7; // 0=A 1=B 2=C 3=D 4=E 5=F 6=G
    static int[][] graph = new int[n][n];

    public static void main(String[] args) {

        graph[0][3] = 3; // A-D
        graph[0][2] = 3; // A-C
        graph[0][1] = 3; // A-B
        graph[3][5] = 6; // D-F
        graph[2][3] = 1; // C-D
        graph[3][4] = 2; // D-E
        graph[5][6] = 9; // F-G
        graph[2][1] = 4; // C-B
        graph[2][4] = 2; // C-E
        graph[4][1] = 1; // E-B
        graph[4][6] = 1; // E-G

        int source = 0;
        int sink = 6;
        int maxFlow = 0;

        int[] parent = new int[n];

        while (bfs(source, sink, parent)) {

            // find the bottleneck capacity in this path
            int pathFlow = 999999;
            int v = sink;
            while (v != source) {
                int u = parent[v];
                if (graph[u][v] < pathFlow)
                    pathFlow = graph[u][v];
                v = u;
            }

            // update capacities along the path
            v = sink;
            while (v != source) {
                int u = parent[v];
                graph[u][v] = graph[u][v] - pathFlow;
                graph[v][u] = graph[v][u] + pathFlow;
                v = u;
            }

            maxFlow = maxFlow + pathFlow;
        }

        System.out.println("Max Flow from A to G = " + maxFlow);
    }

    // simple bfs, returns true if we can reach sink from source
    static boolean bfs(int source, int sink, int[] parent) {
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