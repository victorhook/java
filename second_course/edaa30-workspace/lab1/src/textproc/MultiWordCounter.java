package textproc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MultiWordCounter implements TextProcessor {

    private Map<String, Integer> regions;

    public MultiWordCounter(String[] regions) {
        this.regions = new HashMap<>();
        Stream.of(regions).forEach(region -> this.regions.put(region, 0));
    }

    @Override
    public void process(String w) {
        if (regions.containsKey(w)) {
            regions.put(w, regions.get(w)+1);
        }
    }

    @Override
    public void report() {
        System.out.println("MultiWordCounter");
        regions.keySet().stream()
                        .sorted((o1, o2) -> {
                            int res = regions.get(o1) - regions.get(o2);
                            return res != 0 ? -1*res : -1*o1.compareTo(o2);
                        })
                        .forEach(key -> System.out.printf("%s: %s\n", key, regions.get(key)));
        System.out.println("");
    }
}
