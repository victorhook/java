package turtle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import se.lth.cs.window.SimpleWindow;

public class TurtleRace extends SimpleWindow implements Runnable{

	private ArrayList<String> names;
	public ArrayList<Color> colors;
	private NinjaTurtle[] turtles;
	
	private int numberOfTurtles = 5, turtleSpeed, stepRange;
	private int width, height;
	private final int FINISH_LINE = 50;
	private Random rand = new Random();
	public boolean gameOn, oneMoreRound;

	public TurtleRace(int height, int width) {
		super(height, width, "Turtle Race");
		this.turtleSpeed = 100;
		this.stepRange = 10;
		this.width = width;
		this.height = height;
		this.gameOn = true;
		this.oneMoreRound = false;
		
	}
	
	public boolean gameIsOn() {
		return gameOn;
	}
	
	public void quit() {
		gameOn = false;
	}

	
	public void newGame() {
		clear();
		colors = new ArrayList<Color>();
		names = new ArrayList<String>();
		addColors();
		addNames();
		
		new StartMenu(this);
		turtles = new NinjaTurtle[numberOfTurtles];

		// Creates n ammount of turtles and draws the racetrack
		initTurtles();
		drawRaceTrack();
		countDown();
	}
	
	
	private void update() {
		clear();
		drawRaceTrack();
		for (NinjaTurtle t: turtles) {
			t.draw();
		}
	}
	
	public void run() {
		int step;
		while (gameRunning()) {
			clear();
			for (NinjaTurtle t: turtles) {
				step = rand.nextInt(stepRange);
				t.forward(step);
			}
			update();
			try {
				Thread.sleep(turtleSpeed);
			} catch (Exception e) {
				
			}
		}
		
		ArrayList<NinjaTurtle> winner = getWinner();
		if (winner.size() > 1) {
			announceTie(winner);
		} else {
			announceWinner(winner.get(0));
		}
		
		Button play_againB = new Button(this, Button.PLAY_AGAIN, width / 2 - 50, height / 2 + 50);
		
		while (!oneMoreRound) {
			waitForMouseClick();
			oneMoreRound = play_againB.click(getMouseX(), getMouseY());
		}
		
	}

	private void announceWinner(NinjaTurtle winner) {
		moveTo(50, height / 2);
		setLineWidth(30);
		setLineColor(Color.red);
		lineTo(width - 50, height / 2);
		
		setLineWidth(2);
		setLineColor(Color.black);
		moveTo(width / 2 - 50, height / 2);
		writeText(String.format("Winner is %s!", winner.getName()));
	}
	
	private void announceTie(ArrayList<NinjaTurtle> ties) {
		moveTo(50, height / 2);
		setLineWidth(30);
		setLineColor(Color.red);
		lineTo(width - 5, height / 2);
		
		setLineWidth(2);
		setLineColor(Color.black);
		
		moveTo(width / 2 - 100, height / 2);
		String msg = "It's a tie between ";
		for (NinjaTurtle t: ties) {
			msg += t.getName() + " ";
		}
		msg += "!";
		writeText(msg);
	}
	
	private ArrayList<NinjaTurtle> getWinner() {
		ArrayList<NinjaTurtle> winners = new ArrayList<NinjaTurtle>();
		for (NinjaTurtle t: turtles) {
			if (t.hasWon(FINISH_LINE)) {
				winners.add(t);
			}
		}
		return winners;
		
	}
	
	private boolean gameRunning() {
		for (NinjaTurtle t: turtles) {
			if (t.hasWon(FINISH_LINE)) {
				return false;
			}
		}
		return true;
	}
	
	public void setSpeed(int speed) {
		/* turtleSpeed is the ammount of delay between each turtle step */
		this.turtleSpeed = speed;
	}
	
	public void setStepRange(int stepRange) {
		/* Range of how big each step can be */
		this.stepRange = stepRange;
	}
	
	private void addNames() {
	     names.add("Rafael");
	     names.add("Bolt");
	     names.add("Zlatan");
	     names.add("FastDude");
	     names.add("Nisse");
	     names.add("Brexit");
	     names.add("Knugen");
	     names.add("Splinter");
	     names.add("Hubert");
	     names.add("Gustav IX");
	     names.add("Tomten");
	}
	
	private void addColors() {
		colors.add(Color.black);
		colors.add(Color.red);
		colors.add(Color.blue);
		colors.add(Color.cyan);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.yellow);
		colors.add(Color.magenta);
		colors.add(Color.orange);
		colors.add(Color.pink);
		colors.add(Color.WHITE);
	}
	
	public Color getColor() {
		Color c = colors.get(rand.nextInt(colors.size()));
		colors.remove(c);
		return c;
	}
	
	private void initTurtles() {
		int raceWidth = getWidth() / numberOfTurtles;
		
		for (int i = 1; i <= numberOfTurtles; i++) {
			String randomName = pickName();
			turtles[i - 1] = new NinjaTurtle(this, raceWidth * (i - 1) + raceWidth / 2,
					                     getHeight() - 50, randomName);
		}
	}
	
	public void countDown() {
		
		for (NinjaTurtle t: turtles) {
			t.stop();		// Makes sure the turtles are still during countdown
		}
		
		moveTo(width / 2, height / 2);
		writeSpecial("3");
		delay(1000);
		update();

		writeSpecial("2");
		delay(1000);
		update();
		
		writeSpecial("1");
		delay(1000);
		update();
		
		writeSpecial("GO");
		delay(1000);
		update();
		
		for (NinjaTurtle t: turtles) {
			t.start();		// Enables turtles to move again
		}
		
	}
	
	
	private void writeSpecial(String action) {
		setLineWidth(10);
		setLineColor(Color.red);
		int x = width, y = height;
		switch (action) {
		case "1":
			moveTo(x / 2, y / 2 - 20);
			lineTo(x / 2, y / 2 + 20);
			break;
		case "2":
			moveTo(x / 2 - 20, y / 2 - 20);
			lineTo(x / 2 + 20, y / 2 - 20);
			lineTo(x / 2 + 20, y / 2);
			lineTo(x / 2 - 20, y / 2);
			lineTo(x / 2 - 20, y / 2 + 20);
			lineTo(x / 2 + 20, y / 2 + 20);					
			break;
		case "3":
			moveTo(x / 2 - 20, y / 2 - 20);
			lineTo(x / 2 + 20, y / 2 - 20);
			moveTo(x / 2 - 20, y / 2);
			lineTo(x / 2 + 20, y / 2);
			moveTo(x / 2 - 20, y / 2 + 20);
			lineTo(x / 2 + 20, y / 2 + 20);
			moveTo(x / 2 + 20, y / 2 - 20);
			lineTo(x / 2 + 20, y / 2 + 20);
			break;
		case "GO":
			moveTo(x / 2 - 10, y / 2 - 20);
			lineTo(x / 2 - 40, y / 2 - 20);
			lineTo(x / 2 - 40, y / 2 + 20);
			lineTo(x / 2 - 10, y / 2 + 20);
			lineTo(x / 2 - 10, y / 2);
			lineTo(x / 2 - 30, y / 2);
			
			moveTo(x / 2 + 10, y / 2 - 20);
			lineTo(x / 2 + 10, y / 2 + 20);
			lineTo(x / 2 + 40, y / 2 + 20);
			lineTo(x / 2 + 40, y / 2 - 20);
			lineTo(x / 2 + 10, y / 2 - 20);
			break;
		}
		setLineColor(Color.black);
		setLineWidth(3);
	}
	
	
	private void drawRaceTrack() {
		int raceWidth = getWidth() / numberOfTurtles;
		setLineWidth(3);
		for (int i = 1; i <= numberOfTurtles; i++) {
			moveTo(raceWidth * i, getHeight());
			lineTo(raceWidth * i, FINISH_LINE);
			moveTo(raceWidth * (i  - 1) + raceWidth / 6, height - 10);
			writeText(turtles[i - 1].getName());
		}
		
		moveTo(0, FINISH_LINE);
		setLineColor(Color.red);
		lineTo(getWidth(), FINISH_LINE);
		moveTo(getWidth() / 2, 30);
		writeText("GOAL");
	}
	
	private String pickName() {
		String name = names.get(rand.nextInt(names.size()));
		names.remove(name);
		return name;
	}
	

	public void setNumberOfTurtles(int n) {
		numberOfTurtles = n;
	}

	private class StartMenu {

		private TurtleRace w;
		private Button yesB, noB, playB;

		StartMenu(TurtleRace w) {
			this.w = w;
			moveTo(width / 4, height / 3);
			writeText("Are you ready for some Turtle race?!");
			yesB = new Button(w, Button.YES, (int) (width / 5), height / 2);
			noB = new Button(w, Button.NO, (int) ((width / 1.5)), height / 2);

			if (!oneMoreRound) {
				if (!proceed()) {
					w.quit();	
			}
					w.clear();
					moveTo(width / 4, height / 3);
					writeText("How many Turtles should race?");
					w.moveTo(width / 2 - 20, height / 2);
					w.setLineWidth(2);
					w.lineTo(width / 2 + 20, height / 2);
					playB = new Button(w, Button.PLAY, (int) (width / 2.5), height - height / 3);

					String keysPressed = "", key = "";
					int action, positioner = -7;
					int nbrOfTurtles = -1;

					while (nbrOfTurtles < 2) {
						w.waitForEvent();
						action = w.getEventType();
						if (action == 1) {
							key = String.valueOf(w.getKey());
							keysPressed += key;
							w.moveTo(width / 2 + positioner, height / 2 - 10);
							w.writeText(key);
							positioner += 7;
						} else {
							if (playB.click(w.getMouseX(), w.getMouseY())) {
								nbrOfTurtles = Integer.parseInt(keysPressed);
							}
						}
					}
					w.clear();
					if (nbrOfTurtles > 10) {
						nbrOfTurtles = 10;
						moveTo(width / 4, height / 3);
						writeText("Tyv�rr �r 10 Max... Vi k�r p� det!");
						w.delay(3000);
						w.clear();
					}
					w.setNumberOfTurtles(nbrOfTurtles);
				}
		}

		private boolean proceed() {

			boolean buttonPressed = false;
			boolean shouldProceed = false;

			while (!buttonPressed) {
				w.waitForMouseClick();
				int xc = w.getMouseX();
				int yc = w.getMouseY();
				if (noB.click(xc, yc)) {
					buttonPressed = true;
				} else if (yesB.click(xc, yc)) {
					shouldProceed = true;
					buttonPressed = true;
				}
			}
			return shouldProceed;
		}

	}

}
