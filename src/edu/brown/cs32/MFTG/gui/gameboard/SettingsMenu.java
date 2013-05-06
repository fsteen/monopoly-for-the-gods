package edu.brown.cs32.MFTG.gui.gameboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import edu.brown.cs32.MFTG.gui.MonopolyGui;
import edu.brown.cs32.MFTG.gui.gameboard.InGameMenu.QuitListener;
import edu.brown.cs32.MFTG.monopoly.Player;
import edu.brown.cs32.MFTG.tournament.Profile;

public class SettingsMenu extends BoardMenu {
	private MonopolyGui _main;
	private Board _board;
	public SettingsMenu(Board board, MonopolyGui main, Profile profile) {
		super(board, main, profile);
		_main=main;
		_board=board;
		
				
		JButton back = new JButton("Back");
		back.addActionListener(new BackListener());
		add(back);
		removeAggregateButton();
	}
	
	protected class BackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				_main.switchPanels("settings");
		}
			
	}
	
}
