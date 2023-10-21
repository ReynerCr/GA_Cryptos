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

    private ArrayList<ArrayList<Double>> Ganadores;
    private ArrayList<ArrayList<Double>> NuevaGeneracion;

    // Constructor
    public AG(ArrayList<Coin> Coins, Double[][] MatrizCovarianza) {
        this.Coins = Coins;
        this.MatrizCovarianza = MatrizCovarianza;
    }

    public ArrayList<Double> getFitness() {
        return fitness;
    }

    public Integer getNuevaPoblación() {
        if (NuevaGeneracion == null) {
            NuevaGeneracion = new ArrayList<ArrayList<Double>>();
        }
        return NuevaGeneracion.size();
    }

    // Funcion para reemplazar la poblacion
    public void ReemplazarPoblacion() {
        Pesos = NuevaGeneracion;
        NuevaGeneracion = new ArrayList<ArrayList<Double>>();
    } // fin ReemplazarPoblacion


    // Funcion para calcular la covarianza
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

    // Funcion para realizar el torneo
    public void Torneo() {
        Random random = new Random();
        Ganadores = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> Participantes = new ArrayList<>();
        ArrayList<Integer> Indices = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 20; j++) {
                int numero = random.nextInt(100);
                Indices.add(numero);
                Participantes.add(fitness.get(Indices.get(j)));
            }

            Double ganador = Collections.max(Participantes);
            Integer index = Participantes.indexOf(ganador);

            Ganadores.add(Pesos.get(index));
        } // fin for j
    } // fin Torneo i

    public ArrayList<ArrayList<Double>> GetGanadores() {
        return Ganadores;
    }

    public ArrayList<ArrayList<Double>> GetNuevaGeneracion() {
        return NuevaGeneracion;
    }
    // Funcion para calcular el fitness
    public void Crearfitness() {
        fitness = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            fitness.add(Rendimientos.get(i) / Covarianza.get(i));
        }
    } // fin Crearfitness

    // Funcion para calcular los rendimientos (funcion max)
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

    // Funcion para crear los numeros aleatorios para pesos
    public void CrearNumeros() {
        Random random = new Random();
        Pesos = new ArrayList<ArrayList<Double>>();

        int M = 100;
        for (int i = 0; i < M; i++) {
            ArrayList<Double> list = new ArrayList<>(); // cada gen de cada cromosoma
            Double acum = 0.0d;
            for (int j = 0; j < 10; j++) {
                Double numero = random.nextDouble() * 100;
                acum = acum + numero;
                list.add(numero);
            }

            for (int j = 0; j < 10; j++) {
                Double numero = list.get(j);
                numero = numero / acum;
                list.set(j, numero);
            }

            Pesos.add(list);
        }
    } // fin CrearNumeros

    // Funcion para el cruce de cromosomas con pesos
    public ArrayList<ArrayList<Double>> crucePorPesos(ArrayList<Double> padre1, ArrayList<Double> padre2) {
        ArrayList<ArrayList<Double>> hijos = new ArrayList<ArrayList<Double>>();
        hijos.add(new ArrayList<Double>());
        hijos.add(new ArrayList<Double>());
        Random rand = new Random();

        Double acum1 = 0.0d;
        Double acum2 = 0.0d;
        for (int i = 0; i < padre1.size(); i++) {
            Double aux = rand.nextDouble();
            hijos.get(0).add(aux * padre1.get(i) + (1 - aux) * padre2.get(i));
            hijos.get(1).add(aux * padre2.get(i) + (1 - aux) * padre1.get(i));
            acum1 += hijos.get(0).get(i);
            acum2 += hijos.get(1).get(i);
        }

        // Re normalizar los pesos
        for (int j = 0; j < 10; j++) {
            hijos.get(0).set(j, hijos.get(0).get(j) / acum1);
            hijos.get(1).set(j, hijos.get(1).get(j) / acum2);
        }

        return hijos;
    } // fin crucePorPesos

    // Funcion para la mutacion de cromosomas
    public void mutacion(ArrayList<Double> cromosoma) {
        // Intercambia de posicion los cromosomas
        Random rand = new Random();
        int pos1 = rand.nextInt(10);
        int pos2 = rand.nextInt(10);
        Double aux = cromosoma.get(pos1);
        cromosoma.set(pos1, cromosoma.get(pos2));
        cromosoma.set(pos2, aux);
    } // fin mutacion

    public void setMatrizCovarianzas(Double[][] MatrizCovarianza) {
        this.MatrizCovarianza = MatrizCovarianza;
    } // fin setMatrizCovarianzas
} // fin clase AG
