package turtle;

public class Main {

	public static void main(String[] args) {
	
		TurtleRace t = new TurtleRace(400, 400);
		
		while (t.gameIsOn()) {
			t.newGame();
			t.run();
		}
		
		t.close();
		
	}
}
