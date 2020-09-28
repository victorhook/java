package global;

import java.io.FileNotFoundException;

public class ClientLogger extends FileSenderLogger {

    private final static String DEFAULT_LOG_FILE = "/var/log/filesender/client.log";

    @Override
    String getLogFile() {
        try {
            return ConfigHandler.getConfig().get("client_log");
        } catch (FileNotFoundException e) {
            return DEFAULT_LOG_FILE;
        }
    }



}
