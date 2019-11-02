package snake;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

	private int width, height;
	
	public GameFrame(int width, int height) {
		super("Snake");
		this.width = width;
		this.height = height;
		
		// Initialize the game frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		
	}
	
}
