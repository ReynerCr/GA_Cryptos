import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            System.out.println(crypto.getMediaRpi());
            Double[] vector2 = crypto.getvector_rpi_m();

            List<Double> conjunto = new ArrayList<>();
            conjunto = Arrays.asList(vector2);
            Acum.add(conjunto);
        }

        System.out.println(Acum.get(0)); // TODO borrar

        Double[][] MatrizCovarianza = new Double[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Double acum = 0.0d;
                for (int k = 0; k < 60; k++) {
                    acum = acum + (Acum.get(i).get(j) * Acum.get(i).get(j));
                }
                acum = acum / 60;

                MatrizCovarianza[i][j] = acum;
            }
        }

        System.out.println(MatrizCovarianza[0][0]);

        AG ag = new AG(cryptos, MatrizCovarianza);

        // TODO a partir de aqui iria el ciclo de generaciones
        ag.CrearNumeros();
        ag.CrearRendimientos();
        ag.CrearCovarianzas();
        ag.Crearfitness();
    }
}
