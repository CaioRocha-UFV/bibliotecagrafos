package Grafo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import Algoritmos.Dijkstra;

public class Grafo{
    private int numeroDeVertices;
    private ArrayList<Vertice> grafo;


    // Função EXTERNA
    // Recebe: nome do arquivo
    // Ação: Gera um grafo com os dados do arquivo
    // Retorna: void
    public void CriarGrafo(String fileName) throws FileNotFoundException, IOException{

        grafo = new ArrayList<Vertice>();
        grafo = LeituraDeArquivo(fileName);
        
    }
    
    // Função protótipo para exibir os vértices no grafo e seus vizinhos
    public void ExibirGrafo(){

        for (Vertice vertice : grafo){
            System.out.print("Vertice: " + vertice.index);
            System.out.print(" Vizinhos: ");
            
            for (Aresta aresta : vertice.vizinhos){
                System.out.print(aresta.verticeAlvo.index + " ");
            }
            System.out.println("");
        }
    }

    // Função INTERNA
    // Recebe: nome do arquivo
    // Ação: Lê o arquivo e criar os devidos vértices e arestas
    // Retorna: Uma array de Vertices (O grafo)
    private ArrayList<Vertice> LeituraDeArquivo (String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            ArrayList<Vertice> adjList  = new ArrayList<Vertice>();
            String currentLine;
            //String pathName;
            int numDeVertices = 0;

            // Setup da leitura do Arquivo
            //pathName = System.getProperty("user.home") + "\\Desktop\\";
            reader = new BufferedReader(new FileReader( fileName));
            
            // Numero de vértices a partir da primeira linha
            numDeVertices = Integer.parseInt(reader.readLine());

            // Armazena internamente no Grafo o número total de vértices
            numeroDeVertices = numDeVertices;

            // Inicio da Leitura
            while ((currentLine = reader.readLine()) != null){
                // Leitura da Linha
                String tokens[] = currentLine.split(" ");

                // Fragmentação dos valores
                int index1 = Integer.parseInt(tokens[0]);
                int index2 = Integer.parseInt(tokens[1]);
                float peso = Float.parseFloat(tokens[2]);
                Vertice vertice1;
                Vertice vertice2;
                int[] retornoFuncao;

                // Pega o retorno da verificação se existe um vértice com aquele índice
                retornoFuncao = ExisteVerticeComIndex(adjList, index1);
                // Testa se os vértices ja estão na lista
                // e adiciona eles caso não estejam
                if (retornoFuncao[0] == 0){
                    vertice1 = new Vertice(index1);
                    adjList.add(vertice1);    

                } else{
                    vertice1 = adjList.get(retornoFuncao[1]);
                }
                
                retornoFuncao = ExisteVerticeComIndex(adjList, index2);
                if (retornoFuncao[0] == 0){
                    vertice2 = new Vertice(index2);
                    adjList.add(vertice2); 
                } else{
                    vertice2 = adjList.get(retornoFuncao[1]);
                }
           
                // Testa se aresta já existe
                if (ExisteArestaEntre(vertice1, vertice2) == false){
                    CriaAresta(vertice1, vertice2, peso);
                }
            }
            return adjList;
            
        } finally{
            try{
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
    }

    // Função EXTERNA
    // Recebe:
    // Ação: 
    // Retorna: Uma ArrayList de Vertices
    public ArrayList<Vertice> Vertices(){
        return grafo;
    }

    // Função EXTERNA
    // Recebe:
    // Ação: Conta o total de arestas e divide por dois
    // Retorna: O tamanho do grafo
    public int Tamanho() {
        int numArestas = 0;

        for(Vertice vertice: grafo){
            numArestas += vertice.Grau();
        }

        return numArestas/2;
    }

    // Função INTERNA
    // Recebe: Um grafo e um Índice
    // Ação: Busca um vértice pelo índice no grafo dado
    // Retorna: Vértice encontrado
    private static Vertice VerticeNoGrafoDeIndex(ArrayList<Vertice> grafo, int index){
        for (Vertice vertice : grafo){
            if (vertice.index == index){
                return vertice;
            }
        }
        return null;
    }

    // Função INTERNA
    // Recebe: Um Índice
    // Ação: Busca um vértice pelo índice no grafo interno
    // Retorna: Vértice encontrado
    private Vertice VerticeDeIndex(int index){
        for (Vertice vertice : grafo){
            if (vertice.index == index){
                return vertice;
            }
        }
        return null;
    }
    
    // Função INTERNA
    // Recebe: Um grafo e um index
    // Ação: Verifica se existe um vértice com o índice dado
    // Retorna: Um vetor[2] informando a existência em [0] e a posição em [1] do vértice com o índice dado
    // Caso não exista o vértice retorna 0 e posição -1
    // Caso exista retorna 1 e a posição do objeto na lista
    private int[] ExisteVerticeComIndex ( ArrayList<Vertice> adjList, int index){
        int[] existenciaEPosicao = {0, -1};
        Vertice vertice = VerticeNoGrafoDeIndex(adjList, index);
        if (vertice != null){
            existenciaEPosicao[0] = 1;
            existenciaEPosicao[1] = adjList.indexOf(vertice);
            return existenciaEPosicao;
        }
        return existenciaEPosicao;
    }

    // Função INTERNA
    // Recebe: Dois vértices
    // Ação: Verifica se existe uma aresta entre os dois vértices
    // Retorna: Um bool indicando a existencia de aresta
    private boolean ExisteArestaEntre(Vertice vertice1, Vertice vertice2){
        // Para cada aresta do Vertice1
        for (Aresta aresta : vertice1.vizinhos){
            if (aresta.verticeAlvo.equals(vertice2)){
                // Se o vertice alvo é o Vertice2, retorna
                return true;
            }
        }
        return false;
    }

    // Função INTERNA
    // Recebe: Dois vértices e um peso
    // Ação: Cria uma aresta entre os vértices
    // Retorna: void
    private void CriaAresta(Vertice vertice1, Vertice vertice2, float peso){
        // Se não houver uma aresta entre os vértices, cria-a
        if (ExisteArestaEntre(vertice1, vertice2) == false){
            vertice1.vizinhos.add(new Aresta(peso, vertice2));
            vertice2.vizinhos.add(new Aresta(peso, vertice1));
        }
    }

    // Função INTERNA
    // Recebe:
    // Ação: Acessa o número de vértices
    // Retorna: Número de vértices
    private int NumeroDeVertices(){
        return numeroDeVertices;
    }

    // Função EXTERNA
    // Recebe: 
    // Ação: Acessa NumeroDeVertices()
    // Retorna: Número de vértices
    public int Ordem(){
        return NumeroDeVertices();
    }

    // Função EXTERNA
    // Recebe: Index do vértice
    // Ação: Busca pelo vértice através de seu index e acessa o grau
    // Retorna: Grau do Vértice
    public int GrauDoVerticeDeIndex(int index){
        int grau = VerticeDeIndex(index).Grau();
        return grau;
    }

    // Função EXTERNA
    // Recebe: Index do vértice
    // Ação: Busca pelos vizinhso do vértice através de seu index
    // Retorna: Uma string {1, 2, 3} de vizinhos
    public String StringVizinhosDoVerticeDeIndice(int index){
        Vertice vertice = VerticeDeIndex(index);
        String vizinhos = vertice.StringDeVizinhos();
        return vizinhos;
    }

    // Pseudocode from https://www.softwaretestinghelp.com/dijkstras-algorithm-in-java/
    public float MenorCaminhoDijkstra(int inicial, int fim){
        Vertice vInicio = VerticeDeIndex(inicial);
        //Vertice vFinal = VerticeDeIndex(fim);
        Dijkstra dijkstra = new Dijkstra(this);
        dijkstra.DistanciasAPartirDoVertice(vInicio);
        return dijkstra.distancias[fim];
    }

    public void VerticesDoMenorCaminhoDijkstra(int inicial, int fim){

    }


    // Função INTERNA
    // Recebe:
    // Ação: Itinera por todos os vertices desmarcando-os
    // Retorna: void
    private void DesmarcarTodosOsVertices(){
        for (Vertice vertice : grafo){
            vertice.Desmarcar();
        }
    } 
}