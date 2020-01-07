package turtle;

import java.awt.Color;

import se.lth.cs.window.SimpleWindow;

public class Button {

	private SimpleWindow w;
	private int x, y;
	private final int HEIGHT = 70, WIDTH = 100;
	private String btnType;
	public static String YES = "YES", NO = "NO", PLAY = "PLAY", PLAY_AGAIN = "PLAY AGAIN";
	
	public Button(TurtleRace w, String btnType, int x, int y) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.btnType = btnType;
		drawFrame();
	}
	
	private void drawFrame() {
		w.moveTo(x, y);
		w.setLineWidth(3);
		w.lineTo(x, y + HEIGHT);
		w.lineTo(x + WIDTH, y + HEIGHT);
		w.lineTo(x + WIDTH, y);
		w.lineTo(x, y);
		
		if (btnType.equals("PLAY AGAIN")) {
			w.moveTo(x + 1, y + HEIGHT / 2);
			w.setLineWidth(HEIGHT);
			w.setLineColor(Color.green);
			w.lineTo(x + WIDTH, y + HEIGHT / 2);
			w.moveTo(x + WIDTH / 6, y + HEIGHT / 2);
			w.setLineColor(Color.black);
			w.setLineWidth(3);
		} else {
			w.moveTo(x + WIDTH / 3, y + HEIGHT / 2);	
		}
		
		w.writeText(btnType);
	}
	
	public boolean click(int xc, int yc) {
		return xc > x && xc < (x + WIDTH) &&
			   yc > y && yc < (y + HEIGHT);
	}
	
}
