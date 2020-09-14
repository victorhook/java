import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        list.removeIf(n -> n % 2 == 0);
        System.out.println(list);

    }
}

class Kula {
    public boolean equals(Kula k) {
        System.out.println("HEY");
        return true;
    }

    public boolean equals(Object obj) {
        return true;
    }
}


class Person {
    protected String name;
    public static int X;

    public Person(String name) {
        this.name = name;
        Person.X = 20;
    }

    public static void T() {
        System.out.println("T!");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

class Student extends Person {
    private String program;

    public Student(String name, String program) {
        super(name);
        this.program = program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String toString() {
        return name + ",  " + program;
    }
}
