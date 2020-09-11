package textproc;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {

		long t0, t1;
		int runs = 20;
		double times[] = new double[runs];

		for (int run = 0; run < runs; run++) {
			t0 = System.nanoTime();
			test();
			t1 = System.nanoTime();
			times[run] = (t1-t0) / (double) 10e6;
		}

		double averageTime = 0;
		for (int run = 0; run < runs; run++) {
			double time = times[run];
			averageTime += time;
			System.out.printf("[Run %s] Execution time: %s ms\n", run, time);
		}

		System.out.printf("Averiage exectuion time: %s ms\n", averageTime / runs);

		//System.out.printf("\nExecution time: %s ms\n", (t1-t0) / (double) 10e6);

	}

	static void test() throws FileNotFoundException {

		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> generalWords = new HashSet<>();
		while (scan.hasNext()) {
			generalWords.add(scan.next());
		}

		ArrayList<TextProcessor> textProcessors = new ArrayList<>();
		MultiWordCounter regionCounter = new MultiWordCounter(REGIONS);
		GeneralWordCounter generalWordCounter = new GeneralWordCounter(generalWords);

		textProcessors.add(generalWordCounter);
		textProcessors.add(regionCounter);

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			for (TextProcessor processor: textProcessors) {
				processor.process(word);
			}
		}

		s.close();
		for (TextProcessor processor: textProcessors) {
			processor.report();
		}


	}


}