package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
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
	private PlayAgainScreen playAgain;
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
	
		startMenu = new StartMenu(engine);
		playAgain = new PlayAgainScreen(engine);
			
		engine.addStartMenu(startMenu);
		engine.addPlayAgainMenu(playAgain);

		
		gbc = new GridBagConstraints();
		
		gbc.gridy = 0;
		add(navBar, gbc);
		
		gbc.gridy = 1;
		add(startMenu, gbc);
		add(map, gbc);
		add(playAgain, gbc);
		
		map.setVisible(false);
		playAgain.setVisible(false);
		navBar.setVisible(false);
		
		startMenu.setVisible(true);
	
		map.addKeyListener(engine);
	
	}
	
	public void showGame() {
		hideHighscore();
		startMenu.setVisible(false);
		playAgain.setVisible(false);
		
		navBar.setVisible(true);
		map.setVisible(true);
	}
	
	public void playAgainMenu() {
		
		hideHighscore();
		startMenu.setVisible(false);
		map.setVisible(false);
		
		playAgain.setVisible(true);
		navBar.setVisible(true);
	}
	
	public void addHighscore(HighScore window) {
		startMenu.setVisible(false);
		playAgain.setVisible(false);
		navBar.setVisible(false);
		map.setVisible(false);
		
		this.window = window;
		add(window, gbc);
	}
	
	public void showStartMenu() {
		hideHighscore();
		playAgain.setVisible(false);
		navBar.setVisible(false);
		map.setVisible(false);
		
		startMenu.setVisible(true);
		
	}
	
	private void hideHighscore() {
		try {
			window.setVisible(false);
			remove(window);
		} catch (Exception e) {}
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
