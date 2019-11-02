package snake;

import java.util.ArrayList;
import java.util.Arrays;

public class Snake {

	// The Instruction buffer includes instructions for the tail to follow.
	// The format is STEPS, DIRECTION. Whenever the buffer is empty, the
	// tail takes 1 step in the direction of the head
	private ArrayList<Instruction> buffer;
	private Point[][] snakeMap;
	private BodyPart head, tail;
	private GameEngine gameEngine;
	private int rows, cols, size, tailTmpDirection;
	private boolean snakeShouldGrow;

	public Snake(GameEngine gameEngine, int rows, int cols, int size) {
		this.gameEngine = gameEngine;
		this.rows = rows;
		this.cols = cols;
		this.size = size;
		buffer = new ArrayList<Instruction>();

		// Initializes the snake body
		snakeMap = new Point[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				snakeMap[row][col] = new Point(row, col);
			}
		}

		head = new BodyPart(rows / 2, cols / 2);
		tail = new BodyPart(head.row + size, head.col);
		tailTmpDirection = GameEngine.UP;
		
		for (int i = 0; i < size; i++) {
			snakeMap[head.row + i][head.col].setState(GameEngine.SNAKE);
		}

		buffer.add(new Instruction(size - 1, GameEngine.UP));
		gameEngine.newFood(snakeMap);

	}

	public void move() {

		if (collision()) {
			gameEngine.gameOver();
		}
		
		System.out.println("BUFFER IS : " + buffer.toString());
		
		if (tail.steps == 0) {
			
			if (!buffer.isEmpty()) {
				// If there's instruction to fetch and the current steps of the tail
				// is 0, the next instruction is fetched.
				tail.steps = buffer.get(0).steps;
				tail.direction = buffer.get(0).direction;
				buffer.remove(0);
				
				if (tail.steps == 0) {
					tail.steps = 1;
					tailTmpDirection = tail.direction;
				}
				
			} else {
				tail.steps = 1;
				tail.direction = tailTmpDirection;
			}
		}
		
		System.out.println(String.format("Steps: %s, Direction: %s", tail.steps, tail.direction));

		switch (head.direction) {
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

		head.steps++;
		checkForFood(head.row, head.col);
		snakeMap[head.row][head.col].setState(GameEngine.SNAKE);

		// If the snake should grow the tail doesn't move for 1 tick
		if (snakeShouldGrow) {
			snakeShouldGrow = false;
		} else {
			switch (tail.direction) {
			// Handles the movement of the tail
			case GameEngine.UP:
				tail.row--;
				break;
			case GameEngine.RIGHT:
				tail.col++;
				break;
			case GameEngine.DOWN:
				tail.row++;
				break;
			case GameEngine.LEFT:
				tail.col--;
				break;
			}
			snakeMap[tail.row][tail.col].setState(GameEngine.EMPTY);
			tail.steps--;
		}

	}
	
	private void checkForFood(int row, int col) {
		// Checks if current position of the head is on top of food.
		if (snakeMap[row][col].getState() == GameEngine.FOOD) {
			gameEngine.newFood(snakeMap);
			grow();
			System.out.println("Food!");
		}
	}
	
	public Point[][] getMap() {
		return snakeMap;
	}
	

	private boolean collision() {
		// Checks if the snake has collided with itself or is out of the map
		switch (head.direction) {
		case GameEngine.UP:
			return hit(head.row - 1, head.col);
		case GameEngine.RIGHT:
			return hit(head.row, head.col + 1);
		case GameEngine.DOWN:
			return hit(head.row + 1, head.col);
		case GameEngine.LEFT:
			return hit(head.row, head.col - 1);
		}
		return false;
	}

	private boolean hit(int row, int col) {
		try {
			if (snakeMap[row][col].getState() == GameEngine.SNAKE) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
		return false;
	}

	public void grow() {
		snakeShouldGrow = true;
	}

	public void turn(int direction) {
		buffer.add(new Instruction(head.steps, head.direction));
		System.out.println("adding new INstruction: " + new Instruction(head.steps, head.direction));
		head.direction = direction;
		head.steps = 0;
	}

	public void print() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				System.out.print(snakeMap[row][col]);
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------");
	}

	class BodyPart {
		int row, col, direction, steps;

		BodyPart(int row, int col) {
			this.row = row;
			this.col = col;
			direction = GameEngine.UP;
			steps = 0;
		}

		public String toString() {
			return String.format("%s %s", row, col);
		}
	}

	class Instruction {
		int steps, direction;

		Instruction(int steps, int direction) {
			this.steps = steps;
			this.direction = direction;
		}
		public String toString() {
			return steps +", " + direction;
		}
	}

}
