package com.bibliotecagrafos.algoritmos.metodo1.nearestneighborheuristic;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.vertice.Vertice;

import java.util.*;

public class NearestNeighbor {
    public static ArrayList<Vertice> nearestNeighbor(HashMap<Integer, Vertice> grafo){

        Random rand = new Random();
        int i;
        double menorPeso = 0;
        int numeroDeVertices;
        double pesoVertice;


        // +1 pois para parâmetro n será gerado números -> 0 a n-1
        ArrayList<Vertice> rota = new ArrayList<Vertice>();
        ArrayList<Aresta> rotaArestas = new ArrayList<>();
        Set<Vertice> verticesVisitados = new HashSet<Vertice>();

        int indiceVerticeAtual = rand.nextInt(grafo.size()) + 1;
        Vertice verticeAtual = grafo.get(indiceVerticeAtual);
        Vertice primeiroVertice = verticeAtual;

        rota.add(verticeAtual);
        verticesVisitados.add(verticeAtual);
        numeroDeVertices = grafo.size();


        while (rota.size() != numeroDeVertices){

            menorPeso = Integer.MAX_VALUE;
            for (i = 0; i < verticeAtual.NumeroDeVizinhos(); i++){


                boolean verificacao = !verticesVisitados.contains(verticeAtual.getVizinhos().get(i).VerticeAlvo());

                if (verificacao){
                    pesoVertice = verticeAtual.getVizinhos().get(i).Peso();

                    if (menorPeso > pesoVertice){
                        menorPeso = pesoVertice;
                        indiceVerticeAtual = i;
                    }
                }

            }
            // Conectar com o início
            rotaArestas.add(verticeAtual.getVizinhos().get(indiceVerticeAtual));
            verticeAtual = verticeAtual.getVizinhos().get(indiceVerticeAtual).VerticeAlvo();
            rota.add(verticeAtual);
            verticesVisitados.add(verticeAtual);

        }
        rota.add(primeiroVertice);
        return rota;
    }
}
