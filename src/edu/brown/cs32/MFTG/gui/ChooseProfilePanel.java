package edu.brown.cs32.MFTG.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import edu.brown.cs32.MFTG.mftg.Main;
import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Profile;


@SuppressWarnings("serial")
public class ChooseProfilePanel extends JPanel{

	private ImagePanel _chooseLite, _backLite, _backDark, _selectLite, _selectDark;
	private BufferedImage _background;
	private Point _chooseLoc, _listLoc, _backLoc, _selectLoc;
	private Main _main;

	private ProfileManager _profileManager;
	private JList<String> _profileList;
	private DefaultListModel<String> _listModel;

	private final int BUTTON_HEIGHT=Constants.FULL_HEIGHT/8;
	private final int BUTTON_WIDTH=2*Constants.FULL_HEIGHT/3;
	private final int START_HEIGHT=Constants.FULL_HEIGHT/8;
	private final int START_WIDTH=Constants.FULL_WIDTH/6;

	public ChooseProfilePanel(Main main) {
		try {
			_main=main;
			java.awt.Dimension size = new java.awt.Dimension(Constants.FULL_WIDTH,Constants.FULL_HEIGHT);
			this.setPreferredSize(size);
			this.setSize(size);
			this.setBackground(Color.GRAY);
			this.setLayout(null);
			_background = Helper.resize(ImageIO.read(new File("images/mountain2.png")),this.getWidth(),this.getHeight());

			_chooseLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/JoinGameLite.png")), BUTTON_WIDTH-40, BUTTON_HEIGHT));
			_chooseLoc= new Point(START_WIDTH, START_HEIGHT);
			_chooseLite.setLocation(_chooseLoc);

			_listLoc= new Point(Constants.FULL_WIDTH/32, START_HEIGHT+BUTTON_HEIGHT*6/5);

			_backLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackLite.png")), 100, 50));
			_backDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/BackDark.png")), 100, 50));
			_backLoc= new Point(20, this.getHeight()-_backDark.getHeight()-10);
			_backLite.setLocation(_backLoc);
			_backDark.setLocation(_backLoc);
			_backDark.setVisible(false);

			_selectLite = new ImagePanel(Helper.resize(ImageIO.read(new File("images/GoLite.png")), 100, 50));
			_selectDark = new ImagePanel(Helper.resize(ImageIO.read(new File("images/GoDark.png")), 100, 50));
			_selectLoc= new Point(this.getWidth()-_selectLite.getWidth()-30, this.getHeight()-_selectLite.getHeight()-20);
			_selectLite.setLocation(_selectLoc);
			_selectDark.setLocation(_selectLoc);
			_selectDark.setVisible(false);


			addMouseListener(new MyMouseListener());

			add(_chooseLite);
			add(_backLite);
			add(_backDark);
			add(_selectLite);
			add(_selectDark);

			addProfileList();
		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}

	}

	private void addProfileList(){
		_profileManager = new ProfileManager();
		_listModel = new DefaultListModel<String>();
		_profileList = new JList<String>(_listModel);
		_profileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		_profileList.setSelectedIndex(0);
		_profileList.addListSelectionListener(new ListListener());
		_profileList.setVisibleRowCount(_profileManager.numProfiles());
		JScrollPane listScrollPane = new JScrollPane(_profileList);
		
		// TODO figure out how the FUCK to add shit
		listScrollPane.setLocation(0, 0);

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "completeAction" );
		_profileList.getActionMap().put( "completeAction", new CompleteAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "DOWN" ), "doUpAction" );
		_profileList.getActionMap().put( "doUpAction", new UpAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "UP" ), "doDownAction" );
		_profileList.getActionMap().put( "doDownAction", new DownAction());
	}

//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D brush = (Graphics2D) g;
//		brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		brush.drawImage(_background, 0, 0, null);
//
//	}

	private class MyMouseListener extends MouseInputAdapter{
		public MyMouseListener() {
			super();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_backLite,_backLoc)) {
				_backLite.setVisible(false);
				_backDark.setVisible(true);
				repaint();
			}
			else if(intersects(xloc,yloc,_selectLite,_selectLoc)) {
				_selectLite.setVisible(false);
				_selectDark.setVisible(true);
				repaint();
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			int xloc=e.getX();
			int yloc=e.getY();
			if(intersects(xloc,yloc,_backLite,_backLoc)) {
				if(_backDark.isVisible()) {
					fixPanels();
					_main.switchPanels("greet");
				}
				else {
					fixPanels();
				}

			}
			else if(intersects(xloc,yloc,_selectLite,_selectLoc)) {
				if(_selectDark.isVisible()) {
					fixPanels();
					_main.switchPanels("lobby");
				}
				else {
					fixPanels();
				}

			}
			else {
				fixPanels();
			}
		}

		private void fixPanels() {
			_backDark.setVisible(false);
			_backLite.setVisible(true);
			_selectDark.setVisible(false);
			_selectLite.setVisible(true);
			repaint();
		}
		private boolean intersects(int xloc, int yloc, JPanel img, Point loc) {
			if(xloc>=loc.x && xloc<=loc.x+img.getWidth() && yloc>=loc.y && yloc<=loc.y+img.getHeight()) {
				return true;
			}
			return false;
		}

	}
	/**********************Private inner classes*************************************/
	public class ListListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {

			}
		}
	}

	/**
	 * CompeteAction
	 * Used to map the enter key in the list to complete the
	 * current last word with the word selected in the list of suggestions
	 */
	class CompleteAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (_listModel.size() < 1) {
				return;
			}

			int index = _profileList.getSelectedIndex();

			String profileName;

			if (index < 0) {
				profileName = _listModel.firstElement().toString();
			} else {
				profileName = _listModel.elementAt(index).toString();
			}

			Profile p = _profileManager.getProfile(profileName);

			// TODO use this mofo
		}
	}

	/**
	 * UpAction
	 * Used to map the up key in the list to move the index of
	 * the list up by one.
	 */
	class UpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int index = _profileList.getSelectedIndex() + 1;
			index = index > _listModel.getSize() ? _listModel.getSize() : index;
			_profileList.setSelectedIndex(index);
		}
	}

	/**
	 * DownAction
	 * Used to map the down key in the list to move the index of
	 * the list down by one.
	 */
	class DownAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int index = _profileList.getSelectedIndex() - 1;
			index = index > 0 ? index : 0;
			_profileList.setSelectedIndex(index);
		}
	}	
}

