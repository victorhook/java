package global;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class ProtocolHandler extends Protocol {

    private int headerSize;
    private byte version;


    private BufferedInputStream in;
    private DataOutputStream out;
    private PublicKey myPubKey, theirPubKey;
    private PrivateKey myPrivKey, theirPrivKey;
    private boolean encryptData;
    Crypter crypter;

    public BufferedInputStream getInputStream() {
        return in;
    }
    public DataOutputStream getOutputStream() {
        return out;
    }

    public ProtocolHandler(BufferedInputStream in, DataOutputStream out, KeyPair keyPair) throws FileNotFoundException, NoSuchAlgorithmException {
        this.in = in;
        this.out = out;
        myPubKey = keyPair.getPublic();
        myPrivKey = keyPair.getPrivate();
        headerSize = Protocol.HEADER_SIZE;
        version = Protocol.CURRENT_VERSION;

        // We can't encrypt the data until the handshake is complete.
        encryptData = false;

        // Create a new crypter that we'll use for encrypting all packages
        crypter = new Crypter(keyPair);
    }

    public PublicKey getMyPubKey() {
        return myPubKey;
    }

    // Initializes the communication (should be called first from CLIENT side)
    // During this init, the keys are exchanged between the hosts.
    public void doInit() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        // Send first init request with our public key as data.
        sendPacket(Protocol.CMD_INIT, getMyPubKey().getEncoded());

        // Read the response and save the public key sent back.
        Packet packet = readPacket();
        theirPubKey = KeyHandler.readPublicKey(packet.data);

        // Send ack to let server know key-exchange is successful.
        sendCommand(Protocol.CMD_INIT_OK_ACK);

        encryptData = true;
    }

    // Responds to initialization (should be called first from SERVER side)
    // During this init, the keys are exchanged between the hosts.
    public void respondInit() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        // Start by reading a packet and save their public key.
        Packet packet = readPacket();
        theirPubKey = KeyHandler.readPublicKey(packet.data);

        // Respond init OK -> Send our public key
        sendPacket(Protocol.CMD_INIT_OK, myPubKey.getEncoded());

        // Wait for ACK
        int command = readCommand();
        if (command == Protocol.CMD_INIT_OK_ACK)
            encryptData = true;
    }

    public Packet readPacket() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Packet packet;
        if (encryptData) {
            packet = readPacketHelper();
            Packet encDataPacket = readPacketHelper();
            byte[] data = crypter.decrypt(packet.data, encDataPacket.data);
            packet.data = data;
        } else {
            packet = readPacketHelper();
        }
        return packet;
    }

    public void sendPacket(int command, byte[] data) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (encryptData) {
            // If we're using encryption, first send the encrypted key, then the data
            Crypter.Pair keyAndData = crypter.encrypt(theirPubKey, data);
            sendPacketHelper(command, keyAndData.encryptedKey);
            sendPacketHelper(command, keyAndData.encryptedData);
        } else {
            sendPacketHelper(command, data);
        }
    }

    /*
        Sends a File-packet, indicating the start of a new file.
     */
    public void sendFilePacket(int fileSize, String dest) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        ByteBuffer packetData = ByteBuffer.allocate(PAYLOAD_HEADER_SIZE + dest.length());
        packetData.putInt(fileSize);
        packetData.putShort((short) dest.length());
        packetData.put(dest.getBytes());
        System.out.printf("Sending: %s\n", Arrays.toString(packetData.array()));
        sendPacket(CMD_FILE_START, packetData.array());
    }

    public void sendFileData(int size, byte[] data) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        sendPacket(CMD_FILE_DATA, Arrays.copyOfRange(data, 0, size));
    }

    public void readFileData(int size, byte[] data) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        sendPacket(CMD_FILE_DATA, Arrays.copyOfRange(data, 0, size));
    }

    /*
        Reads a file-packet returning only the payload of the original packet.
     */
    public FilePacket readFilePacket() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        Packet packet = readPacket();
        FilePacket fp = new FilePacket(packet);
        System.out.println(Arrays.toString(packet.data));
        return fp;
    }

    /*
        Wrapper-method when we only want to send a single command and no data.
        These packets are never encrypted.
    */
    public int readCommand() throws IOException {
        return readPacketHelper().command;
    }

    /*
        Wrapper-method when we only want to read a single command and no data.
        These packets are never encrypted.
     */
    public void sendCommand(int command) throws IOException {
        sendPacketHelper(command, null);
    }

    private Packet readPacketHelper() throws IOException {
        byte[] headBuf = new byte[headerSize];
        in.read(headBuf, 0, headerSize);
        ByteBuffer headBuffer = ByteBuffer.wrap(headBuf);

        Packet packet = new Packet(headBuffer.get(), headBuffer.get(), headBuffer.getInt());
        in.read(packet.data, 0, packet.size);
        return packet;
    }

    private void sendPacketHelper(int command, byte[] data) throws IOException {
        Packet packet = new Packet(version, command, data);
        out.write(packet.bytes());
        out.flush();
    }

    public boolean respondAuthentication() throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        Packet packet;
        int attempts = 0;
        boolean authorized = false;

        // Tell client we're expecting a password
        sendCommand(Protocol.CMD_AUTH_PASS_REQUIRED);

        // Client only has n number of login attempts.
        while (attempts < Protocol.LOGIN_ATTEMPTS_LIMIT && !authorized) {
            if (attempts > 0)
                sendCommand(Protocol.CMD_AUTH_BAD_LOGIN);
            // Read password and try to authenticate the user
            packet = readPacket();
            authorized = Authenticator.authenticate(new String(packet.data));
            attempts++;
        }

        if (attempts == Protocol.LOGIN_ATTEMPTS_LIMIT && !authorized) {
            System.out.printf("Too many attempts!\n");
            return false;
        }

        sendCommand(Protocol.CMD_AUTH_OK);
        return true;
    }

    public boolean authenticate(String password) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        sendPacket(Protocol.CMD_AUTH_PASS, password.getBytes());
        return readCommand() == Protocol.CMD_AUTH_OK;
    }

    public boolean md5SumIsOk(String file, byte[] md5Sum) throws NoSuchAlgorithmException {
        return crypter.md5SumIsOk(file, md5Sum);
    }

}
