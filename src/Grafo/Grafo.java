package Grafo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Grafo{
    private ArrayList<Vertice> grafo;


    public Grafo(){

    }

    // COPY CONSTRUCTOR
    public Grafo(ArrayList<Vertice> grafo){
        this.grafo = grafo;
    }

    // Função EXTERNA
    // Recebe: Nome do arquivo
    // Ação: Gera um grafo com os dados do arquivo e armazena internamente
    // Retorna: void
    public void CriarGrafo(String fileName) throws FileNotFoundException, IOException{

        grafo = new ArrayList<Vertice>();
        grafo = LeituraDeArquivo(fileName);
        
    }

    public ArrayList<Vertice> getGrafo(){
        return grafo;
    }

    // Função INTERNA
    // Recebe: Nome do arquivo
    // Ação: Lê o arquivo e cria os devidos vértices e arestas
    // Retorna: Uma array de Vertices (O grafo)
    private ArrayList<Vertice> LeituraDeArquivo (String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            ArrayList<Vertice> adjList;
            String currentLine;
            //String pathName;
            int numDeVertices = 0;

            // Setup da leitura do Arquivo
            //pathName = System.getProperty("user.home") + "\\Desktop\\";
            reader = new BufferedReader(new FileReader( fileName));
            
            // Numero de vértices a partir da primeira linha
            numDeVertices = Integer.parseInt(reader.readLine());

            adjList  = new ArrayList<Vertice>(numDeVertices);
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
                vertice1 = AdicionaVertice(adjList, index1);
                vertice2 = AdicionaVertice(adjList, index2);
           
                if (vertice1.EhvizinhoDe(vertice2) == false){
                    // Adiciona as arestas
                    Aresta arest1 = vertice1.AdicionarVizinho(vertice2, peso);
                    Aresta arest2 = vertice2.AdicionarVizinho(vertice1, peso);
                    arest1.setEquivalente(arest2);
                    arest2.setEquivalente(arest1);
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
    // Ação: Adiciona um vértice no grafo
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

    void AdicionaVertice (Vertice newVert){
        int indexListaDeVertices = 0;
        for (Vertice vertice : this.grafo){
            // Testa se o novo vertice ja está na lista
            if (vertice.equals(newVert))
                return;

            // Se o index novo é menor que o do vertice nesta posição
            // Cria um novo vertice aqui
            if (newVert.Index() < vertice.Index()){
                indexListaDeVertices = grafo.indexOf(vertice);

                this.grafo.add(indexListaDeVertices, newVert);
                return;
            }
        }
        // Se chegou aqui, o vertice é adicionado ao final da lista
        this.grafo.add(newVert);
    }

    // Função INTERNA
    // Recebe: Index do vértice
    // Ação: Adiciona um vértice em um grafo dado
    // Retorna: (true) Caso tudo dê certo
    //          (false) Caso o vértice já esteja na lista
    static Vertice AdicionaVertice(ArrayList<Vertice> novoGrafo, int newIndex){

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

    // Função INTERNA
    // Recebe: Index do vértice
    // Ação: Remove um vértice de um grafo dado
    // Retorna: Vertice removido
    public Vertice RemoveVertice(int index){
        Vertice v = VerticeDeIndex(index);

        for (Aresta vizinho : v.Vizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }

        grafo.remove(v);
        //numeroDeVertices -= 1;
        return v;
    }

    public static ArrayList<Vertice> RemoveVertice(ArrayList<Vertice> novoGrafo ,int index){
        Vertice v = VerticeDeIndex(novoGrafo, index);

        for (Aresta vizinho : v.Vizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }

        novoGrafo.remove(v);
        return novoGrafo;
    }


    // Função EXTERNA
    // Recebe:
    // Ação: Acessa a array de vertices
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

    // Função EXTERNA
    // Recebe:
    // Ação: Compara os vertices do grafo e encontra o de menor grau
    // Retorna: o grau mínimo do grafo
    public int GrauMinimo(){
        int grauMinimo = Integer.MAX_VALUE;

        for(Vertice vertice: grafo){
            if (vertice.Grau() < grauMinimo)
                grauMinimo = vertice.Grau();
        }

        return grauMinimo;
    }

    // Função EXTERNA
    // Recebe:
    // Ação: Compara os vertices do grafo e encontra o de maior grau
    // Retorna: o grau máximo do grafo
    public int GrauMaximo(){
        int grauMaximo = 0;

        for(Vertice vertice: grafo){
            if (vertice.Grau() > grauMaximo)
                grauMaximo = vertice.Grau();
        }

        return grauMaximo;
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
    private static Vertice VerticeDeIndex(ArrayList<Vertice> novoGrafo  ,int index){

        for (Vertice vertice : novoGrafo){
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
        return grafo.size();
    }

    // Função EXTERNA
    // Recebe: 
    // Ação: Acessa NumeroDeVertices()
    // Retorna: Ordem do Grafo
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

    // Função EXTERNA
    // Recebe: O index do vértice inicial e do final
    // Ação: Busca o menor caminho entre os vertices dados através do algoritmo Dijkstra
    // Retorna: O valor do caminho
    public String MenorCaminhoDijkstra(int inicial, int fim){
        Vertice vInicio = VerticeDeIndex(inicial);
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

    public String CaminhoDoMenorCaminhoDijkstra(int inicial, int fim){
        Vertice vInicio = VerticeDeIndex(inicial);
        Vertice vFinal = VerticeDeIndex(fim);

        Dijkstra dijkstra = new Dijkstra(this);

        dijkstra.DistanciasAPartirDoVertice(vInicio);

        String resultado = "";

        Stack<Vertice> caminho = dijkstra.CaminhoEntre(vInicio, vFinal);

        while (caminho.size() > 1){
            resultado += caminho.pop().Index() + " -> ";
        }
        resultado += caminho.pop().Index();

        return resultado;
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

        // Inicia a Busca em Profundidade

    }

    public int NumeroDeComponentesConexas(){
        Vertice inicio = grafo.get(0);
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade.Explorar(inicio , grafo);
        return componentesConexas.size();
    }

    public boolean EhArticulacao(int index, int conexasInicial){
        // Armazena o numero de componentes conexas inicial
        int numConexasInicial = conexasInicial;
        int numConexasFinal = 0;

        // Remove o vertice
        Vertice v = VerticeDeIndex(index);
        for (Aresta vizinho : v.Vizinhos()){
            vizinho.VerticeAlvo().RemoveVizinho(v);
        }
        grafo.remove(v);

        // Calcula o novo numero de componentes conexas
        numConexasFinal = NumeroDeComponentesConexas();
        
        // Readiciona o vertice
        AdicionaVertice(v);
        for (Aresta vizinho : v.Vizinhos()){
            vizinho.VerticeAlvo().AdicionarVizinho(v, vizinho.Peso());
        }


        if(numConexasFinal > numConexasInicial){
            return true;
        }
        return false;

    }

    public boolean EhPonte(int index1, int index2, int conexasInicial){
        if (VerticeDeIndex(index1).EhvizinhoDe(VerticeDeIndex(index2)) == false){
            return false;
        }

        // Armazena o numero de componentes conexas inicial
        int numConexasInicial = conexasInicial;
        int numConexasFinal = 0;

        // Armazena os devidos vertices e ao peso
        Vertice v1 = VerticeDeIndex(index1);
        Vertice v2 = VerticeDeIndex(index2);
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



    public void GerarArquivos(int indiceVerticePesquisar, int indiceVertArestaPesq1, int indiceVertArestaPesq2) throws IOException{

        // BUSCA EM PROFUNDIDADE
        ArrayList<LinkedHashMap<Aresta, Boolean>> componentesConexas = BuscaEmProfundidade.Explorar(VerticeDeIndex(1) , grafo);
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
                    VertsDasArestasRetorno.add(aresta.verticeOrigem);
                    VertsDasArestasRetorno.add(aresta.verticeAlvo);
                }
                else{
                    VertsDasArestasBusca.add(aresta.verticeOrigem);
                    VertsDasArestasBusca.add(aresta.verticeAlvo);
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
        bw1.write("Grau máximo do Grafo: " + GrauMaximo() + "\n\n");

        bw1.write("---------------------------------------------------\n");

        bw1.write("Informações gerais sobre o vértice " + indiceVerticePesquisar + ".\n");
        bw1.write("Verificação de ponte para os vértices: " + indiceVertArestaPesq1 + " " + indiceVertArestaPesq2 + ".\n");

        bw1.write("Vizinhos de "+ indiceVerticePesquisar + ": "+ StringVizinhosDoVerticeDeIndice(indiceVerticePesquisar) + "\n");
        bw1.write("Grau de "+ indiceVerticePesquisar + ": " + VerticeDeIndex(indiceVerticePesquisar).Grau() + "\n------------\n");


        if (EhArticulacao(indiceVerticePesquisar, numComponentesConexas)){
            bw1.write("Vértice "+ indiceVerticePesquisar + " É uma articulação!" + "\n");
        } else{
            bw1.write("Vértice "+ indiceVerticePesquisar + " NÃO É uma articulação!" + "\n");
        }


        if (EhPonte(indiceVertArestaPesq1, indiceVertArestaPesq2, numComponentesConexas)){
            bw1.write("A aresta " + indiceVertArestaPesq1 + "-" + indiceVertArestaPesq2 +  "É uma ponte!!\n");
        }
        else {
            bw1.write("A aresta " + indiceVertArestaPesq1 + "-" + indiceVertArestaPesq2 +  " NÃO É uma ponte!!\n");
        }
        bw1.write("\n");

        bw1.write("---------------------------------------------------\n");

        bw1.write("\n");

        bw1.write("O grafo possui " + numComponentesConexas + " componente(s) conexa(s). \n");

        for (Set<Vertice> vertsComponente : verticesDaComponente){

            bw1.write("Componente " + numComponente + ": \n");
            bw1.write("Vertices: ");
            for (Vertice verts : vertsComponente){
                bw1.write(verts.index + " ");
            }
            numComponente++;
            bw1.write("\n\n");
        }
        bw1.close();
        fw1.close();
    }






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
                    bw2.write(aresta.VerticeDeOrigem().Index() + " " + aresta.VerticeAlvo().Index() + " " + aresta.Peso() + "\n");
                }
                else {
                    bw1.write(aresta.VerticeDeOrigem().Index() + " " + aresta.VerticeAlvo().Index() + " " + aresta.Peso() + "\n");
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
}