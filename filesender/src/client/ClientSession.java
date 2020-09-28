package client;

import global.*;
import server.Callback;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
    This class handles the main file-sending actions.
    This class is spawned when a client has successfully authenticated
    with the server.
 */
public class ClientSession {

    private Socket connection;
    private static int buff_size;
    private ProtocolHandler ph;
    private Callback cbFileSuccess, cbFileErrorMd5, cbFileErrorOther, cbUnknownError;

    public ClientSession(Socket connection, ProtocolHandler ph) throws FileNotFoundException {
        this.connection = connection;
        this.ph = ph;
        buff_size = Integer.parseInt(ConfigHandler.getConfig().getOrDefault("send_buffer", "8192"));

        cbFileSuccess = msg -> System.out.println(msg);
        cbFileErrorMd5 = msg -> System.out.println(msg);
        cbUnknownError = msg -> System.out.println(msg);
    }

    public void start() throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        String destOnHost = "/home/victor/tmp/output.txt";
        String fileDest = "/home/victor/tmp/test.txt";
    }

    private void sendFile(String destOnHost, String localFilePath) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        // Send "Start-of-file" packet, including filesize and destination on other machine
        int fileSize = (int) new File(localFilePath).length();
        ph.sendFilePacket(fileSize, destOnHost);

        // Send the actual data
        String md5sum = sendFileData(localFilePath);

        // Send "End-of-file", including the md5-sum to let host ensure file sent is OK.
        ph.sendPacket(Protocol.CMD_FILE_END, md5sum.getBytes());

        switch(ph.readCommand()) {
            case Protocol.CMD_FILE_SUCCESS:
                cbFileSuccess.emit(String.format("File: '%s' successfully sent to destination: '%s'\n",
                                             localFilePath, destOnHost));
                break;
            case Protocol.CMD_FILE_ERR_MD5:
                cbFileErrorMd5.emit(String.format("MD5-sum of '%s' to '%s' failed.\n",
                                                  localFilePath, destOnHost));
                break;
            default:
                cbUnknownError.emit(String.format("Unknown error occured when sending: '%s' to destination: '%s'\n",
                                                  localFilePath, destOnHost));
        }
    }

    /*
        Sends the given file-data through the out stream and returns the md5sum
        of the file.
     */
    private String sendFileData(String file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        // Get streams and wrap inputstream with "Digest" to compute md5sum.
        MessageDigest md = MessageDigest.getInstance("MD5");
        InputStream in = new FileInputStream(file);
        DigestInputStream dis = new DigestInputStream(in, md);
        DataOutputStream out = ph.getOutputStream();

        int count;
        byte[] buffer = new byte[buff_size];

        while ((count = dis.read(buffer)) > 0) {
            ph.sendFileData(count, buffer);
            System.out.printf("Size: %d", count);
        }

        System.out.printf("Done sending file!\n");
        in.close();

        String md5Sum = Crypter.byteArrayToHex(md.digest());
        return md5Sum;
    }

    public static void main(String[] args) throws IOException {

    }

}
