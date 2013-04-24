package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class GreetingPanel extends JPanel{
	private BufferedImage _playLite, _settingsLite, _recordsLite,_playDark, _settingsDark, _recordsDark, _background;
	private Point2D.Double _playLoc, _settingsLoc, _statsLoc;
	public GreetingPanel() {
		try {
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);

			_background = ImageIO.read(new File("images/MonopolyHome.png"));
			
			
			_playLite = ImageIO.read(new File("images/PlayLite.png"));
			_playDark = ImageIO.read(new File("images/PlayDark.png"));
			
			_settingsLite = ImageIO.read(new File("images/SettingsLite.png"));
			_settingsDark = ImageIO.read(new File("images/SettingsDark.png"));
			
			_recordsLite = ImageIO.read(new File("images/RecordsLite.png"));
			_recordsDark = ImageIO.read(new File("images/RecordsDark.png"));

			addMouseListener(new MyMouseListener());
			
			
		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		brush.drawImage(_background, 0, 0, null);


	}

	private class MyMouseListener extends MouseInputAdapter{
		public MyMouseListener() {
			super();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_playLite,_playLoc)) {
				_playLite.
			}
			else if(intersects(xloc,yloc,_settingsLite,_settingsLoc)) {
				//SETTINGS
			}
			else if(intersects(xloc,yloc,_statsLite,_statsLoc)) {
				//STATS
			}
		}

		private boolean intersects(int xloc, int yloc, BufferedImage img, Point2D.Double loc) {
			if(xloc>=loc.x && xloc<=loc.x+img.getWidth() && yloc>=loc.y && yloc<=loc.y+img.getHeight()) {
				return true;
			}
			return false;
		}
	}


}
