package edu.brown.cs32.MFTG.gui.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.spaces.TestPanel;

public class ColorButtonTest extends JButton {
	
	private Colors _colorInfo;
	private Orientation _orientation;
	private List<Rectangle> _houses = new ArrayList<>();

	public ColorButtonTest(Colors colorInfo) {
		super();
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(null);
		
		this.setLocation(0, 0);
		_colorInfo = colorInfo;
		_orientation = colorInfo.getOrientation();
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			this.setPreferredSize(new Dimension(4*Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMaximumSize(new Dimension(4*Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
		}
		else {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, 4*Constants.ACTUAL_WIDTH));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, 4*Constants.ACTUAL_WIDTH));
		}
		
		setupHouses();
	}
	
	public void setupHouses() {
		for(int i=0; i<3; i++) {
			if(i != 3 || ( _colorInfo != Colors.PURPLE && _colorInfo != Colors.DARK_BLUE)) {
				Rectangle rec = new Rectangle(0, 0, 0, 0);
				changeHouse(rec, Constants.ACTUAL_WIDTH/2 - 10, 0, 20, 40 + i*Constants.ACTUAL_WIDTH/2);
				_houses.add(rec);
				System.out.println("added");
			}
		}
	}
	
	public void changeHouse(Rectangle house, double x, double y, double width, double height) {
		if(_orientation == Orientation.LEFT || _orientation == Orientation.RIGHT) {
			house.setLocation((int) x, (int) (4*Constants.ACTUAL_WIDTH - height + y));
			house.setSize((int) width, (int) height);
		}
		else {
			house.setLocation((int) y, (int) (Constants.HEIGHT - width + x));
			house.setSize((int) height, (int) width);
		}
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		for(int i=0; i<_houses.size(); i++) {
			System.out.println("painting");
			Color c = _colorInfo.getColor();
			//Color color = new Color (c.getRed(), c.getGreen(), c.getBlue(), (int) (255*(1 - i/3.)));
			drawHouse(g, _houses.get(i), c);
		}
	}
	
	public void drawHouse (Graphics g, Rectangle house, Color color) {
		g.setColor(color);
		System.out.println(house.getX() + " " + house.getY());
		g.fillRect((int) house.getX(), (int) house.getY(), (int) house.getWidth(), (int) house.getHeight());
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Constants.ACTUAL_WIDTH*4, 4*Constants.ACTUAL_HEIGHT));
		frame.add(new ColorButtonTest(Colors.RED));
		frame.pack();
		frame.setVisible(true);
	}

}
