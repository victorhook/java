package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class BackEnd implements ActionListener {

	private ChatGui gui;
	private Client client;
	private Crypter crypter;
	private boolean connected;
	private final static MyLogger LOGGER = new MyLogger(BackEnd.class.getName());
	
	private String host;
	private int port;
	
	
	public static void main(String[] args) {
		BackEnd backEnd = new BackEnd("127.0.0.1", 6000);
	}
	
	public BackEnd(String host, int port) {
		this.host = host;
		this.port = port;
		gui = new ChatGui(this);
		
	}
	
	public void connect(byte[] userName, byte[] pass) {
		
		if (!connected) {
			Thread connect = new Thread(new ServerConnect(this, host, port, userName, pass));
			connect.start();
		}
		
	}
	
	public void connectionFailed() {
		// Callback from connection thread if the socket can't be attached with given host & port
		LOGGER.log("Failed to connect to server");
		gui.connectionFailed();
	}
		
	public void connected() {
		connected = true;
		LOGGER.log("Login OK. Connected to server");
		gui.login();
	}
	
	public void userDoesntExist() {
		LOGGER.log("Given username doesn't exist");
		gui.userDoesntExist();
	}
	
	public void wrongPassword() {
		LOGGER.log("Wrong password");
		gui.wrongPassword();
	}
	
	public void initActionListeners(JButton sendBtn, JButton connectBtn) {
		
		Action sendHandler = new AbstractAction() {
			public void actionPerformed(ActionEvent pressed) {
				String msg = ((JTextArea) pressed.getSource()).getText();
				System.out.println(msg);
			}
		};
		
		gui.getChatBox().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("shift ENTER"), "pressed");
		gui.getChatBox().getActionMap().put("pressed", sendHandler);
	}
	
	public void actionPerformed(ActionEvent button) {
		
		String name = ((JButton) button.getSource()).getName();
		if (name.equals("connect")) {
			//this.connect();
		} else if (name.equals("disconnect")) {
			this.disconnect();
		} 
		else {
			String msg = gui.getChatBoxText();
			gui.clearChatBox();
			client.send(msg);
		}
		
	}
	
	public void disconnect() {
		client.close();
		connected = false;
		gui.setConnectInfo("Disconnected");
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	
}
