package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.properties.AggregateColorProperty;
import edu.brown.cs32.MFTG.gui.properties.MyColorPropertyPanel;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;

public class ColorGroup extends JPanel {
	
	private List<PropertyPanel> _myProperties = new ArrayList<>();
	private List<PropertyPanel> _aggregateProperties = new ArrayList<>();
	private View _view = View.ME;
	private Orientation _orientation;
	private GridBagConstraints _c = new GridBagConstraints();
	
	public ColorGroup (Orientation orientation, List<PropertyPanel> myPanel, List<PropertyPanel> aggregatePanel) {
		super();
		_orientation = orientation;
		
		this.setLayout(new GridBagLayout());
		
		/* Set up the size of the panel */
		if(orientation == Orientation.UP || orientation == Orientation.DOWN) {
			Dimension dimension = new Dimension(4*Constants.WIDTH, Constants.HEIGHT);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		else {
			Dimension dimension = new Dimension(Constants.HEIGHT, 4*Constants.WIDTH);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		
		_myProperties = myPanel;
		_aggregateProperties = aggregatePanel;
		update();
	}
	
	public void update () {
		this.removeAll();
		switch (_view) {
		case ME:
			this.putObjects(_myProperties);
			break;
		case AGGREGATE:
			this.putObjects(_aggregateProperties);
			break;
		}
	}
	
	public void putObjects (List<PropertyPanel> panel) {
		_c.ipadx = 0; _c.ipady = 0;
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_c.gridy = 0;
			_c.gridx = 0;
			this.add(panel.get(0), _c);
			_c.gridx = 1;
			this.add(panel.get(1), _c);
			_c.gridx = 2;
			this.add(panel.get(2), _c);
			_c.gridx = 3;
			this.add(panel.get(3), _c);
		}
		else {
			_c.gridx = 0;
			_c.gridy = 0;
			this.add(panel.get(0), _c);
			_c.gridy = 1;
			this.add(panel.get(1), _c);
			_c.gridy = 2;
			this.add(panel.get(2), _c);
			_c.gridy = 3;
			this.add(panel.get(3), _c);
		}
	}
	
	public void initialize(Colors color) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		List<ColorProperties> colors = new ArrayList<>();
		for(ColorProperties p: ColorProperties.values()) {
			if(p.getColor().equals(color.getColor())) {
				colors.add(p);
			}
		}
		//System.out.println()
	}
	
/* ----------------------------------------------------------------------------
 * ----------------------------- CHANGE VIEW ----------------------------------
 * ----------------------------------------------------------------------------
 */
	public void viewMyProperty() {
		
	}
	
	public void viewAggregateProperty () {
		
	}
	
	public void viewColor () {
		
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(Constants.FULL_WIDTH, Constants.FULL_HEIGHT));
		JMenuBar menu = new JMenuBar();
		
		List<PropertyPanel> myProperties = new ArrayList<>();
		List<PropertyPanel> aggregateProperties = new ArrayList<>();
		myProperties.add(new MyColorPropertyPanel(ColorProperties.KENTUCKY_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.KENTUCKY_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.KENTUCKY_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.KENTUCKY_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.INDIANA_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.INDIANA_AVENUE));
		myProperties.add(new MyColorPropertyPanel(ColorProperties.ILLINOIS_AVENUE));
		aggregateProperties.add(new AggregateColorProperty(ColorProperties.ILLINOIS_AVENUE));
		ColorGroup red = new ColorGroup(Orientation.UP, myProperties, aggregateProperties);
		
		
		frame.add(red);
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setVisible(true);
	}
}
