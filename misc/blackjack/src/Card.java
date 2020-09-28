import java.util.HashMap;
import java.util.Map;

public class Card {

    private static final Map<Integer, String> SUITS;
    static {
        SUITS = new HashMap<>();
        SUITS.put(0, "Clubs");
        SUITS.put(1, "Diamonds");
        SUITS.put(2, "Hearts");
        SUITS.put(3, "Spades");
    }
    private int rank, suit;
    public static final int ACE = 13;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public String toString() {
        return String.format("%s of %s", rank, SUITS.get(suit));
    }

}
