package global;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Packet {
    public final int HEADER_SIZE = Protocol.HEADER_SIZE;
    public int version, command, size;
    public byte[] data;

    public Packet(int version, int command, int size) {
        this.version = version;
        this.command = command;
        this.size = size;
        this.data = new byte[size];
    }

    public Packet(int version, int command, byte[] data) {
        this.version = version;
        this.command = command;
        this.size = data != null ? data.length : 0;
        this.data = data;
    }

    public byte[] bytes() {
        ByteBuffer packet = ByteBuffer.allocate(HEADER_SIZE + size);
        packet.put((byte) version);
        packet.put((byte) command);
        packet.putInt(size);
        if (data != null)
            packet.put(data);
        return packet.array();
    }

    public String toString() {
        return String.format("[ CMD: %s ]  Size: %s  Data: %s\n",
                            command, size, Arrays.toString(data));
    }

}
