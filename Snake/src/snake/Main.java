package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		
		int rows = 20, cols = 20, snakeSize = 3;
		int width = 600, height = 600;
		
		GameFrame frame = new GameFrame(width, height);
		GridMap map = new GridMap(rows, cols, frame);
		GameEngine engine = new GameEngine(map, snakeSize);

		frame.addKeyListener(engine);

		JPanel j = new JPanel();
		j.setPreferredSize(new Dimension(600, 50));
		
		frame.add(j, BorderLayout.NORTH);
		frame.add(map, BorderLayout.SOUTH);
		frame.setVisible(true);
		
		engine.play();
		

		engine.play();
		
	}

}
