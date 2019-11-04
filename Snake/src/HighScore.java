import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


public class HighScore extends JPanel {

	public HighScore() {
		setMinimumSize(new Dimension(600, 600));
		setPreferredSize(new Dimension(600, 600));
		setBackground(MainGame.GRAY);
		setLayout(new GridBagLayout());
		
		JPanel frame = new JPanel();
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(600, 100));
		frame.setPreferredSize(new Dimension(600, 100));
		frame.setBackground(MainGame.GRAY);
		
		JLabel highScoreLabel = new JLabel("Highscores");
		highScoreLabel.setFont(new Font("Courier", Font.BOLD, 35));
		highScoreLabel.setBackground(MainGame.GRAY);
		highScoreLabel.setPreferredSize(new Dimension(600, 100));
		
		Object[][] data = { {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)}, {"Olof", new Integer(2)},  };
		String[] cols = {"User", "Score"};
		
		JTable highscore = new JTable(data, cols);
		highscore.setPreferredSize(new Dimension(500, 500));
		highscore.setFont(new Font("Courier", 0, 20));
		highscore.setRowHeight(20);
		highscore.setEnabled(false);
		highscore.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane scroll = new JScrollPane(highscore);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.createVerticalScrollBar();

		StyledBtn backBtn = new StyledBtn("Back to menu", new Dimension(300, 50));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridy = 1;
		frame.add(backBtn, gbc);
		gbc.gridy = 0;
		frame.add(highScoreLabel, gbc);
		
		gbc.gridx = 0;
		add(frame, gbc);

		gbc.gridy = 1;
		add(scroll, gbc);
		
	}
	
}
