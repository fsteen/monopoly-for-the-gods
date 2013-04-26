package edu.brown.cs32.MFTG.gui.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.spaces.House;

public class HousePrice extends JPanel {

	private House _house;
	private JFormattedTextField _valueField;
	private double _value;
	private int _valueFont = Constants.FONT_SIZE;
	private boolean _checkFont = false;
	private Rectangle _spaceBackground;
	
	private int _houseWidth = 80;
	private int _houseHeight = 80;

	
	public HousePrice(Orientation orientation) {
		this.setLayout(null);
		
		if(orientation == Orientation.UP || orientation == Orientation.DOWN) {
			this.setSize(2*Constants.WIDTH, Constants.HEIGHT);
			this.setLocation(2*Constants.WIDTH, 0);
		} else {
			this.setSize(Constants.HEIGHT, 2*Constants.WIDTH);
			this.setLocation(0,2*Constants.WIDTH);
		}
		_house = new House(_houseWidth, _houseHeight, (int) (this.getWidth()/2 - (_houseWidth/2)), (int) (this.getHeight()/2 - (_houseHeight/2)));
		//_house = new House((int) (this.getWidth()), (int) (.75 * this.getHeight()), (int) (this.getWidth()/2 - (this.getWidth()/2)), (int) (this.getHeight()/2 - (.75*this.getHeight()/2)));
		
		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(this.getWidth(), this.getHeight());
		_spaceBackground.setLocation(0, 0);
		
		initializeValueField();
	}
	
	public void initializeValueField () {
		_valueField = new JFormattedTextField(NumberFormat.getInstance());
		_valueField.setValue(new Double(_value));
		_valueField.setFont(Constants.FONT);
		_valueField.setForeground(Color.RED);
		_valueField.addPropertyChangeListener(new ValueChangeListener());
		
		_valueField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_valueField.setOpaque(false);
		_valueField.setHorizontalAlignment(JTextField.CENTER);
		
		_valueField.setSize((int) (.5*_houseWidth), 40);
		_valueField.setLocation((int) ((this.getWidth()/2) - _valueField.getWidth()/2), this.getHeight()/2);
		this.add(_valueField);
	}
	
	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_spaceBackground);
		_house.paint(g2);
	
		if(_checkFont) {
			_checkFont = false;
			_valueFont = Constants.FONT_SIZE;
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, _valueFont);
			while(g2.getFontMetrics(font).stringWidth(Double.toString(_value)) > _valueField.getWidth() - _valueField.getWidth()/10) {
					System.out.println("decrease");
					_valueFont = _valueFont -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, _valueFont);
			}
			_valueField.setFont(font);
		}
	}
	
	private class ValueChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			try {
				_valueField.commitEdit();
				_value = ((Number)_valueField.getValue()).intValue();
				_checkFont = true;
			} catch (ParseException e) {
				System.err.println("Invalid Text Entry");
			}
		}
	}
}
