package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants.Railroads;

public class AggregateRailroadProperty extends PropertyPanel {

	private BufferedImage _picture;
	
	public AggregateRailroadProperty(Railroads railroad) throws IOException {
		super(railroad);
		_picture = ImageIO.read(new File("Deed_Cards/railroad.jpg"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _picture, Color.GRAY, Color.WHITE);
	}
}
