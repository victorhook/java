package distribution;

public abstract class DiscreteDistribution implements Distribution {

    protected int n;

    abstract double getProbabilityLT(int stop);
    abstract double getProbabilityLTE(int stop);
    abstract double getProbabilityGT(int stop);
    abstract double getProbabilityGTE(int stop);

    public String toString() { return getName(); }

}
