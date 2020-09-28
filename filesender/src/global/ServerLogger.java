package global;

import java.io.FileNotFoundException;

public class ServerLogger extends FileSenderLogger {

    private final static String DEFAULT_LOG_FILE = "/var/log/filesender/server.log";

    @Override
    String getLogFile() {
        try {
            return ConfigHandler.getConfig().get("server_log");
        } catch (FileNotFoundException e) {
            return DEFAULT_LOG_FILE;
        }
    }
}
