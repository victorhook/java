package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GridMap extends JPanel {

		// Frame containing the grid mapping of the game
		private int[][] map;
		private final int GRID_SIZE = 35;
		private final int EMPTY = 0, SNAKE = 1, FOOD = 2; 
		private Snake snake;
		
		public GridMap(Snake snake) {
			setBackground(Color.black);
			this.snake = snake;
			map = new int[14][14];
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));

			for (int row = 0; row < 14; row++) {
				for (int col = 0; col < 14; col++) {
					switch (map[row][col]) {
					case EMPTY:
						g2d.setColor(Color.black);
						break;
					case SNAKE:
						g2d.setColor(Color.red);
						break;
					case FOOD:
						g2d.setColor(Color.yellow);
					}
					g2d.fillRect(col*GRID_SIZE, row*GRID_SIZE, GRID_SIZE, GRID_SIZE);
				}
			}
		}
		
		
}
