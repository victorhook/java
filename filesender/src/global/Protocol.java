package global;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Protocol {

    public static int HEADER_SIZE = 6;

    public final static byte
            CMD_INIT = 1,
            CMD_INIT_OK = 2,
            CMD_AUTH = 3,
            CMD_AUTH_OK = 4,
            CMD_AUTH_PASS_REQUIRED = 5,
            CMD_AUTH_PASS = 6,
            CMD_FILE = 7,
            CMD_ACK = 8,
            CMD_NACK = 9,
            CMD_END = 10,
            CMD_END_OK = 11;

    public static byte CURRENT_VERSION;

    static {
        try {
            CURRENT_VERSION = Byte.valueOf(ConfigHandler.getConfig().get("version"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static byte[] packet(byte command, byte[] data) {
        int size = data != null ? data.length : 0;
        ByteBuffer packet = ByteBuffer.allocate(HEADER_SIZE + size);
        packet.put(CURRENT_VERSION);
        packet.put(command);
        packet.putInt(size);
        if (data != null)
            packet.put(data);
        return packet.array();
    }

    public static void main(String[] args) {
        byte[] a = new byte[] {};
        byte[] p = packet((byte) 2, a);
        System.out.println(Arrays.toString(p));
    }

}
