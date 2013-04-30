package edu.brown.cs32.MFTG.gui.properties;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.StaticProperties;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class StaticProperty extends PropertyPanel {

	private BufferedImage _picture;
	private StaticProperties _property;
	
	public StaticProperty(StaticProperties property) throws IOException {
		super(property);
		_property = property;
		_picture = ImageIO.read(new File(property.getFile()));
		_picture = Helper.resize(_picture, Constants.WIDTH, Constants.HEIGHT);
		_picture = Helper.rotate(_picture, property.getOrientation());
	}
	
	protected void resizePicture(int width, int height) {
		_picture = Helper.resize(_picture, width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_picture, 0, 0, null);
		
		//g2.rotate(_property.getOrientation().getRotation(), (int) (Constants.HEIGHT/2.), (int) (Constants.WIDTH/2.));
		//g2.draw(_spaceOutline);
	}

	@Override
	public void setData(PropertyDataReport data) {}
	
	public int getValue() {
		throw new NullPointerException("Static Properties do not have values");
	}

}
