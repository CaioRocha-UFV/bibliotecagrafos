package com.bibliotecagrafos.vertice;

import java.util.*;
import com.bibliotecagrafos.aresta.Aresta;

public class Vertice {
    private int index;
    boolean marcado;
    ArrayList<Aresta> vizinhos;
    double coordenadaX;
    double coordenadaY;

    // Construtor a partir de um index
    // Inicia a Array de vizinhos e desmarcado
    public Vertice (int index){
        this.index = index;
        marcado = false;
        vizinhos = new ArrayList<Aresta>();
    }

    public Vertice(int index, double coordX, double coordY){
        this.index = index;
        this.coordenadaX = coordX;
        this.coordenadaY = coordY;
        this.vizinhos = new ArrayList<Aresta>();
    }

    // Acessa o index
    public int getIndex(){
        return index;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    // Acessa a Array de vizinhos
    public ArrayList<Aresta> getVizinhos(){
        return vizinhos;
    }

    // Retorna uma string dos vizinhos {1, 2, 3}
    public String StringDeVizinhos(){
        if (NumeroDeVizinhos() > 0){
            String vizinhosString = "{";

            for (Aresta aresta : vizinhos){
                int index = aresta.VerticeAlvo().getIndex();
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
    public int NumeroDeVizinhos(){
        if (vizinhos != null)
            return vizinhos.size();
        return -1;
    }

    // Função INTERNA
    // Recebe:
    // Ação: Conta o número de vizinhos
    // Retorna: Grau do vértice
    public int Grau(){
        return NumeroDeVizinhos();
    }

    // Função INTERNA
    // Recebe: Dois vértices e um peso
    // Ação: Cria uma aresta entre os vértices
    // Retorna: void
    public Aresta AdicionarVizinho(Vertice vertice2, float peso){
        // Se não houver uma aresta entre os vértices
        if (this.EhvizinhoDe(vertice2) == false){
            Aresta novoVizinho = new Aresta(peso, this, vertice2);
            int indexListaDeVizinhos = 0;
            // Adiciona a aresta respeitando a inserção ordenada
            for (Aresta vizinho : this.vizinhos){
                if (vertice2.getIndex() < vizinho.VerticeAlvo().getIndex()){
                    indexListaDeVizinhos = this.vizinhos.indexOf(vizinho);
                    this.vizinhos.add(indexListaDeVizinhos , novoVizinho);
                    return novoVizinho;
                }
            }
            // Caso o index do vértice alvo seja maior que de todos os vizinhos
            this.vizinhos.add(novoVizinho);
            return novoVizinho;
        }

        return getArestaCom(vertice2);
    }

    // Recebe: Um értice
    // Ação: Verifica se existe uma aresta entre este e um vértice dado
    // Retorna: Um bool indicando a existencia de aresta
    public boolean EhvizinhoDe(Vertice vertice2){
        // Para cada aresta do Vertice1
        for (Aresta aresta : this.vizinhos){
            if (aresta.VerticeAlvo().equals(vertice2)){
                // Se o vertice alvo é o Vertice2, retorna
                return true;
            }
        }
        return false;
    }

    public Aresta getArestaCom(Vertice vertice2){
        // Para cada aresta do Vertice1
        for (Aresta aresta : this.vizinhos){
            if (aresta.VerticeAlvo().equals(vertice2)){
                // Se o vertice alvo é o Vertice2, retorna
                return aresta;
            }
        }
        return null;
    }


    // Recebe: Um vértices
    // Ação: Encontra uma aresta buscada
    // Retorna: A aresta buscada
    public Aresta ArestaCom(Vertice vertice2){
        // Aresta com si mesmo
        //if (vertice2.equals(this)){
        //    return (new Aresta(0,this, this));
        //}

        // Para cada aresta do Vertice1
        for (Aresta aresta : this.vizinhos){
            if (aresta.VerticeAlvo().equals(vertice2)){
                // Encontra a aresta buscada
                return aresta;
            }
        }
        return null;
    }

    // Recebe: Um vértices
    // Ação: Remove o vertice dado dos vizinhos
    // Retorna: O peso da aresta removida
    public float RemoveVizinho(Vertice v){
        for (Aresta aresta : vizinhos){
            if (aresta.VerticeAlvo() == v){
                vizinhos.remove(aresta);
                return aresta.Peso();
            }
        }
        return Float.NaN;
    }

}
