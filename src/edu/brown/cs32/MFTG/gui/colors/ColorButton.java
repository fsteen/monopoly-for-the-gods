package edu.brown.cs32.MFTG.gui.colors;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.old.House;

public class ColorButton extends JButton {

	private Colors _colorInfo;
	private Orientation _orientation;
	private House _house;
	private HousePrice _housePrice;
	private MonopolyInformation _monopoly;
	private Rectangle _spaceOutline;

	public ColorButton(Colors colorInfo) {
		super();
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(null);

		this.setLocation(0, 0);
		_colorInfo = colorInfo;
		_orientation = colorInfo.getOrientation();
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			this.setPreferredSize(new Dimension(4*Constants.WIDTH, Constants.HEIGHT));			
		}
		else {
			this.setPreferredSize(new Dimension(Constants.HEIGHT, 4*Constants.WIDTH));
		}
		
		try {
			this.add(new ColorPanel(colorInfo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*_housePrice = new HousePrice(colorInfo.getOrientation());
		try {
			_monopoly = new MonopolyInformation(colorInfo.getOrientation());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Make the box outline 
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		if(colorInfo.getOrientation() == Orientation.UP || colorInfo.getOrientation() == Orientation.DOWN) {
			_spaceOutline.setSize(Constants.HEIGHT-Constants.BORDER, 2*Constants.HEIGHT - Constants.BORDER);
		} else {
			_spaceOutline.setSize(2*Constants.HEIGHT-Constants.BORDER, Constants.HEIGHT - Constants.BORDER);
		}
		
		this.add(_monopoly, 0, 0);
		this.add(_housePrice, this.getWidth()/2, 0);*/
	}
	
	/*protected void paintComponent(Graphics g) {
		//((Graphics2D) g).draw(_spaceOutline);
	}

	public static void main (String[] args) {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Constants.ACTUAL_WIDTH*4, 4*Constants.ACTUAL_HEIGHT));
		frame.add(new ColorButton(ColorInfo.PURPLE));
		frame.pack();
		frame.setVisible(true);
	}
*/
}
