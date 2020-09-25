import java.util.ArrayList;
import java.util.List;

public class Butler extends GlobalSimulation {

    public int serving, served;
    public List<Double> times;

    public Butler() {
        times = new ArrayList<>();
    }

    public void serve(double time) {
        times.add(time);
    }

    public void update() {
        times.removeIf(t -> t < time);
    }

    public void display() {
        System.out.printf("Serving: %d\n", times.size());
    }

}
