package distribution;

import com.sun.webkit.network.Util;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class HypergeometricDistribution implements Distribution {

    private double p;
    private int N, n;
    private ArrayList<Integer> probsCached;


    public HypergeometricDistribution() {
        probsCached = new ArrayList<>();
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
        int upper = (int) (N * p);
        System.out.println(upper);
        int res = Utils.factorial(upper) / ( Utils.factorial((int) x) );
        //int l1 = Utils.factorial(upper) / (Utils.factorial((int) x) * Utils.factorial(upper - (int) x));
        return res;
/*        int l2 =  Utils.factorial(N - upper) / ( Utils.factorial(n-(int) x) * Utils.factorial(N-upper-n-(int) x ));
        return (l1 * l2) / (Utils.factorial(N) / (Utils.factorial(n) * Utils.factorial(N - n)) );*/
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

    public static void main(String[] args) {
        HypergeometricDistribution h = new HypergeometricDistribution();
        h.N = 10;
        h.p = .7;
        h.n = 2;
        System.out.println(h.getProbability(10));
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
