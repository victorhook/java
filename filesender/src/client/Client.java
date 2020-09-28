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
    private String host;

    public static void main(String[] args) {
        new Thread(new Client()).start();
    }


    public void run() {
        String ip = "127.0.0.1";
        int port = 8899;
        try {
            connection = new Socket();
            connection.connect(new InetSocketAddress(ip, port));
            host = connection.getInetAddress().getHostAddress();

            BufferedInputStream in;
            DataOutputStream out;

            out = new DataOutputStream(connection.getOutputStream());
            in = new BufferedInputStream(connection.getInputStream());
            KeyPair keyPair = KeyHandler.getKeys();
            ProtocolHandler ph = new ProtocolHandler(in, out, keyPair);

            ph.doInit();

            ph.sendCommand(Protocol.CMD_AUTH);

            if (ph.readCommand() == Protocol.CMD_AUTH_PASS_REQUIRED) {
                String password = Authenticator.getPassForHost(host);

                if (password != null) {
                    System.out.println(password);
                    if (ph.authenticate(password)) {
                        System.out.println("Auth ok!");

                        ClientSession session = new ClientSession(connection, ph);
                        session.start();

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
