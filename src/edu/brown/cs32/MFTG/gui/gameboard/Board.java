package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Corners;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;
import edu.brown.cs32.MFTG.gui.Constants.Utilities;
import edu.brown.cs32.MFTG.gui.center.Center;
import edu.brown.cs32.MFTG.gui.properties.AggregateColorProperty;
import edu.brown.cs32.MFTG.gui.properties.AggregateUtilityProperty;
import edu.brown.cs32.MFTG.gui.properties.MyColorPropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.MyUtilityProperty;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.StaticProperty;

public class Board extends JPanel {
	
	private Set<ColorGroup> _colorGroups = new HashSet<>();
	private Set<Railroad> _railroads = new HashSet<>();
	private JMenuBar _menu;
	
	public Board (JMenuBar menu) throws IOException {
		super();
		_menu = menu;
		
		/* Set the dimension */
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.FULL_HEIGHT);
		this.setSize(dimension);
		this.setPreferredSize(dimension);
		
		/* Set up the layout */
		BorderLayout layout = new BorderLayout(0, 0);
		this.setLayout(layout);
		
		initializeTop();
		initializeBottom();
		initializeLeft();
		initializeRight();
		initializeCenter();
	
		initializeMenu();
		
		this.setVisible(true);
	}
	
	public void initializeMenu () {
		JMenu boardView = new JMenu("Board View");
		
		JMenuItem myProperty = new JMenuItem("My Properties");
		myProperty.addActionListener(new MyPropertyListener());
		boardView.add(myProperty);
		
		JMenuItem aggregateProperty = new JMenuItem("Aggregate Properties");
		aggregateProperty.addActionListener(new AggregatePropertyListener());
		boardView.add(aggregateProperty);
		
		JMenuItem colorGroup = new JMenuItem("Color Groups");
		colorGroup.addActionListener(new ColorListener());
		boardView.add(colorGroup);
		
		_menu.add(boardView);
	}
	
	public void initializeTop () throws IOException {
		JPanel top = new JPanel();
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.HEIGHT);
		top.setSize(dimension);
		top.setPreferredSize(dimension);
		
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.weightx = Constants.HEIGHT;
		top.add(new Corner(Corners.FREE_PARKING));
		
		c.gridx = 1;
		c.weightx = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.KENTUCKY_AVENUE));
		aggregateProperties.add(new StaticProperty(StaticProperties.BLUE_CHANCE));
		myProperties.add(new StaticProperty(StaticProperties.BLUE_CHANCE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.KENTUCKY_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.INDIANA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.INDIANA_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ILLINOIS_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ILLINOIS_AVENUE));
		ColorGroup red = new ColorGroup(Orientation.UP, myProperties, aggregateProperties);
		top.add(red, c);
		_colorGroups.add(red);
		
		c.gridx = 2;
		c.weightx = Constants.WIDTH;
		Railroad bAndO = new Railroad(Railroads.B_AND_O_RAILROAD);
		top.add(bAndO, c);
		_railroads.add(bAndO);
		
		c.gridx = 3;
		c.weightx = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ATLANTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ATLANTIC_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VENTNOR_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VENTNOR_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.MARVIN_GARDENS));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.MARVIN_GARDENS));
		ColorGroup yellow = new ColorGroup(Orientation.UP, myProperties, aggregateProperties);
		top.add(yellow);
		_colorGroups.add(yellow);
		
		c.gridx = 4;
		c.weightx = Constants.HEIGHT;
		top.add(new Corner(Corners.GO_TO_JAIL), c);
		
		this.add(top, BorderLayout.NORTH);
	}
	
	public void initializeBottom () throws IOException {
		JPanel bottom = new JPanel();
		Dimension dimension = new Dimension(Constants.FULL_WIDTH, Constants.HEIGHT);
		bottom.setSize(dimension);
		bottom.setPreferredSize(dimension);
		
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.weightx = Constants.HEIGHT;
		bottom.add(new Corner(Corners.JAIL), c);
		
		c.gridx = 1;
		c.weightx = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.CONNECTICUT_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.CONNECTICUT_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VERMONT_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VERMONT_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ORIENTAL_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ORIENTAL_AVENUE));
		ColorGroup blue = new ColorGroup(Orientation.DOWN, myProperties, aggregateProperties);
		bottom.add(blue, c);
		_colorGroups.add(blue);
		
		c.gridx = 2;
		c.weightx = Constants.WIDTH;
		Railroad reading = new Railroad(Railroads.READING_RAILROAD);
		bottom.add(reading, c);
		_railroads.add(reading);
		
		c.gridx = 3;
		c.weightx = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ATLANTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ATLANTIC_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.BALTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.BALTIC_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.MEDITERRANEAN_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.MEDITERRANEAN_AVENUE));
		ColorGroup purple = new ColorGroup(Orientation.UP, myProperties, aggregateProperties);
		bottom.add(purple, c);
		_colorGroups.add(purple);
		
		c.gridx = 4;
		c.weightx = Constants.HEIGHT;
		bottom.add(new Corner(Corners.GO), c);
		
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	public void initializeLeft () throws IOException {
		JPanel left = new JPanel();
		Dimension dimension = new Dimension(Constants.HEIGHT, 9*Constants.HEIGHT);
		left.setPreferredSize(dimension);
		left.setSize(dimension);
		
		left.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.ipadx = 0;
		c.ipady = 0;
		
		c.gridy = 0;
		c.weighty = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.NEW_YORK_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.NEW_YORK_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.TENNESSEE_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.TENNESSEE_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ST_JAMES_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ST_JAMES_PLACE));
		ColorGroup orange = new ColorGroup(Orientation.LEFT, myProperties, aggregateProperties);
		left.add(orange, c);
		_colorGroups.add(orange);
		
		c.gridy = 1;
		c.weighty = Constants.WIDTH;
		Railroad pennsylvania = new Railroad(Railroads.PENNSYLVANIA_RAILROAD);
		left.add(pennsylvania, c);
		_railroads.add(pennsylvania);
		
		c.gridy = 2;
		c.weighty = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.VIRGINIA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.VIRGINIA_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.STATES_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.STATES_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.ELECTRIC_COMPANY));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.ELECTRIC_COMPANY));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ST_CHARLES_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ST_CHARLES_PLACE));
		ColorGroup pink = new ColorGroup(Orientation.LEFT, myProperties, aggregateProperties);
		left.add(pink, c);
		_colorGroups.add(pink);
		
		this.add(left, BorderLayout.WEST);
	}
	
	public void initializeRight () throws IOException {
		JPanel right = new JPanel();
		Dimension dimension = new Dimension(Constants.HEIGHT, 9*Constants.HEIGHT);
		right.setPreferredSize(dimension);
		right.setSize(dimension);
		
		right.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.ipadx = 0;
		c.ipady = 0;
		
		c.gridy = 0;
		c.weighty = 4*Constants.WIDTH;
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PACIFIC_GARDEN));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PACIFIC_GARDEN));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.NORTH_CAROLINA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.NORTH_CAROLINA_AVENUE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PENNSYLVANIA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PENNSYLVANIA_AVENUE));
		ColorGroup green = new ColorGroup(Orientation.RIGHT, myProperties, aggregateProperties);
		right.add(green, c);
		_colorGroups.add(green);
		
		c.gridy = 1;
		c.weighty = Constants.WIDTH;
		Railroad shortLine = new Railroad(Railroads.SHORT_LINE);
		right.add(shortLine, c);
		_railroads.add(shortLine);
		
		c.gridy = 2;
		c.weighty = 4*Constants.WIDTH;
		myProperties = new ArrayList<>();
		aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ATLANTIC_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ATLANTIC_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.PARK_PLACE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.PARK_PLACE));
		myProperties.add(new MyUtilityProperty(Utilities.WATER_WORKS));
		aggregateProperties.add(new AggregateUtilityProperty(Utilities.WATER_WORKS));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.BOARDWALK));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.BOARDWALK));
		ColorGroup blue = new ColorGroup(Orientation.RIGHT, myProperties, aggregateProperties);
		right.add(blue, c);
		_colorGroups.add(blue);
		
		this.add(right, BorderLayout.EAST);
	}
	
	public void initializeCenter() {
		JPanel center = new Center();
		this.add(center, BorderLayout.CENTER);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(Constants.FULL_WIDTH, Constants.FULL_HEIGHT));
		try {
			JMenuBar menu = new JMenuBar();
			frame.add(new Board(menu));
			frame.setJMenuBar(menu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.pack();
		frame.setVisible(true);
	}

/* ----------------------------------------------------------------------------
 * ----------------------------- MENU LISTENERS -------------------------------
 * ----------------------------------------------------------------------------
 */
	
	private class MyPropertyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(ColorGroup c: _colorGroups) {
				c.viewMyProperty();
			}
		}
	}
	
	private class AggregatePropertyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(ColorGroup c: _colorGroups) {
				c.viewAggregateProperty();
			}
		}
	}
	
	private class ColorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(ColorGroup c: _colorGroups) {
				c.viewColor();
			}
		}
	}

}
