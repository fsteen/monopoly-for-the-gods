package edu.brown.cs32.MFTG.gui;

import java.io.IOException;

import javax.swing.JMenuBar;

import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.gui.gameboard.InGameMenu;
import edu.brown.cs32.MFTG.gui.gameboard.SettingsMenu;
import edu.brown.cs32.MFTG.tournament.Profile;

public class SettingsBoard extends Board {
	public SettingsBoard(MonopolyGui main, Profile profile) throws IOException {
		super(-1, main, profile, null);
		super.removeSetHeuristicsButton();
		
		
	}
	
	@Override
	public void initializeMenu () {
		JMenuBar menu = new SettingsMenu(this,_main, _profile);
		_main.setJMenuBar(menu);
	}
}
