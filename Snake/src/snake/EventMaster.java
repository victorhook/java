package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EventMaster implements KeyListener {

	private GameEngine engine;
	
	public EventMaster(GameEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// Sends the corresponding action that should be taken when key is pressed
		// its WASD mapping, for UP - LEFT - DOWN - RIGHT
		char c = arg0.getKeyChar();
		/*
		switch (c) {
		
		case 'w':
			engine.moveUp();
			break;
		case 'a':
			engine.moveLeft();
			break;
		case 's':
			engine.moveDown();
			break;
		case 'd':
			engine.moveRight();
			break;
		}
		*/
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
}

	