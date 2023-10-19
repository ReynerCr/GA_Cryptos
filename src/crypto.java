import java.util.Map;

public class crypto {
    private String name;
    private String symbol;
    private double price;
    private Map<String, Double> historical; // TODO si lo usamos asi?

    public crypto(String name, String symbol, double price, Map<String, Double> historical) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.historical = historical;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Double> getHistorical() {
        return historical;
    }
}
