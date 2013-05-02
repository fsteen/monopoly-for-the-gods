package edu.brown.cs32.MFTG.gui;

import java.io.IOException;

import javax.swing.JMenuBar;

import edu.brown.cs32.MFTG.gui.gameboard.Board;
import edu.brown.cs32.MFTG.gui.gameboard.InGameMenu;
import edu.brown.cs32.MFTG.gui.gameboard.SettingsMenu;

public class SettingsBoard extends Board {
	private MonopolyGui _main;
	private String _profile;
	public SettingsBoard(MonopolyGui main, String profile) throws IOException {
		super(-1, main, profile);
		_main=main;
		_profile=profile;
		
	}
	
	@Override
	public void initializeMenu () {
		JMenuBar menu = new SettingsMenu(this,_main, _profile);
		_main.setJMenuBar(menu);
	}
}
