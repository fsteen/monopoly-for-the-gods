package edu.brown.cs32.MFTG.gui;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;
import edu.brown.cs32.MFTG.gui.spaces.PropertyButton;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JPanel;

public class Board extends JPanel {

	private BufferedImage image;
	private GridBagConstraints _c;
	
	public Board() {
		super();

		/* Set initialization information */
		java.awt.Dimension size = new java.awt.Dimension(9*Monopoly.WIDTH + 2*Monopoly.HEIGHT, 9*Monopoly.WIDTH + 2*Monopoly.HEIGHT);
		this.setPreferredSize(size);
		this.setSize(size);
		this.setBackground(Color.GRAY);
		
		setLayout(new GridBagLayout());
		_c = new GridBagConstraints();
	}
	
	public void put (PropertyButton oldButton, PropertyButton newButton) {
		this.remove(oldButton);
		this.add(newButton);
		this.revalidate();
	}
}
