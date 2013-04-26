package edu.brown.cs32.MFTG.gui.spaces;

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
import javax.swing.BorderFactory;
import javax.swing.JButton;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Helper;

public class CornerButton extends JButton {

	private Orientation _orientation;
	private BufferedImage _image;
	private Rectangle _spaceOutline;
	
	public CornerButton(Orientation orientation, String file) throws IOException {
		super();
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(null);
		
		this.setLocation(0, 0);
		_orientation = orientation;
		
		this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT));
		this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT));
		
		_image = ImageIO.read(new File(file));
		_image = Helper.resize(_image, Constants.HEIGHT, Constants.HEIGHT);
		
		/* Make the box outline */
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		_spaceOutline.setSize(Constants.HEIGHT-Constants.BORDER, Constants.HEIGHT - Constants.BORDER);

	}
	
	public void paintComponent(Graphics g) {
		System.out.println("painting");
		/* Rotate and resize the image */
		BufferedImage image = new BufferedImage(Constants.HEIGHT, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();		
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g2.drawImage(_image, 0, 0, null);
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);
		
		/* Rotate and resize the image */
		image = Helper.rotate(image, _orientation);
		image = Helper.resize(image,  Constants.ACTUAL_HEIGHT, Constants.ACTUAL_HEIGHT);
		
		
		g.drawImage(image, 0, 0, null);
	}

}
