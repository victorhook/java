import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Deck {

    private static final int TOTAL_CARDS = 52;
    private List<Card> cards;
    private static Random rand;

    public Deck() {
        cards = new ArrayList<>();
        rand = new Random();
        reset();
    }

    public void reset() {
        cards.clear();
        IntStream.range(0, TOTAL_CARDS)
                 .forEach(i -> cards.add(new Card(2 + i % 13, i / 13)));
        shuffle();
    }

    public boolean hasCards() {
        return cards.size() > 0;
    }

    public Card draw() {
        return cards.remove(rand.nextInt(cards.size()));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

}
