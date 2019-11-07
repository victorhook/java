package game;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartMenu extends JPanel {

	public StartMenu(GameEngine engine) {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel snakeLabel = new JLabel("Welcome to SNAKE");
		snakeLabel.setFont(new Font("Courier", Font.BOLD, 40));
		snakeLabel.setBackground(MainGame.GRAY);
		
		StyledBtn play = new StyledBtn(engine.PLAY, engine); 
		
		StyledBtn highScore = new StyledBtn(engine.HIGHSCORE, engine);
		
		StyledBtn quit = new StyledBtn(engine.QUIT, engine);
		
		
		gbc.insets = new Insets(0,0,30,0);
		
		gbc.gridy = 0;
		add(snakeLabel, gbc);		
		
		gbc.insets = new Insets(15,0,15,0);
		gbc.gridy = 2;
		add(play, gbc);
		
		gbc.gridy = 3;
		add(highScore, gbc);
		
		gbc.gridy = 4;
		add(quit, gbc);
	}
	
}
