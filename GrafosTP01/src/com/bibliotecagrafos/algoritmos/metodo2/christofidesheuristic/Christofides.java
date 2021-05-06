package com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.grafo.Grafo;
import com.bibliotecagrafos.vertice.Vertice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Christofides {

    public static void  Christofides(Grafo grafo){

        // Árvore Geradora Mínima
        Aresta[] mst = KruskalSpanningTree.KuskalMSP(grafo);

        // Print
        System.out.println("Following are the edges in "
                + "the constructed MST");
        int minimumCost = 0;
        for (int i = 0; i < mst.length-1; ++i)
        {
            System.out.println(mst[i].VerticeDeOrigem().getIndex() + " -- "
                    + mst[i].VerticeAlvo().getIndex()
                    + " == " + mst[i].Peso());
            minimumCost += mst[i].Peso();
        }
        System.out.println("Minimum Cost Spanning Tree "
                + minimumCost);

        // Indução de Vertices impares da MST
        // Exemplo:
        // Grafo  1-2-1-3-4-5  vira   1--3--5
        // Sendo que deve-se somar o peso das arestas para forma a nova aresta

        /*
        ArrayList<Integer> mstImpares = new ArrayList<Aresta>();

        // Isso apenas abstrai os graus dos vértices da nossa MST (arvore geradora)
        HashMap<Integer, HashSet<Integer>> vertices = new HashMap<>();
        for (int i = 0; i < mst.length-1; ++i)
        {
            int origin =  mst[i].VerticeDeOrigem().getIndex();
            int end =  mst[i].VerticeAlvo().getIndex();

            // Se vertices possui o vertice origin
            if (vertices.containsKey(origin)) {
                // Adiciona seus vizinhos
                vertices.get(origin).add(end);
            }
            else { // Se nao possui, adiciona ambos
                    vertices.put(origin, new HashSet<>());
                    vertices.get(origin).add(end);
            }
            // Se vertices possui o vertice end
            if (vertices.containsKey(end)) {
                vertices.get(end).add(origin);
            } else { // Se nao possui, adiciona ambos
                vertices.put(end, new HashSet<>());
                vertices.get(end).add(origin);
            }
        }

        // Agora que sabemos o grau dos vértices podemos escolher apenas aqueles de grau impar
        for (Integer vert : vertices.keySet()){
            if (vertices.get(vert).size() % 2 == 1){
                // para cada vertice impar
                // armazenar

                // mas como fazer as arestas depois?
                // isso tudo deve estar errado
            }
        }
*/
        // Matching Perfeito Mínimo para os vértices de grau ímpar
        EdmondsPerfectMatching epm = new EdmondsPerfectMatching();
        epm.ChristofidesEmparelhar(mst);

    }

    public void SubGrafoDeImpares(){

    }
}

