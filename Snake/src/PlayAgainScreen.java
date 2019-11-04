import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PlayAgainScreen extends JPanel {

	public PlayAgainScreen() {
		setMinimumSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		StyledBtn play = new StyledBtn("Play again"); 
		
		StyledBtn submit = new StyledBtn("Submit score");
		
		StyledBtn highScore = new StyledBtn("Show HighScores");
		
		StyledBtn quit = new StyledBtn("Quit");
		
		gbc.gridy = 0;
		add(play, gbc);
		
		gbc.gridy = 1;
		add(submit, gbc);
		
		gbc.gridy = 2;
		add(highScore, gbc);
		
		gbc.gridy = 3;
		add(quit, gbc);
		
	}
	
	class StyledBtn extends JButton implements MouseListener {
		
		public StyledBtn(String text) {
			super(text);
			setPreferredSize(new Dimension(400, 80));
			setFont(new Font("Courier", Font.BOLD, 25));
			setFocusPainted(false);
			setForeground(MainGame.ORANGE);
			setBackground(MainGame.GRAY);
			setBorder(new LineBorder(Color.black, 3));
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setBackground(MainGame.GRAY2);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setBackground(MainGame.GRAY);
		}

		public void mousePressed(MouseEvent arg0) {
			setBackground(MainGame.GRAY2);
		}
		public void mouseReleased(MouseEvent arg0) {}

	}
	
}
