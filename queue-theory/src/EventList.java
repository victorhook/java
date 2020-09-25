import java.util.PriorityQueue;

public class EventList {
    PriorityQueue<Event> list;

    public void insertEvent(int type, double time) {
        list.add(new Event(type, time));
    }

    public Event getEvent() {
        return list.poll();
    }

    public EventList() {
        list = new PriorityQueue<>((o1, o2) -> (int) (o1.time - o2.time));
    }

    public void display() {
        System.out.printf("SIZE: %s\t", list.size());
        list.stream().sorted((o1, o2) -> (int) (o1.time - o2.time))
                .forEach(x -> System.out.printf("%s\t", x));
        System.out.println("");
    }
}