package Grafo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

                // Adiciona os vértices na lista
                vertice1 = AdicionaVertice(adjList, index1);
                vertice2 = AdicionaVertice(adjList, index2);
           
                // Adiciona as arestas
                if (vertice1.EhvizinhoDe(vertice2) == false){
                    vertice1.AdicionarVizinho(vertice2, peso);
                    vertice2.AdicionarVizinho(vertice1, peso);
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



    // Função INTERNA
    // Recebe: Index do vértice
    // Ação: Adiciona um vértice na lista
    // Retorna: (true) Caso tudo dê certo
    //          (false) Caso o vértice já esteja na lista
    Vertice AdicionaVertice(int newIndex){

        int indexListaDeVertices = 0;
        for (Vertice vertice : this.grafo){
            // Testa se o novo vertice ja está na lista
            if (newIndex == vertice.Index())
                return vertice;

            // Se o index novo é menor que o do vertice nesta posição
            // Cria umnovo vertice aqui
            if (newIndex < vertice.Index()){
                indexListaDeVertices = grafo.indexOf(vertice);
                Vertice novoVertice = new Vertice(newIndex);

                this.grafo.add(indexListaDeVertices, novoVertice);
                return novoVertice;
            }
        }
        // Se chegou aqui, o vertice é adicionado ao final da lista
        Vertice novoVertice = new Vertice(newIndex);
        this.grafo.add(novoVertice);
        return novoVertice;
    }

    // Função INTERNA
    // Recebe: Index do vértice
    // Ação: Adiciona um vértice na lista
    // Retorna: (true) Caso tudo dê certo
    //          (false) Caso o vértice já esteja na lista
    Vertice AdicionaVertice(ArrayList<Vertice> novoGrafo, int newIndex){

        int indexListaDeVertices = 0;
        for (Vertice vertice : novoGrafo){
            // Testa se o novo vertice ja está na lista
            if (newIndex == vertice.Index())
                return vertice;

            // Se o index novo é menor que o do vertice nesta posição
            // Cria umnovo vertice aqui
            if (newIndex < vertice.Index()){
                indexListaDeVertices = novoGrafo.indexOf(vertice);
                Vertice novoVertice = new Vertice(newIndex);

                novoGrafo.add(indexListaDeVertices, novoVertice);
                return novoVertice;
            }
        }
        // Se chegou aqui, o vertice é adicionado ao final da lista
        Vertice novoVertice = new Vertice(newIndex);
        novoGrafo.add(novoVertice);
        return novoVertice;
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
        System.out.println("VERTICE NÃO ENCONTRADO -- Index: "+index);
        return null;
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

    public void MenorCaminhoAStar(int inicial, int fim){
        Vertice vInicio = VerticeDeIndex(inicial);
        Vertice vFinal = VerticeDeIndex(fim);

        Astar aStar = new Astar();

        List<Vertice> rota = aStar.EncontrarCaminho(vInicio, vFinal);
        System.out.println(rota.stream().map(Vertice::Index).collect(Collectors.toList()));
    }


    public void BuscaEmProfundidade(int index){
        // Transforma o index dado no vertice buscado
        Vertice vertice = VerticeDeIndex(index);

        
        ArrayList<Vertice> verticesNoGrafo = grafo;
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = new ArrayList<>();


        // Aplica a busca em um componente conexo
        ParVertArest novaComponenteConexa = BuscaDFS(vertice);
        for (Vertice verticeExplorado : novaComponenteConexa.getVertices()){
            verticesNoGrafo.remove(verticeExplorado);
        }
        componentesConexas.add(novaComponenteConexa.getArestas());

        // Enquanto houver vértices não explorados, os explora
        while (verticesNoGrafo.size() > 0){

            novaComponenteConexa = BuscaDFS(verticesNoGrafo.remove(0));

            for (Vertice verticeExplorado : novaComponenteConexa.getVertices()){
                verticesNoGrafo.remove(verticeExplorado);
            }

            componentesConexas.add(novaComponenteConexa.getArestas());
        }

        /*
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = componentesConexas.get(0);
        // Print do caminho feito

        System.out.print("\n");
        for (Aresta arest : arestasVisitadas.keySet()){
                System.out.print("Caminho: De " + arest.VerticeDeOrigem().Index() + " -para-> "+ arest.VerticeAlvo().Index());
            if (arestasVisitadas.get(arest) == true)
                System.out.print(" (RETORNO)");
            System.out.print("\n");
        }
*/
        PrintBuscaEmProfundidade(componentesConexas);
        System.out.print("\\.:.\\");
    }

    private void PrintBuscaEmProfundidade(ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas){
        int indexCompConex = 0;

        // Print do caminho feito
        System.out.print("\n");
        for (LinkedHashMap<Aresta, Boolean> componenteConex : componentesConexas){
            indexCompConex++;
            System.out.print("\n COMPONENTE CONEXA "+ indexCompConex + "\n");

            for (Aresta arest : componenteConex.keySet()){
                    System.out.print(arest.VerticeDeOrigem().Index() + " --> "+ arest.VerticeAlvo().Index());
                if (componenteConex.get(arest) == true)
                    System.out.print(" (RETORNO)");
                System.out.print("\n");
            }
        }
    }

    private ParVertArest BuscaDFS(Vertice vertice){
        // Cria duas ArrayLists que acompanharão os vértices já visitados na ordem em que são 
        // visitados e as arestas de retorno, marcadas como TRUE para aresta de retorno e FALSE para arestas comuns
        // Essa escolha foi a mais apropriada em relação ao objetivo:
        //                          - Armazenar a ordem de entrada de cada vértice
        //                          - Armazenas o caminho feito na ordem em que foi feito
        //                          - Armazenar as arestas de retorno e o momento em que foram encontradas
        ArrayList<Vertice> verticesVisitados = new ArrayList<Vertice>();
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = new LinkedHashMap<Aresta, Boolean>();

        // Com o intuito de armazenar ambas estruturas e manter o aspecto recursivo,
        // o retorno da função recursiva é feito através do objeto ParVertArest,
        // que armazena nossas duas estruturas a dessa forma possibilita o retorno
        // de duas estruturas de três diferentes tipos de objetos
        ParVertArest retorno = new ParVertArest(verticesVisitados, arestasVisitadas);

        // Chamada da função recursiva que fará a busca
        System.out.print("Iniciando busca em profundidade:\n");
        retorno = BuscaRecursivaDFS(vertice, retorno);

        return retorno;
    }

    private ParVertArest BuscaRecursivaDFS(Vertice vertice, ParVertArest retorno){
        ArrayList<Vertice> verticesVisitados = retorno.getVertices();
        LinkedHashMap<Aresta, Boolean> arestasVisitadas = retorno.getArestas();

        // Marca o vertice atual (Alvo desta aresta) como visitado
        verticesVisitados.add(vertice);
        retorno.setVertices(verticesVisitados);

        // Recursividade em todos os vizinhos
        for (Aresta vizinho : vertice.Vizinhos()){

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

    class ParVertArest{
        ArrayList<Vertice> vertices;
        LinkedHashMap<Aresta, Boolean> arestas;
        public ParVertArest(ArrayList<Vertice> vertices, LinkedHashMap<Aresta, Boolean> arestas) {
            this.vertices = vertices;
            this.arestas = arestas;
        }
        public ArrayList<Vertice> getVertices() {
            return vertices;
        }
        public void setVertices(ArrayList<Vertice> vertices) {
            this.vertices = vertices;
        }
        public LinkedHashMap<Aresta, Boolean> getArestas() {
            return arestas;
        }
        public void setArestas(LinkedHashMap<Aresta, Boolean> arestas) {
            this.arestas = arestas;
        }
    }
}