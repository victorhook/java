import java.util.List;

public interface Callback {
    void announceTies(List<Player> ties);
    void announceWinners(Player winners);
    void announceNoWinners();
}
