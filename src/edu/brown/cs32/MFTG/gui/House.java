package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class House {

	private Polygon _roof;
	private Polygon _house;
	
	public House(int width, int height, int xOffset, int yOffset) {
		int[] xpoints = new int[3];
		int[] ypoints = new int[3];
		
		xpoints[0] = xOffset;
		xpoints[1] = xOffset + (int) width/2;
		xpoints[2] = xOffset + width;
		ypoints[0] = yOffset + height/2;
		ypoints[1] = yOffset + 0;
		ypoints[2] = yOffset + height/2;
		_roof = new Polygon(xpoints, ypoints, 3);
		
		xpoints = new int[4];
		ypoints = new int[4];
		xpoints[0] = xOffset + width/4;
		xpoints[1] = xOffset + width/4;
		xpoints[2] = xOffset + width - width/4;
		xpoints[3] = xOffset + width - width/4;
		ypoints[0] = yOffset + height;
		ypoints[1] = yOffset + height/2;
		ypoints[2] = yOffset + height/2;
		ypoints[3] = yOffset + height;
		_house = new Polygon(xpoints, ypoints, 4);
	}
	
	public void paint (Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.fillPolygon(_house);
		g2.fillPolygon(_roof);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(_house);
		g2.drawPolygon(_roof);
	}

}
