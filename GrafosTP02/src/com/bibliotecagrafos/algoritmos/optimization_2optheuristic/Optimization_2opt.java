package com.bibliotecagrafos.algoritmos.optimization_2optheuristic;

import com.bibliotecagrafos.vertice.Vertice;

import java.util.ArrayList;

public class Optimization_2opt {

    public static double CalcularCustoRotaVertices(ArrayList<Vertice> rota){

        double custo = 0;

        for (int i = 0; i < rota.size()-1; i += 1){
            custo += rota.get(i).ArestaCom(rota.get(i+1)).Peso();
        }

        return custo;

    }



    private static ArrayList<Vertice> swap(ArrayList<Vertice> rotaVertices, int i, int j) {
        //conducts a 2 opt swap by inverting the order of the points between i and j
        ArrayList<Vertice> newTour = new ArrayList<>();

        //take array up to first point i and add to newTour
        int size = rotaVertices.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(rotaVertices.get(c));
        }

        //invert order between 2 passed points i and j and add to newTour
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(rotaVertices.get(j - dec));
            dec++;
        }

        //append array from point j to end to newTour
        for (int c = j + 1; c < size; c++) {
            newTour.add(rotaVertices.get(c));
        }

        return newTour;
    }

    public static double Optimization_2Opt(ArrayList<Vertice> rotaVertices){

        ArrayList<Vertice> rotaVerticesNova;

        double melhorCusto;
        double novoMelhorCusto = 0;
        int iteracao = 0;

        melhorCusto = CalcularCustoRotaVertices(rotaVertices);
        //System.out.println("custo inicial: " + melhorCusto);

        long startTime = System.nanoTime();
        while (true){

            for (int i = 1; i < rotaVertices.size() - 2; i++){
                for (int j = i + 1; j < rotaVertices.size() - 1; j++){

                    if (rotaVertices.get(i).ArestaCom(rotaVertices.get(i-1)).Peso() + rotaVertices.get(j+1).ArestaCom(rotaVertices.get(j)).Peso() >=
                            rotaVertices.get(i).ArestaCom(rotaVertices.get(j+1)).Peso() + rotaVertices.get(i-1).ArestaCom(rotaVertices.get(j)).Peso()){

                        rotaVerticesNova = swap(rotaVertices, i, j);

                        novoMelhorCusto = CalcularCustoRotaVertices(rotaVerticesNova);
                        //System.out.println("Custo novo: " + novoMelhorCusto);
                        if (novoMelhorCusto < melhorCusto){
                            rotaVertices = rotaVerticesNova;
                            melhorCusto = novoMelhorCusto;
                            iteracao = 0;
                        }
                    }
                }
                //Não sei se aqui é a melhor posição
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);

                duration = duration/1_000_000_000;

                if(duration/60 >= 3){
                    break;
                }

            }
            iteracao++;

            if (iteracao > 10){
                break;
            }
        }
        //System.out.println("Custo apos execucao: " + melhorCusto);

        return melhorCusto;
    }

}
