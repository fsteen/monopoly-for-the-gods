package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.gui.Constants.Corners;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;

public class Corner extends JPanel {

	private Orientation _orientation;
	private BufferedImage _image;
	private Rectangle _spaceOutline;
	
	public Corner(Corners corner) throws IOException {
		super();
		this.setBackground(Color.PINK);
		_orientation = corner.getOrientation();
		
		/* Set dimension */
		Dimension dimension = new Dimension(Constants.HEIGHT, Constants.HEIGHT);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		
		/* Initialize the image */
		_image = ImageIO.read(new File(corner.getFile()));
		_image = Helper.resize(_image, Constants.HEIGHT, Constants.HEIGHT);
		
		/* Make the box outline */
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		_spaceOutline.setSize(Constants.HEIGHT-Constants.BORDER, Constants.HEIGHT - Constants.BORDER);
	}
	
	public void paintComponent(Graphics g) {
		/* Make a new image to draw to */
		BufferedImage image = new BufferedImage(Constants.HEIGHT, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* Draw our picture */
		g2.drawImage(_image, 0, 0, null);
		
		/* Draw border */
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);
		
		/* Rotate and resize the image */
		image = Helper.rotate(image, _orientation);
		image = Helper.resize(image,  Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT);
		g.drawImage(image, 0, 0, null);
	}

}
