package turtle;

import java.awt.Color;
import java.util.Random;

import se.lth.cs.window.SimpleWindow;

public class TurtleRace extends SimpleWindow {

	private String[] names = {"Leonardo", "Rafael", "Nisse", "Bolt",
							  "Splinter", "Mr Brexit", "Zlatan",
							  "Knugen", "4-Våning", "FastDude"};
	private NinjaTurtle[] turtles;
	private int numberOfTurtles;
	private Random rand = new Random();

	public TurtleRace(int height, int width) {
		super(height, width, "Turtle Race");
		StartMenu startScreen = new StartMenu(this);
		turtles = new NinjaTurtle[numberOfTurtles];
		
		// Creates n ammount of turtles and draws the racetrack
		initTurtles();
		drawRaceTrack();
		countDown();
		
	}
	
	private void initTurtles() {
		int raceWidth = getWidth() / numberOfTurtles;
		
		for (int i = 1; i <= numberOfTurtles; i++) {
			String randomName = pickName();
			turtles[i - 1] = new NinjaTurtle(this, raceWidth * (i - 1) + raceWidth / 2,
					                     getHeight() - 50, randomName);
		}
	}
	
	private void countDown() {
		moveTo(getWidth() / 2, getHeight() / 2);
		writeSpecial("3");
		delay(1000);
		clear();
		drawRaceTrack();
		
		writeSpecial("2");
		delay(1000);
		clear();
		drawRaceTrack();
		
		writeSpecial("1");
		delay(1000);
		clear();
		drawRaceTrack();
		
		writeSpecial("GO");
		delay(1000);
		clear();
		drawRaceTrack();
	}
	
	
	private void writeSpecial(String action) {
		setLineWidth(10);
		setLineColor(Color.red);
		int x = getWidth(), y = getHeight();
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
		
		for (int i = 1; i <= numberOfTurtles; i++) {
			moveTo(raceWidth * i, getHeight());
			lineTo(raceWidth * i, 50);
			moveTo(raceWidth * (i  - 1) + raceWidth / 6, getHeight() - 10);
			writeText(turtles[i - 1].getName());
		}
		
		moveTo(0, 50);
		setLineColor(Color.red);
		lineTo(getWidth(), 50);
		moveTo(getWidth() / 2, 30);
		writeText("GOAL");
	}
	
	private String pickName() {
		
		String name = names[rand.nextInt(names.length)];
		String[] tmp = new String[names.length - 1];
		int index = 0;
		for (String n: names) {
			if (n != name) {
				tmp[index] = n;
				index++;
			}
		}
		names = tmp;
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
			moveTo(getWidth() / 4, getHeight() / 3);
			writeText("Are you ready for some Turtle race?!");
			yesB = new Button(w, Button.YES, (int) (getWidth() / 4), getHeight() / 2);
			noB = new Button(w, Button.NO, (int) (getWidth() / 2), getHeight() / 2);

			if (!proceed()) {
				w.close();
			} else {
				w.clear();
				moveTo(getWidth() / 4, getHeight() / 3);
				writeText("How many Turtles should race?");
				w.moveTo(0, getHeight() / 2);
				w.setLineWidth(2);
				w.lineTo(getWidth(), getHeight() / 2);
				playB = new Button(w, Button.PLAY, (int) (getWidth() / 2.5), getHeight() - getHeight() / 3);

				String keysPressed = "", key = "";
				int action, positioner = 0;
				int nbrOfTurtles = -1;

				while (nbrOfTurtles < 2) {
					w.waitForEvent();
					action = w.getEventType();
					System.out.println(action);
					if (action == 1) {
						key = String.valueOf(w.getKey());
						keysPressed += key;
						w.moveTo(getWidth() / 2 + positioner, getHeight() / 2 - 10);
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
					moveTo(getWidth() / 4, getHeight() / 3);
					writeText("Tyvärr är 10 Max... Vi kör på det!");
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
