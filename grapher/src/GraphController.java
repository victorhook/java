import distribution.Distribution;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;

public class GraphController {

    @FXML private Canvas canvas;
    @FXML private Slider sliderP, sliderN;
    @FXML private ComboBox<Distribution> distBox;
    @FXML private Label labelP, labelN;

    @FXML Tab graphTab;

    private Distribution distribution;
    private ObservableList<Distribution> distributions;
    private Graph graph;

    public void initialize() {
       /* sliderP.valueProperty().addListener((observable, oldValue, newValue) -> {
            ((BinomialDistribution) distribution).setP((double) newValue);
            labelP.setText(String.format("%.2f", newValue));
            updateUI();
        });

        sliderN.valueProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.intValue();
            ((BinomialDistribution) distribution).setN(value);
            labelN.setText(String.valueOf(value));
            updateUI();
        });

        labelP.setText(String.format("%.2f", sliderP.getValue()));
        labelN.setText(String.valueOf(sliderN.getValue()));
*/


/*        distributions = new DistributionList();
        distBox.setItems(distributions);
        distBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            distribution = newValue;
        });
        distBox.getSelectionModel().select(0);

        graph = new Graph(canvas);
        graph.draw(distribution);*/


    }

    private void updateUI() {
        graph.draw(distribution);
    }

    @FXML
    public void distChanged(Object o) {
        System.out.println(o);
    }

}
