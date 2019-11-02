package snake;

public class Point {

	private int row;
	private int col;
	private int state;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
		this.state = GameEngine.EMPTY;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setRow() {
		this.row = row;
	}
	
	public void setCol() {
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getState() {
		return state;
	}
	
	public String toString() {
		return String.format(" %s ", state);
	}
	
}
