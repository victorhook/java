package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameEngine implements Runnable, KeyListener {

	private GridMap map;
	private Snake snake;
	private Random rand = new Random();
	private final int DELAY = 300;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	public static final int EMPTY = 0, SNAKE = 10, FOOD = 20;
	private boolean foodOnMap, gameRunning;
	
	public GameEngine(GridMap map, int snakeSize) {
		this.map = map;
		snake = new Snake(this, map.getRows(), map.getCols(), snakeSize);
		map.updateMap(snake.getMap());
	}
	
	public void newFood(Point[][] snakeMap) {
		ArrayList<Point> foodList = new ArrayList<Point>(); 
		Point box;
		
		for (int row = 0; row < snakeMap.length; row++) {
			for (int col = 0; col < snakeMap[0].length; col++) {
				box = snakeMap[row][col];
				if (box.getState() == EMPTY) {
					foodList.add(box);
				}
			}
		}
		
		// Picks one of the empty boxes on random and places food in that box
		Point randomBox = foodList.get(rand.nextInt(foodList.size()));
		snakeMap[randomBox.getRow()][randomBox.getCol()].setState(FOOD);
	}
	
	public void gameOver() {
		gameRunning = false;
	}
	
	public void play() {
		gameRunning = true;


		
		
		
		Thread game = new Thread(this);
		game.start();
	}
	
	public void run() {
		
		while (gameRunning) {
			
			snake.move();
			map.updateMap(snake.getMap());
			map.repaint();
			snake.print();
			try {
				Thread.sleep(DELAY);
			} catch (Exception e) {}
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// The keys 'W' 'A' 'S' 'D' are used to navigate the snake
		
		char c = arg0.getKeyChar();
		
		switch (c) {
			case 'w':
				snake.turn(UP);
				break;
			case 'a':
				snake.turn(LEFT);
				break;
			case 's':
				snake.turn(DOWN);
				break;
			case 'd':
				snake.turn(RIGHT);
				break;
			}
	}

	// Unused methods from KeyListener Interface
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
}
