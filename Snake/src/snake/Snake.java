package snake;

public class Snake {

	private boolean[][] body;
	private Head head;
	
	public Snake() {
		// Initializes the snake body and places the head (first position)
		// in the middle of the grid map (row 7, col 7)
		body = new boolean[15][15];
		head = new Head(7, 7);
		body[7][7] = true;
	}
	
	public void grow() {
		
	}
	
	
	class Head {
		int row;
		int col;
		
		public Head(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
	}
}
