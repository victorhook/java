package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientSocket extends Thread {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String highscores;
	private Server server;
	private String userName;
	private int score;
	
	ClientSocket(Server server, Socket socket, String highscores) {
		this.server = server;
		this.socket = socket;
		this.highscores = highscores;
	}
	
	public void run() {
		
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()) );
			out = new DataOutputStream(socket.getOutputStream());
			
			String msg = "";
			
			while (!msg.equals("over")) {
				
				msg = in.readUTF();

				if (msg.equals("GET")) {
					// Sends the highscores to the client			
					out.writeUTF(highscores);
					out.flush();
					
					out.writeUTF("over");
					out.flush();
					
				} else if (msg.equals("POST")) {
					
					userName = in.readUTF();
					score = in.readInt();
					
					// Gives the response to the server 
					boolean userMadeIt = server.addNewHighscore(userName, score);
					
					out.writeBoolean(userMadeIt);
					out.flush();
					
				}
				
			}
			
			socket.close();
			in.close();
			out.close();
			
		} 
		catch (IOException e) {}
		
	}
	
}
