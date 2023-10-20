import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;

public class AG {

    private ArrayList<Double> Rendimientos;
    private ArrayList<ArrayList<Double>> Pesos;
    private ArrayList<Double> Covarianza;
    private Double[][] Matriz;
    private ArrayList<Double> fitness;

    private ArrayList<Double> Promedios;

    public void CrearCovarianzas() {
        Covarianza = new ArrayList<>();

        for (int k = 0; k < 100; k++) {
            Double cova = 0.0;
            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < 10; j++) {
                    cova = cova + (Pesos.get(k).get(i) * Pesos.get(k).get(j) * Matriz[i][j]);
                }
            }
            Covarianza.add(cova);
        }

    } // fin CrearCovarianzas

    public void Crearfitness() {
        fitness = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            fitness.add(Rendimientos.get(i) / Covarianza.get(i));
        }
    } // fin Crearfitness

    public void CrearRendimientos(ArrayList<Coin> Coins) {
        Rendimientos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Double acumulador = 0.0d;

            for (int j = 0; j < 10; j++) {
                acumulador = acumulador + Pesos.get(i).get(j);
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
            for (int j = 0; j < 10; j++) {

                list.add(random.nextDouble() * 100);
            }
            list.replaceAll(numero -> numero = numero / list.stream().mapToInt(Double::intValue).sum());

            Pesos.add(list);
        }

        return;
    } //  fin CrearNumeros
}
