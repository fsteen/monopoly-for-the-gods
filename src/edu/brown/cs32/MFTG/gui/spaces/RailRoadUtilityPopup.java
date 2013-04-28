package edu.brown.cs32.MFTG.gui.spaces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Helper;

public class RailRoadUtilityPopup extends JFrame{

	private BufferedImage _image;
	
	public RailRoadUtilityPopup(Railroads railRoadInfo) throws IOException {
		super(railRoadInfo.getName());
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(257, 325));
		_image = ImageIO.read(new File(railRoadInfo.getFile()));
		_image = Helper.resize(_image, 246, 294);
		
		JPanel panel = new JPanel () {
			protected void paintComponent (Graphics g) {
				g.drawImage(_image, 0, 0, null);
			}
		};
		
		this.add(panel);
		
		this.pack();
		this.setVisible(true);
	}
}
