package snake;

public class Point {

	private int row;
	private int col;
	private PointState state;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
		this.state = PointState.EMPTY;
	}
	
	public void setState(PointState p) {
		this.state = p;
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
	
	public PointState getState() {
		return state;
	}
	
	public String toString() {
		return String.format("Row: %s,  Col: %s", row, col);
	}
	
}
