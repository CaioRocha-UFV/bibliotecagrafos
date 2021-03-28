package com.bibliotecagrafos.aresta;

import java.util.*;
import com.bibliotecagrafos.vertice.Vertice;

public class Aresta implements Comparator<Aresta> {
    // O peso default é 1
    float peso = 1;
    Vertice verticeOrigem;
    Vertice verticeAlvo;
    Aresta equivalente;

    // Construtor a partir de um peso e um vértice alvo
    public Aresta(float peso, Vertice verticeOrigem , Vertice verticeAlvo) {
        this.peso = peso;
        this.verticeAlvo = verticeAlvo;
        this.verticeOrigem = verticeOrigem;
    }

    // Construtor vazio -> Usado no Dijkstra
    public Aresta(){

    }

    public void setEquivalente(Aresta equiv){
        this.equivalente = equiv;
    }

    public Aresta getEquivalente(){
        return this.equivalente;
    }

    // Acessa peso
    public float Peso(){
        return peso;
    }

    // Retorna o Vértice Alvo
    public Vertice VerticeAlvo(){
        return verticeAlvo;
    }

    public Vertice VerticeDeOrigem() {
        return verticeOrigem;
    }

    @Override
    public int compare(Aresta aresta1, Aresta aresta2){
        if (aresta1.peso < aresta2.peso)
            return -1;
        if (aresta1.peso > aresta2.peso)
            return 1;
        return 0;
    }

    public boolean EhEquivalenteA(Aresta aresta2){
        return (aresta2 == equivalente);
    }
}
