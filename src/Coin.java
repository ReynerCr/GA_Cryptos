import java.util.Map;

public class Coin {
    private String symbol;
    private Map<String, Double> historical;

    private int genNumber;

    public Coin(String symbol, Map<String, Double> historical, int genNumber) {
        this.symbol = symbol;
        this.historical = historical;
        this.genNumber = genNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public Map<String, Double> getHistorical() {
        return historical;
    }

    public int getGenNumber() {
        return genNumber;
    }
}
