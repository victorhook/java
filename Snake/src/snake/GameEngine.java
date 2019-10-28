package snake;

public class GameEngine {

	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	private Direction direction;

	public GameEngine() {
		this.direction = Direction.UP;
	}

	public void moveUp() {
		this.direction = Direction.UP;
	}

	public void moveDown() {
		this.direction = Direction.DOWN;
	}

	public void moveLeft() {
		this.direction = Direction.LEFT;
	}

	public void moveRight() {
		this.direction = Direction.RIGHT;
	}
}
