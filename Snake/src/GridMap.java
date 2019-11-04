import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridMap extends JPanel {

	private int ROWS = 20, COLS = 20, GRID_SIZE = 25;
	private Box[][] snakeMap;
	private final Color FOOD_COLOR = Color.yellow, SNAKE_COLOR = Color.green, EMPTY_COLOR = Color.black;
	
	public GridMap() {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(Color.black);
	}
	
	public void updateMap(Box[][] snakeMap) {
		this.snakeMap = snakeMap;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		Box box;
		
		for (int row = 0; row < snakeMap.length; row++) {
			for (int col = 0; col < snakeMap[0].length; col++) {
				box = snakeMap[row][col];
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
				g2d.fillRect((col + 2)*GRID_SIZE, row*GRID_SIZE, GRID_SIZE, GRID_SIZE);
			}
		}
		
		// Outer frame
		g2d.setColor(Color.red);
		g2d.drawRect(50, 0, getWidth() - 100, getHeight());
	}
	
	public int getRows() {
		return ROWS;
	}
	public int getCols() {
		return COLS;
	}
	
}
