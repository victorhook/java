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
	
	public NavBar() {
		setBackground(MainGame.GRAY);
		setMinimumSize(new Dimension(600, 70));
		setPreferredSize(new Dimension(600, 70));
		setLayout(new GridBagLayout());
		
		
		previousBest = new JLabel("Previous best: None");
		scoreBoard = new JLabel("Points: ");
		
		previousBest.setFont(new Font("Courier", Font.BOLD, 20));
		scoreBoard.setFont(new Font("Courier", Font.BOLD, 20));
		

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		gbc.gridx = 0;
		add(previousBest, gbc);
		
		gbc.gridx = 1;
		add(scoreBoard, gbc);
		
	}
	
	public void updateScore(int score) {
		scoreBoard.setText("Points: " + score);
	}
	
	public void updateNewBest(int score) {
		previousBest.setText("Previous best: " + score);
	}
	
}
