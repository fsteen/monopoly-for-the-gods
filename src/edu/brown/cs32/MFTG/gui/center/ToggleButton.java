package edu.brown.cs32.MFTG.gui.center;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Balance;
import edu.brown.cs32.MFTG.gui.Constants.Toggle;

public class ToggleButton extends JButton {
	
	private Toggle _toggle;
	
	public ToggleButton(Toggle toggle) {
		super();
		
		Dimension d = new Dimension (Constants.BUTTON_DIMENSION, Constants.BUTTON_DIMENSION);
		this.setSize(Constants.BUTTON_DIMENSION, Constants.BUTTON_DIMENSION);
		this.setPreferredSize(d);
		
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.addActionListener(new MyListener(this));
		
		_toggle = toggle;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(_toggle.getIcon(), (int) (Constants.BUTTON_DIMENSION/2. - _toggle.getIcon().getWidth(null)/2.), (int) (Constants.BUTTON_DIMENSION/2. - _toggle.getIcon().getHeight(null)/2.), null); 
	}
	
	private class MyListener implements ActionListener {

		private JButton _button;
		public MyListener (JButton button) {
			_button = button;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			_toggle = _toggle.next();
		}
		
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ToggleButton(Balance.EVEN));
		frame.pack();
		frame.setVisible(true);
	}
	
	public void setValue (Toggle toggle) {
		_toggle = toggle;
	}
	
	public Toggle getValue() {
		return _toggle;
	}

}
