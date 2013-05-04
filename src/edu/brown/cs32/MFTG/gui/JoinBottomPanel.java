package edu.brown.cs32.MFTG.gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class JoinBottomPanel extends JPanel {
	private BufferedImage _whiteBack, _foodler;
	private final int BOTTOM_HEIGHT=Constants.FULL_PANEL_HEIGHT*3/5;
	private final int BOTTOM_WIDTH=Constants.FULL_WIDTH*15/16;
	private JTextField _port, _hostName;
	private JLabel _portLabel, _hostLabel;
	
	private int DEFAULT_PORT=3232;
	
	public JoinBottomPanel() {
		super();
		try {
			_whiteBack = Helper.resize(ImageIO.read(new File("images/WhiteBack.png")), BOTTOM_WIDTH, BOTTOM_HEIGHT);
			_foodler = Helper.resize(ImageIO.read(new File("images/foodler.png")), BOTTOM_WIDTH-320, BOTTOM_HEIGHT-180);
			this.setLayout(null);
			setBackground(Constants.CLEAR);
			this.setSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setPreferredSize(new Dimension(_whiteBack.getWidth(),_whiteBack.getHeight()));
			this.setBorder(new EmptyBorder(20,20,20,20));
			

			_port = new JTextField(4);
			_port.setDocument(new NumDocument(4));
			_port.setLocation(400,35);
			Dimension portSize = new Dimension(120,50);
			_port.setSize(portSize);
			_port.setPreferredSize(portSize);
			_port.setFont(new Font("portFont",Font.PLAIN,40));
			_port.setText(DEFAULT_PORT + "");
			_hostName = new JTextField(4);
			Dimension hostNameSize = new Dimension(200,40);
			_hostName.setSize(hostNameSize);
			_hostName.setPreferredSize(hostNameSize);
			_hostName.setLocation(400,90);
			_hostName.setFont(new Font("hostFont",Font.PLAIN,30));
			_hostName.setText("localhost");

			_portLabel= new JLabel("Port: ");
			_portLabel.setFont(new Font("portLabelFont",Font.PLAIN,40));
			Dimension portLabelSize = new Dimension(200,50);
			_portLabel.setSize(portLabelSize);
			_portLabel.setPreferredSize(portLabelSize);
			_portLabel.setLocation(270,35);
			_hostLabel= new JLabel("Host Name: ");
			_hostLabel.setFont(new Font("hostLabelFont",Font.PLAIN,30));
			Dimension hostLabelSize = new Dimension(300,40);

			_hostLabel.setSize(hostLabelSize);
			_hostLabel.setPreferredSize(hostLabelSize);
			_hostLabel.setLocation(185,90);
			add(_portLabel);
			add(_port);
			add(_hostLabel);
			add(_hostName);
			

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
		brush.drawImage(_foodler, 20, 120, null);

	}

	/**
	 * 
	 * @return hostname
	 */
	public String getHost() {
		return _hostName.getText().equals("")? "localhost" : _hostName.getText();
	}
	
	/**
	 * 
	 * @return port number
	 */
	public int getPort() {
		return _port.getText().equals("") ? DEFAULT_PORT : Integer.parseInt(_port.getText());
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
	

}
