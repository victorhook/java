package distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public final static String
            LETTER_XI = "\u03be", LETTER_ZETA = "\u03b6",
            LETTER_ETA = "\u03b7", LETTER_LAMBDA = "\u03bb",
            LETTER_MU = "\u03bc", LETTER_PI = "\u03c0",
            LETTER_TAU = "\u03c4", LETTER_PHI = "\u03c6",
            LETTER_SIGMA = "\u03c3";

    public static int factorial(int i) {
        if (i == 0 || i == 1)
            return 1;
        return i * factorial(i-1);
    }

    public static void main(String[] args) {
        var res = new ArrayList<String>();
        res.add("hey");
        res.add("hey2");

        var k = "hey";

        var res2 = res.stream()
                .filter(obj -> obj.equals(k))
                .findFirst().
                        get();

        System.out.println(res2.getClass());

    }

}
