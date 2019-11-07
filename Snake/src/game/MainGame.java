package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainGame extends JFrame {

	public final static Color GRAY = Color.decode("#C0C0C0"), GRAY2 = Color.decode("#999999"), ORANGE = Color.decode("#00cc00");;
	
	private GridMap map;
	private NavBar navBar;
	private HighScore window;
	private GameEngine engine;
	private StartMenu startMenu;
	private GridBagConstraints gbc;
	
	
	private MainGame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 600));
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(GRAY);
		
		map = new GridMap();
		navBar = new NavBar();
		engine = new GameEngine(this, map, navBar);
	
		//PlayAgainScreen playAgain = new PlayAgainScreen(engine);
		
		startMenu = new StartMenu(engine);
				
		engine.addStartMenu(startMenu);
		addKeyListener(engine);
		
		gbc = new GridBagConstraints();
			
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(startMenu, gbc);

		
		//engine.startGame();
		
	}
	
	public void addHighscore(HighScore window) {
		startMenu.setVisible(false);
		this.window = window;
		add(window, gbc);
	}
	
	public void showStartMenu() {
		try {
			window.setVisible(false);
			remove(window);
		} catch (Exception e) {e.printStackTrace();}
		startMenu.setVisible(true);
		
	}
	
	public void hideStartMenu() {
		startMenu.setVisible(false);
	}
	
	public void hideNavBar() {
		navBar.setVisible(false);
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
