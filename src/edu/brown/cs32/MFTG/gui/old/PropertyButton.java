package edu.brown.cs32.MFTG.gui.old;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.ColorProperties;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;
import edu.brown.cs32.MFTG.gui.Helper;

public class PropertyButton extends JButton {

	/* My board */
	private Property _property;
	
	/* Game results and Heuristics */
	private double _timeOwned;
	private JLabel _profitField;
	private double _profit;
	private JFormattedTextField _valueField;
	private double _value;
	private double _houses;
	
	/* Space specific information */
	private Viewer _viewer;
	private ColorProperties _propertyInfo;
	private Color _color;
	private Orientation _orientation;
	
	/* Background and border rectangles */
	private Rectangle _spaceBackground;
	private Rectangle _headerOutline;
	private Rectangle _spaceOutline;
	
	/* Percentage owned and profit rectangle */
	private Rectangle _profitBlock;
	private Houses _houseRow;
	
	/* Font sizes */
	private int _valueFont = Constants.FONT_SIZE;
	private boolean _checkFont = false;
	
	public PropertyButton(Property property, ColorProperties propertyInfo, Viewer viewer, double timeOwned, double profit, double value, double houses) {
		/* Initialize the button */
		super();
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(null);
		this.addMouseListener(new ButtonMouseListener());
		
		this.setLocation(0, 0);
		_orientation = propertyInfo.getOrientation();
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
		}
		else {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
		}
		
		/* Set instance variables */
		_property = property;
		_propertyInfo = propertyInfo;
		_color = propertyInfo.getColor();
		_viewer = viewer;
		_timeOwned = timeOwned;
		_profit = profit;
		_value = value;
		_houses = houses;
		
		/* Initialize Components */
		initializeBackground();
		initializeProfitBlock();
		initializeProfitField();
		if(_viewer == Viewer.ME) initializeValueField();
		_houseRow = new Houses(_houses, _color, _timeOwned, _profit);
		
		/* Add text fields */
		this.add(_profitField);
		
		if(_viewer == Viewer.ME) this.add(_valueField);
	}
	
	public void paintComponent(Graphics g) {
		/* Draw to a buffered image which will then be resized and rotated appropriately */
		BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		
		
		/* If we have a value field that has been edited */
		if(_viewer == Viewer.ME && _checkFont) {
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
		
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_spaceBackground);
		
		g2.setColor(Color.WHITE);
		g2.fill(_profitBlock);
		
		Color c = new Color(_color.getRed(), _color.getGreen(), _color.getBlue(), (int) (255*_profit/10000.));
		g2.setColor(c);
		g2.fill(_profitBlock);
		
		_houseRow.paint(g2);
		
		g2.setColor(_viewer.getColor());
		g2.setStroke(new BasicStroke(Constants.BORDER/2));
		g2.draw(_headerOutline);
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);
		
		/* Rotate and resize the image */
		image = Helper.resize(image,  Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT);
		image = Helper.rotate(image, _orientation);
		
		g.drawImage(image, 0, 0, null);
	}
	
	
	public void initializeBackground () {
		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(Constants.WIDTH, Constants.HEIGHT);
		_spaceBackground.setLocation(0, 0);
		
		/* Make the header outline */
		_headerOutline = new Rectangle();
		_headerOutline.setSize(Constants.WIDTH - Constants.BORDER, Constants.HEADER_HEIGHT - Constants.BORDER);
		_headerOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		
		/* Make the box outline */
		_spaceOutline = new Rectangle();
		_spaceOutline.setLocation(Constants.BORDER/2, Constants.BORDER/2);
		_spaceOutline.setSize(Constants.WIDTH-Constants.BORDER, Constants.HEIGHT - Constants.BORDER);
	}
	
	public void initializeProfitBlock () {
		_profitBlock = new Rectangle();
		_profitBlock.setSize(Constants.WIDTH, (int) (Constants.HEIGHT * _timeOwned));
		_profitBlock.setLocation(0, 0);
	}
	
	public void initializeProfitField () {
		_profitField = new JLabel();
		NumberFormat numFormat = NumberFormat.getCurrencyInstance();
		numFormat.setMaximumFractionDigits(0);
		_profitField.setFont(Constants.FONT);
		_profitField.setForeground(Color.BLACK);
		_profitField.setHorizontalAlignment(JTextField.CENTER);
		
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_profitField.setSize(_orientation.getWidth(), 40);
			_profitField.setLocation(0, _orientation.getHeight()/2 - 30);
		}
		else if (_orientation == Orientation.RIGHT) {
			_profitField.setSize((int) (_orientation.getWidth()*(3./4)), 40);
			_profitField.setLocation(_orientation.getWidth()/4, _orientation.getHeight()/2 - 30);
		}
		else {
			_profitField.setSize((int) (_orientation.getWidth()*(3./4)), 40);
			_profitField.setLocation(0, _orientation.getHeight()/2 - 30);
		}
		
			_profitField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_profitField.setOpaque(false);
		_profitField.setText(numFormat.format(_profit));
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
		
		
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			_valueField.setSize(_orientation.getWidth(), 40);
			_valueField.setLocation(0, _orientation.getHeight()/2 - 10);
		}
		else if (_orientation == Orientation.RIGHT){
			_valueField.setSize((int) (_orientation.getWidth()*(3./4)), 40);
			_valueField.setLocation(_orientation.getWidth()/4, _orientation.getHeight()/2 - 10);
		}
		else {
			_valueField.setSize((int) (_orientation.getWidth()*(3./4)), 40);
			_valueField.setLocation(0, _orientation.getHeight()/2 - 10);
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
	
	private class ButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {
				if(_viewer == Viewer.ME) _property.changeViewer(Viewer.ALL);
				else _property.changeViewer(Viewer.ME);
			}
			if(e.getButton() == MouseEvent.BUTTON3) {
				_property.popup();
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
}
