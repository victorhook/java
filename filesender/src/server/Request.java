package server;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import global.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Request implements Runnable {

    private static double TIMEOUT = 2000;
    private Socket connection;
    private String host;

    Request(Socket connection) {
        this.connection = connection;
        this.host = connection.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        double t0 = System.currentTimeMillis(), t1 = 0;
        System.out.printf("Connected: %s\n", host);

        BufferedInputStream in;
        DataOutputStream out;
        Packet packet;
        int command;

        while (t1 - t0 < TIMEOUT) {
            try {
                in = new BufferedInputStream(connection.getInputStream());
                out = new DataOutputStream(connection.getOutputStream());

                KeyPair keyPair = KeyHandler.getKeys();
                ProtocolHandler ph = new ProtocolHandler(in, out, keyPair);

                ph.respondInit();
                command = ph.readCommand();

                switch (command) {
                    case Protocol.CMD_AUTH:
                        if (ph.respondAuthentication()) {
                            System.out.printf("Login from %s successful\n",
                                              connection.getInetAddress().getHostAddress());
                            ServerSession session = new ServerSession(connection, ph);
                            session.start();
                        }
                        break;
                }

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
