package turtle;

import se.lth.cs.window.SimpleWindow;

public class NinjaTurtle {

	private SimpleWindow w;
	private int xHome, yHome;
	private int x, y;
	private int direction;
	private boolean penIsDown;
	private String name;
	
    public NinjaTurtle(SimpleWindow w, int xHome, int yHome, String name) {
		  this.w = w;
		  this.xHome = x = xHome;
		  this.yHome = y = yHome;
		  this.name = name;
		  direction = 90;
		  penIsDown = false;
		  penDown();
		  forward(10);
	  }

      public String getName() {
    	  return name;
      }
    
	  void penDown() {
		  penIsDown = true;
	  }

	  void penUp() {
		  penIsDown = false;
	  }

	  void forward(int n) {
		  
		  w.moveTo(x,  y);
		  int newX = (int) Math.round( ( x + n * Math.cos(Math.toRadians(direction)) ) );
		  int newY = (int) Math.round( ( y - n * Math.sin(Math.toRadians(direction)) ) );

		  if (penIsDown) {
			  w.lineTo(newX, newY);
		  } else {
			  w.moveTo(newX, newY);
		  }
		  
		  x = w.getX();
		  y = w.getY();
	  }

	  void left(int beta) {
		  direction += beta;
	  }

	  void right(int beta) {
		  direction -= beta;
	  }

	  void jumpTo(int newX, int newY) {
		  w.moveTo(newX, newY);
	  }

	  void turnNorth() {
		  direction = 90;
	  }

	  int getX() {
		  return x;
	  }

	  int getY() {
		  return y;
	  }
	
}
