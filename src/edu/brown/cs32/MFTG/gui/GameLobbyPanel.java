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


/**
 * This is the opening panel for when the game starts
 *
 * @author jschvime
 *
 */
public class GameLobbyPanel extends JPanel{
	private ImagePanel _createLite, _joinLite, _createDark, _joinDark,  _backLite, _backDark;
	private BufferedImage _background;
	private Point _createLoc, _joinLoc,_backLoc;
	private final int BUTTON_HEIGHT=Constants.FULL_PANEL_HEIGHT/8;
	private final int BUTTON_WIDTH=2*Constants.FULL_PANEL_HEIGHT/3;
	private final int START_HEIGHT=Constants.FULL_PANEL_HEIGHT/3;
	private final int START_WIDTH=Constants.FULL_WIDTH/6;
	private MonopolyGui _main;
	public GameLobbyPanel(MonopolyGui main) {
		try {
			_main=main;
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_PANEL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);
			this.setLayout(null);
			_background = Helper.resize(ImageIO.read(new File("images/mountain2.png")),this.getWidth(),this.getHeight());
			
			
			_createLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/CreateGameLite.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_createDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/CreateGameDark.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_createLoc= new Point(START_WIDTH, START_HEIGHT);
			_createLite.setLocation(_createLoc);
			_createDark.setLocation(_createLoc);
			_createDark.setVisible(false);
			
			_joinLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/JoinGameLite.png")), BUTTON_WIDTH-20, BUTTON_HEIGHT));
			_joinDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/JoinGameDark.png")), BUTTON_WIDTH-20, BUTTON_HEIGHT));
			_joinLoc= new Point(START_WIDTH-10, START_HEIGHT+BUTTON_HEIGHT*6/5);
			_joinLite.setLocation(_joinLoc);
			_joinDark.setLocation(_joinLoc);
			_joinDark.setVisible(false);
			
			_backLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackLite.png")), 100, 50));
			_backDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackDark.png")), 100, 50));
			_backLoc= new Point(Constants.BACK_X, Constants.BACK_Y);
			_backLite.setLocation(_backLoc);
			_backDark.setLocation(_backLoc);
			_backDark.setVisible(false);
			
			
			addMouseListener(new MyMouseListener());
			
			add(_createLite);
			add(_createDark);
			add(_joinLite);
			add(_joinDark);
			add(_backLite);
			add(_backDark);

			
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
			if(intersects(xloc,yloc,_createLite,_createLoc)) {
				_createDark.setVisible(true);
				_createLite.setVisible(false);
				repaint();
			}
			else if(intersects(xloc,yloc,_joinLite,_joinLoc)) {
				_joinDark.setVisible(true);
				_joinLite.setVisible(false);
				repaint();
			}
			else if(intersects(xloc,yloc,_backLite,_backLoc)) {
				_backDark.setVisible(true);
				_backLite.setVisible(false);
				repaint();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {

			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_createLite,_createLoc)) {
				if(_createDark.isVisible()) {
					fixPanels();
					_main.switchPanels("create");
				}
				else {
					fixPanels();
				}
			}
			else if(intersects(xloc,yloc,_joinLite,_joinLoc)) {
				if(_joinDark.isVisible()) {
					fixPanels();
					_main.switchPanels("join");
				}
				else {
					fixPanels();
				}
			}
			else if(intersects(xloc,yloc,_backLite,_backLoc)) {
				if(_backDark.isVisible()) {
					fixPanels();
					_main.switchPanels("choose");
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
			_createDark.setVisible(false);
			_createLite.setVisible(true);
			_joinDark.setVisible(false);
			_joinLite.setVisible(true);
			_backDark.setVisible(false);
			_backLite.setVisible(true);
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

