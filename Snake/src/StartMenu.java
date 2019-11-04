import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class StartMenu extends JPanel {

	public StartMenu() {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		StyledBtn play = new StyledBtn("Play"); 
		
		StyledBtn highScore = new StyledBtn("Show HighScores");
		
		StyledBtn quit = new StyledBtn("Quit");
		
		gbc.gridy = 0;
		add(play, gbc);
		
		gbc.gridy = 2;
		add(highScore, gbc);
		
		gbc.gridy = 3;
		add(quit, gbc);
	}
	
}
