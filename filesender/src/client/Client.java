package client;

import global.*;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Client implements Runnable {

    private Socket connection;

    public static void main(String[] args) {
        new Thread(new Client()).start();
    }

    public void run() {
        String ip = "127.0.0.1";
        int port = 8899;
        try {
            connection = new Socket();
            connection.connect(new InetSocketAddress(ip, port));
            BufferedInputStream in;
            DataOutputStream out;
            Packet packet;

            out = new DataOutputStream(connection.getOutputStream());
            in = new BufferedInputStream(connection.getInputStream());
            KeyPair keyPair = KeyHandler.getKeys();
            ProtocolHandler ph = new ProtocolHandler(keyPair);
            Crypter crypter = new Crypter(keyPair);


/*            ph.sendPacket(Protocol.CMD_INIT, ph.getMyPubKey().getEncoded(), out);

            packet = ph.readPacket(in);
            PublicKey pubKey = KeyHandler.readPublicKey(packet.data);
            System.out.printf("Key is from server: %s", pubKey);*/
            ph.doInit(out, in);

/*            ph.sendPacket(1, "asd".getBytes(), out);

            packet = ph.readPacket(in);
            byte[] encKey = packet.data;
            packet = ph.readPacket(in);
            byte[] encData = packet.data;

            System.out.println(encKey);
            System.out.println(encData);

            byte[] data = crypter.decrypt(encKey, encData);
            System.out.printf("Recieved data: %s\n", new String(data));*/



            //Protocol.sendPacket(Protocol.CMD_INIT, myPubKey.getEncoded(), out);


            //otherPubKey = new HandShakeHandler(in, out, myPubKey).init();

/*            Crypter crypter = new Crypter(myPubKey, myPrivKey);
            byte[] signedKey = crypter.sign(otherPubKey.getEncoded());
            byte[] packet = Protocol.packet(Protocol.CMD_AUTH, signedKey);
            out.write(packet);
            out.flush();*/



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }


/*
    private class HandShakeHandler implements HandShake {
        private BufferedInputStream in;
        private DataOutputStream out;
        private PublicKey pubKey;

        public HandShakeHandler(BufferedInputStream in, DataOutputStream out, PublicKey pubKey) {
            this.in = in;
            this.out = out;
            this.pubKey = pubKey;
        }

        public PublicKey init() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
            // Send first init-message with public key as data
            byte[] packet = Protocol.packet(Protocol.CMD_INIT, pubKey.getEncoded());
            System.out.printf("SEND: %s\n", Arrays.toString(packet));
            out.write(packet);
            out.flush();

           */
/* System.out.printf("Data send, reading packet...\n");

            // Read the response, which should contain the other hosts public key
            ByteBuffer headBuf = ByteBuffer.allocate(Protocol.HEADER_SIZE);
            int version, command, size;
            headBuf.put(Protocol.readHeader(in)).flip();

            version = headBuf.get();
            command = headBuf.get();
            size = headBuf.getInt();
            System.out.printf("V: %s, C: %s, Size: %s\n", version, command, size);

            byte[] data = new byte[size];
            in.read(data, 0, size);
            pubKey = KeyHandler.readPublicKey(data);
            return pubKey;*//*

            return null;
        }

    }
*/


}
