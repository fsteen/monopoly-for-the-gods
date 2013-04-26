package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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

import edu.brown.cs32.MFTG.mftg.Main;

/**
 * This is the opening panel for when the game starts
 *
 * @author jschvime
 *
 */
public class GreetingPanel extends JPanel{
	private ImagePanel _playLite, _settingsLite, _recordsLite,_playDark, _settingsDark, _recordsDark;
	private BufferedImage _background;
	private Point _playLoc, _settingsLoc, _recordsLoc;
	private Main _main;
	private final int BUTTON_HEIGHT=Constants.FULL_HEIGHT/10;
	private final int BUTTON_WIDTH=3*Constants.FULL_HEIGHT/5;
	private final int START_HEIGHT=3*Constants.FULL_HEIGHT/7;
	private final int START_WIDTH=Constants.FULL_WIDTH/4;
	public GreetingPanel(Main main) {
		try {
			_main=main;
			
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.BLACK);
			this.setLayout(null);
			_background = Helper.resize(ImageIO.read(new File("images/MonopolyHome2.png")),this.getWidth(),this.getHeight());
			
			
			_playLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/PlayLite.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_playDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/PlayDark.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_playLoc= new Point(START_WIDTH, START_HEIGHT);
			_playLite.setLocation(_playLoc);
			_playDark.setLocation(_playLoc);
			_playDark.setVisible(false);
			
			_settingsLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/SettingsLite.png")), BUTTON_WIDTH-20, BUTTON_HEIGHT));
			_settingsDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/SettingsDark.png")), BUTTON_WIDTH-20, BUTTON_HEIGHT));
			_settingsLoc= new Point(START_WIDTH-10, START_HEIGHT+BUTTON_HEIGHT*6/5);
			_settingsLite.setLocation(_settingsLoc);
			_settingsDark.setLocation(_settingsLoc);
			_settingsDark.setVisible(false);
			
			_recordsLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/RecordsLite.png")), BUTTON_WIDTH, BUTTON_HEIGHT));
			_recordsDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/RecordsDark.png")), BUTTON_WIDTH, BUTTON_HEIGHT));
			_recordsLoc= new Point(START_WIDTH-20, START_HEIGHT+2*BUTTON_HEIGHT*6/5);
			_recordsLite.setLocation(_recordsLoc);
			_recordsDark.setLocation(_recordsLoc);
			_recordsDark.setVisible(false);
			
			addMouseListener(new MyMouseListener());
			
			add(_playLite);
			add(_playDark);
			add(_settingsLite);
			add(_settingsDark);
			add(_recordsLite);
			add(_recordsDark);
			
			
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
				_playDark.setVisible(true);
				_playLite.setVisible(false);
				repaint();
			}
			else if(intersects(xloc,yloc,_settingsLite,_settingsLoc)) {

				_settingsDark.setVisible(true);
				_settingsLite.setVisible(false);
				repaint();
			}
			else if(intersects(xloc,yloc,_recordsLite,_recordsLoc)) {

				_recordsDark.setVisible(true);
				_recordsLite.setVisible(false);
				repaint();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {

			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_playLite,_playLoc)) {
				if(_playDark.isVisible()) {
					fixPanels();
					_main.switchPanels("lobby");
				}
				else {
					fixPanels();
				}
			}
			else if(intersects(xloc,yloc,_settingsLite,_settingsLoc)) {
				
				if(_settingsDark.isVisible()) {
					//TODO
				}
				else {
					fixPanels();
				}
			}
			else if(intersects(xloc,yloc,_recordsLite,_recordsLoc)) {
				if(_recordsDark.isVisible()) {
					//TODO
				}
				else {
					fixPanels();
				}
			}
			else {
				fixPanels();
			}
		}
		
		private void fixPanels() {
			_playDark.setVisible(false);
			_playLite.setVisible(true);
			_settingsDark.setVisible(false);
			_settingsLite.setVisible(true);
			_recordsDark.setVisible(false);
			_recordsLite.setVisible(true);
			repaint();
		}
		private boolean intersects(int xloc, int yloc, JPanel img, Point loc) {
			if(xloc>=loc.x && xloc<=loc.x+img.getWidth() && yloc>=loc.y && yloc<=loc.y+img.getHeight()) {
				return true;
			}
			return false;
		}

	}


}
