import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.IntStream;

public class EventHandler extends GlobalSimulation {

    private EventList eventList;
    private Butler butler;

    private static Random rand;
    public int noInQueue, measurements, customers;

    public EventHandler(EventList list, Butler butler) {
        this.butler = butler;
        eventList = list;
        rand = new Random();
        noInQueue = measurements = customers = 0;
    }

   public double delay(double mean) {
       return 2 * mean * rand.nextDouble();
   }

    public void handleEvent(Event event) {
        switch (event.type) {
            case ARRIVAL:
                arrival();
                break;
            case DEPARTURE:
                departure();
                break;
            case MEASURE:
                measure();
                break;
        }
    }

   private void insertEvent(int type, double time) {
       eventList.insertEvent(type, time);
   }

    private void arrival() {
        if (noInQueue == 0)
            insertEvent(DEPARTURE, time + delay(1));
        insertEvent(ARRIVAL, time + delay(2));
        noInQueue++;
    }

    private void departure() {
        noInQueue--;
        if (noInQueue != 0) {
            double serveTime = time + delay(1);
            butler.serve(serveTime);
            insertEvent(DEPARTURE, serveTime);
        }
    }

    private void measure() {
        customers += noInQueue;
        System.out.printf("Number in queue: %s\n", noInQueue);
        measurements++;
        insertEvent(MEASURE, time + delay(5));
    }

}
