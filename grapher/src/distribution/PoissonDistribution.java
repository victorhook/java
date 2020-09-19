package distribution;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

public class PoissonDistribution extends DiscreteDistribution {

    double lambda;

    @Override
    public double getMaxY() {
        return 0;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getLambda() {
        return this.lambda;
    }

    @Override
    public void selected() {

    }

    @Override
    public double getExpectedValue() {
        return 0;
    }

    @Override
    public double getExpectedVariance() {
        return 0;
    }

    @Override
    public double getExpectedMean() {
        return 0;
    }

    @Override
    public String getName() {
        return "Poisson";
    }

    private double getProbability(int x) {
        return Math.exp(-lambda) * Math.pow(lambda, x) / Utils.factorial(x);
    }

    private double getProbabilityUpTo(int stop) {
        double prob = 0;
        for (int i = 0; i < stop; i++) {
            prob += getProbability(i);
        }
        return prob;
    }

    @Override
    double getProbabilityLT(int x) {
        return getProbabilityUpTo(x);
    }

    @Override
    double getProbabilityLTE(int x) {
        return getProbabilityUpTo(x+1);
    }

    @Override
    double getProbabilityGT(int x) {
        return 1 - getProbabilityUpTo(x+1);
    }

    @Override
    double getProbabilityGTE(int x) {
        return 1 - getProbabilityUpTo(x-1);
    }

    public void draw(GraphicsContext g, double lenX, double lenY,
                     double startX, double startY, double maxY) {
        g.setLineWidth(2);
        g.setFill(Paint.valueOf("blue"));

        double x = startX, y;

        Map<Integer, Double> values = new HashMap<>();
        for (int i = 0; i < n; i++) {
            values.put(i, getProbability(i));
        }
        System.out.println(values);

        double plotStepSize = lenX / n;
        for (Integer key: values.keySet()) {
            y = startY - ((values.get(key) / maxY) * lenY + POINT_RADIUS);
            System.out.println(y);
            g.fillOval(x, y, POINT_SIZE, POINT_SIZE);
            x += plotStepSize;
        }

    }

    public static void main(String[] args) {
        PoissonDistribution pos = new PoissonDistribution();
        pos.setLambda(2);
        System.out.printf("EQ: %s\n", pos.getProbability(2));
        System.out.printf("LT: %s\n", pos.getProbabilityLT(2));
        System.out.printf("LTE: %s\n", pos.getProbabilityLTE(2));
        System.out.printf("GTE: %s\n", pos.getProbabilityGTE(2));
        System.out.printf("GT: %s\n", pos.getProbabilityGT(2));
    }

}
