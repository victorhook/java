package server;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import global.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Request implements Runnable {

    private static double TIMEOUT = 2000;
    private Socket connection;

    Request(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        double t0 = System.currentTimeMillis(), t1 = 0;
        System.out.printf("Connected: %s\n", connection.getInetAddress());

        BufferedInputStream in;
        DataOutputStream out;
        Packet packet;

/*        try {
            in = new BufferedInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
            ProtocolHandler ph = new ProtocolHandler();

            packet = ph.readPacket(in);



        } catch (Exception e) {}*/


        while (t1 - t0 < TIMEOUT) {
            try {
                in = new BufferedInputStream(connection.getInputStream());
                out = new DataOutputStream(connection.getOutputStream());

                KeyPair keyPair = KeyHandler.getKeys();
                ProtocolHandler ph = new ProtocolHandler(keyPair);
                Crypter crypter = new Crypter(keyPair);

                ph.respondInit(out, in);

                /*
                TODO: After handshake is complete, let's (by default) use encryption
                        for every packet.

                        This check should be done in sendPacket() at ph

                 */
                Crypter.Pair pair = crypter.encrypt(ph.getTheirPubKey(), "SECRET DATA!".getBytes());
                ph.sendPacket(1, pair.encryptedKey, out);
                ph.sendPacket(1, pair.encryptedData, out);
/*
                switch (packet.command) {
                    case Protocol.CMD_INIT: {
                        PublicKey pubKey = KeyHandler.readPublicKey(packet.data);
                        ph.setTheirPubKey(pubKey);

                        // Read in response, handshake complete
                        packet = ph.readPacket(in);

                        Crypter.Pair pair = crypter.encrypt(ph.getTheirPubKey(), "SECRET DATA!".getBytes());
                        ph.sendPacket(1, pair.encryptedKey, out);
                        ph.sendPacket(1, pair.encryptedData, out);


                    }
                    break;
                    default: {
                        System.out.println("Not implemented yet");
                    }
                    break;
                }*/

            } catch (IOException e) {
                e.printStackTrace();
            }  catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }

            t1 = System.currentTimeMillis();
            break;
        }

        if (t1 - t0 >= 5000)
            System.out.println("Timed out");

        System.out.println("Closing connection");
    }

}
