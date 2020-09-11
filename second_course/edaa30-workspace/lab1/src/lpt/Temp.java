package lpt;

import java.util.*;

public class Temp {

    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<>();
        m.put("albatross", 12);
        m.put("pelikan", 27);
        m.put("lunnefågel", 19);
        m.put("albatross", 7);
        System.out.println(m.get("albatross"));
    }

}
