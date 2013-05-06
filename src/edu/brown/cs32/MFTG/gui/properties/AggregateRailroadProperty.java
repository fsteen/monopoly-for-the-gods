package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class AggregateRailroadProperty extends NonstaticProperty {

	private BufferedImage _picture;
	
	public AggregateRailroadProperty(Railroads railroad) throws IOException {
		super(railroad);
		_picture = ImageIO.read(new File("Deed_Cards/railroad.jpg"));
		updateTimeOwned(1);
		
		/* Move the label */
		if(_orientation == Orientation.UP){
			moveProfitField(0, _orientation.getHeight()/2);
		}
		else if ( _orientation == Orientation.DOWN) {
			moveProfitField(0, _orientation.getHeight()/2 - 40);
		}
		else if (_orientation == Orientation.RIGHT) {
			moveProfitField(_orientation.getWidth()/16 + 10, _orientation.getHeight()/2 - 30);
		}
		else {
			moveProfitField(_orientation.getWidth()/2 - _orientation.getWidth()/16 - 10, _orientation.getHeight()/2 - 30);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _picture, Color.GRAY, Color.WHITE);
	}

	@Override
	public void setData(PropertyDataReport data) {
		System.out.println("aggregate railroad property data");
		updateProfit(data.accTotalRevenueWithoutHouses);
	}
	
	public int getValue() {
		throw new NullPointerException("user cannot set value of aggregate property");
	}

	@Override
	public void setValue(int integer) {}
}
