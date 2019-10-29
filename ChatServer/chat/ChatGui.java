package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class ChatGui extends JFrame {
	
	private JTextArea chatDisplay;
	private JTextArea chatBox;
	private JTextArea connectInfo;
	private BackEnd backEnd;
	private LoginScreen login;
	
	public ChatGui(BackEnd backEnd) {
		super("ChatNet");
		this.backEnd = backEnd;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(700, 600));
		setSize(new Dimension(700, 600));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				close();
			}
		});

		login = new LoginScreen(this, backEnd);
		add(login);
		login.setVisible(true);
		setVisible(true);
		
		/*
		JPanel bigFrame = new JPanel();
		bigFrame.setLayout(new BoxLayout(bigFrame, BoxLayout.X_AXIS));
		
		
		// Navigation bar
		JPanel navBar = new JPanel(new FlowLayout());
		navBar.setMinimumSize(new Dimension(150, 400));
		navBar.setBackground(Color.decode("#d5ff80"));
		navBar.setPreferredSize(new Dimension(400, 400));
	
		JButton connectBtn = new JButton("Connect");
		connectBtn.setFont(new Font("Courier", 0, 15));
		connectBtn.setName("connect");
		connectBtn.setPreferredSize(new Dimension(140, 25));
		
		JButton disConnectBtn = new JButton("Disconnect");
		disConnectBtn.setFont(new Font("Courier", 0, 15));
		disConnectBtn.setName("disconnect");
		disConnectBtn.setPreferredSize(new Dimension(140, 25));
		
		connectInfo = new JTextArea();
		connectInfo.setPreferredSize(new Dimension(140, 140));
		connectInfo.setEditable(false);
		connectInfo.setBackground(Color.decode("#d5ff80"));
		
		navBar.add(connectBtn);
		navBar.add(disConnectBtn);
		navBar.add(connectInfo);
		
		// Chat Frame
		
		JPanel chatFrame = new JPanel(new FlowLayout());
		chatFrame.setBackground(Color.decode("#c2c2a3"));
		
		chatDisplay = new JTextArea();
		chatDisplay.insert("test", 0);
		chatDisplay.setPreferredSize(new Dimension(500, 400));
		chatDisplay.setEditable(false);
		
		chatBox = new JTextArea();
		chatBox.setPreferredSize(new Dimension(400, 150));
		chatBox.setFont(new Font("Courier", 0, 20));
		chatBox.insert("test2", 0);
		chatBox.setLineWrap(true);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setName("send");
		sendBtn.setFont(new Font("Courier", 0, 15));
		sendBtn.setPreferredSize(new Dimension(80, 50));
		
		chatFrame.add(chatDisplay);
		chatFrame.add(chatBox);
		chatFrame.add(sendBtn);
		
		//
		
		backEnd = new BackEnd(this, "192.168.0.7", 6000);
		backEnd.initActionListeners(sendBtn, connectBtn);
		
		sendBtn.addActionListener(backEnd);
		connectBtn.addActionListener(backEnd);
		disConnectBtn.addActionListener(backEnd);
		
		bigFrame.add(navBar);
		bigFrame.add(chatFrame);
		
		add(bigFrame);
		setVisible(true);
		*/
	}
	
	public void login() {
		login.setVisible(false);
	}
	
	public void connectionFailed() {
		login.connectionFailed();
	}
	
	public void userDoesntExist() {
		login.userDoesntExist();
	}
	
	public void wrongPassword() {
		login.wrongPassword();
	}
	
	
	public void close() {
		try {
			backEnd.disconnect();
		} catch (Exception e) {}
		System.exit(0);
		return;
	}
	
	public void clearChatBox() {
		chatBox.setText("");
	}
	
	public JTextArea getChatBox() {
		return chatBox;
	}
	
	public JTextArea getChatDisplay() {
		return chatDisplay;
	}
	
	public void setConnectInfo(String msg) {
		connectInfo.setText(msg);
	}
	
	public String getDisplayText() {
		return chatDisplay.getText();
	}
	
	public String getChatBoxText() {
		return chatBox.getText();
	}
}
