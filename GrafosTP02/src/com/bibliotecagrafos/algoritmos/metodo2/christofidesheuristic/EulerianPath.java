package com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.grafo.Grafo;
import com.bibliotecagrafos.vertice.Vertice;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

 public class EulerianPath {

    // Function to find out the path
    // It takes the adjacency matrix
    // representation of the graph as input
    static Vector<Integer> findpath(ArrayList<Aresta> arestas, Grafo grafo)
    {
        //Condição necessaria e suficiente não precisa ser verificada, pelo menos eu acho

        // If there is a path find the path
        // Initialize empty stack and path
        // take the starting current as discussed
        Stack<Integer> stack = new Stack<>();
        Vector<Integer> path = new Vector<>();
        int cur = 1;

        // Loop will run until there is element in the stack
        // or current edge has some neighbour.
        while (!stack.isEmpty() || contemAresta(arestas, cur))
        {

            // If current node has not any neighbour
            // add it to path and pop stack
            // set new current to the popped element
            if (!contemAresta(arestas, cur))
            {
                path.add(cur);
                cur = stack.pop();

                // If the current vertex has at least one
                // neighbour add the current vertex to stack,
                // remove the edge between them and set the
                // current to its neighbour.
            }
            else
            {
                for (int i = 1; i <= grafo.Ordem(); i++)
                {
                    if (contarArestaEntre(arestas, cur, i) == 1)
                    {
                        stack.add(cur);
                        removerAresta(arestas, cur, i);
                        //System.out.println("Index: " + i);
                        cur = i;
                        break;
                    }
                }
            }
        }

        // print the path
        /*
        for (int ele : path)
            System.out.print(ele + " -> ");
        System.out.println(cur);
         */

        return path;
    }

    private static boolean contemAresta(ArrayList<Aresta> arestas, int cur){
        
        for(Aresta a: arestas){
            if(a.VerticeAlvo().getIndex() == cur || a.VerticeDeOrigem().getIndex() == cur) return true;
        }


        return false;
    }

    private static boolean contemArestaEntre(ArrayList<Aresta> arestas, int cur, int i){

        for(Aresta a: arestas){
            if(a.VerticeAlvo().getIndex() == cur && a.VerticeDeOrigem().getIndex() == i ) return true;
            if(a.VerticeAlvo().getIndex() == i && a.VerticeDeOrigem().getIndex() == cur ) return true;
            
        }

        return false;
    }

    private static void removerAresta(ArrayList<Aresta> arestas, int cur, int i){

        int indexRemove = -1;
        int j = 0;
        for(Aresta a: arestas){
            if(a.VerticeAlvo().getIndex() == cur && a.VerticeDeOrigem().getIndex() == i ){
                indexRemove = j;
                break;
            } 
            if(a.VerticeAlvo().getIndex() == i && a.VerticeDeOrigem().getIndex() == cur ) {
                indexRemove = j;
                break;
            };

            j++;
        }

        if(indexRemove != -1) arestas.remove(indexRemove);
    }

    public static int contarArestaEntre(ArrayList<Aresta> arestas, int cur, int i){
        int j = 0;
        for(Aresta a: arestas){
            if(a.VerticeAlvo().getIndex() == cur && a.VerticeDeOrigem().getIndex() == i ){
                j++;
                break;
            } 
            if(a.VerticeAlvo().getIndex() == i && a.VerticeDeOrigem().getIndex() == cur ) {
                j++;
                break;
            };
        }

        return j;
    }


}
