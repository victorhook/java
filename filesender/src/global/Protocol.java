package global;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Protocol {

    public static int HEADER_SIZE = 6,
                      PAYLOAD_HEADER_SIZE = 6,
                      LOGIN_ATTEMPTS_LIMIT = 3;

    public final static byte
            CMD_INIT = 1,
            CMD_INIT_OK = 2,
            CMD_INIT_OK_ACK = 12,
            CMD_AUTH = 3,
            CMD_AUTH_OK = 4,
            CMD_AUTH_PASS_REQUIRED = 5,
            CMD_AUTH_PASS = 6,
            CMD_AUTH_BAD_LOGIN = 12,
            CMD_FILE_START = 7,
            CMD_FILE_DATA = 14,
            CMD_FILE_END = 13,
            CMD_FILE_SUCCESS = 15,
            CMD_FILE_ERR_MD5 = 16,
            CMD_FILE_ERR_OTHER = 17,
            CMD_ACK = 8,
            CMD_NACK = 9,
            CMD_END = 10,
            CMD_END_OK = 11;

/*    public final static Map<Integer, String> CMD = new HashMap<>() {{
        CMD.put(1, "CMD_INIT");
        CMD.put(2, "CMD_INIT_OK");
        CMD.put(3, "CMD_AUTH");
        CMD.put(4, "CMD_AUTH_OK");
        CMD.put(5, "CMD_AUTH_PASS_REQUIRED");
        CMD.put(6, "CMD_AUTH_PASS");
        CMD.put(7, "CMD_FILE");
        CMD.put(8, "CMD_ACK");
        CMD.put(9, "CMD_NACK");
        CMD.put(10, "CMD_END");
        CMD.put(11, "CMD_END_OK");
    }};*/

    public static byte CURRENT_VERSION;

    public static void initProtocol(Map<String, String> config) {
        CURRENT_VERSION = Byte.valueOf(config.get("version"));
    }

}
