package chat;

import java.net.*;
import java.io.*;

public class Server {

	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private File file;
    private int port;
 
    public Server(int port) {
        this.port = port;

        try {
            server = new ServerSocket(port);
            socket = server.accept();
            System.out.println("Client connected");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            String line = "";

            while (!line.equals("Over")) {
                try {
                    line = in.readUTF();
                    if (line.equals("hello")) {
                    	out.writeUTF("HELLO BACK!");
                    	out.writeUTF("Over");
                    	System.out.println(line);
                    }
                }
              catch (IOException e) {
                //e.printStackTrace();
            }
            }

            System.out.println("Closing connection");
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)     {
        Server server = new Server(6000);
    }

}