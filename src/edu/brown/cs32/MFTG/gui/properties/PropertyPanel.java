package edu.brown.cs32.MFTG.gui.properties;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataAccumulator;

public abstract class PropertyPanel extends JPanel {

	//private PropertyContainer _container;
	protected Orientation _orientation;

	/* Background and border rectangles */
	protected Rectangle _spaceBackground;
	protected Rectangle _spaceOutline;


	public PropertyPanel(Properties myProperty) {
		/* Initialize the panel */
		super();
		this.setLayout(null);
		this.setLocation(0, 0);

		_orientation = myProperty.getOrientation();
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMinimumSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
		}
		else {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
			this.setMinimumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
		}
		
		initializeBackground();

	}

	public void initializeBackground () {
		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(Constants.WIDTH, Constants.HEIGHT);
		_spaceBackground.setLocation(0, 0);

		/* Make the box outline */
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		_spaceOutline.setSize(Constants.WIDTH-Constants.BORDER, Constants.HEIGHT - Constants.BORDER);
	}

	public abstract int getValue();

	public abstract void paintComponent(Graphics g);
	
	public abstract void setData (PropertyDataAccumulator data);

	
}