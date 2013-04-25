package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Class that just creates a panel with 1 image
 * @author jschvime
 *
 */
public class ImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage _im;
	public ImagePanel(BufferedImage im) {
		super();
		_im=im;
		setBackground(new Color(0,0,0,0));
		this.setSize(new Dimension(_im.getWidth(),_im.getHeight()));
		this.setPreferredSize(new Dimension(_im.getWidth(),_im.getHeight()));
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		brush.drawImage(_im, 0, 0, null);

	}
}
