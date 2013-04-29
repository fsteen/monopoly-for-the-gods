package edu.brown.cs32.MFTG.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.brown.cs32.MFTG.networking.ProfileManager;
import edu.brown.cs32.MFTG.tournament.Profile;

@SuppressWarnings("serial")
public class ProfileScrollPane extends JScrollPane{
	ProfileManager _profileManager;
	DefaultListModel<String> _listModel;
	JList<String> _profileList;

	public ProfileScrollPane(JList<String> profileList, ListModel<String> listModel) {
		super(profileList);
		_profileList = profileList;
		listModel = _listModel; 
	}
                                                        
	void setup(){
		_profileManager = new ProfileManager();
		_profileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		_profileList.setSelectedIndex(0);
		_profileList.addListSelectionListener(new ListListener());
		_profileList.setVisibleRowCount(_profileManager.numProfiles());
		
		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "completeAction" );
		_profileList.getActionMap().put( "completeAction", new CompleteAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "DOWN" ), "doUpAction" );
		_profileList.getActionMap().put( "doUpAction", new UpAction());

		_profileList.getInputMap().put( KeyStroke.getKeyStroke( "UP" ), "doDownAction" );
		_profileList.getActionMap().put( "doDownAction", new DownAction());
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
