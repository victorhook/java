package textproc;

import java.util.*;

public class GeneralWordCounter implements TextProcessor {

    private Set<String> stopWords;
    private Map<String, Integer> wordCount;

    public GeneralWordCounter(Set<String> stopWords) {
        this.stopWords = stopWords;
        this.wordCount = new HashMap<>();
    }

    @Override
    public void process(String w) {
        if (!stopWords.contains(w)) {
            if (wordCount.putIfAbsent(w, 1) != null) {
                wordCount.put(w, wordCount.get(w)+1);
            }
        }
    }

    @Override
    public void report() {
/*        Set<Map.Entry<String, Integer>> wordSet = wordCount.entrySet();
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
        wordList.sort((o1, o2) -> {
                int count1 = o1.getValue(), count2 = o2.getValue();
                if (count1 < count2) {
                    return -1;
                } else if (count1 > count2) {
                    return 1;
                } else {
                    return o1.getKey().compareTo(o2.getKey());
                }
        });

        int totalWords = wordList.size() - 1;
        int wordsToDisplay = 5;
        int stop = totalWords - wordsToDisplay;
        for (int i = totalWords; i > stop; i--) {
            System.out.println(wordList.get(i));
        }
        System.out.println("");*/

        System.out.println("GeneralWordCounter");
        wordCount.keySet()
                 .stream()
                 .sorted(Comparator.comparingInt((String o) -> wordCount.get(o)).thenComparing(o -> o).reversed())
                 .limit(5)
                 .forEach(key -> System.out.printf("%s: %s\n", key, wordCount.get(key)));
        System.out.println("");
    }
}
