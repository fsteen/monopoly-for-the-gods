package edu.brown.cs32.MFTG.gui.spaces;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import edu.brown.cs32.MFTG.gui.Constants;


public class Houses {

	private House _house1;
	private House _house2;
	private House _house3;
	private House _house4;
	
	private Rectangle _cover;
	private Rectangle _colorCover;
	private Color _color;
	
	private double _timeOwned;
	private double _profit;
		
	private int HOUSE_WIDTH = (int) (Constants.WIDTH/5) - 4;
	private int HOUSE_HEIGHT = (int) HOUSE_WIDTH*8/9;
	
	private int NUM_HOUSES = 5;
	
	public Houses(double numHouses, Color color, double timeOwned, double profit) {
		_house1 = new House(HOUSE_WIDTH, HOUSE_HEIGHT, (int) (Constants.WIDTH/NUM_HOUSES - HOUSE_WIDTH)/2, (int) ((Constants.HEADER_HEIGHT - HOUSE_HEIGHT)/2));
		_house2 = new House(HOUSE_WIDTH, HOUSE_HEIGHT, (int) (Constants.WIDTH/NUM_HOUSES - HOUSE_WIDTH)/2 + Constants.WIDTH/NUM_HOUSES, (int) ((Constants.HEADER_HEIGHT - HOUSE_HEIGHT)/2));
		_house3 = new House(HOUSE_WIDTH, HOUSE_HEIGHT, (int) (Constants.WIDTH/NUM_HOUSES - HOUSE_WIDTH)/2 + 2*Constants.WIDTH/NUM_HOUSES, (int) ((Constants.HEADER_HEIGHT - HOUSE_HEIGHT)/2));
		_house4 = new House(HOUSE_WIDTH, HOUSE_HEIGHT, (int) (Constants.WIDTH/NUM_HOUSES - HOUSE_WIDTH)/2 + 3*Constants.WIDTH/NUM_HOUSES, (int) ((Constants.HEADER_HEIGHT - HOUSE_HEIGHT)/2));
		
		_timeOwned = timeOwned;
		_profit = profit;
		
		double percentHouses = numHouses/NUM_HOUSES;
		int xLoc = (int) (Constants.WIDTH*percentHouses);
		int width = (int) (Constants.WIDTH*(1-percentHouses));
		_cover = new Rectangle (xLoc, 0, width, Constants.HEIGHT);
		_colorCover = new Rectangle(xLoc, 0, width, (int) (timeOwned*Constants.HEIGHT));
		_color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255*profit/10000.));
	}
	
	public void paint (Graphics2D g2) {
		_house1.paint(g2);
		_house2.paint(g2);
		_house3.paint(g2);
		_house4.paint(g2);
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_cover);
		
		g2.setColor(Color.WHITE);
		g2.fill(_colorCover);
		
		g2.setColor(_color);
		g2.fill(_colorCover);
	}

}
