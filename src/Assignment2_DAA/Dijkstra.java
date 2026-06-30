package Assignment2_DAA;

public class Dijkstra {

    static int V = 9;
    static int INF = 999999;

    public static void main(String[] args) {

        int[][] graph = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = 0;
            }
        }
        addEdge(graph, 0, 1, 4);
        addEdge(graph, 0, 7, 8);
        addEdge(graph, 1, 2, 8);
        addEdge(graph, 1, 7, 11);
        addEdge(graph, 2, 3, 7);
        addEdge(graph, 2, 8, 2);
        addEdge(graph, 2, 5, 4);
        addEdge(graph, 3, 4, 9);
        addEdge(graph, 3, 5, 14);
        addEdge(graph, 4, 5, 10);
        addEdge(graph, 5, 6, 2);
        addEdge(graph, 6, 7, 1);
        addEdge(graph, 6, 8, 6);
        addEdge(graph, 7, 8, 7);

        int src = 0;
        int[] dist = new int[V];
        int[] pred = new int[V];
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = INF;
            pred[i] = -1;
            visited[i] = false;
        }
        dist[src] = 0;

        System.out.println("Step 0: Initialization.\n");
        printTable(dist, pred, visited);
        printQueue(dist, visited);

        int step = 1;
        for (int count = 0; count < V; count++) {

            int u = -1;
            int min = INF;
            for (int i = 0; i < V; i++) {
                if (!visited[i] && dist[i] < min) {
                    min = dist[i];
                    u = i;
                }
            }

            if (u == -1) break;

            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !visited[v]) {
                    if (dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                        pred[v] = u;
                    }
                }
            }
            System.out.println("\nStep " + step + ": Extract-Min returns vertex " + u +
                    " (d[" + u + "] = " + dist[u] + "), color it black, relax its edges.\n");
            printTable(dist, pred, visited);
            printQueue(dist, visited);

            step++;
        }

        System.out.println("\nFinal shortest distances from source 0:");
        for (int i = 0; i < V; i++) {
            System.out.println("d[" + i + "] = " + dist[i] + "   pred[" + i + "] = " + (pred[i] == -1 ? "nil" : pred[i]));
        }
    }

    static void addEdge(int[][] graph, int u, int v, int w) {
        graph[u][v] = w;
        graph[v][u] = w;
    }

    static void printTable(int[] dist, int[] pred, boolean[] visited) {
        System.out.print("v        | ");
        for (int i = 0; i < V; i++) System.out.printf("%-5d", i);
        System.out.println();

        System.out.print("d[v]     | ");
        for (int i = 0; i < V; i++) {
            String val = dist[i] >= INF ? "inf" : String.valueOf(dist[i]);
            System.out.printf("%-5s", val);
        }
        System.out.println();

        System.out.print("pred[v]  | ");
        for (int i = 0; i < V; i++) {
            String val = pred[i] == -1 ? "nil" : String.valueOf(pred[i]);
            System.out.printf("%-5s", val);
        }
        System.out.println();

        System.out.print("color[v] | ");
        for (int i = 0; i < V; i++) {
            System.out.printf("%-5s", visited[i] ? "B" : "W");
        }
        System.out.println();
    }

    static void printQueue(int[] dist, boolean[] visited) {
        System.out.print("\nPriority Queue:  v    | ");
        for (int i = 0; i < V; i++) {
            if (!visited[i]) System.out.printf("%-5d", i);
        }
        System.out.print("\n                 d[v] | ");
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                String val = dist[i] >= INF ? "inf" : String.valueOf(dist[i]);
                System.out.printf("%-5s", val);
            }
        }
        System.out.println("\n");
    }
}