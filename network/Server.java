package chat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private int port;

	public Server(int port) {
		this.port = port;
		
		try {
			server = new ServerSocket(port);
			System.out.println("Server has started");
			System.out.println("Waiting for client...");
			
			socket = server.accept();
			System.out.println("Client accepted");
			
			// Takes input from client socket
			in = new DataInputStream( new BufferedInputStream(socket.getInputStream()) );
			
			String line = "";
			
			while (!line.contentEquals("Over" )) {
				try {
					line = in.readUTF();
					System.out.println(line);
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			
			System.out.println("Closing connection");
			socket.close();
			in.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {

		Server server = new Server(5000);
		
	}
}
