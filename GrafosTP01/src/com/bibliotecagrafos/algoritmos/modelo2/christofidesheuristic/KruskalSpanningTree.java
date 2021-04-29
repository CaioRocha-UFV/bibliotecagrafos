package com.bibliotecagrafos.algoritmos.modelo2.christofidesheuristic;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.grafo.Grafo;
import com.bibliotecagrafos.vertice.Vertice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class KruskalSpanningTree {
    //https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/

    /**
     *
     * @param vertice
     * @param grafo
     * @return Um HashSet com as arestas da spanning tree mínima encontrada
     */
    public static void KuskalMSP(Vertice vertice, Grafo grafo){

        int V = grafo.Ordem();
        int E = grafo.Tamanho();

        int i = 0;
        int e = 0;

        HashSet<Aresta> arestas = grafo.getHashSetArestas();
        HashSet<Aresta> result = new HashSet<>();

        PriorityQueue<Aresta> arestasPQ = new PriorityQueue<>(arestas);

        // Armazena o resultado final
        //Aresta[] result = new Aresta[V];

        // An index variable, used for result[]
        //int e = 0;

        // An index variable, used for sorted edges
        //int i = 0;
        //for (i = 0; i < V; ++i)
        //    result[i] = new Aresta();

        // Step 1:  Sort all the edges in non-decreasing
        // order of their weight.  If we are not allowed to
        // change the given graph, we can create a copy of
        // array of edges
        //Arrays.sort(edge);


        // Allocate memory for creating V subsets
        subset[] subsets = new subset[V];

        for (i = 0; i < V; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1)
        {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Aresta next_edge = arestasPQ.poll();

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in "
                + "the constructed MST");
        int minimumCost = 0;
        for (i = 0; i < e; ++i)
        {
            System.out.println(result[i].src + " -- "
                    + result[i].dest
                    + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.println("Minimum Cost Spanning Tree "
                + minimumCost);

    }

    /**
     * Classe util para ser usado em union-find
     */
    private static class subset{
        int parent, rank;
    }

    /**
     * A utility function to find set of an
     * element i (uses path compression technique)
     */
    private static int find(subset subsets[], int i)
    {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent
                    = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    /**
     *  A function that does union of two sets
     *  of x and y (uses union by rank)
     */
    private static subset[] Union(subset[] subsets, int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank
                < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as
            // root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }

        return subsets;
    }
}
