package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/* Server which handles highscore requests and updates highscore if needed
   for snake game. 
*/

class Server {
	
	private ServerSocket server;
	private Socket socket;
	private final int PORT = 6789;
	private boolean serverIsUp;
	private Database database;
	
	Server() {
		
		database = new Database();
		
		try {
			server = new ServerSocket(PORT);
			serverIsUp = true;
		} catch (IOException e) {}
		
		
		while (serverIsUp) {
			
			try {
				socket = server.accept();
			} catch (IOException e) {}
			
			String highscores = database.getHighscores();
			new ClientSocket(this, socket, highscores).start();
			
		}
	}
	
	public void addNewHighscore(String userName, int score) {
		database.updateHighscore(userName, score);
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}
