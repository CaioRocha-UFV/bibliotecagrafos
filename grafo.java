

//import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Grafo{

    int numeroDeVertices;
    ArrayList<Vertice> grafo;

    void CriarGrafo(String fileName) throws FileNotFoundException, IOException{

        grafo = new ArrayList<Vertice>();
        grafo = LeituraDeArquivo(fileName);
        
    }
    
    // Função protótipo para exibir os vértices no grafo e seus vizinhos
    void ExibirGrafo(){
        
        for (Vertice vertice : grafo){
            System.out.print("Vertice: " + vertice.index);
            System.out.print(" Vizinhos: ");
            
            for (Aresta aresta : vertice.vizinhos){
                System.out.print(aresta.verticeAlvo.index + " ");
            }
            System.out.println("");
        }
    }
    // Le um arquivo .txt e retorna uma matriz (?) ou uma lista de adjacencia
    private ArrayList<Vertice> LeituraDeArquivo (String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            ArrayList<Vertice> adjList  = new ArrayList<Vertice>();
            String currentLine, pathName;
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

    // Verifica se existe vértice com determinado índice
    // Retorna vetor informando a existência e a posição onde está o vértice com aquele índice
    // TODO
    public int[] ExisteVerticeComIndex ( ArrayList<Vertice> adjList, int index){
        // Caso não exista o vértice retorna 0 e posição -1
        // Caso exista retorna 1 e a posição do objeto na lista
        int[] ExistenciaEPosicao = {0, -1};
        for (Vertice vert1 : adjList){
            if (vert1.index == index){
                ExistenciaEPosicao[0] = 1;
                ExistenciaEPosicao[1] = adjList.indexOf(vert1);
                return ExistenciaEPosicao;
            }
        }
        return ExistenciaEPosicao;
    }

    // TODO
    public boolean ExisteArestaEntre(Vertice vertice1, Vertice vertice2){
        
        
        for (Aresta aresta : vertice1.vizinhos){
            
            if (aresta.verticeAlvo.equals(vertice2)){
                return true;
            }
        }
        return false;
    }

    // Cria uma aresta a partir de dois vértices e um peso
    void CriaAresta(Vertice vertice1, Vertice vertice2, float peso){
        
        vertice1.vizinhos.add(new Aresta(peso, vertice2));
        vertice2.vizinhos.add(new Aresta(peso, vertice1));    
    }

    // 
    class Vertice {
        int index;  
        boolean marcado;
        ArrayList<Aresta> vizinhos;

        public Vertice (int index){
            this.index = index;
            marcado = false;
            vizinhos = new ArrayList<Aresta>();
        }

        public int NumeroDeVizinhos(){
            if (vizinhos != null)
                return vizinhos.size();
            return -1;
        }

        public int Grau(){
            return NumeroDeVizinhos();
        }


        public void Marcar(){
            if (!marcado){
                marcado = true;
            }
        }

        public boolean Marcado(){
            return marcado;
        }
    }

    //
    class Aresta {
        float peso;
        Vertice verticeAlvo;

        public Aresta(float peso, Grafo.Vertice verticeAlvo) {
            this.peso = peso;
            this.verticeAlvo = verticeAlvo;
        }
    }
}