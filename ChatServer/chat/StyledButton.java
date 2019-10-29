package chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class StyledButton extends JButton implements MouseListener {

	private int thickness;
	private Color bg;
	private Color fg;
	private Color borderColor;

	public StyledButton(String text, Color bg) {
		super(text);
		this.bg = bg;
		this.fg = Color.black;
		this.borderColor = Color.black;
		this.thickness = 3;

		setFont(new Font("Courier", 0, 15));
		setPreferredSize(new Dimension(100, 40));
		setFocusPainted(false);
		setBackground(this.bg);
		setContentAreaFilled(false);
		addMouseListener(this);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(this.borderColor, this.thickness));
	}

	public void mouseEntered(MouseEvent arg0) {
		setBorder(BorderFactory.createLineBorder(this.borderColor, this.thickness + 1));
	}

	public void mouseExited(MouseEvent arg0) {
		setBorder(BorderFactory.createLineBorder(this.borderColor, this.thickness));
	}

	public void mousePressed(MouseEvent arg0) {
		this.setBackground(Color.white);
	}

	public void mouseReleased(MouseEvent arg0) {
		this.setBackground(this.bg);
	}

	public void mouseClicked(MouseEvent arg0) {
	}
}
