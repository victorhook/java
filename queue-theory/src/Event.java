import java.util.HashMap;
import java.util.Map;

public class Event extends GlobalSimulation {
    public double time;
    public int type;
    public static Map<Integer, String> types;

    public Event(int type, double time) {
        this.time = time;
        this.type = type;
        types = new HashMap<>();
        types.put(ARRIVAL, "Arrival");
        types.put(DEPARTURE, "Departure");
        types.put(MEASURE, "MEASURE");
    }

    public String toString() {
        return String.format("> %.4f %s", time, types.get(type));
    }

}
