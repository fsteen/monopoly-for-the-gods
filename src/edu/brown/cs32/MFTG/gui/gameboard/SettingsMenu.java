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
		
		JButton delete = new JButton("Delete Player");
		delete.addActionListener(new DeleteListener());
		
		add(Box.createHorizontalGlue());
		
		JButton back = new JButton("Back");
		back.addActionListener(new BackListener());
		back.setHorizontalAlignment(SwingConstants.RIGHT);
		add(back);
	}
	
	protected class BackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				_main.switchPanels("settings");
		}
			
	}
	
	protected class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_currentPlayer ==null) {
				int remove=JOptionPane.showConfirmDialog(_main, "Are you sure you want to delete this new player and start over?");
				if(remove!=0) {
					return;
				}
				_board.setHeuristics(new Player(-1));		
			}
			else {
				int remove=JOptionPane.showConfirmDialog(_main, "Are you sure you want to remove the player \""+_currentPlayer+"\"?\nThis action cannot be reversed.");
				if(remove!=0) {
					return;
				}
				_profile.removePlayer(_currentPlayer);
				for(int i = 0; i<_playerItems.size(); i++) {
					JMenuItem item = _playerItems.get(i);
					if(item.getText().equals(_currentPlayer)) {
						_players.remove(i);
						break;
					}
				}
				_players.setText(_playerItems.get(0).getText());
			}
			_main.saveProfiles();

		}
			
	}


}
