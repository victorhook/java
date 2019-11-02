package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class GridMap extends JPanel {

		// Frame containing the grid mapping of the game
		private Point[][] snakeMap;
		private int rows, cols, gridSize;
		private final Color FOOD_COLOR = Color.yellow, SNAKE_COLOR = Color.green, EMPTY_COLOR = Color.black; 
		
		public GridMap(int rows, int cols, GameFrame frame) {
			setBackground(Color.red);
			this.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() - 100));
			this.rows = rows;
			this.cols = cols;
			
			int min = (rows < cols) ? rows : cols;
			gridSize = (frame.getHeight() - 100) / min; 
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));
			Point box;
			
			for (int row = 1; row <= snakeMap.length; row++) {
				for (int col = 1; col <= snakeMap[0].length; col++) {
					box = snakeMap[row - 1][col - 1];
					switch (box.getState()) {
					case GameEngine.FOOD:
						g2d.setColor(FOOD_COLOR);
						break;
					case GameEngine.SNAKE:
						g2d.setColor(SNAKE_COLOR);
						break;
					default:
						g2d.setColor(EMPTY_COLOR);
					}
					g2d.fillRect(col*gridSize, row*gridSize, gridSize, gridSize);
				}
			}
		}
		
		public void updateMap(Point[][] snakeMap) {
			this.snakeMap = snakeMap;
		}
		
		public int getRows() {
			return rows;
		}
		
		public int getCols() {
			return cols;
		}
		
}
