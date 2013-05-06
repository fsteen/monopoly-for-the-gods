package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Utilities;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class MyUtilityProperty extends MyPropertyPanel {

	private BufferedImage _picture;
	
	public MyUtilityProperty(Utilities utility) throws IOException {
		super(utility);
		_picture = ImageIO.read(new File(utility.getFile()));
		
		/* Move the label */
		if(_orientation == Orientation.UP){
			moveProfitField(0, _orientation.getHeight()/2);
			moveValueField(0, _orientation.getHeight()/2 + 10);
		}
		else if ( _orientation == Orientation.DOWN) {
			moveProfitField(0, _orientation.getHeight()/2 - 40);
			moveValueField(0, _orientation.getHeight()/2 - 10);
		}
		else if (_orientation == Orientation.RIGHT) {
			moveProfitField(_orientation.getWidth()/16 + 10, _orientation.getHeight()/2 - 30);
			moveValueField(_orientation.getWidth()/16 + 10, _orientation.getHeight()/2);
		}
		else {
			moveProfitField(_orientation.getWidth()/2 - _orientation.getWidth()/16 - 10, _orientation.getHeight()/2 - 30);
			moveValueField(_orientation.getWidth()/2 - _orientation.getWidth()/16 - 10, _orientation.getHeight()/2);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _picture, Color.WHITE);
	}

	@Override
	public void setData(PropertyDataReport data) {
		updateProfit(data.accTotalRevenueWithoutHouses);
	}
}
