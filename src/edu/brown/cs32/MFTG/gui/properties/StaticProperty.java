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
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
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
		fixSpaceOutline();
	}
	
	public void fixSpaceOutline() {
		if(_orientation == Orientation.LEFT || _orientation == Orientation.RIGHT) {
			_spaceOutline.setSize(Constants.HEIGHT, Constants.WIDTH);
			//if(_orientation == Orientation.RIGHT) {
				_spaceOutline.setLocation(0, 0);
			//}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_picture, 0, 0, null);
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);
	}

	@Override
	public void setData(PropertyDataReport data) {}
	
	public int getValue() {
		throw new NullPointerException("Static Properties do not have values");
	}

	@Override
	public void setValue(int integer) {}

}
