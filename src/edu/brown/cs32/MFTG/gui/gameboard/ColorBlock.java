package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.gui.Constants.Colors;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.House;

public class ColorBlock extends JPanel {

	private Color _color;
	private Orientation _orientation;
	private Rectangle _spaceBackground;
	private Rectangle _spaceOutline;
	private Dimension _dimension;
	private boolean _checkFont = false;
	private Colors _colorInfo;
	
	/* Monopoly setting information */
	private BufferedImage _twoHousesPic;
	private BufferedImage _monopolyPic;
	private BufferedImage _noMonopolyPic;
	private JFormattedTextField _twoField;
	private JFormattedTextField _monopolyField;
	private JFormattedTextField _noMonopolyField;
	private double _sameColorEffect = 1;
	private double _monopolyValue = 1;
	private double _breakMonopolyValue = 1;

	
	/* House setting information */
	private House _house;
	private int _houseWidth = 80;
	private int _houseHeight = 80;
	private JFormattedTextField _houseField;
	private double _houseValue = 1;
	private String _name;
	
	public ColorBlock(Colors color) throws IOException {
		this.setLayout(null);
		_colorInfo = color;
		_orientation = color.getOrientation();
		_color = color.getColor();
		_name = color.getName();
		
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_dimension = new Dimension(4*Constants.WIDTH, Constants.HEIGHT);
			this.setPreferredSize(_dimension);
			this.setMaximumSize(_dimension);
			this.setMinimumSize(_dimension);
		} else {
			_dimension = new Dimension(Constants.HEIGHT, 4*Constants.WIDTH);
			this.setPreferredSize(_dimension);
			this.setMaximumSize(_dimension);
			this.setMinimumSize(_dimension);
		}
		
		initializeImages();
		initializeInputFields();
		initializeHouse();
		initializeBackground();
		
		repaint();
	}
	
	public String getName() {
		return _name;
	}
	
	public void initializeHouse() {
		
		_houseField = new JFormattedTextField(NumberFormat.getInstance());
		_houseField.setValue(new Double(_houseValue));
		_houseField.setFont(Constants.FONT);
		_houseField.setForeground(Color.RED);
		_houseField.addPropertyChangeListener(new ValueChangeListener(_houseField));
		
		_houseField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_houseField.setOpaque(false);
		_houseField.setHorizontalAlignment(JTextField.CENTER);
		
		_houseField.setSize((int) (.5*_houseWidth), 40);
		
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_house = new House(_houseWidth, _houseHeight, 180, (int) (_dimension.getHeight()/2 - _houseHeight/2));
			_houseField.setLocation((int) (180 + _houseWidth/2 - _houseField.getWidth()/2), (int) (_dimension.getHeight()/2));
		}
		else {
			_house = new House(_houseWidth, _houseHeight, (int) (_dimension.getWidth()/2 - _houseWidth/2), 180);
			_houseField.setLocation((int) (_dimension.getWidth()/2 - _houseField.getWidth()/2), (180 + _houseHeight/2));
		}
		
		
		this.add(_houseField);
	}
	
	public void initializeInputFields () {
		_twoField = new JFormattedTextField(NumberFormat.getInstance());
		_twoField.setValue(new Double(1));
		_twoField.setFont(Constants.FONT);
		_twoField.setForeground(Color.RED);
		_twoField.addPropertyChangeListener(new ValueChangeListener(_twoField));

		_twoField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_twoField.setOpaque(false);
		_twoField.setHorizontalAlignment(JTextField.CENTER);

		this.add(_twoField);

		_monopolyField = new JFormattedTextField(NumberFormat.getInstance());
		_monopolyField.setValue(new Double(1));
		_monopolyField.setFont(Constants.FONT);
		_monopolyField.setForeground(Color.RED);
		_monopolyField.addPropertyChangeListener(new ValueChangeListener(_monopolyField));

		_monopolyField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_monopolyField.setOpaque(false);
		_monopolyField.setHorizontalAlignment(JTextField.CENTER);

		this.add(_monopolyField);

		_noMonopolyField = new JFormattedTextField(NumberFormat.getInstance());
		_noMonopolyField.setValue(new Double(1));
		_noMonopolyField.setFont(Constants.FONT);
		_noMonopolyField.setForeground(Color.RED);
		_noMonopolyField.addPropertyChangeListener(new ValueChangeListener(_noMonopolyField));

		_noMonopolyField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_noMonopolyField.setOpaque(false);
		_noMonopolyField.setHorizontalAlignment(JTextField.CENTER);

		this.add(_noMonopolyField);
		
		/* Set the location of the objects based on orientation */
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_twoField.setSize(40, 40);
			_twoField.setLocation(20, (int) (_dimension.getHeight()/2));
			
			_monopolyField.setSize(40, 40);
			_monopolyField.setLocation(70, (int) (_dimension.getHeight()/2));
			
			_noMonopolyField.setSize(40, 40);
			_noMonopolyField.setLocation(120, (int) (_dimension.getHeight()/2));	
		}
		else {
			_twoField.setSize(40, 40);
			_twoField.setLocation(65, 20);
			
			_monopolyField.setSize(40, 40);
			_monopolyField.setLocation(65, 70);
			
			_noMonopolyField.setSize(40, 40);
			_noMonopolyField.setLocation(65, 120);	
		}
	}
	
	public void initializeImages () throws IOException {
		_twoHousesPic = ImageIO.read(new File(_colorInfo.getTwo()));
		_twoHousesPic = Helper.resize(_twoHousesPic, 40, 40);

		_monopolyPic = ImageIO.read(new File(_colorInfo.getMonopoly()));
		_monopolyPic = Helper.resize(_monopolyPic, 40, 40);

		_noMonopolyPic = ImageIO.read(new File(_colorInfo.getNoMonopoly()));
		_noMonopolyPic = Helper.resize(_noMonopolyPic, 40, 40);
	}
	
	public void initializeBackground () {
		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(_dimension);
		_spaceBackground.setLocation(0, 0);
		
		/* Make the box outline */
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		_spaceOutline.setSize((int) (_dimension.getWidth()-Constants.BORDER), (int) (_dimension.getHeight() - Constants.BORDER));
	}
	
	private class ValueChangeListener implements PropertyChangeListener {
		private JFormattedTextField _field;
		
		public ValueChangeListener (JFormattedTextField field) {
			_field = field;
		}
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			try {
				_field.commitEdit();
				if(_field.equals(_twoField)){
					_sameColorEffect = ((Number)_field.getValue()).doubleValue();
				} else if (_field.equals(_monopolyField)) {
					_monopolyValue = ((Number)_field.getValue()).doubleValue();
				} else if (_field.equals(_noMonopolyField)) {
					_breakMonopolyValue = ((Number)_field.getValue()).doubleValue();
				} else if(_field.equals(_houseField)) {
					_houseValue = ((Number)_field.getValue()).intValue();
				}
				_checkFont = true;
			} catch (ParseException e) {
				System.err.println("Invalid Text Entry");
			}
		}
	}
	
	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		/* If we have a value field that has been edited */
		if(_checkFont) {
			_checkFont = false;
			
			int size = Constants.FONT_SIZE;
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_sameColorEffect)) > _twoField.getWidth() - _twoField.getWidth()/10) {
					size = size -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			}
			_twoField.setFont(font);
			
			size = Constants.FONT_SIZE;
			font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_monopolyValue)) > _monopolyField.getWidth() - _monopolyField.getWidth()/10) {
					size = size -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			}
			_monopolyField.setFont(font);
			
			size = Constants.FONT_SIZE;
			font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_breakMonopolyValue)) > _noMonopolyField.getWidth() - _noMonopolyField.getWidth()/10) {
					size = size -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			}
			_noMonopolyField.setFont(font);
			
			size = Constants.FONT_SIZE;
			font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_houseValue)) > _houseField.getWidth() - _houseField.getWidth()/10) {
					size = size -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			}
			_houseField.setFont(font);
		}
		
		g2.setColor(_color);
		g2.fill(_spaceBackground);
		
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			g2.drawImage(_twoHousesPic, 20, 20, null);
			g2.drawImage(_monopolyPic, 70, 20, null);
			g2.drawImage(_noMonopolyPic, 120, 20, null);
		}
		else {
			g2.drawImage(_twoHousesPic, 20, 20, null);
			g2.drawImage(_monopolyPic, 20, 70, null);
			g2.drawImage(_noMonopolyPic, 20, 120, null);
		}
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);
		
		_house.paint(g2);
	}

	public Double[] getColorValues() {
		Double[] colorValues = new Double[4];
		colorValues[0] = _monopolyValue;
		colorValues[1] = _houseValue;
		colorValues[2] = _breakMonopolyValue;
		colorValues[3] = _sameColorEffect;
		return colorValues;
	}
}
