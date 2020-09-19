package textproc;

import java.util.*;

public class GeneralWordCounter implements TextProcessor {

    private Set<String> stopWords;
    private Map<String, Integer> wordCount;

    public GeneralWordCounter(Set<String> stopWords) {
        this.stopWords = stopWords;
        this.wordCount = new HashMap<>();
        //this.wordCount = new TreeMap<>();
    }

    @Override
    public void process(String w) {
        if (!stopWords.contains(w)) {
            if (wordCount.putIfAbsent(w, 1) != null) {
                wordCount.put(w, wordCount.get(w)+1);
            }
        }
    }

    public void clear() {
        this.wordCount.clear();
    }

    @Override
    public void report() {
/*        for (String word: wordCount.keySet()) {
            int count = wordCount.get(word);
            if (count >= 200) {
                System.out.println(String.format("%s: %s", word, count));
            }
        }*/
        Set<Map.Entry<String, Integer>> wordSet = wordCount.entrySet();
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
        wordList.sort(new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second) {
                int count1 = first.getValue(), count2 = second.getValue();
                if (count1 < count2) {
                    return -1;
                } else if (count1 > count2) {
                    return 1;
                } else {
                    return first.getKey().compareTo(second.getKey());
                }
            }
        });

        int totalWords = wordList.size() - 1;
        int wordsToDisplay = 5;
        int stop = totalWords - wordsToDisplay;
        for (int i = totalWords; i > stop; i--) {
            System.out.println(wordList.get(i));
        }
        System.out.println("");
    }

    public List<Map.Entry<String, Integer>> getWordList() {
        return new ArrayList<>(wordCount.entrySet());
    }

}