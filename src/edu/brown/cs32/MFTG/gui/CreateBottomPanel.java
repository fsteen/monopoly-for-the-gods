package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CreateBottomPanel extends JPanel {
	private BufferedImage _whiteBack;
	private final int BOTTOM_HEIGHT=Constants.FULL_HEIGHT/2;
	private final int BOTTOM_WIDTH=Constants.FULL_WIDTH*7/8;
	private JTextField _port, _timeBegin, _timeBetween;
	private JLabel _portLabel, _timeBeginLabel, _timeBetweenLabel;
	private JRadioButton _mostMoney, _mostSets, _lastMatch;
	private ButtonGroup _winCond;
	public CreateBottomPanel() {
		super();
		try {
			_whiteBack = Helper.resize(ImageIO.read(new File("images/WhiteBack.png")), BOTTOM_WIDTH, BOTTOM_HEIGHT);
			this.setLayout(new FlowLayout());
			setBackground(new Color(255,0,0,255));
			this.setSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setPreferredSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));

			_port = new JTextField(4);
			_port.setDocument(new MyDocument());
			_port.setSize(200,30);
			_port.setFont(new Font("myFont",Font.PLAIN,40));

			_portLabel= new JLabel("Port: ");
			_portLabel.setSize(40, 40);

			_mostMoney=new JRadioButton("Most Cummulative Wealth");
			_mostSets=new JRadioButton("Most Sets Won");
			_lastMatch=new JRadioButton("Win Last Match");
			_winCond = new ButtonGroup();
			_winCond.add(_mostSets);
			_winCond.add(_mostMoney);
			_winCond.add(_lastMatch);
			_mostSets.setSelected(true);

			_timeBeginLabel=new JLabel("Time At Beginning (min): ");
			_timeBetweenLabel=new JLabel("Time At Beginning (min): ");
			_timeBegin = new JTextField(4);
			_timeBetween= new JTextField(4);

			add(_portLabel);
			add(_port);
			add(_mostMoney);
			add(_mostSets);
			add(_lastMatch);
		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}
	}
	//@Override
	/*public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		brush.drawImage(_whiteBack, 0, 0, null);

	}*/

	/**
	 * This class makes sure that the port can only take in numbers of length 4 or less
	 * @author jschvime
	 *
	 */
	private class MyDocument extends PlainDocument{
		@Override  
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException  {  
			if (!isInteger(str)) {
				return;
			}
			if (this.getLength() < 4)  {  
				int newLength = this.getLength() + str.length();  
				if (newLength> 4)  
				{  
					str = str.substring(0, 4 - this.getLength());  
				}  
				super.insertString(offs, str, a);  
			}  
		}  
		
		/**
		 * checks if string is an integer
		 * @param str
		 * @return if its an integer
		 */
		private boolean isInteger(String str) {
			try{
				int num = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
	}

}
