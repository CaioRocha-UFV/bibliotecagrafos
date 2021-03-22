package Grafo;

import java.util.*;

public class BuscaEmProfundidade {

    // Função INTERNA
    // Recebe: Um vértice e o grafo
    // Ação: Explora um grafo componente por componentes através da Busca em Profundidade
    // Retorna: Uma Array de Componentes Conexas
    //             Casa componente conexa é uma lista encadeada de pares <Aresta, Boolean> na ordem de inserção
    //                Para os valores Booleans tem-se que:
    //                     - true  -> É uma aresta de retorno
    //                     - false -> É uma aresta de caminho
    public static ArrayList<LinkedHashMap<Aresta, Boolean>> Explorar(Vertice vertice, HashMap<Integer, Vertice> grafo){

        Stack<Vertice> verticesNaoExplorados = new Stack<Vertice>();
        verticesNaoExplorados.addAll(grafo.values());

        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = new ArrayList<>();

        //System.out.println("Início da BUSCA EM PROFUNDIDADE");
        // Aplica a busca em um primeiro componente conexo que contém o vertice dado
        ParVertArest novaComponenteConexa = BuscaDFS(vertice);
        for (Vertice verticeExplorado : novaComponenteConexa.getVertices()){
            verticesNaoExplorados.remove(verticeExplorado);
        }
        componentesConexas.add(novaComponenteConexa.getArestas());
        //.out.println("DFS: Primeiro Componente percorrido");

        // Explora o resto do Grafo caso este seja desconexo
        while (verticesNaoExplorados.size() > 0){

            novaComponenteConexa = BuscaDFS(verticesNaoExplorados.pop());
            
            for (Vertice verticeExplorado : novaComponenteConexa.getVertices()){
                verticesNaoExplorados.remove(verticeExplorado);
            }

            componentesConexas.add(novaComponenteConexa.getArestas());
            //System.out.println("DFS: Componente " + componentesConexas.indexOf(novaComponenteConexa.getArestas()) + " percorrido");
        }
        //System.out.println("FIM da BUSCA EM PROFUNDIDADE");
        // Retorna o conjunto de componentes conexas
        return componentesConexas;
    }

    // Função INTERNA
    // Recebe: Um vértice inicial
    // Ação: Explora uma componente conexa através da DFS (Busca em Profundidade)
    // Retorno: Uma instância de ParVertArest que armazena aexploração dos vértices e aresta
    //          desta componente conexa
    private static ParVertArest BuscaDFS(Vertice vertice){
        // Cria duas ArrayLists que acompanharão os vértices já visitados na ordem em que são 
        // visitados e as arestas de retorno, marcadas como TRUE para aresta de retorno e FALSE para arestas comuns
        // Essa escolha foi a mais apropriada em relação ao objetivo:
        //                          - Armazenar a ordem de entrada de cada vértice
        //                          - Armazenas o caminho feito na ordem em que foi feito
        //                          - Armazenar as arestas de retorno e o momento em que foram encontradas
        HashSet<Vertice> verticesVisitados = new HashSet<Vertice>();
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = new LinkedHashMap<Aresta, Boolean>();

        // Com o intuito de armazenar ambas estruturas e manter o aspecto recursivo,
        // o retorno da função recursiva é feito através do objeto ParVertArest,
        // que armazena nossas duas estruturas a dessa forma possibilita o retorno
        // de duas estruturas de três diferentes tipos de objetos
        ParVertArest retorno = new ParVertArest(verticesVisitados, arestasVisitadas);

        // Chamada da função recursiva que fará a busca
        //System.out.print("Iniciando busca em profundidade:\n");
        retorno = BuscaIterativaDFS(vertice, retorno);

        return retorno;
    }

    private static ParVertArest BuscaRecursivaDFS(Vertice vertice, ParVertArest retorno){
        HashSet<Vertice> verticesVisitados = retorno.getVertices();
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = retorno.getArestas();

        // Marca o vertice atual (Alvo desta aresta) como visitado
        verticesVisitados.add(vertice);
        retorno.setVertices(verticesVisitados);

        // Recursividade em todos os vizinhos
        for (Aresta vizinho : vertice.getVizinhos()){

            // Se o vizinho ainda não foi visitado
            if (verticesVisitados.contains(vizinho.VerticeAlvo()) == false){
                // Visita
                arestasVisitadas.put(vizinho, false);
                retorno.setArestas(arestasVisitadas);
                
                retorno = BuscaRecursivaDFS(vizinho.VerticeAlvo(), retorno);
            } else if (verticesVisitados.contains(vizinho.VerticeAlvo()) == true){ 
                // Caso ja tenha sido visitado
                // E se for uma aresta de retorno não armazenada
                // E não Explorada
                boolean flagExplored = false;
                for (Aresta aresta : arestasVisitadas.keySet()){
                    if (aresta.EhEquivalenteA(vizinho) || aresta.equals(vizinho)){
                        flagExplored = true;
                    }
                }
                if (flagExplored == false){
                    // Armazena e explora a aresta
                    arestasVisitadas.put(vizinho, true);
                    retorno.setArestas(arestasVisitadas);
                }
            }
        }

        return retorno;
    }


    private static ParVertArest BuscaIterativaDFS(Vertice vertice, ParVertArest retorno){
        
        HashSet<Vertice> verticesVisitados = retorno.getVertices();
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = retorno.getArestas();

        // variaveis internas
        Stack<Vertice> pilhaVertices = new Stack<Vertice>();
        Stack<Aresta> pilhaArestas = new Stack<Aresta>();
        Vertice vertTiradoDaPilha = new Vertice(0);
        Aresta vizinho = new Aresta();
        int i;
        
        // Coloca o vértice por onde se vai iniciar a busca na pilha
        pilhaVertices.push(vertice);
        

        while (pilhaVertices.empty() == false || pilhaArestas.empty() == false){
           
            // Caso a pilha de vertices nao esteja vazia, pega o ultimo valor inserido e retira ele da pilha
            if (pilhaVertices.empty() == false){
                vertTiradoDaPilha = pilhaVertices.pop();
            }
            
            // Verifica se o vertice tirado da pilha ja nao foi visitado.
            // Caso em que pode acontecer eh se ele ja foi marcado e entrou na verificacao de aresta de retorno
            if (verticesVisitados.contains(vertTiradoDaPilha) == false){
               
                // Marca o vertice atual (Alvo desta aresta) como visitado
                verticesVisitados.add(vertTiradoDaPilha);
                //retorno.setVertices(verticesVisitados);
            
                // Pega a lista de vizinhos do vertice e adiciona na pilha a partir do ultimo para que o primeiro
                // fique no topo.
                for (i = (vertTiradoDaPilha.vizinhos.size() - 1); i >= 0; i--){

                    Aresta aresta = vertTiradoDaPilha.vizinhos.get(i);

                    if (pilhaArestas.contains(aresta) == false){
                        pilhaArestas.push(aresta);
                    } 
                }
            }
          

            // Caso a pilha de arestas não esteja vazia, pega um vizinho do vertice e retira da pilha
            if (pilhaArestas.empty() == false){
                vizinho = pilhaArestas.pop();
            }
            

            if (verticesVisitados.contains(vizinho.VerticeAlvo()) == false){
                // Visita
                arestasVisitadas.put(vizinho, false);
                //retorno.setArestas(arestasVisitadas);
                
                // Coloca o vertice na pilha para que seus vizinhos possam ser verificados
                pilhaVertices.push(vizinho.VerticeAlvo());

            } 
            else {
                // Caso ja tenha sido visitado
                // E se for uma aresta de retorno não armazenada
                // E não Explorada
                /*
                boolean flagExplored = false;
                for (Aresta aresta : arestasVisitadas.keySet()){
                    if (aresta.EhEquivalenteA(vizinho) || aresta.equals(vizinho)){
                        flagExplored = true;
                    }
                }
                if (flagExplored == false){
                    // Armazena e explora a aresta
                    arestasVisitadas.put(vizinho, true);
                    //retorno.setArestas(arestasVisitadas);
                }*/
                if (arestasVisitadas.containsKey(vizinho) == false){
                    if (arestasVisitadas.containsKey(vizinho.getEquivalente()) == false){
                        arestasVisitadas.put(vizinho, true);
                    }
                }
            }
        }
        retorno.setVertices(verticesVisitados);
        retorno.setArestas(arestasVisitadas);
        return retorno;
    }
}


class ParVertArest{
    HashSet<Vertice> vertices;
    LinkedHashMap<Aresta, Boolean> arestas;
    public ParVertArest(HashSet<Vertice> vertices, LinkedHashMap<Aresta, Boolean> arestas) {
        this.vertices = vertices;
        this.arestas = arestas;
    }
    public HashSet<Vertice> getVertices() {
        return vertices;
    }
    public void setVertices(HashSet<Vertice> vertices) {
        this.vertices = vertices;
    }
    public LinkedHashMap<Aresta, Boolean> getArestas() {
        return arestas;
    }
    public void setArestas(LinkedHashMap<Aresta, Boolean> arestas) {
        this.arestas = arestas;
    }
}