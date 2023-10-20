import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AG {

    private List<Double> Rendimientos;
    private List<List<Double>> Pesos;
    private List<Double> Covarianza;
    private Double[][] Matriz;
    private List<Double> fitness;

    private List<Double> Promedios;

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

    }

    public void Crearfitness() {

        fitness = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            fitness.add(Rendimientos.get(i) / Covarianza.get(i));
        }

    }

    public void CrearRendimientos(List<Coin> Coins) {

        Rendimientos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Double acumulador = 0.0d;

            for (int j = 0; j < 10; j++) {
                acumulador = acumulador + Pesos.get(i).get(j);
            }
            Rendimientos.add(acumulador);
        }

    }

    public static List<List<Double>> CrearNumeros() {

        Random random = new Random();
        List<List<Double>> lists = new ArrayList<>();

        // Creamos pesos aleatorios todavía no conozco la población que va a ser M

        int M = 100;
        for (int i = 0; i < M; i++) {
            List<Double> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {

                list.add(random.nextDouble() * 100);
            }
            list.replaceAll(numero -> numero = numero / list.stream().mapToInt(Double::intValue).sum());

            lists.add(list);
        }

        return lists;
    }
}
