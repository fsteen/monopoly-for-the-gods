package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JToolTip;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.gameboard.IconToolTip;
import edu.brown.cs32.MFTG.gui.Houses;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class MyColorPropertyPanel extends MyPropertyPanel {

	private Color _color;
	private Houses _houseRow;
	private double _houses = 0;
	
	public MyColorPropertyPanel(ColorProperties color) {
		super(color);
		_color = color.getColor();
		_houseRow = new Houses(_houses, _color, getTimeOwned(), getProfit());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g, _color, _houseRow, Color.BLACK);
	}
	
	public void updateProfit(double profit) {
		super.updateProfit(profit);
		_houseRow.setProfit(profit);
	}

	@Override
	public void setData(PropertyDataReport data) {
		updateProfit(data.accTotalRevenueWithHouses);
		updateTimeOwned(data.timeOwned);
		_houseRow.setNumHouses(data.accNumHouses);
		_houseRow.setTimeOwned(data.timeOwned);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Example");
		frame.setSize(300, 300);
		frame.setPreferredSize(new Dimension(300, 300));
		frame.setLayout(null);
		
		MyColorPropertyPanel example = new MyColorPropertyPanel(ColorProperties.CONNECTICUT_AVENUE);
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
