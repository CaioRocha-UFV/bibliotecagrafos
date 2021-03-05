import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Grafo{

    int numeroDeVertices;
    ArrayList<Vertice> grafo;


    private void CriarGrafo(String fileName){


    }

    // Le um arquivo .txt e retorna uma matriz (?) ou uma lista de adjacencia
    private ArrayList<Vertice> LeituraDeArquivo (String fileName)  throws FileNotFoundException, IOException{
        BufferedReader reader = null;

        try {
            ArrayList<Vertice> adjList  = new ArrayList<Vertice>();
            String currentLine, pathName;
            int numDeVertices = 0;

            // Setup da leitura do Arquivo
            pathName = System.getProperty("user.home") + "\\Desktop\\";
            reader = new BufferedReader(new FileReader(pathName + fileName));
            
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


                // Testa se os vértices ja estão na lista
                if (ExisteVerticeComIndex(index1) == false){
                    // Se nao estiver cria
                    vertice1 = new Vertice(index1);
                } else {
                    // Se estive, get
                    vertice1 = adjList.get(index1);
                }
                if (ExisteVerticeComIndex(index2) == false){
                    vertice2 = new Vertice(index2);
                } else{
                    vertice2 = adjList.get(index2);
                }

                // Testa se aresta já existe
                if (ExisteArestaEntre(vertice1, vertice2) == false){
                    CriaAresta(vertice1, vertice2, peso);
                }
            }
            
        } finally{
            try{
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        }
    }

    // TODO
    public boolean ExisteVerticeComIndex (int index){

        return true;
    }

    // TODO
    public boolean ExisteArestaEntre(Vertice vertice1, Vertice vertice2){

        return true;
    }

    // Cria uma aresta a partir de dois vértices e um peso
    Vertice[] CriaAresta(Vertice vertice1, Vertice vertice2, float peso){
        vertice1.vizinhos.add(new Aresta(peso, vertice2));
        vertice2.vizinhos.add(new Aresta(peso, vertice1));

        Vertice[] vertices = {vertice1, vertice2};
        return vertices; // CHECAR SE PRECISA RETORNAR (TALVEZ JA TENHA MUDADO O VALOR REAL)
    }

    // 
    class Vertice {
        int index;  
        boolean marcado;
        ArrayList<Aresta> vizinhos;

        public Vertice (int index){
            this.index = index;
            marcado = false;
            ArrayList<Aresta> vizinhos = new ArrayList<Aresta>();
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
