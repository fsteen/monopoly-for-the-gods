package edu.brown.cs32.MFTG.gui.center;

import edu.brown.cs32.MFTG.gui.gameboard.Board;

public class SettingsCenter extends Center {

	public SettingsCenter(Board board, int id) {
		super(board, id);
		
	}
	
	@Override
	public void initializeViewPanel() {
		_viewPanel = new SettingsViewPanel(_board);
		this.add(_viewPanel);
	}

}
