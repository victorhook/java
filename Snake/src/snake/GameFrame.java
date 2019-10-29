package snake;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

	private final int WIDTH = 600, HEIGHT = 600;
	private GridMap gridMap;
	
	public GameFrame() {
		super("Snake");
		
		// Initialize the game frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);

		gridMap = new GridMap();
		GameEngine engine = new GameEngine(gridMap, new Snake());

		this.addKeyListener(new EventMaster(engine));
		
		add(gridMap);
		setVisible(true);

		Thread t = new Thread(engine);
		t.start();
		
	}

	
	

}
