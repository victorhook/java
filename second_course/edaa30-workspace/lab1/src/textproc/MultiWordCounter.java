package textproc;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MultiWordCounter implements TextProcessor {

    private Map<String, Integer> regions;

    public MultiWordCounter(String[] regions) {
        this.regions = new HashMap<>();
        //this.regions = new TreeMap<>();
        for (String region: regions) {
            this.regions.put(region, 0);
        }
    }

    @Override
    public void process(String w) {
        if (regions.containsKey(w)) {
            regions.put(w, regions.get(w)+1);
        }
    }

    @Override
    public void report() {
        for (String region: regions.keySet()) {
            System.out.printf("%s: %s\n", region, regions.get(region));
        }
        System.out.println("");
    }
}
