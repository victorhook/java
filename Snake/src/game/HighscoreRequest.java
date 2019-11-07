package game;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HighscoreRequest extends Thread {

	private final int PORT = 6789;
	private final String IP = "127.0.0.1";
	public static final String GET = "GET", POST = "POST", OVER = "over";
	
	private Socket socket;
	private GameEngine engine;
	private String request;
	private String userName;
	private int score;
		
	public HighscoreRequest(GameEngine engine, String request, String userName, int score) {
		this.engine = engine;
		this.request = request;
		this.userName = userName;
		this.score = score;
	}
	
	public HighscoreRequest(GameEngine engine, String request) {
		this.engine = engine;
		this.request = request;
	}
	
	public void run() {

		try {
			
			socket = new Socket(IP, PORT);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			String msg = "";
			
			while (!msg.equals(OVER)) {
				
				if (request.equals(GET)) {
					
					out.writeUTF(GET);
					out.flush();
					
					String highscores = in.readUTF();
					engine.updateHighscores(highscores);
					
					msg = in.readUTF();
			
				} else {
					
					out.writeUTF(POST);
					out.flush();
					
					out.writeUTF(userName);
					out.flush();
					
					out.writeInt(score);
					out.flush();
					
					out.writeUTF(OVER);
					out.flush();
					
					break;
				}
			}
			
			socket.close();
			in.close();
			out.close();
						
		} 
		catch (IOException e) {}
		
	}

	
}
