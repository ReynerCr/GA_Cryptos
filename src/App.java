import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        // TODO quedaria mas bonito si se leen los argumentos desde la linea de comandos para la cantidad de meses a leer por archivo

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

        System.out.println("--------------------------------");
        System.out.println("Se leyeron " + cryptos.size() + " archivos .csv");
        for (Coin crypto : cryptos) {
            System.out.println(crypto.getSymbol());
            System.out.println("Precio historico");
            Double [] historical = crypto.getHistorical();
            for (Double precio : historical) {
                System.out.println(precio);
            }
        }


        System.out.println("--------------------------------");
        System.out.println("Se leyeron " + cryptos.size() + " archivos .csv");
        for (Coin crypto : cryptos) {
            System.out.println(crypto.getSymbol());
            System.out.println("Rpi");
            Double [] rpi = crypto.getRpi();
            for (Double rpii : rpi) {
                System.out.println(rpii);
            }
        }
    }
}
