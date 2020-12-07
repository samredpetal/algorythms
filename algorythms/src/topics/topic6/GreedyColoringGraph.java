package topics.topic6;

import java.util.*;
import java.util.LinkedList;

/**
 * Неориентированный граф основанный не смежной матрице
 */
public class GreedyColoringGraph
{
    public static void main(String args[])
    {
        GreedyColoringGraph g1 = new GreedyColoringGraph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        System.out.println("Раскраска к графом 1");
        g1.greedyColoring();

        System.out.println();
        GreedyColoringGraph g2 = new GreedyColoringGraph(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 2);
        g2.addEdge(1, 4);
        g2.addEdge(2, 4);
        g2.addEdge(4, 3);
        System.out.println("Раскраска с графом 2");
        g2.greedyColoring();
    }

    /**
     * Кол-во вершин
     */
    private int V;
    /**
     * Матрица смежности
     */
    private LinkedList<Integer> adj[];

    GreedyColoringGraph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    /**
     * Добавление вершины
     * @param v
     * @param w
     */
    void addEdge(int v,int w)
    {
        adj[v].add(w);
        adj[w].add(v);
    }


    /**
     * Раскраска вершин начиная с 0
     */
    void greedyColoring()
    {
        int result[] = new int[V];

        result[0]  = 0;

        for (int u = 1; u < V; u++)
            result[u] = -1;

        /*
         * Временный массив для хранения доступных цветов.
         * Значение available[cr] будет означать что цвет cr присвоен к одному из его смежных вершин
         */
        boolean available[] = new boolean[V];
        for (int cr = 0; cr < V; cr++)
            available[cr] = false;

        /* Присвоить цвета остальным V-1 вершинам */
        for (int u = 1; u < V; u++)
        {
            /* Обработать все смежные вершины и обозначить цвета недоступным */
            Iterator<Integer> it = adj[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = true;
            }

            /* Найти первый доступный цвет */
            int cr;
            for (cr = 0; cr < V; cr++)
                if (!available[cr])
                    break;

            result[u] = cr; // придать найденный цвет

            /* Вернуть значения назад для следующей итерации */
            it = adj[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }
        }

        /* Вывод результата */
        for (int u = 0; u < V; u++)
            System.out.println("Вершина " + u + " --->  Цвет "
                    + result[u]);
    }
}
