public class Coin {
    private String symbol;
    private Double[] historical;
    private Double[] rpi;

    private Double[] vector_rpi_m;
    private Double mediaRpi;
    private int index;

    public Coin(String symbol, Double[] historical, Double[] rpi, int index) {
        this.symbol = symbol;
        this.historical = historical;
        this.rpi = rpi;
        this.index = index;
        this.vector_rpi_m = new Double[60];

        this.mediaRpi = 0.0d;
        for (int i = 0; i < rpi.length; i++) {
            this.mediaRpi += rpi[i];
        }

        this.mediaRpi = this.mediaRpi / rpi.length;

        calculate_vector_rpi_m();
    }

    private void calculate_vector_rpi_m() {
        for (int i = 0; i < 60; i++) {
            vector_rpi_m[i] = rpi[i] - this.mediaRpi;
        }
    }

    public Double[] getvector_rpi_m() {
        return vector_rpi_m;
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
