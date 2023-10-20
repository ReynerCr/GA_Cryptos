import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // TODO borrar estas impresiones que son de muestra
        System.out.println("--------------------------------");
        System.out.println("Se leyeron " + cryptos.size() + " archivos .csv");
        for (Coin crypto : cryptos) {
            if (!crypto.getSymbol().equals("BTC")) {
                continue;
            }
            System.out.println(crypto.getSymbol());
            System.out.println("Precio historico");
            Double [] historical = crypto.getHistorical();
            for (Double precio : historical) {
                System.out.println(precio);
            }
        }
/*
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
*/
        

        List<List<Double>> Acum = new ArrayList<>();
        List<Double> RentabilidadTitulos= new ArrayList<>();

        System.out.println("Hola");
        for(Coin crypto: cryptos){
            crypto.calculate_vector_rpi_m();
        
            System.out.println(crypto.getMediaRpi());
            Double [] vector2 = crypto.getvector_rpi_m();

            List<Double> conjunto = new ArrayList<>();
            conjunto=Arrays.asList(vector2);
            Acum.add(conjunto);   
            
            RentabilidadTitulos.add(crypto.getMediaRpi());
        }

       System.out.println(Acum.get(0));

        Double [][] Matrix_covarianza = new Double[10][10];
 
        for( int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Double acum=0.0;
                for(int k=0;k<60;k++){
                    acum=acum+(Acum.get(i).get(j)*Acum.get(i).get(j));
                }
                acum= acum / 60;
                
                Matrix_covarianza[i][j]=acum;
                
            }
        }

        System.out.println(Matrix_covarianza[0][0]);

}
}
