package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Houses;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class AggregateColorProperty extends NonstaticProperty {

	private Color _color;
	private Houses _houseRow;
	private double _houses = 0;

	
	public AggregateColorProperty(ColorProperties color) {
		super(color);
		_color = color.getColor();
		_houseRow = new Houses(_houses, _color, getTimeOwned(), getProfit());
		updateTimeOwned(1);
	}
	
	public void updateTimeOwned(double timeOwned) {
		super.updateTimeOwned(timeOwned);
		_houseRow.setTimeOwned(timeOwned);
	}
	
	public void updateProfit(double profit) {
		super.updateProfit(profit);
		_houseRow.setProfit(profit);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _color, _houseRow, Color.WHITE);
	}

	@Override
	public void setData(PropertyDataReport data) {
		this.updateProfit(data.accTotalRevenueWithHouses);
		_houseRow.setNumHouses(data.accNumHouses);
	}
	
	public int getValue() {
		throw new NullPointerException("user cannot set value of aggregate property");
	}

	@Override
	public void setValue(int integer) {}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Example");
		frame.setSize(300, 300);
		frame.setPreferredSize(new Dimension(300, 300));
		frame.setLayout(null);
		
		AggregateColorProperty example = new AggregateColorProperty(ColorProperties.CONNECTICUT_AVENUE);
		PropertyDataReport data = new PropertyDataReport("connecticut", 1, 1.3, 40, 40, 0, .45, 100);
		example.setData(data);
		
		example.setLocation(50, 50);
		example.setSize(Constants.WIDTH, Constants.HEIGHT);
		example.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		
		MyColorPropertyPanel example2 = new MyColorPropertyPanel(ColorProperties.CONNECTICUT_AVENUE);
		PropertyDataReport data2 = new PropertyDataReport("connecticut", 1, 1.3, 40, 40, 0, .45, 100);
		example.setData(data2);
		
		example.setLocation(120, 50);
		example.setSize(Constants.WIDTH, Constants.HEIGHT);
		example.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		
		frame.add(example);
		frame.add(example2);
		frame.pack();
		frame.setVisible(true);
		
	}
}
