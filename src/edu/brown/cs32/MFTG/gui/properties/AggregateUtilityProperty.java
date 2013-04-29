package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Utilities;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataAccumulator;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class AggregateUtilityProperty extends NonstaticProperty {

	private BufferedImage _picture;
	
	public AggregateUtilityProperty(Utilities utility) throws IOException {
		super(utility);
		_picture = ImageIO.read(new File(utility.getFile()));
		updateTimeOwned(1);
		
		/* Move the label */
		if(_orientation == Orientation.UP){
			moveProfitField(0, _orientation.getHeight()/2 -10);
		}
		else if ( _orientation == Orientation.DOWN) {
			moveProfitField(0, _orientation.getHeight()/2 - 50);
		}
		else if (_orientation == Orientation.RIGHT) {
			moveProfitField(_orientation.getWidth()/16, _orientation.getHeight()/2 - 30);
		}
		else {
			moveProfitField(_orientation.getWidth()/2 - _orientation.getWidth()/16, _orientation.getHeight()/2 - 30);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _picture, Color.GRAY, Color.WHITE);
	}

	@Override
	public void setData(PropertyDataReport data) {
		updateProfit(data.accTotalRevenueWithHouses);
	}
	
	public int getValue() {
		throw new NullPointerException("user cannot set value of aggregate property");
	}
}
