import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {

    private Deck deck;
    private List<Player> players;
    private int pot;
    private Callback callback;

    public GameController(Deck deck) {
        this(deck, new ArrayList<>());
    }

    public GameController(Deck deck, List<Player> players) {
        this.deck = deck;
        players = players;
    }

    public void playRound() {
        deck.shuffle();
        deal();
        deal();
        pot = getBets();

        while (gameRunning()) {
            deal();
            askToContinue();
        }

        List<Player> winners = findWinner();
        if (winners == null)
            callback.announceNoWinners();
        else if (winners.size() > 1)
            callback.announceTies(winners);
        else
            callback.announceWinners(winners.get(0));
    }

    private int getBets() {
        return players.stream()
                      .mapToInt(player -> player.getBet())
                      .sum();
    }

    private List<Player> findWinner() {
        Player winner = null;
        List<Player> ties = new ArrayList<>();
        List<Player> loosers = new ArrayList<>();

        for (Player player: players) {
            int score = player.getScore();
            if (score > 21)
                loosers.add(player);
            else if (winner == null)
                winner = player;
            else {
                int prevScore = winner.getScore();

                if (score > prevScore) {
                    winner = player;
                    loosers.add(winner);
                    ties.clear();
                } else if (score == prevScore) {
                    winner = player;
                    ties.add(player);
                    ties.add(winner);
                }
            }
        }

        if (winner == null)
            return null;

        if (ties.size() > 0)
            return ties;

        List<Player> winnerList = new ArrayList<>();
        winnerList.add(winner);
        return winnerList;
    }

    private void askToContinue() {
        players.stream()
                .filter(player -> player.wantsToPlay())
                .forEach(player -> player.hitOrStay());
    }

    private void deal() {
        players.stream()
               .filter(player -> player.wantsToPlay())
               .forEach(player -> player.giveCard(deck.draw()));
    }

    private boolean gameRunning() {
        for (Player player: players) {
            if (player.wantsToPlay())
                return true;
        }
        return false;
    }

}
