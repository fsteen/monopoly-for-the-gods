package edu.brown.cs32.MFTG.gui.old;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.PropertyColor;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private BufferedImage image;
	private GridBagConstraints _c;
	
	private HashMap<ColorProperties, JPanel> _properties = new HashMap<>();
	private HashMap<Railroads, JPanel> _railroads = new HashMap<>();
	
	public Board() {
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
	
	public void fillBoard () throws IOException {
		
		JPanel panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.DOWN, "Deed_Cards/go.jpg"));
		_c.gridx = 10;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1, 1, 0, 0));
		Property property = new Property(this, ColorProperties.MEDITERRANEAN_AVENUE);
		panel.add(property.getMe());
		_properties.put(ColorProperties.MEDITERRANEAN_AVENUE, panel);
		_c.gridx = 9;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.DOWN, "Deed_Cards/community_chest.jpg"));
		_c.gridx = 8;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		property = new Property(this, ColorProperties.BALTIC_AVENUE);
		panel.add(property.getMe());
		_properties.put(ColorProperties.BALTIC_AVENUE, panel);
		_c.gridx = 7;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.DOWN, "Deed_Cards/income_tax.jpg"));
		_c.gridx = 6;
		_c.gridy = 10;
		this.add(panel, _c);

		panel = new JPanel (new GridLayout(1,1,0,0));
		RailRoadUtility railroad = new RailRoadUtility(this, Railroads.READING_RAILROAD);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.READING_RAILROAD, panel);
		_c.gridx = 5;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.DOWN, "Deed_Cards/pink_chance.jpg"));
		_c.gridx = 3;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.ORIENTAL_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.ORIENTAL_AVENUE, panel);
		_c.gridx = 4;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.VERMONT_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.VERMONT_AVENUE, panel);
		_c.gridx = 2;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.CONNECTICUT_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.CONNECTICUT_AVENUE, panel);
		_c.gridx = 1;
		_c.gridy = 10;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.LEFT, "Deed_Cards/jail.jpg"));
		_c.gridx = 0;
		_c.gridy = 10;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.ST_CHARLES_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.ST_CHARLES_PLACE, panel);
		_c.gridx = 0;
		_c.gridy = 9;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		railroad = new RailRoadUtility(this, Railroads.ELECTRIC_COMPANY);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.ELECTRIC_COMPANY, panel);
		_c.gridx = 0;
		_c.gridy = 8;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.STATES_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.STATES_AVENUE, panel);
		_c.gridx = 0;
		_c.gridy = 7;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.VIRGINIA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.VIRGINIA_AVENUE, panel);
		_c.gridx = 0;
		_c.gridy = 6;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		railroad = new RailRoadUtility(this, Railroads.PENNSYLVANIA_RAILROAD);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.PENNSYLVANIA_RAILROAD, panel);
		_c.gridx = 0;
		_c.gridy = 5;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.ST_JAMES_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.ST_JAMES_PLACE, panel);
		_c.weightx = 1;
		_c.gridx = 0;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.LEFT, "Deed_Cards/community_chest.jpg"));
		_c.gridx = 0;
		_c.gridy = 3;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.TENNESSEE_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.TENNESSEE_AVENUE, panel);
		_c.gridx = 0;
		_c.gridy = 2;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.NEW_YORK_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.NEW_YORK_AVENUE, panel);
		_c.gridx = 0;
		_c.gridy = 1;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.UP, "Deed_Cards/free_parking.jpg"));
		_c.gridx = 0;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.KENTUCKY_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.KENTUCKY_AVENUE, panel);
		_c.weightx = 1;
		_c.gridx = 1;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.UP, "Deed_Cards/blue_chance.jpg"));
		_c.gridx = 2;
		_c.gridy = 0;
		this.add(panel, _c);
		
		
		property = new Property(this, ColorProperties.INDIANA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.INDIANA_AVENUE, panel);
		_c.gridx = 3;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.ILLINOIS_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.ILLINOIS_AVENUE, panel);
		_c.gridx = 4;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		railroad = new RailRoadUtility(this, Railroads.B_AND_O_RAILROAD);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.B_AND_O_RAILROAD, panel);
		_c.gridx = 5;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.ATLANTIC_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.ATLANTIC_AVENUE, panel);
		_c.gridx = 6;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.VENTNOR_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.VENTNOR_AVENUE, panel);
		_c.gridx = 7;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		railroad = new RailRoadUtility(this, Railroads.WATER_WORKS);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.WATER_WORKS, panel);
		_c.gridx = 8;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.MARVIN_GARDENS);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.MARVIN_GARDENS, panel);
		_c.gridx = 9;
		_c.gridy = 0;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new CornerButton(Orientation.RIGHT, "Deed_Cards/go_to_jail.jpg"));
		_c.gridx = 10;
		_c.gridy = 0;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.PACIFIC_GARDEN);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.PACIFIC_GARDEN, panel);
		_c.gridx = 10;
		_c.gridy = 1;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.NORTH_CAROLINA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.NORTH_CAROLINA_AVENUE, panel);
		_c.gridx = 10;
		_c.gridy = 2;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.RIGHT, "Deed_Cards/community_chest.jpg"));
		_c.gridx = 10;
		_c.gridy = 3;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.PENNSYLVANIA_AVENUE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.PENNSYLVANIA_AVENUE, panel);
		_c.gridx = 10;
		_c.gridy = 4;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		railroad = new RailRoadUtility(this, Railroads.SHORT_LINE);
		panel.add(railroad.getMe());
		_railroads.put(Railroads.SHORT_LINE, panel);
		_c.gridx = 10;
		_c.gridy = 5;
		this.add(panel, _c);
		
		panel = new JPanel (new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.RIGHT, "Deed_Cards/red_chance.jpg"));
		_c.gridx = 10;
		_c.gridy = 6;
		this.add(panel, _c);
		
		property = new Property(this, ColorProperties.PARK_PLACE);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.PARK_PLACE, panel);
		_c.gridx = 10;
		_c.gridy = 7;
		this.add(panel, _c);
		
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(new EmptyButton(Orientation.RIGHT, "Deed_Cards/ring.jpg"));
		_c.gridx = 10;
		_c.gridy = 8;
		this.add(panel, _c);
		
		
		property = new Property(this, ColorProperties.BOARDWALK);
		panel = new JPanel(new GridLayout(1,1,0,0));
		panel.add(property.getMe());
		_properties.put(ColorProperties.BOARDWALK, panel);
		_c.gridx = 10;
		_c.gridy = 9;
		this.add(panel, _c);
	}
	
	public void put (ColorProperties property, PropertyButton oldButton, PropertyButton newButton) {
		JPanel panel = _properties.get(property);
		panel.remove(oldButton);
		panel.add(newButton);
		panel.revalidate();
		this.revalidate();
	}
	
	public void put (Railroads railroad, RailroadPanel oldButton, RailroadPanel newButton) {
		JPanel panel = _railroads.get(railroad);
		panel.remove(oldButton);
		panel.add(newButton);
		panel.revalidate();
		this.revalidate();
	}
}
