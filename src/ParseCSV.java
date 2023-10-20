import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseCSV {
	// leer datos de archivos .csv en el directorio /prices
	private ArrayList<Coin> cryptos; 

	public ParseCSV(String ruta, int cantMeses) throws Exception {
		this.cryptos = new ArrayList<Coin>();

		try {
			loadData(ruta, cantMeses);
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el directorio");
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");
		} catch (CsvValidationException e) {
			System.out.println("Error al validar el archivo");
		}
	}

	public ArrayList<Coin> getCryptos() {
		return cryptos;
	}

	private void loadData(String ruta, int cantMeses) throws CsvValidationException, NumberFormatException, IOException {
		File rutaPrecios = new File(ruta);
		String archivosPrecios[] = rutaPrecios.list();

		int i = 0, j = 0;
		for (String archivo : archivosPrecios) {
			String[] nombreExtension = archivo.split("\\.");
			if (nombreExtension[1].equals("csv")) {
				// leer el archivo
				FileReader fileReader = new FileReader(ruta + "/" + archivo);

				final CSVParser parser = new CSVParserBuilder()
						.withSeparator(';').build();

				final CSVReader csvReader = new CSVReaderBuilder(fileReader)
						.withCSVParser(parser).withSkipLines(1)
						.build();

				String[] nextLine;

				Double [] historical = new Double[cantMeses];
				Double [] rpi = new Double[cantMeses];
				Double precio;

				while (j < cantMeses && (nextLine = csvReader.readNext()) != null) {
					// leer cada linea y tomar los valores de "close"
					// 7 respectivamente
					// Esta ordenado por fecha
					precio = Double.parseDouble(nextLine[7]);
					historical[j] = precio;
					if (j > 0) { // para j = 0 no hay un precio mas nuevo
						rpi[j-1] = (historical[j-1]/precio) - 1;
					}

					++j;
				}

				// calculando el ultimo rpi
				if ((nextLine = csvReader.readNext()) != null) {
					precio = Double.parseDouble(nextLine[7]);
					rpi[j-1] = (historical[j-1]/precio) - 1;
				} else {
					System.out.println("Advertencia: no se pudo calcular Rpi para ultimo valor porque no hay mas datos.");
				}

				Coin crypto = new Coin(nombreExtension[0], historical, rpi, i+1);
				cryptos.add(crypto);

				fileReader.close();
				csvReader.close();
				j = 0;
				++i;
			} else {
				System.out.println("No es un archivo .csv");
			}
		} // for
	} // loadData
} // class ParseCSV
