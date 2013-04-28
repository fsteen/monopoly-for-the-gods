package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;

public class StaticProperty extends PropertyPanel {

	private BufferedImage _picture;
	
	public StaticProperty(StaticProperties properties) throws IOException {
		super(properties);
		_picture = ImageIO.read(new File(properties.getFile()));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		_picture = Helper.resize(_picture, this.getWidth(), this.getHeight());
		g.drawImage(_picture, 0, 0, null);
	}

}
