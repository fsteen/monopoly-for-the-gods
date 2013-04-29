package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;

import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Properties;

public class MyColorPropertyPanel extends MyPropertyPanel {

	private Color _color;
	
	public MyColorPropertyPanel(ColorProperties color) {
		super(color);
		_color = color.getColor();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _color);
	}

}
