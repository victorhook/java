package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket          = null;
	private DataInputStream  input = null;
	private DataOutputStream out   = null; 
	private String ip;
	private int port;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		if (connect()) {
			// Input string 
			String line = "";
			
			// Keep reading until "over" is input
			while (!line.contentEquals("Over")) {
				try {
					line = input.readLine();
					out.writeUTF(line);
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			
			try {
				input.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
			
		}
		
	}
	
	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			System.out.println("Connected!");
			
			// Takes input from terminal
			input = new DataInputStream(System.in);
			
			// Sends ouput to the socket
			out = new DataOutputStream(socket.getOutputStream());
			
			return true;
			
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException e) {
			System.out.println(e);
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		Client client = new Client("127.0.0.1", 5000);
		
	}

}
