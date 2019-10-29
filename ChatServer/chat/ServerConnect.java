package chat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class ServerConnect implements Runnable {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	private BackEnd backEnd;
	private String host;
	private int port;
	private byte[] userName;
	private byte[] pass;
	
	private final String ENCRYPT_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private final String ENCRYPT_TYPE = "RSA";
	
	private final int USER_DOESNT_EXIST = 100, INCORRECT_PASSWORD = 200, 
			          LOGIN_OK = 300, USER_EXIST = 400;
	
	private boolean connected;
	
	public ServerConnect(BackEnd backEnd, String host, int port, byte[] userName, byte[] pass) {
		this.host = host;
		this.port = port;
		this.backEnd = backEnd;
		this.userName = userName;
		this.pass = pass;
	}
	
	public void run() {
		
		try {
			// Opens a socket with pre-chosen host & port and creates input and output stream to communicate
			socket = new Socket(host, port);
			//connected = true;
			//backEnd.connected();
			
			in = new DataInputStream((socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			
			DataInputStream input = new DataInputStream(System.in);
			String msg = "";
			
			// Declaration of public encryption key which is used to encrypt the user credentials before sending
			PublicKey key;
			
			while (!msg.equals("Over")) {
				
				// Communication is initialized by the client sending a "hello" and waiting for ack, followed by
				// an int containing byte-size of the public encryption key.
				
				while (!msg.equals("ack" )) {
					out.writeUTF("hello");
					out.flush();
					
					msg = in.readUTF();	
				}
				
				int size = in.readInt();

				try {
					KeyFactory kf = KeyFactory.getInstance(ENCRYPT_TYPE);
					key = kf.generatePublic(new X509EncodedKeySpec(in.readNBytes(size)));
					
					Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
					cipher.init(Cipher.ENCRYPT_MODE, key);
					
					// The string "username" and "pass" is to make sure the server knows what type of info is coming
					out.writeUTF("username");
					out.flush();
					
					// Encryption key recieved from server is used to encrypt the information					
					cipher.update(userName);
					byte[] encryptedUserName = cipher.doFinal();
					
					out.writeInt(encryptedUserName.length);
					out.flush();
					
					out.write(encryptedUserName);
					out.flush();
					
					int userStatus = 0;
					while (userStatus == 0) {
						userStatus = in.readInt();
						
						if (userStatus == USER_DOESNT_EXIST) {
							backEnd.userDoesntExist();
							close(in, out);
						}
					}
					
					out.writeUTF("pass");
					out.flush();
					
					cipher.update(pass);
					byte[] encryptedPass = cipher.doFinal();
					
					out.writeInt(encryptedPass.length);
					out.flush();
					
					out.write(encryptedPass);
					out.flush();
					
					userStatus = 0;
					
					while (userStatus == 0) {
						
						userStatus = in.readInt();
						if (userStatus == INCORRECT_PASSWORD) {
							backEnd.wrongPassword();
							close(in, out);
						} else if (userStatus == LOGIN_OK) {
							clientConnected();
						}
					}
				} 
				catch (Exception e) {
					break;
				}
					
				}
			
		} 
		catch (IOException e) {
			backEnd.connectionFailed();
			close(in, out);
		}
	}
	
	private void clientConnected() {
		backEnd.connected();
	}
	
	private void close(DataInputStream in, DataOutputStream out) {
		try {
			socket.close();
			in.close();
			out.close();
			Thread.currentThread().stop();
		} catch (Exception e) {}
	}
	
}
