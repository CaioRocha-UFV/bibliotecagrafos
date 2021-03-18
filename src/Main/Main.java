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
        arquivo = "grafo5 07032021093040.txt";

        // Passa o nome do arquivo e cria o grafo daqueles dados
        System.out.println("Comecando a criar o grafo.");
        shy.CriarGrafo(arquivo);
        System.out.println("Terminado de ler o grafo.");
        System.out.println("Ordem o grafo: " + shy.Ordem());
        System.out.println("Tamanho grafo: " + shy.Tamanho());
        shy.GerarArquivoBuscaEArestasRetorno();
        entrada.close();
    }
}
