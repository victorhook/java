import com.sun.tools.javac.Main;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.IntStream;

public class MainSimulation extends GlobalSimulation {

    private EventList eventList;
    private Butler butler;
    private EventHandler handler;
    private AtomicBoolean running;
    private Function callback;

    MainSimulation(Function callback) {
        this.running = new AtomicBoolean(false);
        this.callback = callback;
        reset();
    }

    public void startSimulation() {
        running.set(true);
        new Thread(this::run).start();
    }

    public void stopSimulation() {
        running.set(false);
    }

    public void reset() {
        eventList = new EventList();
        butler = new Butler();
        handler = new EventHandler(eventList, butler);
    }

    public void run() {
        eventList.insertEvent(ARRIVAL, 1);
        eventList.insertEvent(MEASURE, 5);

        while (running.get() && time < 5000) {
            next();
        }

        double average = handler.customers / (double) handler.measurements;
        System.out.printf("Measurements: %d\n", handler.measurements);
        System.out.printf("Customer in queue: %.4f\n", average);
    }

    public int getNoInQueue() {
        return handler.noInQueue;
    }
    public int getNoServerd() {
        return butler.served;
    }
    public int getNoServing() {
        return butler.serving;
    }
    public double getTime() {
        return time;
    }

    public void next() {
        Event nextEvent = eventList.getEvent();
        time = nextEvent.time;
        handler.handleEvent(nextEvent);
        ui.updateIO();
    }
}
