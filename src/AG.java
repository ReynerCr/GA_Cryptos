import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AG{

    private List<Double> Rendimientos;
    private List<List<Double>> Pesos;
    
    public static void CrearRendimientos(List<List<Double>> Pesos, List<Coin> Coins){

        List<Double> list = new ArrayList<>();

        
        
        for(int i=1;i<=Pesos.size();i++){
        //    double rendimientos = 0;

            for(int j=1;j<=Pesos.get(i).size();i++){
               // Pesos.get(i).get(j) * coin[i]
            }
         }
        }

     //   list.add(rendimientos);

    }

    public static List<List<Double>> CrearNumeros(){

    Random random = new Random();
    List<List<Double>> lists = new ArrayList<>();
    
    //Creamos pesos aleatorios todavía no conozco la población que va a ser M

    int M = 3;
        for (int i = 0; i < M; i++)  {
            List<Double>list = new ArrayList<>();
            for(int j=0; j<10;j++){

                list.add(random.nextDouble() * 100);
            }
            list.replaceAll(numero -> numero = numero / list.stream().mapToInt(Double::intValue).sum() );

            lists.add(list);
        }
        
    return lists;
    }
}

