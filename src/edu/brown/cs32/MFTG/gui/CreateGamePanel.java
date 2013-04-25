package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class CreateGamePanel extends JPanel{
	private ImagePanel _createLite, _whiteBack,_backLite, _backDark, _goLite, _goDark;
	private BufferedImage _background;
	private Point _createLoc, _whiteLoc;
	private final int BUTTON_HEIGHT=Constants.FULL_HEIGHT/8;
	private final int BUTTON_WIDTH=2*Constants.FULL_HEIGHT/3;
	private final int START_HEIGHT=Constants.FULL_HEIGHT/5;
	private final int START_WIDTH=Constants.FULL_WIDTH/6;
	private final int BOTTOM_HEIGHT=Constants.FULL_HEIGHT/2;
	private final int BOTTOM_WIDTH=Constants.FULL_WIDTH*7/8;
	public CreateGamePanel() {
		try {
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);
			this.setLayout(null);
			_background = ImageIO.read(new File("images/mountain.jpg"));
			
			
			_createLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/CreateGameLite.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_createLoc= new Point(START_WIDTH, START_HEIGHT);
			_createLite.setLocation(_createLoc);
			
			_whiteBack = new ImagePanel(Helper.resize(ImageIO.read(new File("images/WhiteBack.png")), BOTTOM_WIDTH, BOTTOM_HEIGHT));
			_whiteLoc= new Point(Constants.FULL_WIDTH/16, START_HEIGHT+BUTTON_HEIGHT*6/5);
			_whiteBack.setLocation(_whiteLoc);
			
				
			addMouseListener(new MyMouseListener());
			
			add(_createLite);
			add(_whiteBack);

			
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
			/*if(intersects(xloc,yloc,_createLite,_createLoc)) {
				_createLite.setVisible(false);
				repaint();
			}*/

		}
		
		@Override
		public void mouseReleased(MouseEvent e) {

			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_createLite,_createLoc)) {
				/*if(_createDark.isVisible()) {
					System.out.println("Create");
				}
				else {
					fixPanels();
				}*/
			}
			else {
				fixPanels();
			}
		}
		
		private void fixPanels() {
			_createLite.setVisible(true);
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

