package Grafo;

import java.util.*;

class Vertice {
    int index;  
    boolean marcado;
    ArrayList<Aresta> vizinhos;

    // Construtor a partir de um index
    // Inicia a Array de vizinhos e desmarcado
    public Vertice (int index){
        this.index = index;
        marcado = false;
        vizinhos = new ArrayList<Aresta>();
    }

    // Acessa o index
    int Index(){
        return index;
    }

    // Acessa a Array de vizinhos
    ArrayList<Aresta> Vizinhos(){
        return vizinhos;
    }

    // Retorna uma string dos vizinhos {1, 2, 3}
    String StringDeVizinhos(){
        if (NumeroDeVizinhos() > 0){
            String vizinhosString = "{";

            for (Aresta aresta : vizinhos){
                int index = aresta.VerticeAlvo().Index();
                vizinhosString = vizinhosString + Integer.toString(index) + ", ";
            }
            

            vizinhosString = vizinhosString.substring(0, vizinhosString.length() - 2) + "}";
            return vizinhosString;
        }
        return null;
    }

    // Função EXTERNA
    // Recebe:
    // Ação: Conta o número de vizinhos
    // Retorna: Número de vizinhos
    int NumeroDeVizinhos(){
        if (vizinhos != null)
            return vizinhos.size();
        return -1;
    }

    // Função INTERNA
    // Recebe:
    // Ação: Conta o número de vizinhos
    // Retorna: Grau do vértice
    int Grau(){
        return NumeroDeVizinhos();
    }

    // Função INTERNA
    // Recebe:
    // Ação: Marca um vértice
    // Retorna: void
    void Marcar(){
        if (!marcado){
            marcado = true;
        }
    }

    // Função INTERNA
    // Recebe:
    // Ação: Desarca um vértice
    // Retorna: void
    void Desmarcar(){
        if (marcado){
            marcado = false;
        }
    }

    // Função INTERNA
    // Recebe:
    // Ação: Acessa o atributo marcado
    // Retorna: Boolean se está marcado
    boolean Marcado(){
        return marcado;
    }

    // Função INTERNA
    // Recebe: Dois vértices e um peso
    // Ação: Cria uma aresta entre os vértices
    // Retorna: void
    void AdicionarVizinho(Vertice vertice2, float peso){
        // Se não houver uma aresta entre os vértices, cria-a
        if (this.EhvizinhoDe(vertice2) == false){
            int indexListaDeVizinhos = 0;

            for (Aresta vizinho : this.vizinhos){
                if (vertice2.Index() < vizinho.VerticeAlvo().Index()){
                    indexListaDeVizinhos = this.vizinhos.indexOf(vizinho);
                    this.vizinhos.add(indexListaDeVizinhos , new Aresta(peso, vertice2));
                    return;
                }
            }

            this.vizinhos.add(new Aresta(peso, vertice2));
        }
    }

    // Função INTERNA
    // Recebe: Dois vértices
    // Ação: Verifica se existe uma aresta entre os dois vértices
    // Retorna: Um bool indicando a existencia de aresta
    boolean EhvizinhoDe(Vertice vertice2){
        // Para cada aresta do Vertice1
        for (Aresta aresta : this.vizinhos){
            if (aresta.verticeAlvo.equals(vertice2)){
                // Se o vertice alvo é o Vertice2, retorna
                return true;
            }
        }
        return false;
    }
}
