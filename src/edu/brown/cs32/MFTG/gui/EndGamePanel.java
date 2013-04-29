package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.MouseInputAdapter;

import edu.brown.cs32.MFTG.mftg.Main;


public class EndGamePanel extends JPanel{
	private ImagePanel  _backLite, _backDark;
	private BufferedImage _winner, _loser, _foreground;
	private Point _backLoc, _goLoc;
	private Main _main;
	private boolean _winning;
	
	private final int BUTTON_HEIGHT=Constants.FULL_HEIGHT/8;
	private final int BUTTON_WIDTH=2*Constants.FULL_HEIGHT/3;
	private final int START_HEIGHT=Constants.FULL_HEIGHT/8;
	private final int START_WIDTH=Constants.FULL_WIDTH/6;

	public EndGamePanel(Main main) {
		try {
			_main=main;
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);
			this.setLayout(null);
			_winner = Helper.resize(ImageIO.read(new File("images/Winner2.png")),this.getWidth(),this.getHeight());
			_loser = Helper.resize(ImageIO.read(new File("images/Loser.png")),this.getWidth(),this.getHeight());
			_foreground = Helper.resize(ImageIO.read(new File("images/WinnerColumns.png")),this.getWidth(),this.getHeight());
								
			_backLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/MainMenuLite.png")), 100, 50));
			_backDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/MainMenuDark.png")), 100, 50));
			_backLoc= new Point(this.getWidth()-_backLite.getWidth()-20, this.getHeight()-_backDark.getHeight()-10);
			_backLite.setLocation(_backLoc);
			_backDark.setLocation(_backLoc);
			_backDark.setVisible(false);
			
			_winning=false;
			addMouseListener(new MyMouseListener());
			
			add(_backLite);
			add(_backDark);

			
		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}

	}
	
	/**
	 * sets if player won
	 * @param didWin
	 */
	public void setWinner(boolean didWin) {
		_winning=didWin;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(_winning)brush.drawImage(_winner, 0, 0, null);
		else brush.drawImage(_loser, 0, 0, null);
		brush.drawImage(_foreground, 0, 0, null);
	}

	private class MyMouseListener extends MouseInputAdapter{
		public MyMouseListener() {
			super();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_backLite,_backLoc)) {
				_backLite.setVisible(false);
				_backDark.setVisible(true);
				repaint();
			}
			else if(intersects(xloc,yloc,_goLite,_goLoc)) {
				_goLite.setVisible(false);
				_goDark.setVisible(true);
				repaint();
			}

		}
		
		@Override
		public void mouseReleased(MouseEvent e) {

			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_backLite,_backLoc)) {
				if(_backDark.isVisible()) {
					fixPanels();
					_main.switchPanels("lobby");
				}
				else {
					fixPanels();
				}

			}
			else if(intersects(xloc,yloc,_goLite,_goLoc)) {
				if(_goDark.isVisible()) {
					fixPanels();
					//_main.switchPanels("lobby");
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
			_backDark.setVisible(false);
			_backLite.setVisible(true);
			_goDark.setVisible(false);
			_goLite.setVisible(true);
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

