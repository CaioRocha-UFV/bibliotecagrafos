package outside.test;

import com.bibliotecagrafos.grafo.Grafo;
import outside.test.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        //Interface interface1 = new Interface();
        //interface1.InterfaceDeAcesso();

        String arquivo;
        Grafo shy = new Grafo();
        Scanner entrada = new Scanner(System.in);

        System.out.println("Comecando a criar o grafo.");
        shy.CriarGrafo("berlin52.tsp");
        System.out.println("Leitura do grafo encerrada.");
        System.out.println("Ordem o grafo: " + shy.Ordem());
        System.out.println("Tamanho grafo: " + shy.Tamanho());
        System.out.println(shy.GrauMaximo() + " " + shy.GrauMinimo());
        shy.PrimeiroMetodo();
    }
}
