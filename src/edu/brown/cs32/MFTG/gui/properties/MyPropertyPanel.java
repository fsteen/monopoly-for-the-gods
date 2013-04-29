package edu.brown.cs32.MFTG.gui.properties;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;

public abstract class MyPropertyPanel extends NonstaticProperty{

	private JFormattedTextField _valueField;
	private int _value;
	
	/* Font sizes */
	private int _valueFont = Constants.FONT_SIZE;
	private int _profitFont = Constants.FONT_SIZE;
	private FontRenderContext _fontRenderContext;
	private boolean _checkFont = false;
	
	public MyPropertyPanel(Properties myProperty) {
		super(myProperty);
		initializeValueField();
	}
	
	protected void moveValueField (double x, double y) {
		_valueField.setLocation((int) x, (int) y);
	}
	
	public void initializeValueField () {
		_valueField = new JFormattedTextField(NumberFormat.getInstance());
		_valueField.setValue(new Integer(_value));
		_valueField.setFont(Constants.FONT);
		_valueField.setForeground(Color.RED);
		
		_valueField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_valueField.setOpaque(false);
		_valueField.setHorizontalAlignment(JTextField.CENTER);
		
		
		if(_orientation == Orientation.DOWN) {
			_valueField.setSize(_orientation.getWidth(), 40);
			_valueField.setLocation(0, _orientation.getHeight()/2 + 10);
		}
		else if(_orientation == Orientation.UP) {
			_valueField.setSize(_orientation.getWidth(), 40);
			_valueField.setLocation(0, _orientation.getHeight()/2 - 30);
		}
		else if (_orientation == Orientation.LEFT){
			_valueField.setSize((int) (_orientation.getWidth()/2), 40);
			_valueField.setLocation(_orientation.getWidth()/16, _orientation.getHeight()/2 - 10);
		}
		else {
			_valueField.setSize((int) (_orientation.getWidth()/2), 40);
			_valueField.setLocation(_orientation.getWidth()/2 - _orientation.getWidth()/16, _orientation.getHeight()/2 - 10);
		}
		
		this.add(_valueField);
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

	public void checkFont (Graphics g) {
		/* If we have a value field that has been edited */
		if(_checkFont) {
			_checkFont = false;
			_valueFont = Constants.FONT_SIZE;
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, _valueFont);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_value)) > _valueField.getWidth() - _valueField.getWidth()/10) {
					System.out.println("decrease");
					_valueFont = _valueFont -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, _valueFont);
			}
			
			_valueField.setFont(font);
		}
	}
	
	public void paintComponent (Graphics g, BufferedImage picture, Color color) {
		checkFont(g);
		super.paintComponent(g, picture, color, Color.BLACK);
	}
	
	public int getValue() {
		return _value;
	}

}
