package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PlayAgainScreen extends JPanel {

	public PlayAgainScreen() {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		StyledBtn play = new StyledBtn("Play again"); 
		
		StyledBtn submit = new StyledBtn("Submit score");
		
		StyledBtn highScore = new StyledBtn("Show HighScores");
		
		StyledBtn quit = new StyledBtn("Quit");
		
		gbc.gridy = 0;
		add(play, gbc);
		
		gbc.gridy = 1;
		add(submit, gbc);
		
		gbc.gridy = 2;
		add(highScore, gbc);
		
		gbc.gridy = 3;
		add(quit, gbc);
		
	}
	
}
