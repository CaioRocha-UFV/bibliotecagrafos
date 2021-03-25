import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class main{

    public static void main (String[] args) throws IOException {

        String nomeArquivo = "GrafoV2.json";
        String linha;
        Scanner entrada = new Scanner(System.in);
        HashMap<String, String> idsRotulos  = new HashMap<>();
     
        FileWriter fw1 = new FileWriter("capricho.txt", false);
        BufferedWriter bw1 = new BufferedWriter(fw1);
     




  
        FileReader fr = new FileReader(nomeArquivo);
        BufferedReader br = new BufferedReader(fr);
        
        linha = br.readLine();

        

        linha = linha.replaceAll("\"",":");

        ArrayList<String> lops = new ArrayList<>(Arrays.asList(linha.split(":|\\,")));

        
        ArrayList<String> vFinale = new ArrayList<String>();
        for (String s : lops){

            
            if (!(s.equals(lops.get(2)) ||  s.contains("{") || s.contains("}"))){

                vFinale.add(s);
            }
        
        }
      
        /*
        for (String s : vFinale){
            System.out.println(s);
        }
        */



        String id = "";
        
        int j = 0;
        for (int i = 0; i < vFinale.size(); i++){



            if (j == 0){

            
                if (vFinale.get(i).equals("id")){
                    id = vFinale.get(i+1);
                }
                if (vFinale.get(i).equals("label")){
                    idsRotulos.put(id, vFinale.get(i+1));
                }
            }

            if (vFinale.get(i).equals("length")){
                if (j == 1){
                    break;
                }
                bw1.write(vFinale.get(i+1) + "\n");
                j++;
            }

            if (j == 1){
                if (vFinale.get(i).equals("from")){
                    bw1.write(idsRotulos.get(vFinale.get(i+1)) + " ");
                }
                if(vFinale.get(i).equals("to")){
                    bw1.write(idsRotulos.get(vFinale.get(i+1)) + " " );
                }  
                if(vFinale.get(i).equals("label")){
                    bw1.write(vFinale.get(i+1) + "\n");
                }
            }
                      
        }


        bw1.close();
        fw1.close();
        
  
        br.close();
        fr.close();
        
 
    }
}