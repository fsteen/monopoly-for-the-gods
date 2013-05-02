package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JToolTip;

import edu.brown.cs32.MFTG.gui.Helper;

public class IconToolTip extends JToolTip {
	
	private BufferedImage _icon;
	private int _width;
	private int _height;
	
	public IconToolTip(String file) throws IOException {
		super();		
		_icon = ImageIO.read(new File(file));
		
		Dimension dimension = new Dimension(_icon.getWidth(), _icon.getHeight());
		setSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(_icon, 0, 0, null);
	}

}
