package distribution;

import javafx.scene.canvas.GraphicsContext;

public interface Distribution {

    int POINT_SIZE = 10;
    int POINT_RADIUS = POINT_SIZE / 2;

    double getExpectedValue();

    double getExpectedVariance();

    double getExpectedMean();

    double getMaxY();

    //void draw(GraphicsContext g, double stepSize, double startX, double startY);

    public void selected();

    String getName();

}
