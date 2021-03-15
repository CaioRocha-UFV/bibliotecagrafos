package Main;

import java.io.IOException;
import java.util.Scanner;
import Grafo.*;

public class Main {
   
    public static void main(String[] args) throws IOException {
    
        String arquivo;
        Grafo shy = new Grafo();
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Nome do arquivo: ");
        //arquivo = entrada.next();
        arquivo = "oks.txt";

        // Passa o nome do arquivo e cria o grafo daqueles dados
        shy.CriarGrafo(arquivo);
        
        shy.GerarArquivoBuscaEArestasRetorno();

    }
}
