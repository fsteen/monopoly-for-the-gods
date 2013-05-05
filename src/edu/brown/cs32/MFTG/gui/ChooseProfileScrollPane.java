package edu.brown.cs32.MFTG.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ChooseProfileScrollPane extends ProfileScrollPane {

	public ChooseProfileScrollPane(JList<String> profileList,
			DefaultListModel<String> listModel, MonopolyGui main) {
		super(profileList, listModel, main);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * partially overrides the add method to add a create new profile option as well
	 */
	@Override
	protected void addProfileNames() {
		super.addProfileNames();
		_listModel.addElement("Create New Profile");
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

	@Override
	public void processPress() {
		//do nothing
	}

}
