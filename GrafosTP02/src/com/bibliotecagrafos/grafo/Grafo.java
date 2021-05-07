package com.bibliotecagrafos.grafo;

import java.io.*;
import java.util.*;

import com.bibliotecagrafos.algoritmos.metodo1.nearestneighborheuristic.NearestNeighbor;
import com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic.Christofides;
import com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic.EdmondsPerfectMatching;
import com.bibliotecagrafos.algoritmos.metodo2.christofidesheuristic.KruskalSpanningTree;
import com.bibliotecagrafos.algoritmos.optimization_2optheuristic.Optimization_2opt;
import com.bibliotecagrafos.vertice.Vertice;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

import com.bibliotecagrafos.aresta.Aresta;
import com.bibliotecagrafos.algoritmos.DepthFirstSearch.BuscaEmProfundidade;
import com.bibliotecagrafos.algoritmos.pathfinding.Dijkstra;


public class Grafo{

    private HashMap<Integer, Vertice> grafo;

    private ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                            FUNÇÕES GERADORAS DO GRAFO                            ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Construtor vazio
     */
    public Grafo(){
        componentesConexas = new ArrayList<>();
    }

    /**
     * Copy Constructor
     * @param grafo HashMap<Integer, Vertice> do Grafo a ser copiado
     */
    public Grafo(HashMap<Integer, Vertice> grafo){
        this.grafo = grafo;
    }

    /*

     */
    public HashMap<Integer, Vertice> GerarGrafoAuto(String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            String currentLine;
            String linhaQntVertices = "DIMENSION:";
            int numDeVertices = 0;
            HashMap<Integer, Vertice> novoGrafo;
            int i;
            // Setup da leitura do Arquivo
            reader = new BufferedReader(new FileReader( fileName));


            /*
                Passando pelas linhas que não são dados dos vértices
             */
            do {
                currentLine = reader.readLine();
                String tokens[] = currentLine.split(" ");

                if (tokens[0].equals(linhaQntVertices)){
                    numDeVertices = Integer.parseInt(tokens[1]);
                }

            } while (! currentLine.equals("NODE_COORD_SECTION"));

            novoGrafo  = new HashMap<Integer, Vertice>(numDeVertices);


            // Inicio da Leitura
            while ((currentLine = reader.readLine()) != null){
               
                //System.out.println(currentLine);
                // Leitura da Linha
                String tokens[] = currentLine.split(" ");

                if (tokens[0].equals("EOF")){
                    break;
                }

                // Fragmentação dos valores
                int index1 = Integer.parseInt(tokens[0]);
                double coordenadaX = Double.parseDouble(tokens[1]);
                double coordenadaY = Double.parseDouble(tokens[2]);
                float peso;
                Vertice vertice1;
                Vertice vertice2;

                // Adiciona os vértices na lista
                vertice1 = AdicionaVertice(novoGrafo, index1, coordenadaX, coordenadaY);

                for (int j : novoGrafo.keySet()){

                    if (j != index1){

                        vertice2 = AdicionaVertice(novoGrafo, j);
                        peso = (float)CalcularPeso(vertice1, vertice2);

                        if (vertice1.EhvizinhoDe(vertice2) == false){
                            // Adiciona as arestas
                            Aresta arest1 = vertice1.AdicionarVizinho(vertice2, peso);
                            Aresta arest2 = vertice2.AdicionarVizinho(vertice1, peso);
                            arest1.setEquivalente(arest2);
                            arest2.setEquivalente(arest1);
                        }

                    }

                }
            }
            return novoGrafo;

        } finally{
            try{
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Acessa a função de Leitura de Arquivo
     * @param fileName Nome do arquivo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void CriarGrafo(String fileName) throws FileNotFoundException, IOException{
        grafo = new HashMap<Integer, Vertice>();
        if (fileName.substring(fileName.length()-4).equals(".tsp")) {
            grafo = GerarGrafoAuto(fileName); // ARQUIVO TSP
        } else if (fileName.substring(fileName.length()-4).equals(".txt")) {
            grafo = LeituraDeArquivo(fileName); // ARQUIVO TXT
        }

    }

    /**
     * Gera um grafo com os dados do arquivo e armazena internamente
     * @param fileName Nome do arquivo
     * @throws FileNotFoundException
     * @throws IOException
     */
    private HashMap<Integer, Vertice> LeituraDeArquivo (String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            HashMap<Integer, Vertice> novoGrafo;
            String currentLine;
            //String pathName;
            int numDeVertices = 0;

            // Setup da leitura do Arquivo
            //pathName = System.getProperty("user.home") + "\\Desktop\\";
            reader = new BufferedReader(new FileReader( fileName));

            // Numero de vértices a partir da primeira linha
            numDeVertices = Integer.parseInt(reader.readLine());

            novoGrafo  = new HashMap<Integer, Vertice>(numDeVertices);
            // Armazena internamente no Grafo o número total de vértices
            //numeroDeVertices = numDeVertices;

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
                vertice1 = AdicionaVertice(novoGrafo, index1);
                vertice2 = AdicionaVertice(novoGrafo, index2);

                if (vertice1.EhvizinhoDe(vertice2) == false){
                    // Adiciona as arestas
                    Aresta arest1 = vertice1.AdicionarVizinho(vertice2, peso);
                    Aresta arest2 = vertice2.AdicionarVizinho(vertice1, peso);
                    arest1.setEquivalente(arest2);
                    arest2.setEquivalente(arest1);
                }

            }
            return novoGrafo;

        } finally{
            try{
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                           FUNÇÕES DE INSERÇÃO E REMOÇÃO                          ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Cria e adiciona um vértice no Grafo
     * @param newIndex Index do vértice
     * @return Vértice adicionado
     */
    Vertice AdicionaVertice(int newIndex){
        // Se o vértice não estiver no grafo
        if (this.grafo.containsKey(newIndex) == false){
            // Adiciona-o no grafo
            Vertice novoVertice = new Vertice(newIndex);
            this.grafo.put(newIndex, novoVertice);
            // Retorna o vertice adicionado
            return novoVertice;
        }

        // Caso o vértice já esteja no grafo, retorna o vértice
        return this.grafo.get(newIndex);
    }


    /**
     * Adiciona um vértice já criado no Grafo
     * @param newVert Vértice
     * @return False caso o vértice já esteja no grafo
     */
    boolean AdicionaVertice (Vertice newVert){
        // Se o vértice não estiver no grafo
        if (this.grafo.containsKey(newVert.getIndex()) == false){
            // Adiciona-o no grafo
            this.grafo.put(newVert.getIndex(), newVert);
            return true;
        }
        return false;
    }

    /**
     * Cria e Adiciona um vértice em um Grafo dado
     * @param novoGrafo
     * @param newIndex
     * @return O vértice adicionado (ou que ja estava)
     */
    static Vertice AdicionaVertice(HashMap<Integer, Vertice> novoGrafo, int newIndex){
        // Se o vértice não estiver no grafo
        if (novoGrafo.containsKey(newIndex) == false){
            // Adiciona-o no grafo
            Vertice novoVertice = new Vertice(newIndex);
            novoGrafo.put(newIndex, novoVertice);
            // Retorna o vertice adicionado
            return novoVertice;
        }

        // Caso o vértice já esteja no grafo, retorna o vértice
        return novoGrafo.get(newIndex);
    }

    /**
     * Cria e Adiciona um vértice em um Grafo dado
     * @param novoGrafo
     * @param newIndex
     * @param coordX
     * @param coordY
     * @return O vértice adicionado (ou que ja estava)
     */
    static Vertice AdicionaVertice(HashMap<Integer, Vertice> novoGrafo, int newIndex, double coordX, double coordY){
        // Se o vértice não estiver no grafo
        if (novoGrafo.containsKey(newIndex) == false){
            // Adiciona-o no grafo
            Vertice novoVertice = new Vertice(newIndex, coordX, coordY);
            novoGrafo.put(newIndex, novoVertice);
            // Retorna o vertice adicionado
            return novoVertice;
        }

        // Caso o vértice já esteja no grafo, retorna o vértice
        return novoGrafo.get(newIndex);
    }

    /**
     * Remove um vértice a partir de seu índice
     * @param index
     * @return
     */
    public Vertice RemoveVertice(int index){
        Vertice v = getVerticeByIndex(index);

        for (Aresta vizinho : v.getVizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }

        grafo.remove(v);

        return v;
    }

    public static ArrayList<Vertice> RemoveVertice(ArrayList<Vertice> novoGrafo ,int index){
        Vertice v = getVerticeByIndex(novoGrafo, index);

        for (Aresta vizinho : v.getVizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }

        novoGrafo.remove(v);
        return novoGrafo;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                            FUNÇÕES DE ACESSO AO GRAFO                            ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return HashMap de vertices (o próprio grafo)
     */
    public HashMap<Integer, Vertice> getGrafo(){
        return grafo;
    }

    /**
     *
     * @return HashMap de vertices (o próprio grafo)
     */
    public ArrayList<LinkedHashMap<Aresta, Boolean>> getComponentesConexas(){
        if (componentesConexas.size() <= 0){
            // Fazer busca em Profundidade
            return BuscaEmProfundidade(1);
        }

        return componentesConexas;
    }

    /**
     *
     * @return um HashSet com todas as Arestas do grafo
     */
    public HashSet<Aresta> getHashSetArestas(){
        if (componentesConexas.size() <= 0){
            // Fazer busca em Profundidade
            BuscaEmProfundidade(1);
        }

        HashSet<Aresta> arestasHS = new HashSet<>();

        for (LinkedHashMap<Aresta, Boolean> componente : componentesConexas) {
            for (Aresta a: componente.keySet()) {
                arestasHS.add(a);
            }
        }

        return arestasHS;
    }

    public ArrayList<Aresta> getArrayListArestas(){
        if (componentesConexas.size() <= 0){
            // Fazer busca em Profundidade
            BuscaEmProfundidade(1);
        }

        ArrayList<Aresta> arestasAL = new ArrayList<>();

        for (LinkedHashMap<Aresta, Boolean> componente : componentesConexas) {
            for (Aresta a: componente.keySet()) {
                arestasAL.add(a);
            }
        }

        return arestasAL;
    }

    /**
     *
     * @return total de arestas no grafo
     */
    public int Tamanho() {
        int numArestas = 0;

        for(Vertice vertice: grafo.values()){
            numArestas += vertice.Grau();
        }

        return numArestas/2;
    }

    /**
     *
     * @return o número de vértices no grafo
     */
    public int Ordem(){
        return getNumeroDeVertices();
    }

    /**
     * Retorna o numero de Vértices no Grafo
     * @return
     */
    private int getNumeroDeVertices(){
        return grafo.size();
    }

    /**
     *
     * @return o grau do vértice de menor grau do grafo
     */
    public int GrauMinimo(){
        int grauMinimo = Integer.MAX_VALUE;

        for(Vertice vertice: grafo.values()){
            if (vertice.Grau() < grauMinimo)
                grauMinimo = vertice.Grau();
        }

        return grauMinimo;
    }

    /**
     *
     * @return o grau do vértice de maior grau do grafo
     */
    public int GrauMaximo(){
        int grauMaximo = 0;

        for(Vertice vertice: grafo.values()){
            if (vertice.Grau() > grauMaximo)
                grauMaximo = vertice.Grau();
        }

        return grauMaximo;
    }

    /**
     * @param index
     * @return O Vertice com o index dado
     */
    private Vertice getVerticeByIndex(int index){
        // Busca o vértice no grafo
        Vertice vertice = grafo.get(index);
        if (vertice != null){
            return vertice;
        }

        // Caso nãoencontre o Grafo, retorna nulo
        System.out.println("VERTICE NÃO ENCONTRADO -- Index: "+index);
        return null;
    }

    /**
     * @param index
     * @return O Vertice com o index dado no grafo dado
     */
    private static Vertice getVerticeByIndex(ArrayList<Vertice> novoGrafo  , int index){
        // Busca o vértice no grafo
        Vertice vertice = novoGrafo.get(index);
        if (vertice != null){
            return vertice;
        }

        // Caso não encontre o Grafo, retorna nulo
        System.out.println("VERTICE NÃO ENCONTRADO -- Index: "+index);
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                            FUNÇÕES DE USO DE ALGORITMOS                          ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    AStar
    public void MenorCaminhoAStar(int inicial, int fim){
        Vertice vInicio = VerticeDeIndex(inicial);
        Vertice vFinal = VerticeDeIndex(fim);

        Astar aStar = new Astar();

        List<Vertice> rota = aStar.EncontrarCaminho(vInicio, vFinal);
        System.out.println(rota.stream().map(Vertice::getIndex).collect(Collectors.toList()));
    }
    */

    public void ChristofidesAlgo(){
        Christofides.Christofides(this);
    }

    public void PrintMST() {
        Aresta[] mst = KruskalSpanningTree.KuskalMSP(this);

        System.out.println("Following are the edges in "
                + "the constructed MST");
        int minimumCost = 0;
        for (int i = 0; i < mst.length-1; ++i)
        {
            System.out.println(mst[i].VerticeDeOrigem().getIndex() + " -- "
                    + mst[i].VerticeAlvo().getIndex()
                    + " == " + mst[i].Peso());
            minimumCost += mst[i].Peso();
        }
        System.out.println("Minimum Cost Spanning Tree "
                + minimumCost);
    }
    
    public void Emparelhamento(){
        /*Aresta[] mst = KruskalSpanningTree.KuskalMSP(this);
        Vertice[] vGImpar = new Vertice[mst.length+1];
        for (int i = 0; i < mst.length; i++) {

        }*/
        EdmondsPerfectMatching epm = new EdmondsPerfectMatching();
        //epm.ChristofidesEmparelhar(this);


    }

    /**
     * Aplica o algortimo de Dijkstra para encontrar o menor caminho
     * entre dois vértices dados e retorna o valor do caminho
     * @param inicial
     * @param fim
     * @return
     */
    public String MenorCaminhoDijkstra(int inicial, int fim){
        Vertice vInicio = getVerticeByIndex(inicial);
        //Vertice vFinal = VerticeDeIndex(fim);
        Dijkstra dijkstra = new Dijkstra(this);
        dijkstra.DistanciasAPartirDoVertice(vInicio);

        String resultado;
        if (dijkstra.distancias[fim] == Integer.MAX_VALUE)
            resultado = "[Caminho não encontrado]";
        else
            resultado = Float.toString(dijkstra.distancias[fim]);
        return resultado;
    }

    /**
     * Aplica Dijkstra e retorna os vértices do
     * menor caminho percorrido
     * @param inicial
     * @param fim
     * @return
     */
    public String CaminhoDoMenorCaminhoDijkstra(int inicial, int fim){
        Vertice vInicio = getVerticeByIndex(inicial);
        Vertice vFinal = getVerticeByIndex(fim);

        Dijkstra dijkstra = new Dijkstra(this);

        dijkstra.DistanciasAPartirDoVertice(vInicio);

        String resultado = "";

        Stack<Vertice> caminho = dijkstra.CaminhoEntre(vInicio, vFinal);

        while (caminho.size() > 1){
            resultado += caminho.pop().getIndex() + " -> ";
        }
        resultado += caminho.pop().getIndex();

        return resultado;
    }



    /**
     * Aplica a busca em profundidade a partir de um vértice dado
     * @param index
     * @return Uma ArrayList com o cada componente conexa
     */
    public ArrayList<LinkedHashMap<Aresta, Boolean>> BuscaEmProfundidade(int index){
        // Inicia a Busca em Profundidade no vértice dado
        System.out.println("Iniciando BuscaEmProfundidade");
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade.Explorar(getVerticeByIndex(index) , grafo);

        this.componentesConexas = componentesConexas;

        System.out.println("Fim da BuscaEmProfundidade");
        return componentesConexas;
    }

    /**
     * Aplica a busca em profundidade a partir de um vértice dado
     * @param index
     * @return Uma ArrayList com o cada componente conexa
     */
    private ArrayList<LinkedHashMap<Aresta, Boolean>> ArmazenarComponentesConexas(int index){
        // Inicia a Busca em Profundidade no vértice dado
        //System.out.println("Iniciando BuscaEmProfundidade");
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade.Explorar(getVerticeByIndex(index) , grafo);

        this.componentesConexas = componentesConexas;

        //System.out.println("Fim da BuscaEmProfundidade");
        return componentesConexas;
    }

    /**
     * Calcula o número de componentes conexas no grafo
     * @return
     */
    public int NumeroDeComponentesConexas(){
        Vertice inicio = grafo.entrySet().stream().findFirst().get().getValue();;
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade.Explorar(inicio , grafo);
        return componentesConexas.size();
    }




    public static double CalcularPeso(Vertice vert1, Vertice vert2){
        double distancia_X, distancia_Y, distanciaEntreVertices;

        distancia_X = vert1.getCoordenadaX() - vert2.getCoordenadaX();
        distancia_Y = vert1.getCoordenadaY() - vert2.getCoordenadaY();

        distanciaEntreVertices = Math.sqrt((distancia_X*distancia_X) + (distancia_Y*distancia_Y));

        return Math.ceil(distanciaEntreVertices);
    }

    /**
     * Testa se um vértice é uma articulação
     * @param index Vértice testado
     * @param conexasInicial Número de componentes conexas
     * @return True se for uma articulação
     */
    public boolean EhArticulacao(int index, int conexasInicial){
        // Armazena o numero de componentes conexas inicial
        int numConexasInicial = conexasInicial;
        int numConexasFinal = 0;

        // Remove o vertice
        Vertice v = getVerticeByIndex(index);
        for (Aresta vizinho : v.getVizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }
        grafo.remove(index);

        // Calcula o novo numero de componentes conexas
        numConexasFinal = NumeroDeComponentesConexas();

        // Readiciona o vertice
        AdicionaVertice(v);
        for (Aresta vizinho : v.getVizinhos()){
            vizinho.VerticeAlvo().AdicionarVizinho(v, vizinho.Peso());
        }


        if(numConexasFinal > numConexasInicial){
            return true;
        }
        return false;

    }

    /**
     * Testa se uma aresta dada é uma ponte
     * @param index1 Vértice 1 da aresta
     * @param index2 Vértice 2 da aresta
     * @param conexasInicial Número de componentes conexas
     * @return True caso seja uma ponte
     */
    public boolean EhPonte(int index1, int index2, int conexasInicial){
        if (getVerticeByIndex(index1).EhvizinhoDe(getVerticeByIndex(index2)) == false){
            return false;
        }

        // Armazena o numero de componentes conexas inicial
        int numConexasInicial = conexasInicial;
        int numConexasFinal = 0;

        // Armazena os devidos vertices e ao peso
        Vertice v1 = getVerticeByIndex(index1);
        Vertice v2 = getVerticeByIndex(index2);
        float peso;

        // Remove a aresta
        peso = v1.RemoveVizinho(v2);
        v2.RemoveVizinho(v1);

        // Calcula o novo numero de componentes conexas
        numConexasFinal = NumeroDeComponentesConexas();

        // Readiciona a aresta
        v1.AdicionarVizinho(v2, peso);
        v2.AdicionarVizinho(v1, peso);

        if(numConexasFinal > numConexasInicial){
            return true;
        }
        return false;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                             FUNÇÕES SEM CATEGORIZAÇÃO                            ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna uma String i.e.{1, 2, 4, 6} de vizinhos de um vértice dado
     * @param index index do vértice
     * @return
     */
    public String getStringDeVizinhos(int index){
        Vertice vertice = getVerticeByIndex(index);
        String vizinhos = vertice.StringDeVizinhos();
        return vizinhos;
    }


    /**
     * Busca pelo vértice através de seu index e acessa o grau
     * @param index
     * @return
     */
    public int GrauDoVerticeDeIndex(int index){
        int grau = getVerticeByIndex(index).Grau();
        return grau;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                            FUNÇÕES LIGADAS A ARQUIVOS                            ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Prepara a geração do arquivo final com os dados do grafo
     * @param indiceVerticePesquisar Usado para testar as funções
     * @param indiceVertArestaPesq1 Usado para compor a aresta de teste
     * @param indiceVertArestaPesq2 Usado para compor a aresta de teste
     * @throws IOException
     */
    public void GerarArquivos(int indiceVerticePesquisar, int indiceVertArestaPesq1, int indiceVertArestaPesq2) throws IOException{

        // BUSCA EM PROFUNDIDADE
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade(1);


        int numComponentesConexas = componentesConexas.size();
        int i = 0;
        Set<Vertice> VertsDasArestasRetorno = new HashSet<Vertice>();
        Set<Vertice> VertsDasArestasBusca = new HashSet<Vertice>();

        // Cria uma lista de sets que contem os vertices pertencentes a cada componente conexa
        ArrayList<Set<Vertice>> verticesDaComponente = new ArrayList<>(numComponentesConexas);

        for (LinkedHashMap<Aresta, Boolean> componente : componentesConexas){
            // Adiciona um set (conjunto de vertices da componente conexa) na lista
            verticesDaComponente.add(new HashSet<Vertice>());
            for (Aresta aresta : componente.keySet()){

                if (componente.get(aresta) == true){
                    VertsDasArestasRetorno.add(aresta.VerticeDeOrigem());
                    VertsDasArestasRetorno.add(aresta.VerticeAlvo());
                }
                else{
                    VertsDasArestasBusca.add(aresta.VerticeDeOrigem());
                    VertsDasArestasBusca.add(aresta.VerticeAlvo());
                }
                verticesDaComponente.get(i).add(aresta.VerticeDeOrigem());
                verticesDaComponente.get(i).add(aresta.VerticeAlvo());
            }
            i++;
        }


        // Chamar funcao que gera o arquivo com dados gerais do grafo e dados especificos de um vertice e aresta
        ArquivoInformacoes(indiceVerticePesquisar, indiceVertArestaPesq1, indiceVertArestaPesq2, numComponentesConexas, verticesDaComponente);

        // Chamar funcao que gera o arquivo que contem as informacoes da busca em profundidade
        GerarArquivoBuscaEArestasRetorno(componentesConexas, VertsDasArestasRetorno, VertsDasArestasBusca);

        System.out.println("Arquivos Gerados com sucesso.");

    }


    /**
     * Efetua a geração do arquivo de dados
     * @param indiceVerticePesquisar Usado para testar as funções
     * @param indiceVertArestaPesq1 Usado para compor a aresta de teste
     * @param indiceVertArestaPesq2 Usado para compor a aresta de teste
     * @param numComponentesConexas Número já calculado de comp conexas
     * @param verticesDaComponente
     * @throws IOException
     */
    public void ArquivoInformacoes(int indiceVerticePesquisar, int indiceVertArestaPesq1, int indiceVertArestaPesq2, int numComponentesConexas, ArrayList<Set<Vertice>> verticesDaComponente) throws IOException{

        String nomeArquivo = "DadosGrafo.txt";
        int numComponente = 1;

        // Arquivo que vai armazenar o grafo resultante da busca em profundidade
        FileWriter fw1 = new FileWriter(nomeArquivo, false);
        BufferedWriter bw1 = new BufferedWriter(fw1);


        bw1.write(" ----------------------------------------------\n");
        bw1.write("|         TRABALHO PRÁTICO 1 - CCF 331         |\n");
        bw1.write(" ----------------------------------------------\n");
        bw1.write(">> Dados sobre o Grafo analisado:\n\n");
        bw1.write("Ordem do Grafo: " + Ordem() + "\n");
        bw1.write("Tamanho do Grafo: "+ Tamanho() +"\n");
        bw1.write("Grau mínimo do Grafo: " + GrauMinimo() + "\n");
        bw1.write("Grau máximo do Grafo: " + GrauMaximo() + "\n");
        bw1.write("---------------------------------------------------\n");
        bw1.write("Informações gerais sobre o vértice " + indiceVerticePesquisar + ".\n");
        bw1.write("Verificação de ponte para os vértices: " + indiceVertArestaPesq1 + " " + indiceVertArestaPesq2 + ".\n");
        bw1.write("---------------------------------------------------\n");
        bw1.write("Vizinhos de "+ indiceVerticePesquisar + ": "+ getStringDeVizinhos(indiceVerticePesquisar) + "\n");
        bw1.write("Grau de "+ indiceVerticePesquisar + ": " + getVerticeByIndex(indiceVerticePesquisar).Grau() + "\n------------\n");
        if (EhArticulacao(indiceVerticePesquisar, numComponentesConexas)){
            bw1.write("Vértice "+ indiceVerticePesquisar + " É uma articulação!" + "\n");
        } else{
            bw1.write("Vértice "+ indiceVerticePesquisar + " NÃO É uma articulação!" + "\n");
        }
        bw1.write("---------------------------------------------------\n");
        bw1.write(
                "Menor caminho Dijsktra entre " +
                        indiceVerticePesquisar + " e " +
                        indiceVertArestaPesq2 + ": " +
                        MenorCaminhoDijkstra(indiceVerticePesquisar, indiceVertArestaPesq2) + "\n");
        bw1.write("Caminho percorrido:\n" + CaminhoDoMenorCaminhoDijkstra(indiceVerticePesquisar, indiceVertArestaPesq2) + "\n");
        bw1.write("---------------------------------------------------\n");
        if (getVerticeByIndex(indiceVertArestaPesq1).EhvizinhoDe(getVerticeByIndex(indiceVertArestaPesq2)) == false)
            bw1.write("A aresta " + indiceVertArestaPesq1 + "-" + indiceVertArestaPesq2 + " não existe\n");
        else {
            if (EhPonte(indiceVertArestaPesq1, indiceVertArestaPesq2, numComponentesConexas)) {
                bw1.write("A aresta " + indiceVertArestaPesq1 + "-" + indiceVertArestaPesq2 + " É uma ponte!!\n");
            } else {
                bw1.write("A aresta " + indiceVertArestaPesq1 + "-" + indiceVertArestaPesq2 + " NÃO É uma ponte!!\n");
            }
        }
        bw1.write("---------------------------------------------------\n");

        bw1.write("O grafo possui " + numComponentesConexas + " componente(s) conexa(s). \n");

        for (Set<Vertice> vertsComponente : verticesDaComponente){

            bw1.write("Componente " + numComponente + ": \n");
            bw1.write("Vertices: ");
            for (Vertice verts : vertsComponente){
                bw1.write(verts.getIndex() + " ");
            }
            numComponente++;
            bw1.write("\n\n");
        }
        bw1.close();
        fw1.close();
    }

    /**
     * Gera os arquivos das arestas de retorno
     * @param componentesConexas
     * @param VertsDasArestasRetorno
     * @param VertsDasArestasBusca
     * @throws IOException
     */
    public void GerarArquivoBuscaEArestasRetorno(ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas, Set<Vertice> VertsDasArestasRetorno, Set<Vertice> VertsDasArestasBusca) throws IOException {

        String nomeArquivo1 = "GrafoBuscaProfundidade.txt";
        String nomeArquivo2 = "GrafoArestasRetorno.txt";

        // Arquivo que vai armazenar o grafo resultante da busca em profundidade
        FileWriter fw1 = new FileWriter(nomeArquivo1, false);
        BufferedWriter bw1 = new BufferedWriter(fw1);

        // Arquivo que vai armazenar grafo formado pelas arestas de retorno
        FileWriter fw2 = new FileWriter(nomeArquivo2, false);
        BufferedWriter bw2 = new BufferedWriter(fw2);

        bw1.write(VertsDasArestasBusca.size() + "\n");
        bw2.write(VertsDasArestasRetorno.size() + "\n");

        // Laço para escrever as arestas nos arquivos
        for (LinkedHashMap<Aresta, Boolean> componente : componentesConexas){

            for (Aresta aresta : componente.keySet()){

                if (componente.get(aresta) == true){
                    // RETORNO
                    bw2.write(aresta.VerticeDeOrigem().getIndex() + " " + aresta.VerticeAlvo().getIndex() + " " + aresta.Peso() + "\n");
                }
                else {
                    bw1.write(aresta.VerticeDeOrigem().getIndex() + " " + aresta.VerticeAlvo().getIndex() + " " + aresta.Peso() + "\n");
                }
            }
        }

        System.out.println("ARQUIVO GERADO!");

        //
        bw1.close();
        fw1.close();
        bw2.close();
        fw2.close();

    }

    /**
     * Lê um arquivo .JSON advindo do site PAAD Grafos
     * e transforma na entrada padrão da biblioteca
     * @throws IOException
     */
    public void LeituraDeJSON(String nomeArquivo) throws IOException {
        String linha;
        Scanner entrada = new Scanner(System.in);
        HashMap<String, String> idsRotulos  = new HashMap<>();

        FileWriter fw1 = new FileWriter("GrafoJSON.txt", false);
        BufferedWriter bw1 = new BufferedWriter(fw1);

        FileReader fr = new FileReader(nomeArquivo);
        BufferedReader br = new BufferedReader(fr);

        linha = br.readLine();


        linha = linha.replaceAll("\"",":");

        ArrayList<String> lops = new ArrayList<>(Arrays.asList(linha.split(":|\\,")));


        ArrayList<String> vFinale = new ArrayList<String>();
        for (String s : lops){


            if (!(s.equals(lops.get(2)) ||  s.contains("{") || s.contains("}"))){

                vFinale.add(s);
            }

        }

        /*
        for (String s : vFinale){
            System.out.println(s);
        }
        */

        String id = "";

        int j = 0;
        for (int i = 0; i < vFinale.size(); i++){



            if (j == 0){


                if (vFinale.get(i).equals("id")){
                    id = vFinale.get(i+1);
                }
                if (vFinale.get(i).equals("label")){
                    idsRotulos.put(id, vFinale.get(i+1));
                }
            }

            if (vFinale.get(i).equals("length")){
                if (j == 1){
                    break;
                }
                bw1.write(vFinale.get(i+1) + "\n");
                j++;
            }

            if (j == 1){
                if (vFinale.get(i).equals("from")){
                    bw1.write(idsRotulos.get(vFinale.get(i+1)) + " ");
                }
                if(vFinale.get(i).equals("to")){
                    bw1.write(idsRotulos.get(vFinale.get(i+1)) + " " );
                }
                if(vFinale.get(i).equals("label")){

                    bw1.write(vFinale.get(i + 1) + "\n");

                }
            }
        }


        bw1.close();
        fw1.close();


        br.close();
        fr.close();
    }







    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////                                      TP 02                                       ///////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void GerarArquivoCicloHamiltoniano(ArrayList<Vertice> listaVertice, String nome) throws IOException {

        String nomeArquivo1 = "CicloHamiltoniano" + nome + ".txt";

        // Arquivo que vai armazenar o grafo resultante da busca em profundidade
        FileWriter fw1 = new FileWriter(nomeArquivo1, false);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.write(listaVertice.size()-1 + "\n");

        for (int i = 0; i < listaVertice.size()-1; i++){

            bw1.write(listaVertice.get(i).getIndex() + " " + listaVertice.get(i+1).getIndex() + " " + listaVertice.get(i).ArestaCom(listaVertice.get(i+1)).Peso());
            bw1.write("\n");

        }

        System.out.println("ARQUIVO GERADO!");

        bw1.close();
        fw1.close();
    }




    public void PrimeiroMetodo(String nomeArquivo) throws IOException{

        String nome = "Metodo1" + nomeArquivo;

        int iteracoes = 30;
        ArrayList<Vertice> listaVertice;
        ArrayList<Vertice> cicloMelhorCusto = new ArrayList<>();
        double custo;
        double melhorCusto = Double.MAX_VALUE;
        double piorCusto = Double.MIN_VALUE;
        double mediaCusto = 0;
        double desvioPadraoCusto = 0;
        double[] vetorCustos = new double[30];

        for (int i = 0; i < iteracoes; i ++){
            
            listaVertice = NearestNeighbor.nearestNeighbor(this.grafo);

            custo = Optimization_2opt.Optimization_2Opt(listaVertice);

            // Pegando o menor custo
            if (custo < melhorCusto){
                melhorCusto = custo;
                cicloMelhorCusto = listaVertice;
            }
            // Pegando o maior custo
            if (custo > piorCusto){
                piorCusto = custo;
            }

            mediaCusto += custo;
            vetorCustos[i] = custo;
        }

        mediaCusto /= iteracoes;

        for (int i = 0; i < iteracoes; i++){
            desvioPadraoCusto += Math.pow((vetorCustos[i]-mediaCusto), 2);
        }

        desvioPadraoCusto /= iteracoes;

        desvioPadraoCusto = Math.sqrt(desvioPadraoCusto);

        System.out.println("Melhor:     " + melhorCusto);
        System.out.println("Pior:       " + piorCusto);
        System.out.format("Media:  %.3f\n", mediaCusto);
        System.out.format("Desvio Padrao: %.3f\n", desvioPadraoCusto);

        GerarArquivoCicloHamiltoniano(cicloMelhorCusto, nome);
    }



    public void SegundoMetodo(String nomeArquivo) throws IOException{

        String nome = "Metodo2" + nomeArquivo;
        int iteracoes = 30;
        ArrayList<Vertice> listaVertice;
        ArrayList<Vertice> cicloMelhorCusto = new ArrayList<>();
        double custo;
        double melhorCusto = Double.MAX_VALUE;
        double piorCusto = Double.MIN_VALUE;
        double mediaCusto = 0;
        double desvioPadraoCusto = 0;
        double[] vetorCustos = new double[30];

        for (int i = 0; i < iteracoes; i ++){

            listaVertice = Christofides.Christofides(this);

            custo = Optimization_2opt.Optimization_2Opt(listaVertice);

            // Pegando o menor custo
            if (custo < melhorCusto){
                melhorCusto = custo;
                cicloMelhorCusto = listaVertice;
            }
            // Pegando o maior custo
            if (custo > piorCusto){
                piorCusto = custo;
            }

            mediaCusto += custo;
            vetorCustos[i] = custo;
        }

        mediaCusto /= iteracoes;

        for (int i = 0; i < iteracoes; i++){
            desvioPadraoCusto += Math.pow((vetorCustos[i]-mediaCusto), 2);
        }

        desvioPadraoCusto /= iteracoes;

        desvioPadraoCusto = Math.sqrt(desvioPadraoCusto);

        System.out.println("Melhor:     " + melhorCusto);
        System.out.println("Pior:       " + piorCusto);
        System.out.format("Media:  %.3f\n", mediaCusto);
        System.out.format("Desvio Padrao: %.3f\n", desvioPadraoCusto);

        GerarArquivoCicloHamiltoniano(cicloMelhorCusto, nome);
    }


    /*
    public void TestChristofides() {

        Christofides.Christofides(this);


        //EdmondsPerfectMatching epm = new EdmondsPerfectMatching();
        //epm.ChristofidesEmparelhar(getArrayListArestas().toArray(new Aresta[0]));

    }
    */
}