package edu.brown.cs32.MFTG.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class GreetingPanel extends JPanel{
	private BufferedImage _play, _settings, _stats;
	private Point2D.Double _playLoc, _settingsLoc, _statsLoc;
	public GreetingPanel() {
		addMouseListener(new MyMouseListener());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
	}
	
	private class MyMouseListener extends MouseInputAdapter{
		public MyMouseListener() {
			super();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_play,_playLoc)) {
				//PLAY
			}
			else if(intersects(xloc,yloc,_settings,_settingsLoc)) {
				//SETTINGS
			}
			else if(intersects(xloc,yloc,_stats,_statsLoc)) {
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
