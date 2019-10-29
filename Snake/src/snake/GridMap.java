package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GridMap extends JPanel {

		// Frame containing the grid mapping of the game
		private Point[][] map;
		private final int GRID_SIZE = 35;
		private Random rand = new Random();
		private boolean foodOnMap;
		int r, c;
		
		public GridMap() {
			setBackground(Color.black);
			map = new Point[14][14];
			for (int row = 0; row < 14; row++) {
				for (int col = 0; col < 14; col++) {
					map[row][col] = new Point(row, col);
				}
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));
			
			System.out.println(map[r][c].getState());
			
			for (int row = 0; row < 14; row++) {
				for (int col = 0; col < 14; col++) {

					switch (map[row][col].getState()) {
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
		
		public boolean mapHasFood() {
			return foodOnMap;
		}
		
		public void updateMap(Point[][] snakeBody) {
			map = snakeBody;
		}
		
	    public void newFood() {
	    	// Checks which boxes are empty and draws a random number from them
	    	// to generate a new food
	    	System.out.println("New food!");
	    	ArrayList<Point> emptyPoints = new ArrayList<Point>();
	    	
	    	for (int row = 0; row < 14; row++) {
	    		for (int col = 0; col < 14; col++) {
	    			if (map[row][col].getState() == PointState.EMPTY) {
	    				emptyPoints.add(map[row][col]);
	    			}
	    		}
	    	}
	    	
	    	// Grabs one of the empty points on random and changes it state to food
	    	int n = rand.nextInt(emptyPoints.size());
	    	r = emptyPoints.get(n).getRow();
	    	c = emptyPoints.get(n).getCol();
	    	System.out.println(emptyPoints.get(n));
	    	map[r][c].setState(PointState.FOOD);
	    	emptyPoints.get(n).setState(PointState.FOOD);
	    	
	    	foodOnMap = true;
	    	
	    }
		
}
