import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        // para la cantidad de meses a leer por archivo

        ArrayList<Coin> cryptos = null;
        // 60 meses = 5 anios
        int cantMeses = 60;

        try {
            ParseCSV csv = new ParseCSV("./prices", cantMeses);
            cryptos = csv.getCryptos();
        } catch (Exception e) {
            System.out.println("Error al leer los archivos");
            e.printStackTrace();
            return;
        }

        // Calculo de la matriz de covarianza de los rendimientos
        List<List<Double>> Acum = new ArrayList<>();

        for (Coin crypto : cryptos) {
            Double[] vector2 = crypto.getvector_rpi_m();

            List<Double> conjunto = new ArrayList<>();
            conjunto = Arrays.asList(vector2);
            Acum.add(conjunto);
        }

        Double[][] MatrizCovarianza = new Double[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Double acum = 0.0d;
                for (int k = 0; k < 60; k++) {
                    // Aqui fue el error
                    acum = acum + (Acum.get(i).get(k) * Acum.get(j).get(k));
                }
                acum = acum / 60;

                MatrizCovarianza[i][j] = acum;
            }
        }

        AG ag = new AG(cryptos, MatrizCovarianza);

        ag.CrearNumeros();
        ag.CrearRendimientos();
        ag.CrearCovarianzas();
        ag.Crearfitness();

        for (int i = 0; i < 30; i++) { // 30 iteraciones
            while (ag.getNuevaPoblación() != 100) { // 100 individuos
                ag.Torneo(); // torneo
                // Calculo random para cruza y muta
                Double prueba_cruce = Math.random();
                Double prueba_muta = Math.random();
                // Ganadores de torneo
                ArrayList<ArrayList<Double>> Ganadores = new ArrayList<ArrayList<Double>>();
                if (prueba_cruce <= 0.80) { // cruza
                    Ganadores = ag.GetGanadores();
                    Ganadores = ag.crucePorPesos(Ganadores.get(0), Ganadores.get(1));
                }
                if (prueba_muta <= 0.05) { // muta
                    for (int k = 0; k < Ganadores.size(); k++) {
                        ag.mutacion(Ganadores.get(k));
                    }
                }

                // Aniado los ganadores a la nueva poblacion
                for (int k = 0; k < Ganadores.size(); k++) {
                    ag.GetNuevaGeneracion().add(Ganadores.get(k));
                }
            } // while para la poblacion

            // Reemplazo la poblacion
            ag.ReemplazarPoblacion();
            // Calculo de fitness de la nueva poblacion
            ag.CrearRendimientos();
            ag.CrearCovarianzas();
            ag.Crearfitness();
        } // fin for de las iteraciones generaciones

        // Calculo de la mejor solucion, fitness y peso
        Double mejor = Collections.max(ag.getFitness());
        Integer index = ag.getFitness().indexOf(mejor);

        // Tengo que imprimir todo con los datos de Coin
        ArrayList<Double> pesos = ag.getPesos().get(index);
        
        System.out.println("Mejor solucion: " + pesos);
        System.out.println("Mejor fitness: " + ag.getFitness().get(index));
        
        // 
    } // fin main
} // fin clase
