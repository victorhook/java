package snake;

import java.util.Arrays;

public class Snake {

	private Head head;
	private Point[][] body;

	public Snake() {
		// Initializes the snake body and places the head (first position)
		// in the middle of the grid map (row 7, col 7)
		// The body is recognized as a 1 in the body-matrice
		body = new Point[14][14];
		body = new Point[14][14];
		for (int row = 0; row < 14; row++) {
			for (int col = 0; col < 14; col++) {
				body[row][col] = new Point(row, col);
			}
		}
		body[7][7].setState(PointState.SNAKE);
		head = new Head(7, 7);
	}

	public Point[][] getBody() {
		return body;
	}
	
	public void move(Direction d) {
		switch (d) {
		case UP:
			head.moveUp();
			break;
		case DOWN:
			head.moveDown();
			break;
		case LEFT:
			head.moveLeft();
			break;
		case RIGHT:
			head.moveRight();
			break;
		}
		body[head.position[0]][head.position[1]].setState(PointState.SNAKE);
	}

	class Head {
		// The head leads the snake which means all measurments of distance
		// must be made with the head position

		int[] position;
		final int ROW = 0, COL = 1;

		public Head(int row, int col) {
			// The int array contains the row and column of the current posittion
			position = new int[2];
			position[ROW] = row;
			position[COL] = col;
		}

		int[] getPos() {
			return position;
		}
		
		public void moveUp() {
			position[ROW]--;
		}

		public void moveDown() {
			position[ROW]++;
		}

		public void moveLeft() {
			position[COL]--;
		}

		public void moveRight() {
			position[COL]++;
		}

	}
}
