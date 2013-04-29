package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.properties.AggregateRailroadProperty;
import edu.brown.cs32.MFTG.gui.properties.MyRailroadProperty;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;

public class Railroad extends JPanel {

	private MyRailroadProperty _myProperty;
	private AggregateRailroadProperty _aggregateProperty;
	private PropertyPanel _current;
	private HashMap<PropertyPanel, PropertyPanel> _other = new HashMap<>();
	
	public Railroad(Railroads railroad) throws IOException {
		super();
		this.setLayout(new GridLayout(1,1, 0, 0));
		
		/* Set the dimensions */
		if(railroad.getOrientation() == Orientation.UP || railroad.getOrientation() == Orientation.DOWN) {
			Dimension dimension = new Dimension(Constants.WIDTH, Constants.HEIGHT);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		else {
			Dimension dimension = new Dimension(Constants.HEIGHT, Constants.WIDTH);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		
		_myProperty = new MyRailroadProperty(railroad);
		_aggregateProperty = new AggregateRailroadProperty(railroad);
		_other.put(_myProperty, _aggregateProperty);
		_other.put(_aggregateProperty, _myProperty);
		_current = _aggregateProperty;
		this.add(_current);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		_current.paintComponent(g);
	}

}