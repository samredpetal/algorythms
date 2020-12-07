package topics.topic2.graphs;

import java.util.*;


/**
 * Опредление сильно связанных компонент графа по алгоритму Тарьяна
 */
public class TarjansAlgorithm {

    List<Integer>[] graph;
    boolean[] visited;
    Stack<Integer> stack;
    int time;
    int[] lowlink;
    List<List<Integer>> components;


    /**
     * Возвращает лист сильносвязанных компонентов из переданного графа
     * @param graph
     * @return
     */
    public List<List<Integer>> stronglyConnectedComponents(List<Integer>[] graph) {
        int n = graph.length;
        this.graph = graph;
        visited = new boolean[n];
        stack = new Stack<>();
        time = 0;
        lowlink = new int[n];
        components = new ArrayList<>();

        for (int u = 0; u < n; u++)
            if (!visited[u])
                dfs(u);

        return components;
    }

    /**
     * Обход графа в глубину
     * @param u
     */
    void dfs(int u) {
        lowlink[u] = time++;
        visited[u] = true;
        stack.add(u);
        boolean isComponentRoot = true;

        for (int v : graph[u]) {
            if (!visited[v])
                dfs(v);
            if (lowlink[u] > lowlink[v]) {
                lowlink[u] = lowlink[v];
                isComponentRoot = false;
            }
        }

        if (isComponentRoot) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int x = stack.pop();
                component.add(x);
                lowlink[x] = Integer.MAX_VALUE;
                if (x == u)
                    break;
            }
            components.add(component);
        }
    }

    public static void main(String[] args) {

        List<Integer>[] g = new ArrayList[7];
        for (int i = 0; i < g.length; i++)
            g[i] = new ArrayList<>();

        g[0].add(1);
        g[0].add(3);
        g[1].add(0);
        g[1].add(4);
        g[2].add(1);
        g[2].add(6);
        g[4].add(3);
        g[5].add(2);
        g[5].add(4);
        g[6].add(5);

        List<List<Integer>> components = new TarjansAlgorithm().stronglyConnectedComponents(g);
        System.out.println("Сильносвязанные компоненты графа: ");
        for (List<Integer> list : components) {
            for (int i : list) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}