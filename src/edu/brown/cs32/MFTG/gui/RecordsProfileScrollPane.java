package edu.brown.cs32.MFTG.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class RecordsProfileScrollPane extends ProfileScrollPane {
	private JList<String> _profileList;
	private RecordsSidePanel _side;
	private MonopolyGui _main;

	public RecordsProfileScrollPane(JList<String> profileList,
			DefaultListModel<String> listModel, MonopolyGui main, RecordsSidePanel side) {
		super(profileList, listModel, main);
		_profileList=profileList;
		_side=side;
		_main=main;
		_profileList.addMouseListener(new myMouseAdapter());
		_side.setRecords(_main.getRecord(_profileList.getSelectedValue()));
	}

	/**
	 * partially overrides the add method to add a create new profile option as well
	 */
	@Override
	protected void addProfileNames() {
		super.addProfileNames();
	}

	/**
	 * processes a click on the screen
	 */
	@Override
	public void processClick() {

	}

	private class myMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			 processPress();
		}
	}

	@Override
	public void processPress() {
		_side.setRecords(_main.getRecord(_profileList.getSelectedValue()));
		
	}
}
