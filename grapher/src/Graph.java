import distribution.BinomialDistribution;
import distribution.Distribution;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Graph {

    private static final int STEPS_PER_AXIS = 10;

    private GraphicsContext gc;
    private double width, height, stepX, stepY, startX, startY;
    private double minX, minY, maxX, maxY;
    private double resX, resY;
    private double lenX, lenY;

    private final double OFFSET = 30.0, TEXT_OFFSET = 15.0;

    private int steps;

    private boolean centerAlign = false;

    private String symbolX, symbolY;

    public String getSymbolX() { return symbolX; }
    public String getSymbolY() { return symbolY; }
    public void setSymbolX(String symbolX) { this.symbolX = symbolX; }
    public void setSymbolY(String symbolY) { this.symbolY = symbolY; }
    public double getOffset() { return OFFSET; }
    public double getLenX() { return lenX; }
    public double getLenY() { return lenY; }
    public void setLenX(double symbolX) { this.lenX = lenX; }
    public void setLenY(double symbolY) { this.lenY = lenY; }


    public Graph(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        this.height = canvas.getHeight();
        this.width = canvas.getWidth();

        this.lenX = width - 2*OFFSET;
        this.lenY = height - 2*OFFSET;

        this.symbolX = "x";
        this.symbolY = "P(X=x)";

        gc.setTextAlign(TextAlignment.CENTER);

        minX = 0;
        minY = 0;
        maxX = 10;
        maxY = .4;

        steps = 10;
    }

    public void draw(Distribution distribution) {

        /*Point center = new Point(minX + ((maxX - minX) / 2),
                                 minY + ((maxY - minY) / 2));*/

        BinomialDistribution bin = (BinomialDistribution) distribution;

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.BLACK);
        gc.strokeLine(OFFSET, height-OFFSET, OFFSET, OFFSET);
        gc.strokeLine(OFFSET, OFFSET, OFFSET-3, OFFSET+3);
        gc.strokeLine(OFFSET, OFFSET, OFFSET+3, OFFSET+3);
        gc.strokeText(symbolY, OFFSET-TEXT_OFFSET, OFFSET);

        gc.strokeLine(OFFSET, height-OFFSET, width-OFFSET, height-OFFSET);
        gc.strokeLine(width-OFFSET, height-OFFSET, width-OFFSET-3, height-OFFSET-3);
        gc.strokeLine(width-OFFSET, height-OFFSET, width-OFFSET-3, height-OFFSET+3);
        gc.strokeText(symbolX, width-OFFSET, height-OFFSET+20);

        bin.draw(gc, lenX, lenY, OFFSET, height-OFFSET, maxY);

/*



        double stepSizeX = lenX / STEPS_PER_AXIS;
        double stepSizeY = lenY / steps;
        double x = 0, y = 0;
        double xVal = minX, yVal = minY;
        double xStep = maxX / steps, yStep = maxY / steps;

        for (int j = 1; j < STEPS_PER_AXIS; j++) {
            x = OFFSET+stepSizeX*j;
            y = height-OFFSET;
            gc.strokeLine(x, y+3, x, y-3);
            xVal += xStep;
            if (xVal % 1 == 0) {
                gc.strokeText(String.valueOf((int) xVal), x, y+TEXT_OFFSET);
            } else
                gc.strokeText(String.format("%.1f", xVal), x, y+TEXT_OFFSET);
        }

        for (int j = 1; j < STEPS_PER_AXIS; j++) {
            x = OFFSET;
            y = height - OFFSET - stepSizeY*j;
            gc.strokeLine(x-3, y, x+3, y);

            yVal += yStep;
            if (yVal % 1 == 0) {
                gc.strokeText(String.valueOf((int) yVal),x-TEXT_OFFSET, y);
            } else
                gc.strokeText(String.format("%.1f", yVal),x-TEXT_OFFSET, y);
        }

*/

    }

    private class Point {
        private double x,y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
