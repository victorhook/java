package textproc;

import javafx.application.Application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BookReaderApplication {

    public static void main(String[] args) throws FileNotFoundException {

        Set<String> stopWords = getStopWords();
        Scanner stream = getTextStream();

        GeneralWordCounter wordCounter = new GeneralWordCounter(stopWords);
        while (stream.hasNext()) {
            wordCounter.process(stream.next().toLowerCase());
        }

        BookReaderController controller = new BookReaderController(wordCounter);


    }

    private static Scanner getTextStream() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("nilsholg.txt"));
        scan.findWithinHorizon("\uFEFF", 1);
        scan.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
        return scan;
    }

    private static Set<String> getStopWords() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("undantagsord.txt"));
        Set<String> set = new HashSet<>();
        while (scan.hasNext()) {
            set.add(scan.next());
        }
        return set;
    }

}
