package turtle;

import se.lth.cs.window.SimpleWindow;

public class Button {

	private SimpleWindow w;
	private int x, y;
	private final int HEIGHT = 70, WIDTH = 100;
	private String btnType;
	public static String YES = "YES", NO = "NO", PLAY = "PLAY";
	
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
		w.moveTo(x + WIDTH / 3, y + HEIGHT / 2);
		w.writeText(btnType);
	}
	
	public boolean click(int xc, int yc) {
		return xc > x && xc < (x + WIDTH) &&
			   yc > y && yc < (y + HEIGHT);
	}
	
}
