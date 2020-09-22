package client;

import global.Protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    public static void main(String[] args) throws IOException {
        String ip = "127.0.0.1";
        int port = 8899;
        Socket sock = new Socket();
        sock.connect(new InetSocketAddress(ip, port));
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());

        String data = "hey dude this is awesome!";
        System.out.println(data.length());

        System.out.println(data.getBytes());

        byte[] packet = Protocol.packet(Protocol.CMD_INIT, data.getBytes());
        System.out.println(Arrays.toString(packet));
        out.write(packet);
        out.flush();

        sock.close();
    }

}
