package ui;

import distribution.BinomialDistribution;
import distribution.DistributionList;
import distribution.Utils;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UiBinomial extends TabContent {

    private int n;
    private double p;
    private Label labelN, labelP;
    private Slider sliderN, sliderP;
    private BinomialDistribution distribution;
    private final String IMAGE_NAME = "binomial_distribution.png";

    public UiBinomial(Function distroChangedCallback) {
        super(distroChangedCallback);

        distribution = new BinomialDistribution();
        name = distribution.getName();
        width = canvas.getWidth();
        height = canvas.getHeight();

        labelP = new Label("asd");
        labelN = new Label("asd");
        sliderP = new Slider(0, 1, .5);
        sliderN = new Slider(0, 30, 10);

        labelP.setMaxWidth(Double.MAX_VALUE);
        labelN.setMaxWidth(Double.MAX_VALUE);
        sliderP.setMaxWidth(Double.MAX_VALUE);
        sliderN.setMaxWidth(Double.MAX_VALUE);

        Label p = new Label("Probability");
        p.setMaxWidth(Double.MAX_VALUE);
        Label n = new Label("N");
        n.setMaxWidth(Double.MAX_VALUE);

        params.add(p, 0, 2);
        params.add(labelP, 1, 2);
        params.add(sliderP, 2, 2);
        params.add(n, 0, 3);
        params.add(labelN, 1, 3);
        params.add(sliderN, 2, 3);

        ColumnConstraints col1 = new ColumnConstraints(), col2 = new ColumnConstraints(), col3 = new ColumnConstraints();
        col1.setPercentWidth(40);
        col2.setPercentWidth(10);
        col3.setPercentWidth(50);
        params.getColumnConstraints().addAll(col1, col2, col3);

       sliderP.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.p = (double) newValue;
            updateUI();
        });

        sliderN.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.n = newValue.intValue();
            updateUI();
        });

        symbolX = "x";
        symbolY = String.format("P(%s=x)", Utils.LETTER_XI);
        this.n = (int) sliderN.getValue();
        this.p = sliderP.getValue();
        gc.setTextAlign(TextAlignment.CENTER);

        updateUI();
    }

    public void updateUI() {
        distribution.setP(this.p);
        distribution.setN(this.n);
        labelP.setText(String.format("%.2f", this.p));
        labelN.setText(String.valueOf(this.n));
        drawFrame();
        draw();
    }

    public void draw() {
        gc.setLineWidth(2);
        gc.setFill(Paint.valueOf("blue"));

        List<Double> probs = IntStream.range(0, n)
                                .mapToDouble(x -> distribution.getProbability(x))
                                .boxed()
                                .collect(Collectors.toList());

        double x = OFFSET + POINT_RADIUS, y;
        double stepX = lenX / n;
        double yStart = height - OFFSET - POINT_RADIUS;

        for (int i = 0; i < n; i++) {
            y = yStart - (probs.get(i) * lenY);
            gc.fillOval(x, y, POINT_SIZE, POINT_SIZE);
            x += stepX;
        }

        double textX = n / (double) STEPS_PER_AXIS;
        double xAxis = 0;
        stepX = lenX / STEPS_PER_AXIS;
        x = OFFSET;
        y = height - TEXT_OFFSET;
        for (int i = 1; i < STEPS_PER_AXIS; i++) {
            x += stepX;
            xAxis += textX;
            if (textX % 1 == 0) {
                gc.strokeText(String.format("%d", (int) xAxis), x, y);
            } else {
                gc.strokeText(String.format("%.1f", xAxis), x, y);
            }
        }

        stepX = lenY / STEPS_PER_AXIS;
        y = height - OFFSET;
        x = TEXT_OFFSET;
        double textY = 0;

        double maxY = distribution.getMaxY();
        double yAxis = maxY / STEPS_PER_AXIS;

        for (int i = 1; i < STEPS_PER_AXIS; i++) {
            y -= stepX;
            textY += yAxis;
            if (textY % 1 == 0) {
                gc.strokeText(String.format("%d", (int) textY), x, y);
            } else {
                gc.strokeText(String.format("%.2f", textY), x, y);
            }
        }



    }

    public static int a(int b) {
        return b;
    }

    @Override
    public void initLabels() {
        title = new Label("Binomial");
        labelMeansFormula = new Label(String.format("E(%s) = np", Utils.LETTER_XI));
        labelVarianceFormula = new Label(String.format("V(%s) = np(1-p)", Utils.LETTER_XI));
        labelNormDivFormula = new Label(String.format("%s = sqrt(np)", Utils.LETTER_SIGMA));
        Image img = new Image(LOGO_PATH + IMAGE_NAME);
        ImageView view = new ImageView(img);
        view.setFitWidth(0xff);
        view.setPreserveRatio(true);
        labelFormula = new Label();
        labelFormula.setGraphic(view);
    }

    @Override
    public void drawFrame() {
        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.BLACK);
        gc.strokeLine(OFFSET, height-OFFSET, OFFSET, OFFSET);
        gc.strokeLine(OFFSET, OFFSET, OFFSET-3, OFFSET+3);
        gc.strokeLine(OFFSET, OFFSET, OFFSET+3, OFFSET+3);
        gc.strokeText(symbolY, OFFSET, TEXT_OFFSET);

        gc.strokeLine(OFFSET, height-OFFSET, width-OFFSET, height-OFFSET);
        gc.strokeLine(width-OFFSET, height-OFFSET, width-OFFSET-3, height-OFFSET-3);
        gc.strokeLine(width-OFFSET, height-OFFSET, width-OFFSET-3, height-OFFSET+3);
        gc.strokeText(symbolX, width-OFFSET, height-OFFSET+20);
    }
}
