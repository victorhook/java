package distribution;

import javafx.collections.ObservableListBase;

import java.util.ArrayList;

public class DistributionList extends ObservableListBase {

    private ArrayList<Distribution> list;

    public DistributionList() {
        list = new ArrayList<>();
        list.add(new BinomialDistribution());
        list.add(new HypergeometricDistribution());
        list.add(new PoissonDistribution());
        System.out.println(list);
    }

    public ArrayList<Distribution> getDistributions() { return list; }

    @Override
    public Object get(int i) {
        return list.get(i);
    }

    @Override
    public int size() {
        return list.size();
    }

}
