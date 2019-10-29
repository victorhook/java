package snake;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame implements Runnable {

	private final int WIDTH = 600, HEIGHT = 600;
	
	public GameFrame() {
		super("Snake");
		
		// Initialize the game frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);

		GridMap gridMap = new GridMap(new Snake());
		GameEngine engine = new GameEngine();
		//drawMap(gridMap);

		this.addKeyListener(new EventMaster(engine));
		
		add(gridMap);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
		
		//setVisible(true);
	}

	
	public void run() {
		// Starts the game
		
	}
	

}
