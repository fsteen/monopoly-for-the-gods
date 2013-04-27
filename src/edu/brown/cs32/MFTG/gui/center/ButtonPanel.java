package edu.brown.cs32.MFTG.gui.center;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Aggression;
import edu.brown.cs32.MFTG.gui.Constants.Balance;
import edu.brown.cs32.MFTG.gui.Constants.Price;
import edu.brown.cs32.MFTG.gui.Constants.Quantity;

public class ButtonPanel extends JPanel {
	
	public ButtonPanel() {
		super();
		this.setSize(9*Constants.WIDTH, 3*Constants.WIDTH);
		this.setLayout(new GridBagLayout());
		
		setup();
	}
	
	public void setup() {
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Price.CHEAP));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 0;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Balance.EVEN));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 1;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Aggression.PASSIVE));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 2;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Price.CHEAP));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 3;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Quantity.FEWER));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 4;
		c.gridy = 0;
		this.add(panel, c);
		
		panel = new JPanel(new GridLayout(1,1));
		panel.add(new ToggleButton(Price.CHEAP));
		c.weightx = 1;
		c.weighty = 2;
		c.gridx = 5;
		c.gridy = 0;
		this.add(panel, c);
	}
}
