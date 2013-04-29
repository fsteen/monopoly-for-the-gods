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
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import edu.brown.cs32.MFTG.monopoly.GamePlayer;
import edu.brown.cs32.MFTG.monopoly.Player;

public class JoinBottomPanel extends JPanel {
	private BufferedImage _whiteBack;
	private final int BOTTOM_HEIGHT=Constants.FULL_HEIGHT*3/5;
	private final int BOTTOM_WIDTH=Constants.FULL_WIDTH*15/16;
	private JPanel _fpPanel;
	private JTextField _port;
	private JLabel _portLabel;
	public JoinBottomPanel() {
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
			
			add(portPanel, BorderLayout.NORTH);


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
		/**
		 * a document to restrict what numbers can be put in a textfield
		 */
		private static final long serialVersionUID = 1L;
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
	
	private class PlayerListener extends MouseInputAdapter{
		List<ImagePanel> _imgs = new ArrayList<>();
		Integer _curr;
		public PlayerListener(Integer curr, ImagePanel ...imgs) {
			super();
			_imgs=Arrays.asList(imgs);
			_curr=curr;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int newCurr=(_curr+1)%_imgs.size();
			_imgs.get(newCurr).setVisible(true);
			_imgs.get(_curr).setVisible(false);
			_curr=newCurr;
		}
		
	}

}
