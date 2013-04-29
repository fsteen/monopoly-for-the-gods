package edu.brown.cs32.MFTG.gui.properties;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.MFTG.gui.Constants;
import edu.brown.cs32.MFTG.gui.Constants.Orientation;
import edu.brown.cs32.MFTG.gui.Constants.Properties;
import edu.brown.cs32.MFTG.gui.spaces.PropertyContainer;
import edu.brown.cs32.MFTG.gui.Helper;

public abstract class PropertyPanel extends JPanel {

	/* Game results and Heuristics */
	protected double _timeOwned;
	protected JLabel _profitField;
	protected double _profit;

	protected PropertyContainer _container;
	protected Orientation _orientation;

	/* Background and border rectangles */
	protected Rectangle _spaceBackground;
	protected Rectangle _spaceOutline;

	/* Percentage owned and profit rectangle */
	protected Rectangle _profitBlock;

	public PropertyPanel(Properties myProperty) {
		/* Initialize the panel */
		super();
		this.setLayout(null);
		this.addMouseListener(new ButtonMouseListener());
		this.setLocation(0, 0);

		_orientation = myProperty.getOrientation();
		if(_orientation == Orientation.UP || _orientation == Orientation.DOWN) {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
			this.setMinimumSize(new Dimension(Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT));
		}
		else {
			this.setPreferredSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
			this.setMaximumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
			this.setMinimumSize(new Dimension(Constants.ACTUAL_HEIGHT, Constants.ACTUAL_WIDTH));
		}
		
		//TODO: take out preassignment
		_timeOwned = .5;
		_profit = 10000;
		
		initializeBackground();
		initializeProfitBlock();
		initializeProfitField();
	}

	public void initializeBackground () {
		/* Background gray rectangle */
		_spaceBackground = new Rectangle();
		_spaceBackground.setSize(Constants.WIDTH, Constants.HEIGHT);
		_spaceBackground.setLocation(0, 0);

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

		if(_orientation == Orientation.UP){
			_profitField.setSize(_orientation.getWidth(), 40);
			_profitField.setLocation(0, _orientation.getHeight()/2 -10);
		}
		else if ( _orientation == Orientation.DOWN) {
			_profitField.setSize(_orientation.getWidth(), 40);
			_profitField.setLocation(0, _orientation.getHeight()/2 - 50);
		}
		else if (_orientation == Orientation.RIGHT) {
			_profitField.setSize((int) (_orientation.getWidth()/2), 40);
			_profitField.setLocation(_orientation.getWidth()/16, _orientation.getHeight()/2 - 30);
		}
		else {
			_profitField.setSize((int) (_orientation.getWidth()/2), 40);
			_profitField.setLocation(_orientation.getWidth()/2 - _orientation.getWidth()/16, _orientation.getHeight()/2 - 30);
		}

		_profitField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
		_profitField.setOpaque(false);
		_profitField.setText(numFormat.format(_profit));
		
		this.add(_profitField);
	}

	public abstract void paintComponent(Graphics g);

	protected void paintComponent (Graphics g, BufferedImage picture, Color color, Color border) {
		super.paintChildren(g);
		
		/* Draw to a buffered image which will then be resized and rotated appropriately */
		BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Painting the background block */
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_spaceBackground);

		Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255*_profit/10000.));
		g2.setColor(c);
		g2.fill(_profitBlock);

		g2.setColor(border);
		g2.setStroke(new BasicStroke(Constants.BORDER/2));
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);

		/* Paint the picture */
		picture = Helper.resize(picture, (int) (Constants.WIDTH*(9./10)), (int) (Constants.HEIGHT/2));
		Image icon = Helper.transparent(picture);
		g2.drawImage(icon, (int) (Constants.WIDTH/20), (int) (Constants.HEIGHT/2), null);
		
		/* Rotate and resize the image */
		image = Helper.resize(image,  Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT);
		image = Helper.rotate(image, _orientation);

		g.drawImage(image, 0, 0, null);
	}
	
	protected void paintComponent (Graphics g, Color color, Color border) {
		super.paintComponent(g);
		
		/* Draw to a buffered image which will then be resized and rotated appropriately */
		BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Painting the background block */
		g2.setColor(Constants.BACKGROUND_COLOR);
		g2.fill(_spaceBackground);

		Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255*_profit/10000.));
		g2.setColor(c);
		g2.fill(_profitBlock);

		g2.setColor(border);
		g2.setStroke(new BasicStroke(Constants.BORDER/2));
		g2.setStroke(new BasicStroke(Constants.BORDER));
		g2.draw(_spaceOutline);

		/* Rotate and resize the image */
		image = Helper.resize(image,  Constants.ACTUAL_WIDTH, Constants.ACTUAL_HEIGHT);
		image = Helper.rotate(image, _orientation);

		g.drawImage(image, 0, 0, null);
	}

	private class ButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {

			}
			if(e.getButton() == MouseEvent.BUTTON3) {

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