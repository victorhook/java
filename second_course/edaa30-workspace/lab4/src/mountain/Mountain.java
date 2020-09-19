package mountain;

import fractal.Fractal;
import fractal.TurtleGraphics;

import java.util.HashMap;
import java.util.Map;

public class Mountain extends Fractal {

    private Point p1, p2, p3;
    Map<Side, Point> sides;

    public Mountain(int dev, Point p1, Point p2, Point p3) {
        super();
        this.dev = dev;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        sides = new HashMap<>();
    }

    @Override
    public String getTitle() {
        return "Mountain";
    }

    @Override
    public void draw(TurtleGraphics g) {
        g.penDown();
        fractalLine(g, dev, order, p1, p2, p3);
        sides.clear();  // Clear hashmap to make searching faster
    }

    private void fractalLine(TurtleGraphics turtle, int dev, int order, Point p1, Point p2, Point p3) {
        // Left: p1, Middle: p2, Right: p3
        int p1X = p1.getX(), p1Y = p1.getY();
        int p2X = p2.getX(), p2Y = p2.getY();
        int p3X = p3.getX(), p3Y = p3.getY();

        // Get sides to lookup middle points in hashmap
        Side side1 = new Side(p1, p2), side2 = new Side(p2, p3), side3 = new Side(p1, p3);

        if (order == 0) {
            // Base case in recursion, time to draw!
            turtle.moveTo(p1X, p1Y);
            turtle.forwardTo(p2X, p2Y);
            turtle.forwardTo(p3X, p3Y);
            turtle.forwardTo(p1X, p1Y);
        } else {
            // Calculate y-shift according to given 'dev' value
            int shift = (int) RandomUtilities.randFunc(dev);

            // Retrieve, or compute the middle-points if they do not exist already.
            Point middleLeft = sides.computeIfAbsent(side1, p ->
                                                     new Point(p1X + ((p2X - p1X) / 2),
                                                               p1Y + ((p2Y - p1Y) / 2)+shift));
            Point middleRight = sides.computeIfAbsent(side2, p ->
                                                      new Point(p3X + ((p2X - p3X) / 2),
                                                                p3Y + ((p2Y - p3Y) / 2)+shift));
            Point middleBottom = sides.computeIfAbsent(side3, p ->
                                                       new Point(p1X + ((p3X - p1X) / 2),
                                                                 p1Y + ((p3Y - p1Y) / 2)+shift));

            // Recursively draw lines, halving the y-shift value and decrementing the order
            fractalLine(turtle, dev >> 1, order-1, p1, middleLeft, middleBottom);
            fractalLine(turtle, dev >> 1, order-1, middleLeft, p2, middleRight);
            fractalLine(turtle, dev >> 1, order-1, middleBottom, middleRight, p3);
            fractalLine(turtle, dev >> 1, order-1, middleBottom, middleLeft, middleRight);
        }
    }
}
