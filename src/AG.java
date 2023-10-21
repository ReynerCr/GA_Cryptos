import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AG {

    private ArrayList<Coin> Coins;
    private ArrayList<Double> Rendimientos;
    private ArrayList<ArrayList<Double>> Pesos;
    private ArrayList<Double> Covarianza;
    private Double[][] MatrizCovarianza;
    private ArrayList<Double> fitness;

    public AG(ArrayList<Coin> Coins, Double[][] MatrizCovarianza) {
        this.Coins = Coins;
        this.MatrizCovarianza = MatrizCovarianza;
        
    }

    public void CrearCovarianzas() {
        Covarianza = new ArrayList<>();
        for (int k = 0; k < 100; k++) {
            Double cova = 0.0;
            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < 10; j++) {
                    cova = cova + (Pesos.get(k).get(i) * Pesos.get(k).get(j) * MatrizCovarianza[i][j]);
                }
            }
            Covarianza.add(cova);
        }
    } // fin CrearCovarianzas

    public void Torneo(){
       
        Random random = new Random();   

        ArrayList<Double> Participantes = new ArrayList<>();
        ArrayList<Integer> Indices = new ArrayList<>();

       
        for(int i=0;i<2;i++){
            for(int j=0;j<20;j++){
            int numero =random.nextInt(100);
            Indices.add(numero);
            Participantes.add(fitness.get(Indices.get(j)));
           System.out.println(Participantes.get(j));
        }
 
        Double ganador=Collections.max(Participantes);
        Participantes.indexOf(ganador);
        System.out.println("este es el ganador");
        System.out.println(ganador);

        }
       

    }


    public void Crearfitness() {
        fitness = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            fitness.add(Rendimientos.get(i) / Covarianza.get(i));
        }
    } // fin Crearfitness

    public void CrearRendimientos() {
        Rendimientos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Double acumulador = 0.0d;

            for (int j = 0; j < 10; j++) {
                acumulador = acumulador + Pesos.get(i).get(j) * Coins.get(j).getMediaRpi();
            }
            Rendimientos.add(acumulador);
        }
    } // fin CrearRendimientos

    public void CrearNumeros() {
        Random random = new Random();
        Pesos = new ArrayList<ArrayList<Double>>();

        // Creamos pesos aleatorios todavía no conozco la población que va a ser M

        int M = 100;
        for (int i = 0; i < M; i++) {
            ArrayList<Double> list = new ArrayList<>(); // cada gen de cada cromosoma
            double acum = 0.0;
            for (int j = 0; j < 10; j++) {
                
                double numero =random.nextDouble() * 100;
                acum=acum+numero;
                list.add(numero);

            }
            
            for(int j=0;j<10;j++){
                double numero=list.get(j);
                numero = numero / acum;
                list.set(j,numero);
            }

            Pesos.add(list);
        }
    } // fin CrearNumeros

    public void setMatrizCovarianzas(Double[][] MatrizCovarianza) {
        this.MatrizCovarianza = MatrizCovarianza;
    }
}
