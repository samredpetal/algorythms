package topics.topic2.graphs;

import java.util.*;
import java.util.LinkedList;

/**
 * Определение сильносвязанных компонентов графа по алгоритму Косайро
 */
public class KosarajusAlgorithmGraph {
    /**
     * Кол-во вершин
     */
    private int V;
    /**
     * Направления вершин графа
     */
    private LinkedList<Integer> adj[];


    KosarajusAlgorithmGraph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList<>();
    }

    /**
     * Добавление вершины для графа
     * @param v
     * @param w
     */
    void addEdge(int v, int w){
        adj[v].add(w);
    }

    /**
     * Метод рекурсивно делает обход в глубину граф начиная с вершины v
     * @param v
     * @param visited
     */
    void DFSUtil(int v, boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v + " ");

        int n;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i =adj[v].iterator();
        while (i.hasNext())
        {
            n = i.next();
            if (!visited[n])
                DFSUtil(n,visited);
        }
    }

    /**
     * Метод возвращает инверсию графа
     * @return
     */
    KosarajusAlgorithmGraph getTranspose()
    {
        KosarajusAlgorithmGraph g = new KosarajusAlgorithmGraph(V);
        for (int v = 0; v < V; v++)
        {
            // Recur for all the vertices adjacent to this vertex
            for (Integer integer : adj[v])
                g.adj[integer].add(v);
        }
        return g;
    }


    void fillOrder(int v, boolean visited[], Stack<Integer> stack)
    {
        /* Обозначение вершины как посещенного */
        visited[v] = true;

        for (Integer n : adj[v]) {
            if (!visited[n])
                fillOrder(n, visited, stack);
        }

        stack.push(v);
    }


    /**
     * Функция находит и выводит все сильносвязанные компоненты графа
     */
    void printSCCs()
    {
        Stack<Integer> stack = new Stack<Integer>();

        /*
         * Обозначение вершин как еще посещенных
         */
        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++)
            visited[i] = false;

        for (int i = 0; i < V; i++)
            if (!visited[i])
                fillOrder(i, visited, stack);

        KosarajusAlgorithmGraph gr = getTranspose();

        /* Обозначить все вершины как еще не посещщенных для второго обхода */
        for (int i = 0; i < V; i++)
            visited[i] = false;

        /* Проверить все вершины по их месту в стеке */
        while (!stack.empty())
        {
            int v = stack.pop();

            /* Вывести сильносвязанные компоненты текущей вершины */
            if (!visited[v])
            {
                gr.DFSUtil(v, visited);
                System.out.println();
            }
        }
    }

    public static void main(String args[])
    {
        KosarajusAlgorithmGraph g = new KosarajusAlgorithmGraph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        System.out.println("Сильносвязанные компоненты графа: ");
        g.printSCCs();
    }
}