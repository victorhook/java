import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class GuiApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Pane root = new Pane();
        PlayerFrame playerFrame = new PlayerFrame(new Player(new InputHandler() {
            public int makeBet() {
                return 0;
            }
            public boolean hit() {
                return false;
            }
        }));

        root.getChildren().addAll(playerFrame);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Blackjack");
        stage.show();
    }
}
