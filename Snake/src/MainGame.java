import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainGame extends JFrame {

	public final static Color GRAY = Color.decode("#C0C0C0"), GRAY2 = Color.decode("#999999"), ORANGE = Color.decode("#00cc00");;
	
	private MainGame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 600));
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(GRAY);
		
		GridMap map = new GridMap();
		NavBar navBar = new NavBar();
		GameEngine engine = new GameEngine(map, navBar);

		// Construction area
		
		PlayAgainScreen playAgain = new PlayAgainScreen();
		StartMenu startMenu = new StartMenu();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new HighScore(), gbc);
		
		/*
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(navBar, gbc);
		
		gbc.gridy = 1;
		// Change playAgain for map when done
		add(startMenu, gbc);
		*/
		addKeyListener(engine);
		
		
		//engine.startGame();
		
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MainGame game = new MainGame();
				game.setVisible(true);
			}
		});

	}
	
}
