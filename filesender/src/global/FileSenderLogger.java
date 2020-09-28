package global;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public abstract class FileSenderLogger {

    private Logger logger;
    private String logFile;
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-mm-dd HH:mm:ss");

    public FileSenderLogger() {
        try {
            logger = Logger.getGlobal();

            // Disable normal stdout logging
            logger.setUseParentHandlers(false);
            logFile = getLogFile();
            ensureFileExist(logFile);

            FileHandler fl = new FileHandler(logFile);
            fl.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord logRecord) {
                    String date = LocalDateTime.now().format(DATE_FORMAT);
                    return String.format("%s | %s\n", date, logRecord.getMessage());
                }
            });
            logger.addHandler(fl);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("FAILED TO START LOGGERS: %s\n", e.getMessage());
        }
    }

    private void ensureFileExist(String logFile) throws IOException {
        File file = new File(logFile);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    abstract String getLogFile();

    protected void log(String msg) {
        logger.info(msg);
    }

}
