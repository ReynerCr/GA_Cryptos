import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

public class cruces {
    public static Double[][] weightedCrossover(Double[] parent1, Double[] parent2) {
        Double[][] children = new Double[2][parent1.length];
        Random rand = new Random();

        Double acum1 = 0.0d;
        Double acum2 = 0.0d;
        for (int i = 0; i < parent1.length; i++) {
            Double alpha = rand.nextDouble();
            children[0][i] = alpha * parent1[i] + (1 - alpha) * parent2[i];
            children[1][i] = alpha * parent2[i] + (1 - alpha) * parent1[i];
            acum1 += children[0][i];
            acum2 += children[1][i];
        }

        // Re-normalize
        for (int j = 0; j < 10; j++) {
            children[0][j] /= acum1;
            children[1][j] /= acum2;
        }

        return children;
    }
}