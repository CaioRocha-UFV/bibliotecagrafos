package Grafo;

import java.util.*;

public class Vertice {
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
    public int Index(){
        return index;
    }

    // Acessa a Array de vizinhos
    public ArrayList<Aresta> Vizinhos(){
        return vizinhos;
    }

    // Retorna uma string dos vizinhos {1, 2, 3}
    public String StringDeVizinhos(){
        if (NumeroDeVizinhos() > 0){
            String vizinhosString = "{";

            for (Aresta aresta : vizinhos){
                int index = aresta.VerticeAlvo().index;
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
    // Recebe:
    // Ação: Marca um vértice
    // Retorna: void
    public void Marcar(){
        if (!marcado){
            marcado = true;
        }
    }

    // Função INTERNA
    // Recebe:
    // Ação: Desarca um vértice
    // Retorna: void
    public void Desmarcar(){
        if (marcado){
            marcado = false;
        }
    }

    // Função INTERNA
    // Recebe:
    // Ação: Acessa o atributo marcado
    // Retorna: Boolean se está marcado
    public boolean Marcado(){
        return marcado;
    }
}
