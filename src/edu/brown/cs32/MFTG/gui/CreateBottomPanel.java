package edu.brown.cs32.MFTG.gui;

import java.awt.BorderLayout;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
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
	private JPanel _extraPanel, _fpPanel;
	private JTextField _port, _timeBegin, _timeBetween, _numSets, _numGames, _freeParkingJackpot;
	private JLabel _portLabel,_winCondLabel, _timeBeginLabel, _timeBetweenLabel, _numSetsLabel, _numGamesLabel, _gameFlowLabel, _extraLabel, _fpLabel;
	private JRadioButton _mostMoney, _mostSets, _lastMatch, _chooseTogether, _rotateChoosing;
	private JCheckBox _freeParking, _auctions, _doubleOnGo;
	private ButtonGroup _winCond, _gameFlow;
	public CreateBottomPanel() {
		super();
		try {
			_whiteBack = Helper.resize(ImageIO.read(new File("images/WhiteBack.png")), BOTTOM_WIDTH, BOTTOM_HEIGHT);
			this.setLayout(new BorderLayout());
			setBackground(Constants.CLEAR);
			this.setSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setPreferredSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setBorder(new EmptyBorder(20,20,20,20));
			JPanel lowerPanel = new JPanel(new GridLayout(2,3));
			lowerPanel.setBackground(Color.WHITE);
			
			JPanel portPanel = new JPanel();
			portPanel.setBackground(Color.WHITE);
			Dimension portSize= new Dimension(BOTTOM_WIDTH,100);
			portPanel.setSize(portSize);
			portPanel.setPreferredSize(portSize);
			_port = new JTextField(4);
			_port.setDocument(new NumDocument(4));
			_port.setSize(200,30);
			_port.setFont(new Font("myFont",Font.PLAIN,40));

			_portLabel= new JLabel("Port: ");
			_portLabel.setFont(new Font("portLabelFont",Font.PLAIN,40));
			_portLabel.setSize(40, 40);
			portPanel.add(_portLabel);
			portPanel.add(_port);
			
			JPanel winCondPanel = new JPanel(new GridLayout(0,1));
			winCondPanel.setAlignmentX(LEFT_ALIGNMENT);
			winCondPanel.setBackground(Color.WHITE);
			_winCondLabel = new JLabel("Win Condition:");
			_mostMoney=new JRadioButton("Most Cummulative Wealth");
			_mostSets=new JRadioButton("Most Sets Won", true);
			_lastMatch=new JRadioButton("Win Last Match");
			_mostMoney.setBackground(Color.WHITE);
			_mostSets.setBackground(Color.WHITE);
			_lastMatch.setBackground(Color.WHITE);
			
			_winCond = new ButtonGroup();
			_winCond.add(_mostSets);
			_winCond.add(_mostMoney);
			_winCond.add(_lastMatch);
			
			winCondPanel.add(_winCondLabel);
			winCondPanel.add(_mostMoney);
			winCondPanel.add(_mostSets);
			winCondPanel.add(_lastMatch);
			
			JPanel timePanel = new JPanel(new GridLayout(0,1));
			timePanel.setAlignmentX(LEFT_ALIGNMENT);
			timePanel.setBackground(Color.WHITE);
			_timeBeginLabel=new JLabel("Time At Beginning (min): ");
			_timeBetweenLabel=new JLabel("Time At Beginning (min): ");
			_timeBegin = new JTextField(4);
			_timeBetween= new JTextField(4);
			_timeBegin.setDocument(new NumDocument(1));
			_timeBetween.setDocument(new NumDocument(1));
			//_timeBegin.setFont(new Font("myFont",Font.PLAIN,20));
			//_timeBetween.setFont(new Font("myFont",Font.PLAIN,20));
			timePanel.add(_timeBeginLabel);
			timePanel.add(_timeBegin);
			timePanel.add(_timeBetweenLabel);
			timePanel.add(_timeBetween);
			
			JPanel gameLengthPanel = new JPanel(new GridLayout(0,1));
			gameLengthPanel.setBackground(Color.WHITE);
			_numSetsLabel=new JLabel("Number of Sets: ");
			_numGamesLabel=new JLabel("Games per Set: ");
			_numSets = new JTextField(4);
			_numGames= new JTextField(4);
			_numSets.setDocument(new NumDocument(3));
			_numGames.setDocument(new NumDocument(5));
			gameLengthPanel.add(_numSetsLabel);
			gameLengthPanel.add(_numSets);
			gameLengthPanel.add(_numGamesLabel);
			gameLengthPanel.add(_numGames);
			
			JPanel gameFlowPanel = new JPanel(new GridLayout(0,1));
			gameFlowPanel.setAlignmentX(LEFT_ALIGNMENT);
			gameFlowPanel.setBackground(Color.WHITE);
			_gameFlowLabel = new JLabel("Game Flow:");
			_chooseTogether=new JRadioButton("Choose Heuristics Together", true);
			_rotateChoosing=new JRadioButton("Rotate Choosing Heuristics");
			_chooseTogether.setBackground(Color.WHITE);
			_rotateChoosing.setBackground(Color.WHITE);
			
			_gameFlow = new ButtonGroup();
			_gameFlow.add(_chooseTogether);
			_gameFlow.add(_rotateChoosing);
			
			gameFlowPanel.add(_gameFlowLabel);
			gameFlowPanel.add(_chooseTogether);
			gameFlowPanel.add(_rotateChoosing);
			
			_extraPanel = new JPanel(new GridLayout(0,1));
			_extraPanel.setBackground(Color.WHITE);
			_extraLabel = new JLabel("In-Game Rules:");
			_extraLabel.setBackground(Color.WHITE);
			_fpPanel = new JPanel(new GridLayout(1,0));
			_fpPanel.setBackground(Color.WHITE);
			_freeParking = new JCheckBox("Free Parking", false);
			_freeParking.addItemListener(new FreeParkingListener());
			_fpLabel = new JLabel("Default Jackpot: ");
			_freeParkingJackpot = new JTextField(4);
			_freeParkingJackpot.setDocument(new NumDocument(3));
			_fpPanel.add(_fpLabel);
			_fpPanel.add(_freeParkingJackpot);
			_auctions = new JCheckBox("Auctions", false);
			_doubleOnGo = new JCheckBox("$400 landing on Go", false);
			_extraPanel.add(_extraLabel);
			_extraPanel.add(_auctions);
			_extraPanel.add(_doubleOnGo);
			_extraPanel.add(_freeParking);
			_extraPanel.add(_fpPanel);
			_fpPanel.setVisible(false);
			
			
			
			lowerPanel.add(winCondPanel);
			lowerPanel.add(timePanel);
			lowerPanel.add(gameLengthPanel);
			lowerPanel.add(gameFlowPanel);
			lowerPanel.add(_extraPanel);
			
			JPanel playerChoicePanel = new JPanel( new GridLayout(1,0));
			playerChoicePanel.setBackground(Color.WHITE);
			JPanel p1Panel = new JPanel(new GridLayout(0,1));
			p1Panel.setBackground(Color.WHITE);
			JLabel player1 = new JLabel("Player 1");
			
			p1Panel.add(player1);
			
			JPanel p2Panel = new JPanel(new GridLayout(0,1));
			p2Panel.setBackground(Color.WHITE);
			JLabel player2 = new JLabel("Player 2");
			
			p2Panel.add(player2);
			
			JPanel p3Panel = new JPanel(new GridLayout(0,1));
			p3Panel.setBackground(Color.WHITE);
			JLabel player3 = new JLabel("Player 3");
			
			p3Panel.add(player3);
			
			JPanel p4Panel = new JPanel(new GridLayout(0,1));
			p4Panel.setBackground(Color.WHITE);
			JLabel player4 = new JLabel("Player 4");
			
			p4Panel.add(player4);
			
			playerChoicePanel.add(p1Panel);
			playerChoicePanel.add(p2Panel);
			playerChoicePanel.add(p3Panel);
			playerChoicePanel.add(p4Panel);
			
			add(portPanel, BorderLayout.NORTH);
			add(lowerPanel, BorderLayout.CENTER);
			add(playerChoicePanel, BorderLayout.SOUTH);


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
	private class NumDocument extends PlainDocument{
		private int _maxLen;
		public NumDocument(int maxLen) {
			_maxLen=maxLen;
		}
		
		@Override  
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException  {  
			if (!isInteger(str)) {
				return;
			}
			if (this.getLength() < _maxLen)  {  
				int newLength = this.getLength() + str.length();  
				if (newLength> _maxLen)  
				{  
					str = str.substring(0, _maxLen - this.getLength());  
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
	
	/**
	 * class that gets called when the checkbox changes
	 * @author jschvime
	 *
	 */
	private class FreeParkingListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				_fpPanel.setVisible(true);
			}
			else {
				_fpPanel.setVisible(false);
			}

			
		}
		
	}

}
