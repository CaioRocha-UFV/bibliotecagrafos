package com.bibliotecagrafos.algoritmos;

import java.util.*;

import com.bibliotecagrafos.vertice.Vertice;

import com.bibliotecagrafos.aresta.Aresta;

public class metodo1 {



    public double CalcularCustoCiclo(ArrayList<Aresta> rotaArestas){

        double custo = 0;

        for (Aresta ares : rotaArestas){

            custo += ares.Peso();
        }

        return custo;
    }



    public static ArrayList<Aresta> nearestNeighbor(HashMap<Integer, Vertice> grafo){

        Random rand = new Random();
        int i;
        double menorPeso = 0;
        int numeroDeVertices;
        double pesoVertice;
        
        
        // +1 pois para parâmetro n será gerado números -> 0 a n-1 
        ArrayList<Vertice> rota = new ArrayList<Vertice>();
        ArrayList<Aresta> rotaArestas = new ArrayList<>();
        Set<Vertice> verticesVisitados = new HashSet<Vertice>();
        
        int indiceVerticeAtual = rand.nextInt(grafo.size()) + 1;
        Vertice verticeAtual = grafo.get(indiceVerticeAtual);
        Vertice primeiroVertice = grafo.get(indiceVerticeAtual);
        
        rota.add(verticeAtual);
        verticesVisitados.add(verticeAtual);
        numeroDeVertices = grafo.size();
        
       
        

        
        while (rota.size() != numeroDeVertices){

            menorPeso = Integer.MAX_VALUE;
            for (i = 0; i < verticeAtual.NumeroDeVizinhos(); i++){
                
              
                boolean verificacao = !verticesVisitados.contains(verticeAtual.getVizinhos().get(i).VerticeAlvo());
               
                if (verificacao){
                    pesoVertice = verticeAtual.getVizinhos().get(i).Peso();
                
                    if (menorPeso > pesoVertice){
                        menorPeso = pesoVertice;
                        indiceVerticeAtual = i;
                    }
                }
                
            }
            // Conectar com o início
            rotaArestas.add(verticeAtual.getVizinhos().get(indiceVerticeAtual));
            verticeAtual = verticeAtual.getVizinhos().get(indiceVerticeAtual).VerticeAlvo();
            rota.add(verticeAtual);
            verticesVisitados.add(verticeAtual);

        }
        System.out.println("Finalizou.");

        /*
        for (Aresta ares : verticeAtual.getVizinhos()){

            if (ares.VerticeAlvo().equals(primeiroVertice)){
                rotaArestas.add(ares);
            }
        }

           
        for (Vertice vert : rota){

            System.out.println("Indice: " + vert.getIndex());

        }
        System.out.println("-------------------------");
        
        for (Aresta ares : rotaArestas){

            System.out.println(ares.VerticeDeOrigem().getIndex() + " - " + ares.VerticeAlvo().getIndex());

        }
        */

        return rotaArestas;
    }   
    


    public void VerificarArestasAdjacentes(int arestaSorteada_1, int arestaSorteada_2, Aresta aresta_1, Aresta aresta_2, ArrayList<Aresta> rotaArestas){
        Random rand = new Random();
        boolean arestasSaoAdjacentes = true;

        // Selecionar duas arestas que não sejam adjacentes
        while (arestasSaoAdjacentes){

            if ((arestaSorteada_1 != arestaSorteada_2) && (!aresta_1.VerticeAlvo().equals(aresta_2.VerticeDeOrigem())) && 
                (!aresta_2.VerticeAlvo().equals(aresta_1.VerticeDeOrigem()))){

                arestasSaoAdjacentes = false;
            }
            else {
                arestaSorteada_1 = rand.nextInt(rotaArestas.size());
                arestaSorteada_2 = rand.nextInt(rotaArestas.size());
                aresta_1 = rotaArestas.get(arestaSorteada_1);
                aresta_2 = rotaArestas.get(arestaSorteada_2);

            }
        }
    }

    public ArrayList<Aresta> CopiarRotaArestas(ArrayList<Aresta> rotaArestas){

        ArrayList<Aresta> copia = new ArrayList<>();

        for (Aresta ares : rotaArestas){
            copia.add(ares);
        }

        return copia;
    }


    public void Optimaztion_2Opt(ArrayList<Aresta> rotaArestas){

        Random rand = new Random();
            
        ArrayList<Aresta> rotaArestasCopia = CopiarRotaArestas(rotaArestas);
        
        int arestaSorteada_1;
        int arestaSorteada_2;
        Aresta aresta_1;
        Aresta aresta_2;
        Aresta novaAresta_1 = new Aresta();
        Aresta novaAresta_2 = new Aresta();
        double melhorCusto;
        double custoIteracaoAtual = 0;

        melhorCusto = CalcularCustoCiclo(rotaArestas);
        System.out.println("custo inicial: " + melhorCusto);

        for (int i = 0; i < 10; i++){


            arestaSorteada_1 = rand.nextInt(rotaArestasCopia.size());
            arestaSorteada_2 = rand.nextInt(rotaArestasCopia.size());
            aresta_1 = rotaArestasCopia.get(arestaSorteada_1);
            aresta_2 = rotaArestasCopia.get(arestaSorteada_2);
            //System.out.println(aresta_1.Peso() + " - " + aresta_2.Peso());
            //System.out.println("Posicoes sorteadas: " + arestaSorteada_1 + " - " + arestaSorteada_2);
            VerificarArestasAdjacentes(arestaSorteada_1, arestaSorteada_2, aresta_1, aresta_2, rotaArestasCopia);

            for (Aresta ares : aresta_1.VerticeDeOrigem().getVizinhos()){

                if (ares.VerticeAlvo().equals(aresta_2.VerticeDeOrigem())){
                    novaAresta_1 = ares;
                }

            }
            for (Aresta ares : aresta_1.VerticeAlvo().getVizinhos()){

                if (ares.VerticeAlvo().equals(aresta_2.VerticeAlvo())){
                    novaAresta_2 = ares;
                }

            }

            rotaArestasCopia.remove(aresta_1);
            rotaArestasCopia.remove(aresta_2);

            rotaArestasCopia.add(novaAresta_1);
            rotaArestasCopia.add(novaAresta_2);

            custoIteracaoAtual = CalcularCustoCiclo(rotaArestasCopia);
            System.out.println("Custos calculados: " + custoIteracaoAtual);

            if (custoIteracaoAtual < melhorCusto){
                melhorCusto = custoIteracaoAtual;
            }

            
        }

        System.out.println("Custo apos execucao: " + melhorCusto);

    }

}
