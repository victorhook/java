package ui;

import distribution.Distribution;
import distribution.DistributionList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class TabContent extends VBox {

    protected final int STEPS_PER_AXIS = 10, OFFSET = 50, TEXT_OFFSET = 30,
                               POINT_SIZE = 10, POINT_RADIUS = POINT_SIZE / 2;
    protected final double GRAPH_WIDTH = 500, GRAPH_HEIGHT = 600, PARAM_WIDTH = 700, PARAM_HEIGHT = 600;
    protected double height, width;
    protected double lenX = GRAPH_WIDTH - 2 * OFFSET, lenY = GRAPH_HEIGHT - 2 * OFFSET;
    protected String symbolX, symbolY;
    protected String name;
    protected GraphicsContext gc;

    protected static final String LOGO_PATH = "ui/images/";

    protected ComboBox<String> distributionComboBox;
    protected Canvas canvas;
    protected GridPane params, stats;
    protected Label labelMeansFormula, labelVarianceFormula, labelNormDivFormula,
                    labelFormula, title;

    Function callback;

    public TabContent(Function callback) {
        this.callback = callback;

        initLabels();
        HBox root = buildRoot();
        Pane canvasFrame = buildCanvas();
        VBox paramFrame = buildParams();
        root.getChildren().addAll(canvasFrame, paramFrame);

        StackPane titlePane = buildTitle();
        HBox settingsTab = buildSettings();

        this.getChildren().addAll(titlePane, root, settingsTab);
    }

    private HBox buildSettings() {
        HBox frame = new HBox();
        frame.setPadding(new Insets(20, 0, 0, 20));

        Button screenshot = new Button("Screenshot");
        Button exportData = new Button("Export data");
        Button importData = new Button("Import data");

        ImageView logoScreenShot = new ImageView(new Image(LOGO_PATH + "screenshot.png"));
        ImageView logoExportData = new ImageView(new Image(LOGO_PATH + "export.png"));
        ImageView logoImportData = new ImageView(new Image(LOGO_PATH + "import.png"));

        for (ImageView image: new ImageView[] {logoScreenShot, logoExportData, logoImportData}) {
            image.setFitWidth(32);
            image.setPreserveRatio(true);
        }
        screenshot.setGraphic(logoScreenShot);
        exportData.setGraphic(logoExportData);
        importData.setGraphic(logoImportData);

        frame.getChildren().addAll(screenshot, importData, exportData);
        return frame;
    }

    private HBox buildRoot() {
        HBox root = new HBox();
        root.setPadding(new Insets(0, 30, 0, 30));
        return root;
    }

    private StackPane buildTitle() {
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(Pos.TOP_CENTER);
        titlePane.setPadding(new Insets(50, 0, 50, 0));
        titlePane.getChildren().add(title);
        title.setId("title");
        return titlePane;
    }

    private Pane buildCanvas() {
        Pane canvasFrame = new Pane();
        canvasFrame.setId("graph");
        canvas = new Canvas(GRAPH_WIDTH, GRAPH_HEIGHT);
        canvasFrame.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        return canvasFrame;
    }

    private VBox buildParams() {
        VBox paramFrame = new VBox();
        paramFrame.setPrefSize(PARAM_WIDTH, PARAM_HEIGHT);
        paramFrame.setId("distroInfo");
        paramFrame.setPadding(new Insets(0, 30, 0, 30));
        params = new GridPane();
        stats = new GridPane();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(50));

        params.setVgap(20);
        params.setHgap(10);
        stats.setVgap(20);
        stats.setHgap(10);
        grid.setId("distroTitle");
        paramFrame.getChildren().addAll(grid, stats, params);

        Label distTitle = new Label("Distribution");
        distTitle.setMaxWidth(Double.MAX_VALUE);
        distributionComboBox = new ComboBox<>();
        distributionComboBox.setMaxWidth(Double.MAX_VALUE);

        grid.add(distTitle, 0, 0, 3, 1);
        grid.add(distributionComboBox, 0, 1, 3, 1);

        stats.add(new Label("Mean"), 0, 1);
        stats.add(new Label("Variance"), 0, 2);
        stats.add(new Label("Normal deviation"), 0, 3);
        stats.add(labelMeansFormula, 1, 1, 2, 1);
        stats.add(labelVarianceFormula, 1, 2, 2, 1);
        stats.add(labelNormDivFormula, 1, 3, 2, 1);

        stats.add(new Label("Formula"), 0, 4);
        stats.add(labelFormula, 1, 4, 2, 1);

        ColumnConstraints col1 = new ColumnConstraints(), col2 = new ColumnConstraints(), col3 = new ColumnConstraints();
        col1.setPercentWidth(40);
        col2.setPercentWidth(20);
        col3.setPercentWidth(40);
        stats.getColumnConstraints().addAll(col1, col2, col3);

        return paramFrame;
    }

    public void setComboBox(TabContentList tabContentList) {
        distributionComboBox.setItems(tabContentList);
        distributionComboBox.getSelectionModel().select(this.toString());
        distributionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            callback.apply(newValue.toString());
        });
    }

    public void comboBoxSelect() {
        distributionComboBox.getSelectionModel().select(this.toString());
    }

    public Node getContent() { return this; }
    public String toString() { return this.name; }

    public abstract void initLabels();
    public abstract void drawFrame();
}
