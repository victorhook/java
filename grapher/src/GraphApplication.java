import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphApplication extends Application {


    public static final int PREF_WIDTH = 1300, PREF_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/grapher.fxml"));
        //Parent root = loader.load();
        //GraphController controller = loader.getController();

        Pane root = new Pane();
        root.setPrefSize(PREF_WIDTH, PREF_HEIGHT);


        TabPane tabPane = new TabPane();
        tabPane.prefWidthProperty().bind(root.widthProperty());
        SessionController defaultSession = new SessionController(tabPane, "Default");

        root.getChildren().add(tabPane);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add("ui/style.css");
        stage.show();
    }
}
