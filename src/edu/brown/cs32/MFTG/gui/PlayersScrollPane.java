package edu.brown.cs32.MFTG.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PlayersScrollPane extends ProfileScrollPane {
	private String _startingProfile;
	public PlayersScrollPane(JList<String> profileList,
			DefaultListModel<String> listModel, MonopolyGui main, String startingProfile) {
		super(profileList, listModel, main);
		_startingProfile=startingProfile;
	}
	
	/**
	 * partially overrides the add method to add a create new profile option as well
	 */
	@Override
	protected void addProfileNames() {
		addPlayerNames(_startingProfile);
	}
	
	void addPlayerNames(String profile) {
		_listModel.clear();
		if(profile.equals("Create New Profile")) {
			return;
		}
		for(String s: _main.getPlayerNames(profile)){
			_listModel.addElement(s);
		}
		_listModel.addElement("Create New Player");
	}
	
	/**
	 * processes a click on the screen
	 */
	@Override
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

}
