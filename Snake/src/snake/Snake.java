package snake;

public class Snake {

	private Head head;
	private int[][] body;
	
	public Snake() {
		// Initializes the snake body and places the head (first position)
		// in the middle of the grid map (row 7, col 7)
		body = new int[14][14];
		body[7][7] = 1;
		
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
