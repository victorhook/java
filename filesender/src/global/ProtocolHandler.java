package global;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class ProtocolHandler {

    private int headerSize;
    private byte version;
    private PublicKey myPubKey, theirPubKey;
    private PrivateKey myPrivKey, theirPrivKey;
    private boolean encryptData;
    Crypter crypter;

    public ProtocolHandler(KeyPair keyPair) throws FileNotFoundException, NoSuchAlgorithmException {
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
    public void doInit(DataOutputStream out, BufferedInputStream in) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        // Send first init request with our public key as data.
        sendPacket(Protocol.CMD_INIT, getMyPubKey().getEncoded(), out);

        // Read the response and save the public key sent back.
        Packet packet = readPacket(in);
        theirPubKey = KeyHandler.readPublicKey(packet.data);

        // Send ack to let server know key-exchange is successful.
        sendPacket(Protocol.CMD_INIT_OK_ACK, null, out);

        encryptData = true;
    }

    // Responds to initialization (should be called first from SERVER side)
    // During this init, the keys are exchanged between the hosts.
    public void respondInit(DataOutputStream out, BufferedInputStream in) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        // Start by reading a packet and save their public key.
        Packet packet = readPacket(in);
        theirPubKey = KeyHandler.readPublicKey(packet.data);

        // Respond init OK -> Send our public key
        sendPacket(Protocol.CMD_INIT_OK, myPubKey.getEncoded(), out);

        // Wait for ACK
        packet = readPacket(in);
        if (packet.command == Protocol.CMD_INIT_OK_ACK)
            encryptData = true;
    }

    public Packet readPacket(BufferedInputStream in) throws IOException {
        byte[] headBuf = new byte[headerSize];
        in.read(headBuf, 0, headerSize);
        ByteBuffer headBuffer = ByteBuffer.wrap(headBuf);

        Packet packet = new Packet(headBuffer.get(), headBuffer.get(), headBuffer.getInt());
        in.read(packet.data, 0, packet.size);
        return packet;
    }

    public void sendPacket(int command, byte[] data, DataOutputStream out) throws IOException {
        Packet packet = new Packet(version, command, data);
        System.out.println(packet);
        out.write(packet.bytes());
        out.flush();
    }

}
