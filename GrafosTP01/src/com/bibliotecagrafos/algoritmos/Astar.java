package com.bibliotecagrafos.algoritmos;


import java.util.*;

import com.bibliotecagrafos.vertice.Vertice;
import com.bibliotecagrafos.aresta.Aresta;

public class Astar {
    public Astar(){

    }

    double ComputarCusto(Vertice from, Vertice to){
        return from.ArestaCom(to).Peso();
    }

    public List<Vertice> EncontrarCaminho(Vertice from, Vertice to){
        return FindRoute(from, to);
    }

    class RouteNode implements Comparable<RouteNode> {
        Vertice vertice;
        Vertice anterior;
        double routeScore;
        double estimatedScore;

        RouteNode(Vertice atual){
            this(atual, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        RouteNode(Vertice vertice, Vertice anterior, double routeScore, double estimatedScore) {
            this.vertice = vertice;
            this.anterior = anterior;
            this.routeScore = routeScore;
            this.estimatedScore = estimatedScore;
        }

        Vertice Vertice() {
            return vertice;
        }

        double RouteScore(){
            return routeScore;
        }

        void NovoAnterior(Vertice novoAnterior){
            anterior = novoAnterior;
        }

        void NovoRouteScore(double novoValor){
            routeScore = novoValor;
        }

        void NovoEstimatedScore(double novoValor){
            estimatedScore = novoValor;
        }

        @Override
        public int compareTo(RouteNode other) {
            if (this.estimatedScore > other.estimatedScore) {
                return 1;
            } else if (this.estimatedScore < other.estimatedScore) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public List<Vertice> FindRoute(Vertice from, Vertice to){
        // Cria a lista aberta
        Queue<RouteNode> openSet = new PriorityQueue<>();

        // Cria um Map que armazena os pares Vertice:RouteNode
        Map<Vertice, RouteNode> allNodes = new HashMap<>();

        // Adiciona o vértice inicial
        RouteNode start = new RouteNode(from, null, 0d, ComputarCusto(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        // Explora os outros vértices
        while (openSet.isEmpty() == false){
            // Pega o próximo da lista de prioridade
            RouteNode proximo = openSet.poll();

            // Se é o vértice final
            if (proximo.Vertice().equals(to)){
                List<Vertice> rota = new ArrayList<>();
                RouteNode atual = proximo;
                do {
                    rota.add(0, atual.Vertice());
                } while (atual != null);
                return rota;
            }

            for (Aresta aresta : proximo.Vertice().getVizinhos() ){
                RouteNode proximoNode = allNodes.getOrDefault(aresta.VerticeAlvo(), new RouteNode(aresta.VerticeAlvo()));
                allNodes.put(aresta.VerticeAlvo(), proximoNode);

                double newScore = proximo.RouteScore() + ComputarCusto(proximo.Vertice(), aresta.VerticeAlvo());
                if (newScore < proximoNode.RouteScore()) {
                    proximoNode.NovoAnterior(proximo.Vertice());
                    proximoNode.NovoRouteScore(newScore);
                    proximoNode.NovoEstimatedScore(newScore + ComputarCusto(aresta.VerticeAlvo(), to));
                    openSet.add(proximoNode);
                }
            }
        }
        throw new IllegalStateException("No route found");
    }
}

