package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Railroads;
import edu.brown.cs32.MFTG.gui.Constants.View;
import edu.brown.cs32.MFTG.gui.properties.AggregateRailroadProperty;
import edu.brown.cs32.MFTG.gui.properties.MyRailroadProperty;
import edu.brown.cs32.MFTG.gui.properties.PropertyPanel;
import edu.brown.cs32.MFTG.tournament.data.PropertyDataReport;

public class Railroad extends JPanel {

	private MyRailroadProperty _myProperty;
	private AggregateRailroadProperty _aggregateProperty;
	private HashMap<PropertyPanel, PropertyPanel> _other = new HashMap<>();
	private Railroads _railroad;
	private List<BufferedImage> _deeds = new ArrayList<>();
	private View _view = View.ME;

	public Railroad(Railroads railroad) throws IOException {//throws IOException {
		super();
		_railroad = railroad;
		this.setLayout(new GridLayout(1,1, 0, 0));
		this.addMouseListener(new ButtonMouseListener());

		BufferedImage im;
		im = ImageIO.read(new File(railroad.getDeed()));
		_deeds.add(im);


		/* Set the dimensions */
		if(railroad.getOrientation() == Orientation.UP || railroad.getOrientation() == Orientation.DOWN) {
			Dimension dimension = new Dimension(Constants.WIDTH, Constants.HEIGHT);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}
		else {
			Dimension dimension = new Dimension(Constants.HEIGHT, Constants.WIDTH);
			this.setPreferredSize(dimension);
			this.setMinimumSize(dimension);
			this.setMaximumSize(dimension);
		}

		_myProperty = new MyRailroadProperty(railroad);
		_aggregateProperty = new AggregateRailroadProperty(railroad);
		_other.put(_myProperty, _aggregateProperty);
		_other.put(_aggregateProperty, _myProperty);
		this.add(_myProperty);
	}

	public String getLowercaseName() {
		return _railroad.getLowercaseName();
	}

	public void setAggregateData (PropertyDataReport data) {
		_myProperty.setData(data);
	}

	public void setMyData (PropertyDataReport propertyDataReport) {
		_aggregateProperty.setData(propertyDataReport);
	}

	public int getValue() {
		return _myProperty.getValue();
	}

	public void update () {
		this.removeAll();
		if(_view == View.ME){
			this.add(_myProperty);
		}
		else {
			this.add(_aggregateProperty);
		}
		this.updateUI();
	}

	private class ButtonMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {
				if(_view == View.ME) {
					_view = View.AGGREGATE;
				}
				else if(_view == View.AGGREGATE) {
					_view = View.ME;
				}
				update();
			}
			if(e.getButton() == MouseEvent.BUTTON3) {
				JFrame frame = new JFrame("Deed Cards");

				Dimension dimension;
				if(_deeds.size() < 4)
					dimension = new Dimension(Constants.DEED_WIDTH + 10,_deeds.size()*Constants.DEED_HEIGHT + 25);
				else 
					dimension = new Dimension(2*Constants.DEED_WIDTH + 10,2*Constants.DEED_HEIGHT + 25);

				frame.setSize(dimension);
				frame.setPreferredSize(dimension);
				frame.setMaximumSize(dimension);
				frame.setMinimumSize(dimension);
				frame.setResizable(false);

				frame.add(new DeedPopup(_deeds));
				frame.pack();
				frame.setVisible(true);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}

	public void setView(View view) {
		if(view == View.ME || view == View.AGGREGATE) {
			_view = view;
			update();
		}
	}

}