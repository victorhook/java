package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NavBar extends JPanel {

	private JLabel scoreBoard;
	private JLabel previousBest;
	private JLabel specialMsg;
	private GridBagConstraints gbc;
	
	public NavBar() {
		setBackground(MainGame.GRAY);
		setMinimumSize(new Dimension(600, 70));
		setPreferredSize(new Dimension(600, 70));
		setLayout(new GridBagLayout());
				
		previousBest = new JLabel("Previous best: None");
		scoreBoard = new JLabel("Points: ");
		
		previousBest.setFont(new Font("Courier", Font.BOLD, 20));
		scoreBoard.setFont(new Font("Courier", Font.BOLD, 20));

		// Used to confirm if user made it to highscores
		specialMsg = new JLabel("");
		specialMsg.setFont(new Font("Courier", Font.BOLD, 25));
		
		gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		gbc.gridx = 0;
		add(previousBest, gbc);
		
		add(specialMsg, gbc);
		specialMsg.setVisible(false);
		
		gbc.gridx = 1;
		add(scoreBoard, gbc);
		
	}
	
	void updateScore(int score) {
		scoreBoard.setText("Points: " + score);
	}
	
	void updateNewBest(int score) {
		previousBest.setText("Previous best: " + score);
	}
	
	void madeItToHighscores(boolean userMadeIt) {
		previousBest.setVisible(false);
		scoreBoard.setVisible(false);
		gbc.gridx = 0;
		String msg = userMadeIt ? "You made it to the Highscores!" : "You didn't make it to the Highscores..."; 
		specialMsg.setText(msg);
		specialMsg.setVisible(true);
	}
	
	void reset() {
		specialMsg.setVisible(false);
		
		scoreBoard.setText("Points: ");;
		previousBest.setText("Previous best: None");;
		previousBest.setVisible(true);
		scoreBoard.setVisible(true);
	}
	
}
