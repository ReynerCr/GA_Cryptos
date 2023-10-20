import java.util.ArrayList;

public class Coin {
    private String symbol;
    private Double[] historical;
    private Double[] rpi;
    private Double mediaRpi;

    private int index;

    public Coin(String symbol, Double [] historical, Double [] rpi, int index) {
        this.symbol = symbol;
        this.historical = historical;
        this.rpi = rpi;
        this.index = index;

        mediaRpi = 0.0d;
        for (int i = 0; i < rpi.length; i++) {
            mediaRpi += rpi[i];
        }

        mediaRpi = mediaRpi / rpi.length;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double[] getHistorical() {
        return historical;
    }

    public int getIndex() {
        return index;
    }

    public Double[] getRpi() {
        return rpi;
    }

    public Double getRpi(int index) {
        return rpi[index];
    }

    public Double getHistorical(int index) {
        return historical[index];
    }

    public Double getMediaRpi() {
        return mediaRpi;
    }
}
