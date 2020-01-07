package turtle;

import java.awt.Color;

import se.lth.cs.window.SimpleWindow;

public class NinjaTurtle {

	private SimpleWindow w;
	private int xHome, yHome;
	private int x, y;
	private int direction;
	private boolean rightFoot, stop;
	private final int WIDTH = 15, HEIGHT = 20, FOOT_SIZE = 5, CRAWL_SIZE = 3;
	private String name;
	private Color bandColor;
	
    public NinjaTurtle(TurtleRace w, int xHome, int yHome, String name) {
		  this.w = w;
		  this.xHome = x = xHome;
		  this.yHome = y = yHome;
		  this.name = name;
		  this.direction = 90;
		  this.bandColor = w.getColor();
	  }
    
      public void draw() {
    	  int tmpX = x;
    	  int tmpY = y;
    	  
    	  w.setLineWidth(WIDTH);
    	  w.setLineColor(Color.green);
    	  w.moveTo(x, y);
    	  w.lineTo(x, y - HEIGHT);
    	  
    	  w.setLineWidth(2);
    	  w.setLineColor(Color.black);
    	  
    	  
    	  final int TURN = 20;
    	  final int HEAD_SIZE = 6;
    	  x = w.getX();
    	  y = w.getY();
    	  w.moveTo(x + WIDTH / 2, y);
    	  
    	  for (int v = 1; v <= TURN; v++) {
    		  int dx = (int) ( HEAD_SIZE *  ( Math.cos(Math.toRadians(v * TURN))) );
    		  int dy = (int) ( HEAD_SIZE *  ( Math.sin(Math.toRadians(v * TURN))) );
    		  w.lineTo(x + dx, y - dy);
    	  }
    	  
    	  x = tmpX;
    	  y = tmpY;
    	  w.moveTo( (int) (x - Math.round(WIDTH / 2.0) ), y - HEIGHT / 2);
    	  w.setLineWidth(2);
    	  w.setLineColor(this.bandColor);
    	  w.lineTo( x + WIDTH / 2, y - HEIGHT / 2);
    	  
    	  w.moveTo(x + WIDTH / 2 + (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE);
    	  w.setLineColor(Color.black);
    	  w.setLineWidth(FOOT_SIZE);
    	  if (rightFoot) {
    		  w.lineTo(x + WIDTH / 2 + (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE - CRAWL_SIZE);
    		  w.moveTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE);
    		  w.lineTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE);
    		  rightFoot = false;
    	  } else if (stop) {
    		  w.lineTo(x + WIDTH / 2 + (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE);
    		  w.moveTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE);
    		  w.lineTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE);
    	  }
    	  else {
    		  w.lineTo(x + WIDTH / 2 + (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE);
    		  w.moveTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE);
    		  w.lineTo(x - WIDTH / 2 - (FOOT_SIZE / 2), y - HEIGHT + FOOT_SIZE - FOOT_SIZE - CRAWL_SIZE);
    		  rightFoot = true;
    	  }
    	  
    	  x = tmpX;
    	  y = tmpY;
      }
      
  	
  	public void stop() {
  		this.stop = true;
  	}
  	
  	public void start() {
  		this.stop = false;
  		rightFoot = true;
  	}
      
      public boolean hasWon(int finishLine) {
    	  return y < finishLine;
      }

      public String getName() {
    	  return name;
      }

	  void forward(int n) {
		  x = (int) Math.round( ( x + n * Math.cos(Math.toRadians(direction)) ) );
		  y = (int) Math.round( ( y - n * Math.sin(Math.toRadians(direction)) ) );
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
