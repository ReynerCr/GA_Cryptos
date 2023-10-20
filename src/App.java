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

        try {
            // 60 meses = 5 anios
            cryptos = new ParseCSV("./prices", 60).getCryptos();
        } catch (Exception e) {
            System.out.println("Error al leer los archivos");
            return;
        }
        

        System.out.println("--------------------------------");
        System.out.println("Se leyeron " + cryptos.size() + " archivos .csv");
        for (Coin crypto : cryptos) {
            System.out.println(crypto.getSymbol());
            for (Map.Entry<String, Double> entry : crypto.getHistorical().entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
