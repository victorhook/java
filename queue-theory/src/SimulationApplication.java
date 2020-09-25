import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimulationApplication extends Application {

    static Label noInQueue, noServing, noServed, time;
    static Queue queue;
    static MainSimulation simulator;
    static final double CANVAS_WIDTH = 1024, CANVAS_HEIGHT = 600;

    @Override
    public void start(Stage stage) throws Exception {

        // Build frame UI
        StackPane root = new StackPane();
        VBox frame = new VBox();
        frame.setPadding(new Insets(50, 0, 50, 50));

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        Queue queue = new Queue(100, 400, 500, 300, canvas.getGraphicsContext2D());
        Cashier cashier = new Cashier(550, 200, 300, 300, canvas.getGraphicsContext2D());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(5);
        createLabels(gridPane);

        // Create simulater
        simulator = new MainSimulation(x -> updateUI());

        // Create buttons & apply callbacks
        createButtons(gridPane, simulator);


        frame.getChildren().addAll(canvas, gridPane);
        root.getChildren().add(frame);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Queue simulator");
        stage.show();
    }

    void updateUI() {
        noInQueue.setText(String.valueOf(queue.noInQueue));
    }

    private static void createLabels(GridPane gridPane) {
        Label labelNoInQueue = new Label("Number in queue:");
        Label labelNoServing = new Label("Customers being served:");
        Label labelNoServed = new Label("Customers served:");
        Label labelTime = new Label("Time:");
        gridPane.add(labelNoInQueue, 0, 0);
        gridPane.add(labelNoServing, 0, 1);
        gridPane.add(labelNoServed, 0, 2);
        gridPane.add(labelTime, 0, 3);

        Label noInQueue = new Label("Number in queue:");
        Label noServing = new Label("Customers being served:");
        Label noServed = new Label("Customers served:");
        Label time = new Label("Time:");

        gridPane.add(noInQueue, 1, 0);
        gridPane.add(noServing, 1, 1);
        gridPane.add(noServed, 1, 2);
        gridPane.add(time, 1, 3);
    }

    private static void createButtons(GridPane gridPane, MainSimulation simulator) {
        Button start = new Button("Simulate");
        Button stop = new Button("Pause");
        Button reset = new Button("Reset");
        Button next = new Button("Next");

        gridPane.add(start, 3, 0, 2, 1);
        gridPane.add(stop, 3, 1, 2, 1);
        gridPane.add(reset, 3, 2, 2, 1);
        gridPane.add(next, 3, 3, 2, 1);
        Stream.of(start, stop, reset, next).forEach(b -> b.setMaxWidth(Double.MAX_VALUE));

        // Apply callbacks
        next.setOnAction(e -> simulator.next());
        start.setOnAction(e -> simulator.startSimulation());
        stop.setOnAction(e -> simulator.stopSimulation());
        reset.setOnAction(e -> simulator.reset());
    }

    static class Cashier {
        GraphicsContext gc;
        int noServing, noServed;
        double width, height, custY, x, y;
        static double custSpeed = 1, custRadius = 20;

        Cashier (int x, int y, int width, int height, GraphicsContext gc) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.custY = (height - y) / 2;
            this.gc = gc;
            drawFrame();
        }

        public void addServing() { noServing++; }
        public void removeServed() { noServing--; noServed++; }

        private void drawFrame() {
            gc.strokeOval(x, y, width, height);
        }

        void update() {
            IntStream.range(1, noServing + 1)
                    .forEach(x -> drawCustomer(width - x*custRadius, custY));
        }

        void drawCustomer(double x, double y) {
            System.out.printf("%f, %f\n", x, y);
            gc.fillOval(x, y, custRadius, custRadius);
        }

    }


    static class Queue {
        GraphicsContext gc;
        int noInQueue;
        double width, height, custY, x, y;
        static double custSpeed = 1, custRadius = 20;

        Queue(int x, int y, int width, int height, GraphicsContext gc) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.custY = (height - y) / 2;
            this.gc = gc;
            drawFrame();
        }

        public void addToQueue() { noInQueue++; }
        public void removeFromQueue() { noInQueue--; }

        private void drawFrame() {
            gc.beginPath();
            gc.moveTo(x, y);
            gc.lineTo(width, y);
            gc.lineTo(width, height);
            gc.lineTo(x, height);
            gc.stroke();
            gc.closePath();
        }

        void update() {
            IntStream.range(1, noInQueue + 1)
                    .forEach(x -> drawCustomer(width - x*custRadius, custY));
        }

        void drawCustomer(double x, double y) {
            System.out.printf("%f, %f\n", x, y);
            gc.fillOval(x, y, custRadius, custRadius);
        }

    }


}
