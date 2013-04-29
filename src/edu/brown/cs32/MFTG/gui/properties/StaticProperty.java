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
import edu.brown.cs32.MFTG.tournament.data.PropertyDataAccumulator;

public class StaticProperty extends PropertyPanel {

	private BufferedImage _picture;
	
	public StaticProperty(StaticProperties properties) throws IOException {
		super(properties);
		_picture = ImageIO.read(new File(properties.getFile()));
		_picture = Helper.resize(_picture, Constants.WIDTH, Constants.HEIGHT);
		_picture = Helper.rotate(_picture, properties.getOrientation());
	}
	
	protected void resizePicture(int width, int height) {
		_picture = Helper.resize(_picture, width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(_picture, 0, 0, null);
	}

	@Override
	public void setData(PropertyDataAccumulator data) {}
	
	public int getValue() {
		throw new NullPointerException("Static Properties do not have values");
	}

}
