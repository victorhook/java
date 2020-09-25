package global;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class ProtocolHandler extends Protocol {

    private int headerSize;
    private byte version;

    private BufferedInputStream in;
    private DataOutputStream out;
    private PublicKey myPubKey, theirPubKey;
    private PrivateKey myPrivKey, theirPrivKey;
    private boolean encryptData;
    Crypter crypter;

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

    public void setMyPubKey(PublicKey myPubKey) {
        this.myPubKey = myPubKey;
    }

    public PublicKey getTheirPubKey() {
        return theirPubKey;
    }

    public void setTheirPubKey(PublicKey theirPubKey) {
        this.theirPubKey = theirPubKey;
    }

    public PrivateKey getMyPrivKey() {
        return myPrivKey;
    }

    public void setMyPrivKey(PrivateKey myPrivKey) {
        this.myPrivKey = myPrivKey;
    }

    public PrivateKey getTheirPrivKey() {
        return theirPrivKey;
    }

    public void setTheirPrivKey(PrivateKey theirPrivKey) {
        this.theirPrivKey = theirPrivKey;
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

}
