package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;

public class DeedPopup extends JPanel {
	
	private List<BufferedImage> _deeds;
	
	public DeedPopup (List<BufferedImage> deeds) {
		super();
		this.setLayout(null);
		_deeds = deeds;
		
		Dimension dimension;
		if(_deeds.size() < 4)
			dimension = new Dimension(Constants.DEED_WIDTH + 10,_deeds.size()*Constants.DEED_HEIGHT + 25);
		else 
			dimension = new Dimension((2*Constants.DEED_WIDTH) + 10, 2*Constants.DEED_HEIGHT + 25);
		setSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		if(_deeds.size() < 4) {
			for(int i=0; i<_deeds.size(); i++) {
				g.drawImage(_deeds.get(i), 0, i*Constants.DEED_HEIGHT, null);
			}
		}
		else {
			for(int i=0; i<_deeds.size(); i++) {
				g.drawImage(_deeds.get(i), ((int) i/2)*Constants.DEED_WIDTH, (i % 2) * Constants.DEED_HEIGHT, null);
			}
		}
	}
}
