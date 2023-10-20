import java.util.ArrayList;

public class Coin {
    private String symbol;
    private Double[] historical;
    private Double[] rpi;

    private int index;

    public Coin(String symbol, Double [] historical, Double [] rpi, int index) {
        this.symbol = symbol;
        this.historical = historical;
        this.rpi = rpi;
        this.index = index;
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
}
