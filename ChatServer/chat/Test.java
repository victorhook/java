package chat;

import java.net.*;
import java.io.*;

public class Test {

	public static void main(String[] args) {
		String ip = "192.168.0.7";
		int port = 6000;
		try {
			Socket socket = new Socket(ip, port);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			String line = "";
			
			out.writeUTF("hey nils!");
			
			out.writeUTF("Over");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		

	}

}
