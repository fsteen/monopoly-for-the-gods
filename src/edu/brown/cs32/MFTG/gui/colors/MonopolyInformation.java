package edu.brown.cs32.MFTG.gui.colors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Helper;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Viewer;

public class MonopolyInformation extends JPanel{

	private Rectangle _spaceBackground;
	private JLabel _timesPrice;
	private BufferedImage _twoHouses;
	private BufferedImage _monopoly;
	private BufferedImage _noMonopoly;

	private JFormattedTextField _twoField;
	private JFormattedTextField _monopolyField;
	private JFormattedTextField _noMonopolyField;
	
	private double _twoValue;
	private double _monopolyValue;
	private double _noMonopolyValue;
	
	private boolean _checkFont = false;


	public MonopolyInformation(Orientation orientation) throws IOException {
		this.setLayout(null);
		
		if(orientation == Orientation.UP || orientation == Orientation.DOWN) {
			this.setSize(2*Constants.WIDTH, Constants.HEIGHT);
		} else {
			this.setSize(Constants.HEIGHT, 2*Constants.WIDTH);
		}

		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(this.getWidth(), this.getHeight());
		_spaceBackground.setLocation(0, 0);

		initializeImages();
		initializeTextField();
		initializeInputFields();
	}

	protected void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		/* If we have a value field that has been edited */
		if(_checkFont) {
			_checkFont = false;
			
			int size = Constants.FONT_SIZE;
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			while(g.getFontMetrics(font).stringWidth(Double.toString(_twoValue)) > _twoField.getWidth() - _twoField.getWidth()/10) {
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
			while(g.getFontMetrics(font).stringWidth(Double.toString(_noMonopolyValue)) > _noMonopolyField.getWidth() - _noMonopolyField.getWidth()/10) {
					size = size -1;
					font = new Font(Font.SANS_SERIF, Font.BOLD, size);
			}
			_noMonopolyField.setFont(font);
		}
		
		
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_spaceBackground);
		g2.drawImage(_twoHouses, 5, this.getHeight()/6 - 16, null);
		g2.drawImage(_monopoly, 5, this.getHeight()/2 - 18, null);
		g2.drawImage(_noMonopoly, 5, (int) (5.*this.getHeight()/6 - 18), null);
	}

	public void initializeImages () throws IOException {
		_twoHouses = ImageIO.read(new File("Deed_Cards/two.jpg"));
		_twoHouses = Helper.resize(_twoHouses, 36, 36);

		_monopoly = ImageIO.read(new File("Deed_Cards/monopoly.jpg"));
		_monopoly = Helper.resize(_monopoly, 36, 36);

		_noMonopoly = ImageIO.read(new File("Deed_Cards/no_monopoly.jpg"));
		_noMonopoly = Helper.resize(_noMonopoly, 36, 36);
	}

	public void initializeTextField () {
		_timesPrice = new JLabel() {
			protected void paintComponent (Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(-Math.PI/2, this.getWidth()/2, this.getHeight()/2);
				super.paintComponent(g2);
				g2.rotate(Math.PI/2, this.getWidth()/2, this.getHeight()/2);
			}
		};
		_timesPrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		_timesPrice.setForeground(Color.BLACK);
		_timesPrice.setHorizontalAlignment(JTextField.CENTER);
		_timesPrice.setVerticalAlignment(JTextField.BOTTOM);

		_timesPrice.setSize((int) (this.getHeight()), (int) (.75*this.getWidth()));
		_timesPrice.setLocation((int) (this.getHeight()/2 - _timesPrice.getWidth()/2), (int) (this.getWidth()/2 - _timesPrice.getHeight()/2));
		//_timesPrice.setLocation((int) (.75*this.getWidth()), (int) (.5*this.getHeight()));

		_timesPrice.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_timesPrice.setOpaque(false);
		_timesPrice.setText("x $Value");
		this.add(_timesPrice);
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

		_twoField.setSize(35, 40);
		_twoField.setLocation(42, this.getHeight()/6 - _twoField.getHeight()/2);
		this.add(_twoField);

		_monopolyField = new JFormattedTextField(NumberFormat.getInstance());
		_monopolyField.setValue(new Double(1));
		_monopolyField.setFont(Constants.FONT);
		_monopolyField.setForeground(Color.RED);
		_monopolyField.addPropertyChangeListener(new ValueChangeListener(_monopolyField));

		_monopolyField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_monopolyField.setOpaque(false);
		_monopolyField.setHorizontalAlignment(JTextField.CENTER);

		_monopolyField.setSize(35, 40);
		_monopolyField.setLocation(42, this.getHeight()/2 - _monopolyField.getHeight()/2);
		this.add(_monopolyField);

		_noMonopolyField = new JFormattedTextField(NumberFormat.getInstance());
		_noMonopolyField.setValue(new Double(1));
		_noMonopolyField.setFont(Constants.FONT);
		_noMonopolyField.setForeground(Color.RED);
		_noMonopolyField.addPropertyChangeListener(new ValueChangeListener(_noMonopolyField));

		_noMonopolyField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_noMonopolyField.setOpaque(false);
		_noMonopolyField.setHorizontalAlignment(JTextField.CENTER);

		_noMonopolyField.setSize(35, 40);
		_noMonopolyField.setLocation(42, (int) (5.*this.getHeight()/6 - _noMonopolyField.getHeight()/2));
		this.add(_noMonopolyField);
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
					_twoValue = ((Number)_field.getValue()).doubleValue();
				} else if (_field.equals(_monopolyField)) {
					_monopolyValue = ((Number)_field.getValue()).doubleValue();
				} else if (_field.equals(_noMonopolyField)) {
					_noMonopolyValue = ((Number)_field.getValue()).doubleValue();
				}
				_checkFont = true;
			} catch (ParseException e) {
				System.err.println("Invalid Text Entry");
			}
		}
	}

}
