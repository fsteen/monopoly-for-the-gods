package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants.Utilities;

public class MyUtilityProperty extends MyPropertyPanel {

	private BufferedImage _picture;
	
	public MyUtilityProperty(Utilities utility) throws IOException {
		super(utility);
		_picture = ImageIO.read(new File(utility.getFile()));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _picture, Color.WHITE);
	}
}
