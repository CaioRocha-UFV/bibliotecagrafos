package Grafo;

import java.util.*;

public class Aresta implements Comparator<Aresta> {
    // O peso default é 1
    float peso = 1;
    Vertice verticeAlvo;

    // Construtor a partir de um peso e um vértice alvo
    public Aresta(float peso, Vertice verticeAlvo) {
        this.peso = peso;
        this.verticeAlvo = verticeAlvo;
    }

    // Construtor vazio -> Usado no Dijkstra
    public Aresta(){

    }

    // Acessa peso
    public float Peso(){
        return peso;
    }

    // Retorna o Vértice Alvo
    public Vertice VerticeAlvo(){
        return verticeAlvo;
    }

    @Override
    public int compare(Aresta aresta1, Aresta aresta2){
        if (aresta1.peso < aresta2.peso)
            return -1;
        if (aresta1.peso > aresta2.peso)
            return 1;
        return 0;
    }
}
