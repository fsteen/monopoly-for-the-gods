package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.MouseInputAdapter;

import edu.brown.cs32.MFTG.tournament.Settings;
import edu.brown.cs32.MFTG.tournament.Tournament;


public class CreateGamePanel extends JPanel{
	private ImagePanel _createLite, _backLite, _backDark, _goLite, _goDark;
	private BufferedImage _background;
	private Point _createLoc, _whiteLoc, _backLoc, _goLoc;
	private CreateBottomPanel _bottomPanel;
	private MonopolyGui _main;
	private JLabel _error;
	
	private final int BUTTON_HEIGHT=Constants.FULL_PANEL_HEIGHT/8;
	private final int BUTTON_WIDTH=2*Constants.FULL_PANEL_HEIGHT/3;
	private final int START_HEIGHT=Constants.FULL_PANEL_HEIGHT/8;
	private final int START_WIDTH=Constants.FULL_WIDTH/6;

	public CreateGamePanel(MonopolyGui main) {
		try {
			_main=main;
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_PANEL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);
			this.setLayout(null);
			_background = Helper.resize(ImageIO.read(new File("images/mountain2.png")),this.getWidth(),this.getHeight());
			
			_createLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/CreateGameLite.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_createLoc= new Point(START_WIDTH, START_HEIGHT);
			_createLite.setLocation(_createLoc);
					
			_whiteLoc= new Point(Constants.FULL_WIDTH/32, START_HEIGHT+BUTTON_HEIGHT*6/5);
			_bottomPanel=new CreateBottomPanel();
			_bottomPanel.setLocation(_whiteLoc);
			
			_backLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackLite.png")), 100, 50));
			_backDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackDark.png")), 100, 50));
			_backLoc= new Point(Constants.BACK_X, Constants.BACK_Y);
			_backLite.setLocation(_backLoc);
			_backDark.setLocation(_backLoc);
			_backDark.setVisible(false);
			
			_goLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/GoLite.png")), 100, 50));
			_goDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/GoDark.png")), 100, 50));
			_goLoc = new Point(this.getWidth()-_goLite.getWidth()-30, this.getHeight()-_goLite.getHeight()-40);
			_goLite.setLocation(_goLoc);
			_goDark.setLocation(_goLoc);
			_goDark.setVisible(false);
			
			_error = new JLabel();
			Dimension errorSize = new Dimension(this.getWidth()-_backLite.getWidth()*3, _backLite.getHeight());
			_error.setSize(errorSize);
			_error.setPreferredSize(errorSize);
			_error.setLocation(Constants.BACK_X+_backLite.getWidth()+20, Constants.BACK_Y);
			_error.setFont(new Font("errorFont",Font.ITALIC,30));
			_error.setForeground(Color.RED);
			
			addMouseListener(new MyMouseListener());
			
			add(_createLite);
			add(_backLite);
			add(_backDark);
			add(_goLite);
			add(_goDark);
			add(_bottomPanel);
			add(_error);

			
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
	
	/**
	 * sets error message, automatically adds "ERROR: " to the front
	 * @param message
	 */
	public void setError(String message) {
		_error.setText("ERROR: "+message);
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
					Settings settings = _bottomPanel.getSettings();
					List<Integer> players = _bottomPanel.getPlayers();
					System.out.println("num players " + players.size());
//					int numPlayers = 2; //TODO change later
					int port = _bottomPanel.getPort();
					//_main.switchPanels("board");
					//System.out.println("switching to board");
					//_main.createBoard(1);
					//_main.switchPanels("board");
					
					_main.getClient().launchTournament(players, settings,port);
					_main.getClient().connect(_bottomPanel.getPort(), "localhost");
					_main.getClient().run();
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

