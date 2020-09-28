package server;

import global.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/*
    This class handles the main file-sending actions.
    This class is spawned when a client has successfully authenticated
    with the server.
 */
public class ServerSession {

    private static int buff_size;
    private Socket connection;
    private ProtocolHandler ph;
    private Callback cbFileSuccess, cbFileErrorMd5;

    public ServerSession(Socket connection, ProtocolHandler ph) throws FileNotFoundException {
        this.connection = connection;
        this.ph = ph;
        buff_size = Integer.parseInt(ConfigHandler.getConfig().getOrDefault("read_buffer", "8192"));
        cbFileSuccess = msg -> System.out.print(msg);
        cbFileErrorMd5 = msg -> System.out.print(msg);
    }

    public void start() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // Start of new file
        FilePacket packet = ph.readFilePacket();
        // Read the data of the file and write it to the chosen destination
        readAndWriteFile(packet.fileSize, packet.dest);
        // Read "EOF-packet" which includes md5-sum and ensure that the file was successfully saved.
        Packet fileEndPacket = ph.readPacket();

        if (fileEndPacket.command == Protocol.CMD_FILE_END) {
            if (!ph.md5SumIsOk(packet.dest, fileEndPacket.data)) {
                cbFileSuccess.emit(String.format("File successfully saved to %s\n", packet.dest));
            } else {
                cbFileErrorMd5.emit(String.format("Wrong md5sum for file, got %s, expected: %s\n",
                                                  packet.dest, new String(fileEndPacket.data)));
            }
        }
    }

    private void readAndWriteFile(int size, String outDst) throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        FileOutputStream out = new FileOutputStream(new File(outDst));
        int read = 0;
        Packet packet;

        while (read < size) {
            packet = ph.readPacket();
            out.write(packet.data);
            read += packet.size + packet.HEADER_SIZE;
        }
        out.close();
    }

}
