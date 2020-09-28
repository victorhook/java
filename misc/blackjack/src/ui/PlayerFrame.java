
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlayerFrame extends HBox {

    public static final String CARD_PATH = "images/cards/";
    private Player player;
    private Label labelName, labelMoney, labelBet, labelScore, labelHit,
                  labelNameVar, labelMoneyVar, labelBetVar, labelScoreVar, labelHitVar;
    private GridPane gridPane;
    private Hand hand;

    public PlayerFrame(Player player) {
        super();
        this.player = player;
        this.gridPane = new GridPane();
        this.hand = new Hand();
        initLabels();

        getChildren().addAll(gridPane, hand);
    }

    private static class Hand extends VBox {
        List<Card> cards;
        List<Label> cards;
        
        Hand() {
            cards = new ArrayList<>();
            update();
        }

        private void update() {
            Label label = new Label();
            ImageView view = new ImageView(new Image(CARD_PATH + "card0.jpg"));
            view.setFitHeight(80);
            view.setPreserveRatio(true);
            label.setGraphic(view);
            getChildren().addAll(label);
        }

        private static class UICard extends Label {

        }

    }

    private void initLabels() {
        labelName = new Label("Name:");
        labelMoney = new Label("Score:");
        labelBet = new Label("Money:");
        labelScore = new Label("Bet:");
        labelHit = new Label("Action:");

        gridPane.add(labelName, 0, 0);
        gridPane.add(labelMoney, 0, 1);
        gridPane.add(labelBet, 0, 2);
        gridPane.add(labelScore, 0, 3);
        gridPane.add(labelHit, 0, 4);

        labelNameVar = new Label(player.getName());
        labelMoneyVar = new Label();
        labelBetVar = new Label();
        labelScoreVar = new Label();
        labelHitVar = new Label();

        gridPane.add(labelNameVar, 1, 0);
        gridPane.add(labelScoreVar, 1, 1);
        gridPane.add(labelMoneyVar, 1, 2);
        gridPane.add(labelBetVar, 1, 3);
        gridPane.add(labelHitVar, 1, 4);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
    }

    private void updateLabels() {
        labelMoneyVar.setText(String.valueOf(player.getMoney()));
        labelBetVar.setText(String.valueOf(player.getBet()));
        labelScoreVar.setText(String.valueOf(player.getScore()));
        labelHitVar.setText(player.wantsToPlay() ? "Hit" : "Stop");
    }

}
