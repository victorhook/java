import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Card> hand;
    private int money, score;
    private boolean wantsToPlay;
    private InputHandler inputHandler;
    private String name;

    public Player(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        hand = new ArrayList<>();
        money = 0;
        wantsToPlay = true;
    }

    public int getBet() {
        int bet = inputHandler.makeBet();
        removeMoney(bet);
        return bet;
    }

    public void hitOrStay() {
        wantsToPlay = inputHandler.hit();
    }

    public String getName() {
        return name == null ? "Player" : name ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWantsToPlay(boolean request) {
        wantsToPlay = request;
    }

    public boolean wantsToPlay() {
        return wantsToPlay;
    }

    public int money() {
        return money;
    }

    public void removeMoney(int amount) {
        money -= amount;
    }

    public void giveMoney(int amount) {
        money += amount;
    }

    public void giveCard(Card card) {
        hand.add(card);
        int cardValue = card.getRank();
        cardValue = cardValue == Card.ACE && score > 10 ? 1 : cardValue;
        score += cardValue;
    }

    public int getScore() {
        return score;
    }

    public void clearHand() {
        hand.clear();
        score = 0;
    }

}
