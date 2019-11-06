package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameEngine implements KeyListener {

	private Snake snake;
	private GridMap map;
	private NavBar navBar;
	private Box[][] snakeMap;
	private ArrayList<Box> snakeBody;
	private Random rand = new Random();
	
	private final int DELAY = 120;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	public static final int EMPTY = 10, SNAKE = 20, FOOD = 30;
	private boolean gameRunning;
	private int rows, cols;
	private int score;
	
	public GameEngine(GridMap map, NavBar navBar) {
		this.map = map;
		this.navBar = navBar;
		this.rows = map.getRows();
		this.cols = map.getCols();
		this.score = 0;
		int snakeSize = 3;
		
		// Initializes the full map and sets the boxes to empty
		snakeMap = new Box[rows][cols];
		snake = new Snake(this, snakeSize, rows / 2, cols / 2);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				snakeMap[row][col] = new Box(row, col);
			}
		}
		
		// Initializes the snake body and sets the correct box-status to SNAKE
		snakeBody = snake.getBody();
		for (Box bodyPart: snakeBody) {
			add(bodyPart);
		}
		
		// Updates frontend game frame and makes it visible
		map.updateMap(snakeMap);
		navBar.updateScore(score);
		
	}
	
	private void generateFood() {
		// Generates food on a random box
		ArrayList<Box> emptyBoxes = new ArrayList<Box>();
		Box potentialFood;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				potentialFood = snakeMap[row][col];
				
				if (potentialFood.getState() == EMPTY) {
					emptyBoxes.add(potentialFood);
				}
			}
		}
		
		Box foodPosition = emptyBoxes.get(rand.nextInt(emptyBoxes.size()));
		snakeMap[foodPosition.getRow()][foodPosition.getCol()].setState(FOOD); 
	}

	public void pop(Box b) {
		// Sets a box to empty
		snakeMap[b.getRow()][b.getCol()].setState(EMPTY);
	}
	
	public void add(Box b) {
		// Adds a new snake box to the map
		snakeMap[b.getRow()][b.getCol()].setState(SNAKE);
	}
	
	private void updateMap() {
		// Updates the frontend with the gridmap
		snakeBody = snake.getBody();
		
		for (Box bodyPart: snakeBody) {
			snakeMap[bodyPart.getRow()][bodyPart.getCol()] = bodyPart;
		}
	}
	
	public void startGame() {
		
		generateFood();
		Timer tick = new Timer(0, new ActionListener() {
			
			int counter = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter == DELAY) {
					counter = 0;
					map.updateMap(snakeMap);
					map.repaint();
					snake.move();
				} else {
					counter++;
				}
			}
		});
		
		tick.setDelay(1);
		tick.setRepeats(true);
		tick.start();
		
	}
	
	public void gameOver() {
		navBar.updateNewBest(score);
		System.out.println("game over");
		map.setVisible(false);
	}
	
	public void onTopOfFood(Box snakeHead) {
		// Checks if snake head is on top of food
		if (snakeMap[snakeHead.getRow()][snakeHead.getCol()].getState() == FOOD) {
			snake.grow();
			generateFood();
			navBar.updateScore(++score);
		}
	}
	
	public boolean collision(int row, int col) {
		// If snake collides with itself or out of game frame it's game over

		try {
			return snakeMap[row][col].getState() == SNAKE;
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
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
			case ' ':
				snake.move();
				break;
			}
	}

	// Unused methods from KeyListener Interface
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public void updateHighscores(String highscores) {
		// highscores is in JSONObject format
		// From here it's sent to the frontend
		JSONArray highscore = new JSONArray(highscores);
		
		for (Object obj : highscore) {
			System.out.println( ((JSONObject)obj).get("Name") );
		}
	}
	
}
