package server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.Base64;

import global.KeyHandler;
import global.Protocol;

public class Request implements Runnable {

    private static double TIMEOUT = 2;
    private Socket connection;

    Request(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        double t0 = System.currentTimeMillis(), t1 = 0;
        System.out.printf("Connected: %s\n", connection.getInetAddress());

        BufferedInputStream in;

        //ByteArrayInputStream input = new ByteArrayInputStream();

        ByteBuffer headBuf = ByteBuffer.allocate(Protocol.HEADER_SIZE);
        int version, command, size;

        while (t1 - t0 < 5000) {
            try {
                in = new BufferedInputStream(connection.getInputStream());

                int next, i = 0;
                while ( (next = in.read()) >= 0 ) {
                    headBuf.put((byte) next);
                    if (++i == Protocol.HEADER_SIZE)
                        break;
                }

                headBuf.flip();
                version = headBuf.get();
                command = headBuf.get();
                size = headBuf.getInt();

                System.out.printf("Version: %s, Command: %s, size: %s\n", version, command, size);

                ByteBuffer dataBuf = ByteBuffer.allocate(size);

                switch (command) {
                    case Protocol.CMD_INIT: {
                        while ( (next = in.read()) >= 0 ) {
                            dataBuf.put((byte) next);
                        }
                        System.out.println(new String(dataBuf.array()));
                    }
                    break;
                    default: {
                        System.out.println("Not implemented yet");
                    }
                    break;
                }

                t1 = System.currentTimeMillis();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (t1 - t0 >= 5000)
            System.out.println("Timed out");

        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closing connection");
        //KeyPair keys = KeyHandler.getKeys();
    }

}
