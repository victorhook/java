package global;

import java.nio.ByteBuffer;

public class FilePacket {

    public int fileSize;
    public short destSize;
    public String dest;

    public FilePacket(Packet packet) {
        ByteBuffer payload = ByteBuffer.wrap(packet.data);
        fileSize = payload.getInt();
        destSize = payload.getShort();
        byte[] destBuf = new byte[destSize];
        payload.get(destBuf, 0, destSize);
        dest = new String(destBuf);
    }

    public String toString() {
        return String.format("Size: %s, dstSize:  %s, dest: %s\n", fileSize, destSize, dest);
    }

}
