package distribution;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class BinomialDistribution extends DiscreteDistribution {

    private double p;
    private int n, radius;

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
    public void selected() {

    }

    //@Override
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

    @Override
    public String getName() {
        return "Binomial";
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getN() { return n; }
    public int getP() { return (int) p; }
    public void setN(int n) { this.n = n; }
    public void setP(double p) { this.p = p; }


    public double getProbability(double x) {
        double nOverX = Utils.factorial(n) / ( Utils.factorial((int) x) * Utils.factorial((int) (n-x)) );
        return nOverX * Math.pow(p, x) * Math.pow(1-p, n-x);
    }

    public double getProbabilityLT(int stop) {
        return getProbabilityUpTo(stop);
    }
    public double getProbabilityLTE(int stop) {
        return getProbabilityUpTo(stop+1);
    }
    public double getProbabilityGT(int stop) {
        return 1 - getProbabilityUpTo(stop+1);
    }
    public double getProbabilityGTE(int stop) {
        return 1 - getProbabilityUpTo(stop);
    }

    @Override
    public double getMaxY() {
        OptionalDouble res = IntStream.range(0, n).
                                    mapToDouble(x -> getProbability(x))
                                    .max();
        return res.isPresent() ? res.getAsDouble() : 0;
    }

    private double getProbabilityUpTo(int stop) {
        double probability = 0;
        for (int i = 0; i < stop; i++) {
            probability += getProbability(i);
        }
        return probability;
    }

/*    public static void main(String[] args) {
        BinomialDistribution bin = new BinomialDistribution();
        bin.setN(10);
        bin.setP(.2);
        System.out.printf("P(X=x) %s\n", bin.getProbability(2));
        System.out.printf("P(X<x) %s\n", bin.getProbabilityLT(2));
        System.out.printf("P(X<=x) %s\n", bin.getProbabilityLTE(2));
        System.out.printf("P(X>x) %s\n", bin.getProbabilityGT(2));
        System.out.printf("P(X>=x) %s\n", bin.getProbabilityGTE(2));
    }*/

}
