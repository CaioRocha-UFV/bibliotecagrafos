
import java.io.IOException;
import java.util.Scanner;

public class Main {
   
    public static void main(String[] args) throws IOException {
    
        String arquivo;
        Grafo shy = new Grafo();
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Nome do arquivo: ");
        arquivo = entrada.next();
        
        // Passa o nome do arquivo e cria o grafo daqueles dados
        shy.CriarGrafo(arquivo);
        
        // Mostra o grafo, seus vértices e quem são seus vizinhos
        shy.ExibirGrafo();
        System.out.println("Tamanho: " + shy.Tamanho());
        System.out.println("Ordem: "+ shy.Ordem());
        System.out.println("Grau do Vértice 1: "+ shy.GrauDoVerticeDeIndex(1));
        System.out.println("Vizinhos do Vértice 1: "+ shy.VizinhosDoVerticeDeIndice(1));
        entrada.close();
    }
}
