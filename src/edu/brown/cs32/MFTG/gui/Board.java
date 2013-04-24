package edu.brown.cs32.MFTG.gui;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.PropertyColor;
import edu.brown.cs32.MFTG.gui.Constants.PropertyInfo;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;
import edu.brown.cs32.MFTG.gui.spaces.Property;
import edu.brown.cs32.MFTG.gui.spaces.PropertyButton;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private BufferedImage image;
	private GridBagConstraints _c;
	
	public Board() {
		super();

		/* Set initialization information */
		java.awt.Dimension size = new java.awt.Dimension(9*Constants.WIDTH + 2*Constants.HEIGHT, 9*Constants.WIDTH + 2*Constants.HEIGHT);
		this.setPreferredSize(size);
		this.setSize(size);
		this.setBackground(Color.GRAY);
		
		setLayout(new GridBagLayout());
		_c = new GridBagConstraints();
		
		fillBoard();
	}
	
	public void fillBoard () {
		JPanel panel = new JPanel(new GridLayout(1, 1, 0, 0));
		Property property = new Property(this, PropertyInfo.MEDITERRANEAN_AVENUE);
		panel.add(property.getMe());
		_c.gridx = 9;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		property = new Property(this, PropertyInfo.BALTIC_AVENUE);
		panel.add(property.getMe());
		_c.gridx = 7;
		_c.gridy = 10;
		this.add(panel, _c);
		
		
		property = new Property(this, PropertyInfo.ORIENTAL_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 4;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.VERMONT_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 2;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.CONNECTICUT_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 1;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.ST_CHARLES_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 0;
		_c.gridy = 9;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.STATES_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 0;
		_c.gridy = 7;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.VIRGINIA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 0;
		_c.gridy = 6;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.ST_JAMES_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.weightx = 1;
		_c.gridx = 0;
		_c.gridy = 4;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.TENNESSEE_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 0;
		_c.gridy = 3;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.NEW_YORK_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 0;
		_c.gridy = 1;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.KENTUCKY_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.weightx = 1;
		_c.gridx = 1;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.INDIANA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 3;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.ILLINOIS_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 4;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.ATLANTIC_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 6;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.VENTNOR_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 7;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.MARVIN_GARDENS);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 9;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.PACIFIC_GARDEN);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 10;
		_c.gridy = 1;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.NORTH_CAROLINA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 10;
		_c.gridy = 2;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.PENNSYLVANIA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 10;
		_c.gridy = 4;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.PARK_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 10;
		_c.gridy = 7;
		this.add(panel, _c);
		
		property = new Property(this, PropertyInfo.BOARDWALK);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_c.gridx = 10;
		_c.gridy = 9;
		this.add(panel, _c);
	}
	
	public void put (PropertyButton oldButton, PropertyButton newButton) {
		
	}
}
