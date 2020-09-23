package server;

import global.ConfigHandler;
import global.Protocol;

import java.net.*;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws Exception {
        Map<String, String> config = ConfigHandler.getConfig();
        Protocol.initProtocol(config);
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(
                    config.getOrDefault("ip", "127.0.0.1"),
                    Integer.valueOf(config.get("port"))));

        while (true) {
            Socket newConnection = server.accept();
            new Thread(new Request(newConnection)).start();
        }

        //server.close();
    }


}
