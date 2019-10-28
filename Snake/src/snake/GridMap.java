package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GridMap extends JPanel {

		// Frame containing the grid mapping of the game
		private int x1, x2, y1, y2;
	
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.black);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.red);
			
			final int GRID_SIZE = 600 / 14 - 2;

			for (int j = 0; j < 14; j++) {
				for (int i = 0; i < 14; i++) {
					g2d.drawRect(i*GRID_SIZE, j*GRID_SIZE, GRID_SIZE, GRID_SIZE);
				}	
			}
			
		}
		
		public void paint() {
			System.out.println("paint");
		}
		
		public void setCoords(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}
	
}
