import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        // para la cantidad de meses a leer por archivo
        // 60 meses = 5 anios
        int cantMeses = 60;

        ArrayList<Coin> cryptos = null;

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

        // Acum es una lista de lista de lista de vector (rpi-media)
        for (Coin crypto : cryptos) {
            Double[] vector2 = crypto.getvector_rpi_m();
            List<Double> conjunto = new ArrayList<>();
            conjunto = Arrays.asList(vector2);
            Acum.add(conjunto);
        }

        // Se calcula multiplicacion de cada i, k de Acum
        // Y se saca la media
        Double[][] MatrizCovarianza = new Double[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Double acum = 0.0d;
                for (int k = 0; k < cantMeses; k++) {
                    acum = acum + (Acum.get(i).get(k) * Acum.get(j).get(k));
                }
                acum = acum / cantMeses;
                MatrizCovarianza[i][j] = acum;
            }
        }

        int tamanioPoblacion = 100; // tamanio de la poblacion
        int tamanioIteraciones = 30; // cantidad de iteraciones
        int iter = 0; // contador de iteraciones

        while (iter < tamanioIteraciones) { // iteraciones de AG distintas con soluciones distintas
            AG ag = new AG(cryptos, MatrizCovarianza, tamanioPoblacion);
            ag.CrearNumeros();
            ag.CrearRendimientos();
            ag.CrearCovarianzas();
            ag.Crearfitness();
            for (int i = 0; i < 30; i++) { // 30 iteraciones o generaciones
                // Seleccion de nueva poblacion
                // Se ejecuta hasta tener los tamanioPoblacion individuos
                while (ag.getNuevaPoblación() != tamanioPoblacion) {
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
            ArrayList<Double> pesos = ag.getPesos().get(index);

            // imprimirResultadosExcel(pesos, mejor);
            imprimirNormal(ag, cryptos, pesos, index);

            ++iter; // aumentar iteracion
        } // fin iteraciones
    } // fin main

    // Funcion para imprimir resultados normal
    public static void imprimirNormal(AG ag, ArrayList<Coin> cryptos, ArrayList<Double> pesos, Integer index) {
        System.out.println("Fitness: " + ag.getFitness().get(index));
        for (int i = 0; i < pesos.size(); i++) {
            System.out.println(cryptos.get(i).getSymbol() + ": " + pesos.get(i));
        }
        System.out.println();
    }

    // Funcion para imprimir resultados para alimentar un Excel
    public static void imprimirResultadosExcel(ArrayList<Double> pesos, Double fitness) {
        // Truncar los pesos a 4 decimales
        for (int i = 0; i < pesos.size(); i++) {
            pesos.set(i, Math.floor(pesos.get(i) * 10000) / 10000);
        }

        // Truncar fitness a 6 decimales
        fitness = Math.floor(fitness * 1000000) / 1000000;

        // Convertir los pesos a string y reemplazar los puntos por comas e imprimir los
        // pesos separados por espacios, quitando los caracteres [] de que era un array
        String pesosString = pesos.toString().replace(",", "");
        pesosString = pesosString.replace(".", ",");
        pesosString = pesosString.replace("[", "");
        pesosString = pesosString.replace("]", "");
        System.out.print(pesosString);

        System.out.print(" ");

        // Imprimir el fitness reemplazando el punto por coma
        String fitnessString = fitness.toString().replace(".", ",");
        System.out.println(fitnessString);
    } // fin imprimirResultados
} // fin clase
