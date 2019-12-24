import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// TODO : Fix dragging effect to make obstacle making easier


public class MazeGUI extends JFrame implements MouseListener, MouseMotionListener {

	/* BACKGROUND is default color for the boxes. 
	 * ABNORMAL represents all the obstacles and barriers of the maze
	 * TEMPORARY represents the paths that have been visited but didn't lead to the exit
	 * PATH represents the chosen path from the beginning to the exit
	 */
	public static final Color BACKGROUND = Color.white, ABNORMAL = Color.BLACK,
						      TEMPORARY = Color.LIGHT_GRAY, PATH = Color.green;
	private Box[][] boxMatrice;
	private boolean btnReleased;
	
	MazeGUI(int rows, int cols) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JButton solveBtn = new JButton("Solve");
		solveBtn.addActionListener((action) -> findMazePath());
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener((action) -> reset());

		JPanel btns = new JPanel();
		btns.setLayout(new BoxLayout(btns, BoxLayout.X_AXIS));
		btns.add(solveBtn);
		btns.add(resetBtn);
		
		JPanel gridMap = new JPanel();
		gridMap.setLayout(new GridLayout(rows, cols));
		
		
		boxMatrice = new Box[rows][cols]; 
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				boxMatrice[row][col] = new Box(row, col, this);
				gridMap.add(boxMatrice[row][col]);
			}
		}

		add(btns);
		add(gridMap);
		pack();
		
	}
	
	/* Resets the matrice to the default settings */
	private void reset() {
		for (int row = 0; row < boxMatrice.length; row++) {
			for (Box box : boxMatrice[row]) {
				box.setColor(BACKGROUND);
			}
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MazeGUI gui = new MazeGUI(10, 10);
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);	
			}
			
		});
	}
	
	/* Represents the boxes in the maze */
	class Box extends JLabel {
		int row, col;
		boolean rdyToFlip;
		Color color;
		
		Box(int row, int col, MazeGUI parent) {
			this.row = row;
			this.col = col;
			
			setOpaque(true);
			setColor(this.color = BACKGROUND);
			setBorder(BorderFactory.createLineBorder(Color.black));
			setMinimumSize(new Dimension(25, 25));
			setPreferredSize(new Dimension(25,25));
			addMouseListener(parent);
			addMouseMotionListener(parent);
		}
		
		/* Helper methods to make the recursion code cleaner */
		void setColor(Color color) {
			setBackground(this.color = color);
		}
		void addToPath() {
			setBackground(this.color = PATH);
		}
		void recolorToVisited() {
			setBackground(this.color = TEMPORARY);
		}
		boolean hasBeenVisited() {
			return this.color != BACKGROUND;
		}
		boolean isMazeExit() {
			return this.row == boxMatrice.length - 1 && this.col == boxMatrice[0].length - 1;
		}
	}
	
	// Wrapper class for the mazefinder
	boolean findMazePath() {
		if (!findMazePath(0, 0)) {
			System.out.println("Can't solve it!");
			return false;
		} else {
			return true;
		}
	}
	
	/* Recursive method to find a path through the maze */
	private boolean findMazePath(int row, int col) {

		// return false if the row or col is out of bounds for the matrice
		if (row < 0 || col < 0 || row >= boxMatrice.length || col >= boxMatrice[0].length) {
			return false;
		} 
		// return false if the current box already has been visited
		else if (boxMatrice[row][col].hasBeenVisited()){
			return false;
		} 
		// return true if the current box is the end of the maze (Path has been found!)
		else if (boxMatrice[row][col].isMazeExit()) {
			boxMatrice[row][col].addToPath();
			return true;
		} 
		// If none of the above returns, the recursion starts
		else {
			
			boxMatrice[row][col].addToPath();

			// Checks the 4 boxes around the current box to see if it recursively can
			// find a path to the exit of the maze
			if (findMazePath(row - 1, col) || findMazePath(row + 1, col) ||
				findMazePath(row, col - 1) || findMazePath(row, col + 1)) {
				return true;
			} 
			// If no path to the exit can be found, the current box is marked as visited
			// and the method return false to start mark a dead end.
			else {
				boxMatrice[row][col].recolorToVisited();
				return false;	
			}
		}
	}

	// TODO : FIX DRAGGING EFFECT
	/* Makes it easy to mark obstacles on the GUI. 
	 * (Click to create/destroy an obstacle */ 
	@Override
	public void mouseClicked(MouseEvent item) {
		if (item.getSource() instanceof Box) {
			Box box = (Box) item.getSource();
			
			if (!btnReleased) {
				if (box.rdyToFlip) {
					if (box.color == BACKGROUND) {
						box.setColor(ABNORMAL);
					} else if (box.color == ABNORMAL) {
						box.setColor(BACKGROUND);
					}		
					box.rdyToFlip = false;
				}
			} else {
				box.rdyToFlip = true;
				
				if (box.color == BACKGROUND) {
					box.setColor(ABNORMAL);
				} else if (box.color == ABNORMAL) {
					box.setColor(BACKGROUND);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent item) {}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		btnReleased = false;
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		btnReleased = true;
	}

	@Override
	public void mouseMoved(MouseEvent item) {
		//mouseClicked(item);
	}	
}
