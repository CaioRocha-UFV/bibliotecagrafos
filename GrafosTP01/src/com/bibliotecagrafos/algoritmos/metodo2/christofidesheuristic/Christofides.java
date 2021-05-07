package com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.grafo.Grafo;
import com.bibliotecagrafos.vertice.Vertice;

import java.util.ArrayList;
import java.util.Arrays;
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



        //Criando Grafo G(W), onde W são os vértices de grau impar da MST (movimento sem terra)

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
        ArrayList<Integer> impares = new ArrayList<>();
        for (Integer vert : vertices.keySet()){
            if (vertices.get(vert).size() % 2 == 1){
                impares.add(vert);
            }
        }

        ArrayList<Aresta> arestasImpares = SubGrafoDeImpares(impares, grafo);

        System.out.println("--------------------");

        for(Aresta a: arestasImpares){
            System.out.println(a.VerticeDeOrigem().getIndex() + " - " + a.VerticeAlvo().getIndex());
        }

        System.out.println("--------------------");

       // Matching Perfeito Mínimo para os vértices de grau ímpar
        EdmondsPerfectMatching epm = new EdmondsPerfectMatching();

        Aresta[] arestasArray = new Aresta[arestasImpares.size()];
        for(int i = 0; i < arestasImpares.size(); i++) arestasArray[i] = arestasImpares.get(i);

        HashMap<Integer, Integer> retorno = epm.ChristofidesEmparelhar(arestasArray);

        ArrayList<Aresta> mstUniaoMatching = new ArrayList();
        for(Aresta a: mst){
            if(a.VerticeAlvo() != null) mstUniaoMatching.add(a);
        }

        for(int k: retorno.keySet()){
            Vertice origem = grafo.getGrafo().get(k);
            Vertice destino = grafo.getGrafo().get(retorno.get(k));
            mstUniaoMatching.add(origem.ArestaCom(destino));
        }  


        for(Aresta a: mstUniaoMatching){
            System.out.println("T U M: " + a.VerticeDeOrigem().getIndex() + " - " + a.VerticeAlvo().getIndex());
        }


        //Euler path
        EulerianPath.findpath(mstUniaoMatching, grafo);

        //Shortcut
    }

    private static ArrayList<Aresta> SubGrafoDeImpares(ArrayList<Integer> impares, Grafo grafo){
        ArrayList<Aresta> arestas = new ArrayList();
        HashSet<Integer> visitados = new HashSet(); 

        for(Integer i: impares) visitados.add(i);

        for(Integer vert: impares){
            Vertice verticeI = grafo.getGrafo().get(vert);
            for(Aresta a: verticeI.getVizinhos()){
                
                //adiciona arestas já não adicionadas
                if(visitados.contains(a.VerticeAlvo().getIndex())){
                    arestas.add(a);
                }

                visitados.remove(vert);
            }

        }

        return arestas;
    }
}

