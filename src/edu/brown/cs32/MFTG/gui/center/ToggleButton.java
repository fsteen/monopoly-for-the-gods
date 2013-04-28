package edu.brown.cs32.MFTG.gui.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.brown.cs32.MFTG.gui.Constants.Balance;
import edu.brown.cs32.MFTG.gui.Constants.Price;
import edu.brown.cs32.MFTG.gui.Constants.Quantity;
import edu.brown.cs32.MFTG.gui.Constants.Toggle;

public class ToggleButton extends JButton {
	
	private Toggle _toggle;

	public ToggleButton(Toggle toggle) {
		super();
		this.setSize(100, 100);
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.addActionListener(new MyListener(this));
		
		_toggle = toggle;
		this.setIcon(_toggle.getIcon());
	}
	
	private class MyListener implements ActionListener {

		private JButton _button;
		public MyListener (JButton button) {
			_button = button;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			_toggle = _toggle.next();
			_button.setIcon(_toggle.getIcon());
		}
		
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ToggleButton(Balance.EVEN));
		frame.pack();
		frame.setVisible(true);
	}

}
