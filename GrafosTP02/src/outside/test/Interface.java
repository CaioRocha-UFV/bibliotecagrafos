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
        String escolha;
        String escolhaTP;
        String escolhaNome;
        boolean linux = false;
        Grafo shy = new Grafo();
        Scanner entrada = new Scanner(System.in);
        int indiceVerticeGeral;
        int indiceVerticePonte1, indiceVerticePonte2;

        System.out.println(" -----------------------------------------------");
        System.out.println("|                                              |");
        System.out.println("|         BIBLIOTECA GRAFOS - CCF 331          |");
        System.out.println("|                                              |");
        System.out.println("|           Caio Rocha, Erian Alves,           |");
        System.out.println("|       Guilherme Sergio e Maria Theresa       |");
        System.out.println(" ----------------------------------------------");

        System.out.println("> Insira o valor que corresponde a sua escolha e pressione Enter\n Escolha seu OS:");
        System.out.println("  (1) Windows\n  (2) Linux\n>> ");
        escolha = entrada.nextLine();

        if (escolha.equals("2")){
            linux = true;
        }


        System.out.println("Escolha qual das funcionalidades deseja utilizar:");
        System.out.println("   (1) Funcionalidades gerais - TP1\n   (2) Métodos para o PCV - TP2\n>> ");
        escolhaTP = entrada.nextLine();

        if (escolhaTP.equals("1")) {

            System.out.println("Escolha a forma de entrada:");
            System.out.println("   |Para a entrada automática, será levada em consideração: \n" +
                    "   |          Nome do arquivo: Grafo.txt\n" +
                    "   |          Vértice de teste: 1\n" +
                    "   |          Aresta de teste: 3-4 ");
            System.out.println("  (1) Manual\n  (2) Automático\n>> ");
            escolha = entrada.nextLine();

            if (escolha.equals("2")) {
                System.out.println("Comecando a criar o grafo.");
                shy.CriarGrafo("Grafo.txt");
                System.out.println("Leitura do grafo encerrada.");
                System.out.println("Ordem o grafo: " + shy.Ordem());
                System.out.println("Tamanho grafo: " + shy.Tamanho());
                shy.GerarArquivos(1, 3, 4);
                entrada.close();

                System.out.println("Execucao finalizada.");
                return;
            }

            System.out.println("Gerar dados de um arquivo: ");
            System.out.println("  (1) .txt\n  (2) .json\n>> ");
            escolha = entrada.nextLine();
            if (escolha.equals("1"))
                System.out.println("\nDigite o nome do arquivo: (i.e. arquivo.txt)\n>> ");
            else if (escolha.equals("2"))
                System.out.println("\nDigite o nome do arquivo: (i.e. arquivo.json)\n>> ");
            arquivo = entrada.nextLine();

            System.out.println("Insira o indice de um vertice para ser usado nos testes (Articulação, Vizinhos, Busca Em Profundidade...): \n>>");
            indiceVerticeGeral = Integer.parseInt(entrada.nextLine());

            System.out.println("Insira os indices de dois vertices para verificar se sua aresta é uma ponte.");
            System.out.print("Primeiro vertice: \n>> ");
            indiceVerticePonte1 = Integer.parseInt(entrada.nextLine());

            System.out.print("Segundo vertice: \n>> ");
            indiceVerticePonte2 = Integer.parseInt(entrada.nextLine());

            if (escolha.equals("2")) {
                System.out.println("Comecando a criar o grafo.");
                shy.LeituraDeJSON(arquivo);
                shy.CriarGrafo("GrafoJSON.txt");
                System.out.println("Leitura do grafo encerrada.");
                System.out.println("Ordem o grafo: " + shy.Ordem());
                System.out.println("Tamanho grafo: " + shy.Tamanho());
                shy.GerarArquivos(indiceVerticeGeral, indiceVerticePonte1, indiceVerticePonte2);
                entrada.close();

                System.out.println("Execucao finalizada.");
                return;
            } else if (escolha.equals("1")) {
                System.out.println("Comecando a criar o grafo.");
                shy.CriarGrafo(arquivo);
                System.out.println("Leitura do grafo encerrada.");
                System.out.println("Ordem o grafo: " + shy.Ordem());
                System.out.println("Tamanho grafo: " + shy.Tamanho());
                shy.GerarArquivos(indiceVerticeGeral, indiceVerticePonte1, indiceVerticePonte2);
                entrada.close();

                System.out.println("Execucao finalizada.");
                return;
            }
        }
        else if (escolhaTP.equals("2")){

            System.out.println("Digite o nome do arquivo o qual contém a instância do TSPLIB: \n>> ");
            escolhaNome = entrada.nextLine();

            shy.CriarGrafo(escolhaNome);

            System.out.println("Escolha qual método será utilizado para resolver o a instância do PCV inserida");
            System.out.println("  (1) Primeiro método -> Vizinho mais próximo + 2-opt\n  (2) Segundo método  -> Chirstofides + 2-opt\n>> ");
            escolha = entrada.nextLine();

            if (escolha.equals("1")){
                shy.PrimeiroMetodo(escolhaNome.substring(0, escolhaNome.length()-4));
                return;
            }
            else if (escolha.equals("2")){
                shy.SegundoMetodo(escolhaNome.substring(0, escolhaNome.length()-4));
                return;
            }
        }

    }


}

