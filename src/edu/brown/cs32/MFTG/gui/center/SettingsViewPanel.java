package edu.brown.cs32.MFTG.gui.center;

import edu.brown.cs32.MFTG.gui.gameboard.Board;

public class SettingsViewPanel extends ViewPanel{

	public SettingsViewPanel(Board board) {
		super(board);
	}
	
	@Override
	public void addButtons() {
		this.add(_myProperty);
		this.add(_colorGroup);
	}

}
