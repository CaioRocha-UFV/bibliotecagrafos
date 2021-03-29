package outside.test;

import com.bibliotecagrafos.grafo.*;


import java.util.Scanner;
import java.io.IOException;


public class Interface {

    // Construtor
    public Interface(){
    }

    public void InterfaceDeAcesso() throws IOException{


        String arquivo;
        Grafo shy = new Grafo();
        shy.LeituraDeJSON();
        Scanner entrada = new Scanner(System.in);
        int indiceVerticeGeral;
        int indiceVerticePonte1, indiceVerticePonte2;

        System.out.println(" -----------------------------------------------");
        System.out.println("|                                              |");
        System.out.println("|         TRABALHO PRÁTICO 1 - CCF 331         |");
        System.out.println("|                                              |");
        System.out.println(" -----------------------------------------------");

        /*
        System.out.print("Digite o nome do arquivo: ");
        arquivo = entrada.nextLine();

        System.out.print("Digite o indice de um vertice para qual sera calculado dados gerais: ");
        indiceVerticeGeral = Integer.parseInt(entrada.nextLine());

        System.out.print("\n");

        System.out.println("Digite os indices de vertices para verificar se eh uma ponte.");
        System.out.println("Pode-se repetir o vertice anterior.");
        System.out.print("Primeiro vertice: ");
        indiceVerticePonte1 = Integer.parseInt(entrada.nextLine());

        System.out.print("Segundo vertice: ");
        indiceVerticePonte2 = Integer.parseInt(entrada.nextLine());

        shy.GerarArquivos(indiceVerticeGeral, indiceVerticePonte1, indiceVerticePonte2);
        */

        System.out.println("Comecando a criar o grafo.");
        shy.CriarGrafo("GrafoJSON.txt");
        System.out.println("Terminado de ler o grafo.");
        System.out.println("Ordem o grafo: " + shy.Ordem());
        System.out.println("Tamanho grafo: " + shy.Tamanho());
        shy.GerarArquivos(1, 3, 4);
        entrada.close();

        System.out.println("Execucao finalizada.");

    }






}

