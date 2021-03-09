package Algoritmos;

import java.util.*;
import Grafo.*;

public class Dijkstra {
    
    // Vetor onde cada posição representa a distância do vértice atual ao inicial
    // Os indexes deste vetor se associam aos indices do vértices do Grafo
    public float distancias[];
    Vertice caminho[];
    Set<Vertice> visitados;
    PriorityQueue<Aresta> pqueue;

    // O grafo onde este algoritmo esta sendo aplicado
    private Grafo grafo;
    private ArrayList<Vertice> verticesDoGrafo;
    private int totalDeVertices;

    // Construtor
    public Dijkstra (Grafo grafo){
        this.grafo = grafo;

        verticesDoGrafo = grafo.Vertices();
        totalDeVertices = verticesDoGrafo.size();

        distancias = new float[totalDeVertices+1];
        visitados = new HashSet<Vertice>();
        pqueue = new PriorityQueue<Aresta>(totalDeVertices, new Aresta());
    }



    // Gera e armazena o vetor de distancias a partir de um vértice dado
    public void DistanciasAPartirDoVertice(Vertice verticeInicial){
        //float dist[] = new float[totalDeVertices];

        // Inicia o vetor de distancias preenchendo todas com INFINITO
        for (int i = 0; i < totalDeVertices+1; i++){
            distancias[i] = Integer.MAX_VALUE;
        }

        // Adiciona a aresta inicial à lista de prioridade com peso 0
        pqueue.add(new Aresta(0, verticeInicial));

        // Distancia ao vertice inicial = 0
        distancias[verticeInicial.Index()] = 0;

        // Visita os vértices
        while (visitados.size() != totalDeVertices){

            // Remove o vértice de menor distancia da lista de prioridades
            Vertice menorV = pqueue.remove().VerticeAlvo();

            // Adiciona o vértice cuja distancia já foi visitada
            visitados.add(menorV);
            
            ExploraVizinhos(menorV);
        }
    }

    // Processa todos os vizinhos de um dado vértice e armazena o próximo vértice na lista de prioridades
    private void ExploraVizinhos(Vertice vertice){
        float distanciaAresta = -1;
        float novaDistancia = -1;

        // Processa os vizinhos de v
        for (Aresta aresta : vertice.Vizinhos()){
            Vertice vizinho = aresta.VerticeAlvo();

            // Testa se ainda não foi visitado
            if (visitados.contains(vizinho) == false){
                distanciaAresta = aresta.Peso();
                novaDistancia = distancias[vertice.Index()] + distanciaAresta;

                // Compara as distancias
                if (novaDistancia < distancias[vizinho.Index()] ) {
                    distancias[vizinho.Index()] = novaDistancia;
                }

                // Adiciona a aresta atual na lista de prioridade
                pqueue.add(new Aresta(aresta.Peso(), vizinho));
            }
        }

    }
}
