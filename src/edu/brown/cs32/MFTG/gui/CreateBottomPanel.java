package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CreateBottomPanel extends JPanel {
	private BufferedImage _whiteBack;
	private final int BOTTOM_HEIGHT=Constants.FULL_HEIGHT*3/5;
	private final int BOTTOM_WIDTH=Constants.FULL_WIDTH*15/16;
	private JTextField _port, _timeBegin, _timeBetween, _numSets, _numGames;
	private JLabel _portLabel,_winCondLabel, _timeBeginLabel, _timeBetweenLabel, _numSetsLabel, _numGamesLabel;
	private JRadioButton _mostMoney, _mostSets, _lastMatch;
	private ButtonGroup _winCond;
	public CreateBottomPanel() {
		super();
		try {
			_whiteBack = Helper.resize(ImageIO.read(new File("images/WhiteBack.png")), BOTTOM_WIDTH, BOTTOM_HEIGHT);
			this.setLayout(new GridLayout(0,1));
			setBackground(Constants.CLEAR);
			this.setSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setPreferredSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setBorder(new EmptyBorder(20,20,20,20));
			JPanel lowerPanel = new JPanel(new GridLayout(2,3));
			lowerPanel.setBackground(Constants.CLEAR);
			
			JPanel portPanel = new JPanel();
			portPanel.setBackground(Constants.CLEAR);
			_port = new JTextField(4);
			_port.setDocument(new MyDocument());
			_port.setSize(200,30);
			_port.setFont(new Font("myFont",Font.PLAIN,40));

			_portLabel= new JLabel("Port: ");
			_portLabel.setFont(new Font("portLabelFont",Font.PLAIN,40));
			_portLabel.setSize(40, 40);
			portPanel.add(_portLabel);
			portPanel.add(_port);
			
			JPanel winCondPanel = new JPanel(new GridLayout(0,1));
			winCondPanel.setAlignmentX(LEFT_ALIGNMENT);
			winCondPanel.setBackground(Constants.CLEAR);
			_winCondLabel = new JLabel("Win Condition:");
			_mostMoney=new JRadioButton("Most Cummulative Wealth");
			_mostSets=new JRadioButton("Most Sets Won", true);
			_lastMatch=new JRadioButton("Win Last Match");

			
			_winCond = new ButtonGroup();
			_winCond.add(_mostSets);
			_winCond.add(_mostMoney);
			_winCond.add(_lastMatch);
			
			winCondPanel.add(_winCondLabel);
			winCondPanel.add(_mostMoney);
			winCondPanel.add(_mostSets);
			winCondPanel.add(_lastMatch);
			
			JPanel timePanel = new JPanel(new GridLayout(2,2));
			timePanel.setAlignmentX(LEFT_ALIGNMENT);
			timePanel.setBackground(Constants.CLEAR);
			_timeBeginLabel=new JLabel("Time At Beginning (min): ");
			_timeBetweenLabel=new JLabel("Time At Beginning (min): ");
			_timeBegin = new JTextField(4);
			_timeBetween= new JTextField(4);
			timePanel.add(_timeBeginLabel);
			timePanel.add(_timeBegin);
			timePanel.add(_timeBetweenLabel);
			timePanel.add(_timeBetween);
			
			JPanel gameLengthPanel = new JPanel(new GridLayout(2,2));
			gameLengthPanel.setBackground(Constants.CLEAR);
			_numSetsLabel=new JLabel("Number of Sets: ");
			_numGamesLabel=new JLabel("Games per Set: ");
			_numSets = new JTextField(4);
			_numGames= new JTextField(4);
			_numSets.setDocument(new MyDocument());
			_numGames.setDocument(new MyDocument());
			gameLengthPanel.add(_numSetsLabel);
			gameLengthPanel.add(_numSets);
			gameLengthPanel.add(_numGamesLabel);
			gameLengthPanel.add(_numGames);
			
			lowerPanel.add(winCondPanel);
			lowerPanel.add(timePanel);
			lowerPanel.add(gameLengthPanel);
			
			add(portPanel);
			add(lowerPanel);
			
			//breaks things
			/*_mostMoney.setBackground(Constants.CLEAR);
			_mostSets.setBackground(Constants.CLEAR);
			_lastMatch.setBackground(Constants.CLEAR);*/
			

		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}
	}
	//@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		brush.drawImage(_whiteBack, 0, 0, null);

	}

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
