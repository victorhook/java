package distribution;

import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class HypergeometricDistribution implements Distribution {

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

    @Override
    public String getName() {
        return "HyperGeometric";
    }

    public String toString() { return getName(); }

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
}
