import java.util.Map;

public class Coin {
    private String symbol;
    private Map<String, Double> historical;

    public int gen;

    public Coin(String symbol,  Map<String, Double> historical) {
        this.symbol = symbol;
        this.historical = historical;
    }

    public String getSymbol() {
        return symbol;
    }

    public Map<String, Double> getHistorical() {
        return historical;
    }
}
