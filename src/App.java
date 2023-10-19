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
        // leer datos de archivos .csv en el directorio /prices
        File rutaPrecios = new File("./prices");
        String archivosPrecios[] = rutaPrecios.list();

        ArrayList<Coin> cryptos = new ArrayList<Coin>();

        int i = 0, j = 0;
        for (String archivo : archivosPrecios) {
            String[] nombreExtension = archivo.split("\\.");
            if (nombreExtension[1].equals("csv")) {
                // leer el archivo
                FileReader fileReader = new FileReader("./prices/" + archivo);

                final CSVParser parser = new CSVParserBuilder()
                        .withSeparator(';').build();

                final CSVReader csvReader = new CSVReaderBuilder(fileReader)
                        .withCSVParser(parser).withSkipLines(1)
                        .build();

                String[] nextLine;

                // TODO esto podria ser por parametro recibido en main?? args
                // son 60 datos porque son 12 meses * 5 años = 60 meses
                Map<String, Double> historical = new HashMap<String, Double>();
                while (j < 60 && (nextLine = csvReader.readNext()) != null) {
                    // leer cada linea y tomar los valores de "closeTime" y "close"
                    // indices 1 y 7 respectivamente
                    String[] fecha = nextLine[1].split("-");
                    String fechaMesAnio = fecha[0] + "-" + fecha[1];
                    historical.put(fechaMesAnio, Double.parseDouble(nextLine[7]));
                    ++j;
                }

                Coin crypto = new Coin(nombreExtension[0], historical);
                cryptos.add(crypto);
                fileReader.close();
                csvReader.close();
                j = 0;
                ++i;
            } else {
                System.out.println("No es un archivo .csv");
            }
        }
        // TODO borrar prueba lectura
        System.out.println("--------------------------------");
        System.out.println("Se leyeron " + i + " archivos .csv");
        for (Coin crypto : cryptos) {
            System.out.println(crypto.getSymbol());
            System.out.println(crypto.getHistorical().size());
            System.out.println(crypto.getHistorical().get("2020-01"));
        }
    }
}
