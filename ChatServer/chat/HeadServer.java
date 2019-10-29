package chat;

import java.net.*;
import java.io.*;

public class HeadServer {

	private DataInputStream in = null;
	private DataOutputStream out = null;
	private Socket socket = null;
	private ServerSocket server = null;
	private final static MyLogger LOGGER = new MyLogger(HeadServer.class.getName());
	private PrintWriter messages;
	private final int PORT = 6000;
	private boolean serverRunning;
	private final short EOF = 0;
	
	private Database db;
	
	public HeadServer() {
		serverRunning = true;
		LOGGER.log("Server initialized");
		db = new Database(this);
		db.addNewUser("victor".getBytes(), "123".getBytes());
		
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log("Failed to open server on port " + PORT);
		}
		
		while (serverRunning) {
			try {
				socket = server.accept();
			} catch (IOException e) {
				LOGGER.log(e.getMessage());
			}
			new ClientThread(socket, LOGGER, db).start();
		}
		
	}
	
	public void log(String msg) {
		LOGGER.log(msg);
	}
	
	public static void main(String[] args) {
		HeadServer server = new HeadServer(); 
	}
	
}
