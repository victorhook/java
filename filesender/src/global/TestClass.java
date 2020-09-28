package global;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TestClass {

    static Random rand = new Random();
    static FileHandler fileHandler;

    public TestClass(int i) throws InterruptedException, IOException {
        Thread.sleep(rand.nextInt(5) * 1000);
        Logger logger = Logger.getGlobal();
        logger.info(String.format("Thread %s\n", i));

    }

    public static void main(String[] args) throws IOException {

        ServerLogger logger = new ServerLogger();
        logger.log("hey test");

        /*Logger global = Logger.getGlobal();
        FileHandler fl = new FileHandler("log.log");
        fl.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord logRecord) {
                return String.format("new -> %s\n", logRecord.getMessage());
            }
        });
        global.addHandler(fl);
        global.info("test");

        for (int i = 0; i < 20; i++) {
            makeThread(i).start();
        }*/
    }

    public static Thread makeThread(int i) {
        return new Thread(() -> {
            try {
                TestClass t = new TestClass(i);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

}
