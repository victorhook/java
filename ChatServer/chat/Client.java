package chat;

import java.net.*;
import java.io.*;

public class Client extends Thread {
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private BackEnd backEnd;
	private String host;
	private int port;
	private boolean connected;
	
	public void run() {
		
	}
	
	public Client(BackEnd backEnd, String host, int port, byte[] userName, byte[] pass) throws ConnectionIssue {
		this.host = host;
		this.port = port;
		this.backEnd = backEnd;
		System.out.print("Client: ");
		System.out.println(Thread.currentThread().getName());
		
		try {
			socket = new Socket(host, port);
			connected = true;
			backEnd.connected();
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			DataInputStream input = new DataInputStream(System.in);
			String msg = "";
			
			while (connected) {
				out.write("username".getBytes());
				out.write(userName);
				out.write("pass".getBytes());
				out.write(pass);
				out.write("Over".getBytes());
				
				//System.out.println(backEnd.getMsg());
				msg = input.readLine();
				out.writeUTF(msg);
			}
			
		} catch (IOException e) {
		  throw new ConnectionIssue("Couldn't connect!");
		}
	}
	
	public void send(String msg) {
		try {
			out.writeUTF(msg);
		} catch (IOException e) {}
	}
	
	public void close() {
		connected = false;
		System.out.println("closing");
		try {
			out.writeUTF("Over");
			in.close();
			out.close();
		} catch (IOException e) {}
	}
	
	//public static void main(String[] args) {
	//	Client client = new Client("192.168.0.7", 6000);
	//}
	
}