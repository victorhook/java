package snake;

public class GameEngine implements Runnable {

	private GridMap map;
	private Snake snake;
	private final int DELAY = 500;
	private Direction direction;
	
	public GameEngine(GridMap map, Snake snake) {
		this.map = map;
		this.snake = snake;
		direction = Direction.UP;
	}
	
	public void run() {
		
		System.out.println("Game started");
		boolean gameRunning = true;
		while (gameRunning) {
			
			if (!map.mapHasFood()) {
				map.newFood();	
			}
			
			move(snake);
			map.updateMap(snake.getBody());
			map.repaint();
			try {
				//Thread.currentThread();
				Thread.sleep(DELAY);
			} catch (Exception e) {}
		}
		
	}

	private void move(Snake snake) {
		snake.move(direction);
			
	}
	
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
