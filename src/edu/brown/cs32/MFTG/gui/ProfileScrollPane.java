package edu.brown.cs32.MFTG.gui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Profile;

@SuppressWarnings("serial")
public class ProfileScrollPane extends JScrollPane{
	private DefaultListModel<String> _listModel;
	private JList<String> _profileList;
	private MonopolyGui _main;

	public ProfileScrollPane(JList<String> profileList, DefaultListModel<String> listModel, MonopolyGui main) {
		super(profileList);
		_main=main;
		_profileList = profileList;
		_listModel=listModel; 
		 getViewport().setView(_profileList);
		 
		 _profileList.addMouseListener(new MyClickListener());
		 _profileList.setFont(new Font("itemFont", Font.PLAIN, 30));
		 addProfileNames();
	}
	
	/**
	 * adds profile names to list
	 */
	private void addProfileNames() {
		for(String s: _main.getProfileNames()) {
			_listModel.addElement(s);
		}
		_listModel.addElement("Create New Profile");
	}
                                                        
	void setup(){
		_profileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		_profileList.setSelectedIndex(0);
		_profileList.addListSelectionListener(new ListListener());
		
		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "completeAction" );
		_profileList.getActionMap().put( "completeAction", new CompleteAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "DOWN" ), "doUpAction" );
		_profileList.getActionMap().put( "doUpAction", new UpAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "UP" ), "doDownAction" );
		_profileList.getActionMap().put( "doDownAction", new DownAction());
	}
	
	public void processClick() {
		int index=_profileList.getSelectedIndex();
		if(index==_listModel.size()-1) {
			String newProfile=JOptionPane.showInputDialog("New profile name: ");
			while(true) {
				if(newProfile==null) {
					_profileList.setSelectedIndex(_listModel.size()-1);
					return;
				}
				if(_main.addProfile(newProfile)!=null) {
					break;
				}
				newProfile=JOptionPane.showInputDialog("Please choose an unused name: ");
			}
			_main.addProfile(newProfile);
			_listModel.add(index, newProfile);
			_profileList.clearSelection();
			_profileList.setSelectedIndex(index);
		}
		_main.setCurrentProfile(_listModel.get(index));
		_main.switchPanels("lobby");
	}
	
	/**********************Private inner classes*************************************/
	public class ListListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {

			}
		}
	}

	private class MyClickListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				processClick();
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

			processClick();
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
			index = index >= _listModel.getSize() ? _listModel.getSize()-1 : index;
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
			index = index >= 0 ? index : 0;
			_profileList.setSelectedIndex(index);
		}
	}	
	
}
