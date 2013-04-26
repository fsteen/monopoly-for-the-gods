package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants.ColorInfo;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.RailRoadInfo;
import edu.brown.cs32.MFTG.gui.colors.ColorButton;
import edu.brown.cs32.MFTG.gui.spaces.CornerButton;
import edu.brown.cs32.MFTG.gui.spaces.EmptyButton;
import edu.brown.cs32.MFTG.gui.spaces.RailRoadUtility;

public class ColorBoard extends JPanel {
	private GridBagConstraints _c;
	public ColorBoard() {
		super();

		/* Set initialization information */
		java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
		this.setPreferredSize(size);
		this.setSize(size);
		this.setBackground(Color.GRAY);
		
		setLayout(new GridBagLayout());
		_c = new GridBagConstraints();
		
		try {
			fillBoard();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fillBoard() throws IOException {
		
		JPanel panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.DOWN, "Deed_Cards/go.jpg"));
		_c.weightx = 1;
		_c.weighty = 1;
		_c.gridx = 4;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.PURPLE));
		_c.weightx = 2;
		_c.weighty = 1;
		_c.gridx = 3;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.DOWN, "Deed_Cards/pink_chance.jpg"));
		_c.weightx = .5;
		_c.weighty = 1;
		_c.gridx = 2;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.LIGHT_BLUE));
		_c.weightx = 2;
		_c.weighty = 1;
		_c.gridx = 1;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.LEFT, "Deed_Cards/jail.jpg"));
		_c.weightx = 1;
		_c.weighty = 1;
		_c.gridx = 0;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.PINK));
		_c.weightx = 1;
		_c.weighty = 2;
		_c.gridx = 0;
		_c.gridy = 3;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.LEFT, "Deed_Cards/pink_chance.jpg"));
		_c.weightx = 1;
		_c.weighty = .5;
		_c.gridx = 0;
		_c.gridy = 2;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.ORANGE));
		_c.weightx = 1;
		_c.weighty = 2;
		_c.gridx = 0;
		_c.gridy = 1;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.UP, "Deed_Cards/free_parking.jpg"));
		_c.weightx = 1;
		_c.weighty = 1;
		_c.gridx = 0;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.RED));
		_c.weightx = 2;
		_c.weighty = 1;
		_c.gridx = 1;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.DOWN, "Deed_Cards/pink_chance.jpg"));
		_c.weightx = .5;
		_c.weighty = 1;
		_c.gridx = 2;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.YELLOW));
		_c.weightx = 2;
		_c.weighty = 1;
		_c.gridx = 3;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.RIGHT, "Deed_Cards/go_to_jail.jpg"));
		_c.weightx = 1;
		_c.weighty = 1;
		_c.gridx = 4;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.GREEN));
		_c.weightx = 1;
		_c.weighty = 2;
		_c.gridx = 4;
		_c.gridy = 1;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.RIGHT, "Deed_Cards/pink_chance.jpg"));
		_c.weightx = 1;
		_c.weighty = .5;
		_c.gridx = 4;
		_c.gridy = 2;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new ColorButton(ColorInfo.DARK_BLUE));
		_c.weightx = 1;
		_c.weighty = 2;
		_c.gridx = 4;
		_c.gridy = 3;
		this.add(panel, _c);
	}

}
