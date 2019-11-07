package game;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class PlayAgainScreen extends JPanel {

	public PlayAgainScreen(GameEngine engine) {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		StyledBtn play = new StyledBtn(engine.PLAY_AGAIN, engine); 
		
		StyledBtn submit = new StyledBtn(engine.SUBMIT, engine);
		
		StyledBtn highScore = new StyledBtn(engine.HIGHSCORE, engine);
		
		StyledBtn quit = new StyledBtn(engine.QUIT, engine);
		
		gbc.insets = new Insets(5, 0, 5, 0);
		
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
