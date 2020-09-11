import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class main {

    public static void main(String[] args) {

        ArrayList<One> arr = new ArrayList<One>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            arr.add(new One(rand.nextInt(20)));
        }

        System.out.println(new One(1) == new One(1));
        System.out.println(new One(1) == new One(1));

    }

}

class One implements Comparable<One>{
    int id = 2;

    One(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(One t) {
        if (t.id < this.id) {
            return -1;
        } else if (t.id > this.id) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            return ((One) o).id == this.id;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return Integer.toString(this.id);
    }
}

