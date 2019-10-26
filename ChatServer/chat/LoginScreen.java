package chat;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

public class LoginScreen extends JPanel {

	private final Color GRAY = Color.decode("#AFAFAF");
	private BackEnd backEnd;
	private JLabel errorMsg;
	
	public LoginScreen(JFrame master, final BackEnd backEnd) {
		this.backEnd = backEnd;
		setPreferredSize(new Dimension(master.getWidth(), master.getHeight()));
		setBackground(GRAY);
		setLayout(new GridBagLayout());

		
		JLabel loginTitle = new JLabel("Welcome to ChatNet");
		loginTitle.setFont(new Font("Courier", 0, 30));
		
		errorMsg = new JLabel("");
		errorMsg.setFont(new Font("Courier", 0, 15));
		errorMsg.setForeground(Color.red);
		errorMsg.setPreferredSize(new Dimension(200, 50));
		
		// Container for the username and password labels and inputs
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setBackground(Color.decode("#AFAFAF"));
		userLabel.setOpaque(true);
		userLabel.setPreferredSize(new Dimension(100, 30));
		userLabel.setFont(new Font("Courier", 0, 15));
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBackground(Color.decode("#AFAFAF"));
		passLabel.setOpaque(true);
		passLabel.setPreferredSize(new Dimension(100, 30));
		userLabel.setFont(new Font("Courier", 0, 15));
		
		final JTextField userEntry = new JTextField();
		userEntry.setPreferredSize(new Dimension(200, 30));
		userEntry.setFont(new Font("Courier", 0, 15));
		
		final JTextField passEntry = new JPasswordField();
		passEntry.setPreferredSize(new Dimension(200, 30));
		passEntry.setFont(new Font("Courier", 0, 15));
		
		JPanel loginFrame = new JPanel(new GridBagLayout());
		loginFrame.setBackground(GRAY);
		GridBagConstraints c1 = new GridBagConstraints();
		
		c1.insets = new Insets(10, 0, 0, 10);
		c1.gridx = 0;
		c1.gridy = 0;
		loginFrame.add(userLabel, c1);
		c1.gridx = 1;
		c1.gridy = 0;
		loginFrame.add(userEntry, c1);
		
		c1.gridx = 0;
		c1.gridy = 1;
		loginFrame.add(passLabel, c1);
		c1.gridx = 1;
		c1.gridy = 1;
		loginFrame.add(passEntry, c1);
		

		StyledButton loginBtn = new StyledButton("Login", GRAY) {
			@Override
			public void mouseClicked(MouseEvent e) {
				backEnd.connect(userEntry.getText().getBytes(), 
								passEntry.getText().getBytes());
			}
		};
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(loginTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(errorMsg, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(60,0,0,0);
		add(loginFrame, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(loginBtn, c);
		
	}
	
	public void connectionFailed() {
		errorMsg.setText("Connection failed");
	}
	
	public void userDoesntExist() {
		errorMsg.setText("That User doesn't exist");
	}
	
	public void wrongPassword() {
		errorMsg.setText("Incorrect password");
	}
	
}
