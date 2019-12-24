package game;
import java.util.ArrayList;

public class Snake {

	private ArrayList<Box> body;
	private GameEngine engine;
	private Head head;
	private int size, direction, lastIn;
	private boolean freeze;
	
	public Snake(GameEngine engine, int size, int startRow, int startCol) {
		this.engine = engine;
		this.size = size;
		this.direction = engine.UP;
		head = new Head(startRow, startCol);
		body = new ArrayList<Box>();
		
		for (int i = 0; i < size; i++) {
			body.add(new Box(startRow + i, startCol, GameEngine.SNAKE));
		}
		
	}

	public void move() {
		
		if (collision()) {
			engine.gameOver();
		}
		
		switch (direction) {
		// Handles the movement of the head
		case GameEngine.UP:
			head.row--;
			break;
		case GameEngine.RIGHT:
			head.col++;
			break;
		case GameEngine.DOWN:
			head.row++;
			break;
		case GameEngine.LEFT:
			head.col--;
			break;
		}
		
		// Checks if snake head is on top of food
		body.add(0, new Box(head.row, head.col, GameEngine.SNAKE));
		engine.onTopOfFood(body.get(0));
		
		engine.add(body.get(0));
		
		lastIn = body.size() - 1;
		
		// Pops the bodypart that was last added to the list, if the snake didn't
		// just eat some food. If it did, the tail freezes for one move
		if (!freeze) {
			engine.pop(body.get(lastIn));
			body.remove(lastIn);
		} else {
			freeze ^= freeze;
		}
		
	}
	
	private boolean collision() {
		// checks if the next move (according to snakes current direction will
		// be a collission or not
		int row = head.row, col = head.col;
		
		if (direction == engine.UP) {
			row--;
		} else if (direction == engine.RIGHT) {
			col++;
		} else if (direction == engine.DOWN) {
			row++;
		} else if (direction == engine.LEFT) {
			 col--;
		}
		
		return engine.collision(row, col);

	}

	
	public void grow() {
		freeze = true;
	}
	
	public ArrayList<Box> getBody() {
		return body;
	}
	
	public void turn(int direction) {
		// Changes direction of the snake, but ensures that the turn isn't 180 degrees
		
		if (this.direction == engine.UP) {
			this.direction = (direction == engine.DOWN) ? this.direction : direction;	
		}
		
		else if (this.direction == engine.DOWN) {
			this.direction = (direction == engine.UP) ? this.direction : direction;	
		}
		
		else if (this.direction == engine.LEFT) {
			this.direction = (direction == engine.RIGHT) ? this.direction : direction;	
		}
		
		else if (this.direction == engine.RIGHT) {
			this.direction = (direction == engine.RIGHT) ? this.direction : direction;	
		}
		
	}
	
	class Head {
		int row, col;
		Head(int row, int col) {
			this.row = row;
			this.col = col;
		}
		public String toString() {
			return row + "; " + col;
		}
	}
	
}
