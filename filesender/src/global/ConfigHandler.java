package global;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigHandler {

    public static final String CONFIG_PATH = System.getProperty("user.home") + "/.config/filesender/",
                               CONF_FILE = CONFIG_PATH + "settings.conf";

    public static Map<String, String> getConfig() throws FileNotFoundException {

        File confDir = new File(CONFIG_PATH);
        if (!confDir.isDirectory()) {
            createNewConfig();
        }

        Map<String, String> config = new HashMap<>();
        String[] confData = new Scanner(new File(CONF_FILE)).useDelimiter("\\Z").next().split("\n");
        for (String line: confData) {
            String[] keywords = line.split("=");
            config.put(keywords[0].trim(), keywords[1].trim());
        }

        return config;
    }

    public static void createNewConfig() {}

    public static void main(String[] args) throws Exception {
        ConfigHandler.getConfig();
    }

}
