package chat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

public class ClientThread extends Thread {

	protected Socket socket;
	protected MyLogger LOGGER;
	private Database db;
	
	private final int USER_DOESNT_EXIST = 100, INCORRECT_PASSWORD = 200, 
					  LOGIN_OK = 300, USER_EXIST = 400;
	
	public ClientThread(Socket clientSocket, MyLogger LOGGER, Database db) {
		this.socket = clientSocket;
		this.LOGGER = LOGGER;
		this.db = db;
	}
	
	public void run() {
		DataInputStream in = null;
		DataOutputStream out = null;
		
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			LOGGER.log("New socket attached on thread " + Thread.currentThread().getName());
			String msg = "";
			
			//String threadName = Thread.currentThread().getName();
			//LOGGER.log(threadName + " => " + msg);
			
			while (!msg.equals("hello")) {
				
				msg = in.readUTF();
				
				LOGGER.log("Hello recieved. Sending ack...");
				
				out.writeUTF("ack");
				out.flush();
				
				byte[] key = db.getPubKey().getEncoded();
				out.writeInt(key.length);
				out.flush();
				
				out.write(key);
				out.flush();
			}
			
			while (!msg.equals("Over")) {
				try {
					
					msg = in.readUTF();
					
					if (msg.equals("username")) {
						
						int size = in.readInt();
						byte nameBuffer[] = new byte[size];
						nameBuffer = in.readNBytes(size);
						
						if (!db.userExist(nameBuffer)) {
							// If user doesn't exist, send status back status number USER_DOESNT_EXIST
							LOGGER.log("Username recieved, checking if user exists...");
							
							out.writeInt(USER_DOESNT_EXIST);
							out.flush();
							LOGGER.log("User doesn't exist");
							close(in, out);
						} else {
							
							out.writeInt(USER_EXIST);
							out.flush();
							
							while (!msg.equals("pass")) {
								// If user does exist, check if passwords and username match
								
								msg = in.readUTF();
								LOGGER.log("Password recieved, checking if OK");
								
								size = in.readInt();
								
								byte passBuffer[] = new byte[size];
								passBuffer = in.readNBytes(size);
								
								if (!db.passwordOk(nameBuffer, passBuffer)) {
									out.writeInt(INCORRECT_PASSWORD);
									out.flush();
									LOGGER.log("Wrong password");
									close(in, out);
								} else {
									out.writeInt(LOGIN_OK);
									out.flush();
									clientConnected(nameBuffer);
								}
							}
							
						}
					}
				}  
				catch (IOException e) {
					LOGGER.log("Socket closed unexpectedly");
					close(in, out);
				}
			}
		} 
		catch (IOException e) {
			return;
		}
		
		LOGGER.log("Closing socket on thread " + Thread.currentThread().getName());
		close(in, out);
	}
	
	private void close(DataInputStream in, DataOutputStream out) {
		try {
			in.close();
			out.close();
			socket.close();
			Thread.currentThread().stop();
		} catch (Exception e) {}
		
	}
	
	private void clientConnected(byte[] userName) {
		LOGGER.log("Login OK!");
	}
	
	
}
