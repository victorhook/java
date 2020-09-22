package textproc;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {

		test();

		long t0, t1;
		int runs = 3;
		double times[] = new double[runs], median;

		for (int run = 0; run < runs; run++) {
			t0 = System.nanoTime();
			test();
			t1 = System.nanoTime();
			times[run] = (t1-t0) / (double) 10e6;
		}

		median = times[runs / 2];
		double average = Arrays.stream(times).average().orElse(Double.NaN);

		Arrays.stream(times).forEach(time -> System.out.printf("Run: %.4f ms\n", time));
		System.out.printf("Averiage exectuion time: %.4f ms\n", average);
		System.out.printf("Median exectuion time: %.4f ms\n", median);
	}

	static void test() throws FileNotFoundException {

		Scanner reader = new Scanner(new File("nilsholg.txt"));
		reader.findWithinHorizon("\uFEFF", 1);
		reader.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> generalWords = new HashSet<>();
		while (scan.hasNext()) {
			generalWords.add(scan.next());
		}

		ArrayList<TextProcessor> textProcessors = new ArrayList<>();
		textProcessors.add(new MultiWordCounter(REGIONS));
		textProcessors.add(new GeneralWordCounter(generalWords));

		while (reader.hasNext()) {
			String word = reader.next().toLowerCase();
			textProcessors.forEach(proc -> proc.process(word));
		}
		reader.close();

		textProcessors.forEach(proc -> proc.report());
	}
}